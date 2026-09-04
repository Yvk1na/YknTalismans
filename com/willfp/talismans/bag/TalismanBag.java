package com.willfp.talismans.bag;

import com.willfp.eco.core.data.PlayerProfile;
import com.willfp.eco.core.data.ProfileExtensions;
import com.willfp.eco.core.data.keys.PersistentDataKey;
import com.willfp.eco.core.data.keys.PersistentDataKeyType;
import com.willfp.eco.core.integrations.placeholder.PlaceholderManager;
import com.willfp.eco.core.items.Items;
import com.willfp.eco.core.placeholder.PlayerPlaceholder;
import com.willfp.libreforge.DispatcherKt;
import com.willfp.libreforge.HolderProviderKt;
import com.willfp.talismans.TalismansPlugin;
import com.willfp.talismans.TalismansPluginKt;
import com.willfp.talismans.talismans.util.TalismanChecks;
import com.willfp.talismans.talismans.util.TalismanUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachmentInfo;

/** A persistent, paged talisman bag with one talisman per slot. */
public final class TalismanBag implements Listener {
    public static final TalismanBag INSTANCE = new TalismanBag();

    private static final int CONTENT_SIZE = 45;
    private static final int MENU_SIZE = 54;
    private static final int PREVIOUS_PAGE_SLOT = 45;
    private static final int PAGE_INFO_SLOT = 48;
    private static final int CLOSE_SLOT = 49;
    private static final int NEXT_PAGE_SLOT = 53;
    private static final int DEFAULT_CAPACITY = 45;
    private static final int MAX_CAPACITY = 10_000;
    private static final String V2_HEADER = "ykntalismans-bag-v2";

    private static PersistentDataKey<List<String>> legacyKey;
    private static PersistentDataKey<List<String>> key;
    private static PersistentDataKey<List<String>> v2Key;
    private static PersistentDataKey<Integer> capacityKey;
    private static ItemStack blockedItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
    private static String emptySlotData;

    /* Keep the upstream field name for binary/reflection compatibility. */
    private static final Map<UUID, List<ItemStack>> savedItems = new HashMap<>();
    private static final Map<UUID, Integer> savedCapacities = new HashMap<>();
    private static final Set<UUID> unreadableBags = new HashSet<>();
    private static boolean placeholderRegistered;

    private TalismanBag() {
    }

    /** Rebuild configuration-backed state. Called by the plugin reload hook. */
    public synchronized void update$core_plugin() {
        TalismansPlugin plugin = plugin();
        legacyKey = new PersistentDataKey<>(
                plugin.getNamespacedKeyFactory().create("talisman_bag"),
                PersistentDataKeyType.STRING_LIST,
                Collections.emptyList()
        );
        key = new PersistentDataKey<>(
                plugin.getNamespacedKeyFactory().create("bag"),
                PersistentDataKeyType.STRING_LIST,
                Collections.emptyList()
        );
        v2Key = new PersistentDataKey<>(
                plugin.getNamespacedKeyFactory().create("bag_v2"),
                PersistentDataKeyType.STRING_LIST,
                Collections.emptyList()
        );
        capacityKey = new PersistentDataKey<>(
                plugin.getNamespacedKeyFactory().create("bag_slots"),
                PersistentDataKeyType.INT,
                -1
        );
        // AIR cannot be encoded by the 1.21 ItemStack codec. A plain barrier
        // is a valid downgrade-safe sentinel and can never pass the talisman check.
        emptySlotData = Items.toSNBT(new ItemStack(Material.BARRIER));
        if (emptySlotData == null) {
            throw new IllegalStateException("Could not initialize empty talisman bag slots");
        }
        blockedItem = createBlockedItem();
        savedItems.clear();
        savedCapacities.clear();
        unreadableBags.clear();

        if (!placeholderRegistered) {
            PlaceholderManager.registerPlaceholder(new PlayerPlaceholder(
                    plugin,
                    "bagsize",
                    player -> Integer.toString(getBagSize(player))
            ));
            placeholderRegistered = true;
        }
    }

    /** Open the first bag page. */
    public void open(Player player) {
        open(player, 0);
    }

    /** Return only occupied talisman slots for the effect provider. */
    public synchronized List<ItemStack> getTalismans(Player player) {
        int capacity = getBagSize(player);
        List<ItemStack> slots = getSlots(player);
        List<ItemStack> result = new ArrayList<>();
        for (int index = 0; index < Math.min(capacity, slots.size()); index++) {
            ItemStack item = slots.get(index);
            if (isItem(item)) {
                result.add(unit(item));
            }
        }
        return Collections.unmodifiableList(result);
    }

    /** Return the player's persistent slot capacity. */
    public synchronized int getCapacity(Player player) {
        return getBagSize(player);
    }

    /**
     * Add or remove bag slots. A negative delta shrinks the bag; talismans in
     * removed slots are returned to the inventory. Shrinking is refused when
     * the inventory cannot hold every returned talisman.
     */
    public synchronized CapacityChange adjustCapacity(Player player, int delta) {
        int oldCapacity = getBagSize(player);
        long requested = (long) oldCapacity + delta;
        int newCapacity;
        if (delta > 0) {
            newCapacity = oldCapacity >= MAX_CAPACITY
                    ? oldCapacity
                    : (int) Math.min(MAX_CAPACITY, requested);
        } else {
            newCapacity = (int) Math.max(0L, requested);
        }
        if (newCapacity == oldCapacity) {
            return new CapacityChange(oldCapacity, newCapacity, 0);
        }

        List<ItemStack> oldSlots = copySlots(getSlots(player));
        if (unreadableBags.contains(player.getUniqueId())) {
            throw new IllegalStateException("The talisman bag contains unreadable saved data");
        }
        List<ItemStack> retained = new ArrayList<>();
        List<ItemStack> returned = new ArrayList<>();
        if (newCapacity < oldCapacity) {
            for (ItemStack item : oldSlots) {
                if (!isItem(item)) {
                    continue;
                }
                if (retained.size() < newCapacity) {
                    retained.add(unit(item));
                } else {
                    returned.add(unit(item));
                }
            }
        } else {
            retained = copySlots(oldSlots);
        }

        ItemStack[] originalStorage = null;
        ItemStack[] plannedStorage = null;
        if (!returned.isEmpty()) {
            originalStorage = cloneItems(player.getInventory().getStorageContents());
            plannedStorage = planInventoryReturn(
                    originalStorage,
                    returned,
                    player.getInventory().getMaxStackSize()
            );
            if (plannedStorage == null) {
                throw new InsufficientInventorySpaceException(returned.size());
            }
        }

        try {
            writeState(player, newCapacity, retained);
        } catch (RuntimeException failure) {
            rollBackState(player, oldCapacity, oldSlots, failure);
            throw failure;
        }

        if (plannedStorage != null) {
            try {
                player.getInventory().setStorageContents(plannedStorage);
            } catch (RuntimeException failure) {
                rollBackState(player, oldCapacity, oldSlots, failure);
                try {
                    player.getInventory().setStorageContents(originalStorage);
                } catch (RuntimeException rollbackFailure) {
                    failure.addSuppressed(rollbackFailure);
                }
                throw failure;
            }
        }
        refreshTalismans(player);
        playSoundSafely(player, Sound.ENTITY_ITEM_PICKUP, 0.7f, 1.0f);
        return new CapacityChange(oldCapacity, newCapacity, returned.size());
    }

    /** Deposit as many unit talismans from the held stack as free slots allow. */
    public synchronized boolean tryDeposit(Player player, EquipmentSlot hand, ItemStack eventItem) {
        if ((hand != EquipmentSlot.HAND && hand != EquipmentSlot.OFF_HAND) || eventItem == null) {
            return false;
        }

        ItemStack held = player.getInventory().getItem(hand);
        if (!isItem(held)) {
            return false;
        }
        TalismanUtils.INSTANCE.convert(held);
        if (held.getAmount() != eventItem.getAmount()
                || !held.isSimilar(eventItem)
                || TalismanChecks.getTalismanOnItem(held) == null) {
            return false;
        }

        int capacity = getBagSize(player);
        List<ItemStack> before = copySlots(getSlots(player));
        List<ItemStack> after = copySlots(before);
        int amountToStore = Math.min(held.getAmount(), freeSlots(after, capacity));
        if (amountToStore <= 0) {
            return false;
        }

        for (int count = 0; count < amountToStore; count++) {
            setFirstEmpty(after, capacity, unit(held));
        }
        writeSlots(player, after);

        try {
            int remaining = held.getAmount() - amountToStore;
            if (remaining == 0) {
                player.getInventory().setItem(hand, null);
            } else {
                ItemStack remainder = held.clone();
                remainder.setAmount(remaining);
                player.getInventory().setItem(hand, remainder);
            }
        } catch (RuntimeException failure) {
            try {
                writeSlots(player, before);
            } catch (RuntimeException rollbackFailure) {
                failure.addSuppressed(rollbackFailure);
            }
            throw failure;
        }

        refreshTalismans(player);
        playSoundSafely(player, Sound.ENTITY_ITEM_PICKUP, 0.6f, 1.3f);
        return true;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory top = event.getView().getTopInventory();
        if (!(top.getHolder() instanceof BagHolder holder)
                || !(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        int liveCapacity = getBagSize(player);
        if (liveCapacity != holder.capacity) {
            event.setCancelled(true);
            scheduleWhileViewing(player, holder, () -> open(player, holder.page));
            return;
        }

        if (event.getAction() == InventoryAction.COLLECT_TO_CURSOR
                || event.getClick() == ClickType.DOUBLE_CLICK) {
            event.setCancelled(true);
            return;
        }

        int rawSlot = event.getRawSlot();
        if (rawSlot < 0) {
            event.setCancelled(true);
            return;
        }
        if (rawSlot >= MENU_SIZE) {
            if (event.isShiftClick()) {
                event.setCancelled(true);
                depositFromPlayerInventory(event, player, holder);
            }
            return;
        }

        event.setCancelled(true);
        if (rawSlot >= CONTENT_SIZE) {
            handleControlClick(player, holder, rawSlot);
            return;
        }

        int slotIndex = holder.page * CONTENT_SIZE + rawSlot;
        if (slotIndex >= holder.capacity) {
            return;
        }

        if (event.getClick() == ClickType.NUMBER_KEY && event.getHotbarButton() >= 0) {
            swapWithHotbar(player, holder, slotIndex, event.getHotbarButton());
        } else if (event.getClick() == ClickType.SWAP_OFFHAND) {
            swapWithOffhand(player, holder, slotIndex);
        } else if (event.isShiftClick()) {
            withdrawToInventory(player, holder, slotIndex);
        } else if (event.getClick() == ClickType.DROP
                || event.getClick() == ClickType.CONTROL_DROP
                || event.getClick() == ClickType.MIDDLE
                || event.getClick() == ClickType.CREATIVE) {
            // Explicitly deny dropping/cloning from the captive top inventory.
        } else {
            clickWithCursor(player, holder, slotIndex);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getView().getTopInventory().getHolder() instanceof BagHolder)) {
            return;
        }
        for (int rawSlot : event.getRawSlots()) {
            if (rawSlot < MENU_SIZE) {
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public synchronized void onQuit(PlayerQuitEvent event) {
        savedItems.remove(event.getPlayer().getUniqueId());
        savedCapacities.remove(event.getPlayer().getUniqueId());
        unreadableBags.remove(event.getPlayer().getUniqueId());
    }

    private void open(Player player, int requestedPage) {
        int capacity = getBagSize(player);
        int pageCount = pageCount(capacity);
        int page = Math.max(0, Math.min(requestedPage, pageCount - 1));
        BagHolder holder = new BagHolder(page, pageCount, capacity);
        String title = plugin().getConfigYml().getFormattedString("bag.title")
                + ChatColor.GRAY + " (" + (page + 1) + "/" + pageCount + ")";
        Inventory inventory = Bukkit.createInventory(holder, MENU_SIZE, title);
        holder.inventory = inventory;
        render(player, holder);
        player.openInventory(inventory);
    }

    private synchronized void render(Player player, BagHolder holder) {
        List<ItemStack> slots = getSlots(player);
        ItemStack filler = named(Material.GRAY_STAINED_GLASS_PANE, " ");
        for (int slot = 0; slot < MENU_SIZE; slot++) {
            holder.inventory.setItem(slot, slot < CONTENT_SIZE ? null : filler.clone());
        }

        int pageStart = holder.page * CONTENT_SIZE;
        for (int menuSlot = 0; menuSlot < CONTENT_SIZE; menuSlot++) {
            int slotIndex = pageStart + menuSlot;
            if (slotIndex >= holder.capacity) {
                holder.inventory.setItem(menuSlot, blockedItem.clone());
            } else if (slotIndex < slots.size() && isItem(slots.get(slotIndex))) {
                holder.inventory.setItem(menuSlot, unit(slots.get(slotIndex)));
            }
        }

        if (holder.page > 0) {
            holder.inventory.setItem(PREVIOUS_PAGE_SLOT, named(
                    Material.ARROW,
                    "&e上一页",
                    "&7点击查看第 &f" + holder.page + " &7页"
            ));
        }
        holder.inventory.setItem(PAGE_INFO_SLOT, named(
                Material.BOOK,
                "&b护符袋 &7(" + (holder.page + 1) + "/" + holder.pageCount + ")",
                "&7容量: &f" + holder.capacity + " &7格",
                "&7已使用: &f" + occupiedSlots(slots) + " &7格",
                "",
                "&7每格只能放置一个护符"
        ));
        holder.inventory.setItem(CLOSE_SLOT, named(Material.BARRIER, "&c关闭", "&7点击关闭护符袋"));
        if (holder.page + 1 < holder.pageCount) {
            holder.inventory.setItem(NEXT_PAGE_SLOT, named(
                    Material.ARROW,
                    "&e下一页",
                    "&7点击查看第 &f" + (holder.page + 2) + " &7页"
            ));
        }
    }

    private void handleControlClick(Player player, BagHolder holder, int rawSlot) {
        if (rawSlot == PREVIOUS_PAGE_SLOT && holder.page > 0) {
            scheduleWhileViewing(player, holder, () -> open(player, holder.page - 1));
        } else if (rawSlot == NEXT_PAGE_SLOT && holder.page + 1 < holder.pageCount) {
            scheduleWhileViewing(player, holder, () -> open(player, holder.page + 1));
        } else if (rawSlot == CLOSE_SLOT) {
            scheduleWhileViewing(player, holder, player::closeInventory);
        }
    }

    private void scheduleWhileViewing(Player player, BagHolder holder, Runnable action) {
        Bukkit.getScheduler().runTask(plugin(), () -> {
            if (player.isOnline()
                    && player.getOpenInventory().getTopInventory() == holder.inventory) {
                action.run();
            }
        });
    }

    private synchronized void clickWithCursor(Player player, BagHolder holder, int slotIndex) {
        List<ItemStack> before = copySlots(getSlots(player));
        ItemStack stored = getSlot(before, slotIndex);
        ItemStack cursor = player.getItemOnCursor();

        if (!isItem(cursor)) {
            if (!isItem(stored)) {
                return;
            }
            List<ItemStack> after = copySlots(before);
            setSlot(after, slotIndex, null);
            if (commitGuiChange(player, holder, after)) {
                applyPlayerChangeAfterCommit(
                        player,
                        holder,
                        before,
                        () -> player.setItemOnCursor(unit(stored))
                );
            }
            return;
        }

        if (!isTalisman(cursor)) {
            deny(player, "&c护符袋只能放置护符。");
            return;
        }

        if (isItem(stored) && cursor.getAmount() > 1) {
            deny(player, "&c交换护符时，光标上只能有一个护符。");
            return;
        }

        List<ItemStack> after = copySlots(before);
        setSlot(after, slotIndex, unit(cursor));
        if (!commitGuiChange(player, holder, after)) {
            return;
        }

        applyPlayerChangeAfterCommit(player, holder, before, () -> {
            if (isItem(stored)) {
                player.setItemOnCursor(unit(stored));
            } else {
                setCursorAmount(player, cursor, cursor.getAmount() - 1);
            }
        });
    }

    private synchronized void withdrawToInventory(Player player, BagHolder holder, int slotIndex) {
        List<ItemStack> before = copySlots(getSlots(player));
        ItemStack stored = getSlot(before, slotIndex);
        if (!isItem(stored)) {
            return;
        }

        ItemStack returned = unit(stored);
        if (!player.getInventory().addItem(returned.clone()).isEmpty()) {
            deny(player, "&c你的背包已满。");
            return;
        }

        List<ItemStack> after = copySlots(before);
        setSlot(after, slotIndex, null);
        if (!commitGuiChange(player, holder, after)) {
            // The inventory insertion already succeeded; remove one unit again
            // if persistence unexpectedly fails, avoiding duplication.
            player.getInventory().removeItem(returned);
            return;
        }
        finishGuiChange(player, holder);
    }

    private synchronized void depositFromPlayerInventory(
            InventoryClickEvent event,
            Player player,
            BagHolder holder
    ) {
        ItemStack clicked = event.getCurrentItem();
        if (!isItem(clicked) || !isTalisman(clicked)) {
            return;
        }

        List<ItemStack> before = copySlots(getSlots(player));
        List<ItemStack> after = copySlots(before);
        int move = Math.min(clicked.getAmount(), freeSlots(after, holder.capacity));
        if (move <= 0) {
            deny(player, "&c护符袋已满。");
            return;
        }

        for (int count = 0; count < move; count++) {
            setFirstEmpty(after, holder.capacity, unit(clicked));
        }
        if (!commitGuiChange(player, holder, after)) {
            return;
        }
        applyPlayerChangeAfterCommit(
                player,
                holder,
                before,
                () -> setCurrentAmount(event, clicked, clicked.getAmount() - move)
        );
    }

    private synchronized void swapWithHotbar(
            Player player,
            BagHolder holder,
            int slotIndex,
            int hotbarSlot
    ) {
        ItemStack source = player.getInventory().getItem(hotbarSlot);
        swapWithPlayerSlot(player, holder, slotIndex, source,
                item -> player.getInventory().setItem(hotbarSlot, item));
    }

    private synchronized void swapWithOffhand(Player player, BagHolder holder, int slotIndex) {
        ItemStack source = player.getInventory().getItemInOffHand();
        swapWithPlayerSlot(player, holder, slotIndex, source,
                item -> player.getInventory().setItemInOffHand(item));
    }

    private void swapWithPlayerSlot(
            Player player,
            BagHolder holder,
            int slotIndex,
            ItemStack source,
            ItemSetter setter
    ) {
        List<ItemStack> before = copySlots(getSlots(player));
        ItemStack stored = getSlot(before, slotIndex);

        if (isItem(source) && !isTalisman(source)) {
            deny(player, "&c护符袋只能放置护符。");
            return;
        }
        if (isItem(stored) && isItem(source) && source.getAmount() > 1) {
            deny(player, "&c交换护符时，该快捷栏格只能有一个护符。");
            return;
        }
        if (!isItem(stored) && !isItem(source)) {
            return;
        }

        List<ItemStack> after = copySlots(before);
        setSlot(after, slotIndex, isItem(source) ? unit(source) : null);
        if (!commitGuiChange(player, holder, after)) {
            return;
        }

        applyPlayerChangeAfterCommit(player, holder, before, () -> {
            if (isItem(stored)) {
                setter.set(unit(stored));
            } else if (source.getAmount() == 1) {
                setter.set(null);
            } else {
                ItemStack remainder = source.clone();
                remainder.setAmount(source.getAmount() - 1);
                setter.set(remainder);
            }
        });
    }

    private boolean commitGuiChange(Player player, BagHolder holder, List<ItemStack> slots) {
        try {
            writeSlots(player, slots);
            return true;
        } catch (RuntimeException exception) {
            plugin().getLogger().log(Level.SEVERE, "Could not save " + player.getName() + "'s talisman bag", exception);
            deny(player, "&c护符袋保存失败，物品未移动。");
            return false;
        }
    }

    private void applyPlayerChangeAfterCommit(
            Player player,
            BagHolder holder,
            List<ItemStack> previousSlots,
            Runnable playerChange
    ) {
        try {
            playerChange.run();
        } catch (RuntimeException failure) {
            try {
                writeSlots(player, previousSlots);
                refreshTalismans(player);
            } catch (RuntimeException rollbackFailure) {
                failure.addSuppressed(rollbackFailure);
            }
            plugin().getLogger().log(
                    Level.SEVERE,
                    "Could not finish a talisman bag transfer for " + player.getName(),
                    failure
            );
            deny(player, "&c护符移动失败，护符袋已恢复。");
            return;
        }
        finishGuiChange(player, holder);
    }

    private void finishGuiChange(Player player, BagHolder holder) {
        refreshTalismans(player);
        try {
            render(player, holder);
        } catch (RuntimeException exception) {
            // Storage and the player's inventory have already committed. A UI
            // refresh failure must not be reported as a failed item transfer.
            plugin().getLogger().log(
                    Level.WARNING,
                    "Saved " + player.getName() + "'s talisman bag, but could not redraw it",
                    exception
            );
        }
        playSoundSafely(player, Sound.UI_BUTTON_CLICK, 0.35f, 1.25f);
    }

    private synchronized List<ItemStack> getSlots(Player player) {
        ensureLoaded(player);
        return savedItems.get(player.getUniqueId());
    }

    private synchronized int getBagSize(Player player) {
        ensureLoaded(player);
        return savedCapacities.get(player.getUniqueId());
    }

    private synchronized void writeSlots(Player player, List<ItemStack> slots) {
        if (unreadableBags.contains(player.getUniqueId())) {
            throw new IllegalStateException("Refusing to overwrite unreadable talisman bag data");
        }
        writeState(player, getBagSize(player), slots);
    }

    private synchronized void ensureLoaded(Player player) {
        UUID playerId = player.getUniqueId();
        if (savedItems.containsKey(playerId) && savedCapacities.containsKey(playerId)) {
            return;
        }

        PlayerProfile playerProfile = profile(player);
        List<String> v2Data = safeList(playerProfile.read(requireV2Key()));
        boolean hasV2 = !v2Data.isEmpty() && V2_HEADER.equals(v2Data.get(0));
        if (!v2Data.isEmpty() && !hasV2) {
            int configured = plugin().getConfigYml().getInt("bag.size");
            int safeCapacity = Math.min(
                    Math.max(
                            configured > 0 ? configured : DEFAULT_CAPACITY,
                            legacyPermissionCapacity(player)
                    ),
                    MAX_CAPACITY
            );
            savedItems.put(playerId, new ArrayList<>());
            savedCapacities.put(playerId, safeCapacity);
            unreadableBags.add(playerId);
            plugin().getLogger().warning(
                    "Loaded " + player.getName()
                            + "'s talisman bag read-only because its v2 format header is unknown"
            );
            return;
        }
        List<String> legacyData = hasV2
                ? Collections.emptyList()
                : safeList(playerProfile.read(requireLegacyKey()));
        List<String> currentData = hasV2
                ? new ArrayList<>(v2Data.subList(Math.min(2, v2Data.size()), v2Data.size()))
                : safeList(playerProfile.read(requireKey()));

        boolean decodeFailed = false;
        int savedCapacity = -1;
        if (hasV2) {
            try {
                if (v2Data.size() < 2) {
                    throw new NumberFormatException("missing capacity");
                }
                savedCapacity = Integer.parseInt(v2Data.get(1));
                if (savedCapacity < 0) {
                    throw new NumberFormatException("negative capacity");
                }
            } catch (NumberFormatException exception) {
                decodeFailed = true;
            }
        }

        List<ItemStack> loaded = new ArrayList<>();
        for (String lookup : legacyData) {
            try {
                appendNormalized(loaded, Items.lookup(lookup).getItem());
            } catch (RuntimeException ignored) {
                // Missing legacy talismans are intentionally discarded.
            }
        }
        for (String snbt : currentData) {
            if (requireEmptySlotData().equals(snbt)) {
                loaded.add(null);
                continue;
            }
            try {
                ItemStack item = Items.fromSNBT(snbt);
                if (item == null) {
                    decodeFailed = true;
                    loaded.add(null);
                } else if (isItem(item) && isTalisman(item)) {
                    appendNormalized(loaded, item);
                } else {
                    loaded.add(null);
                }
            } catch (RuntimeException exception) {
                decodeFailed = true;
                loaded.add(null);
            }
        }
        trimTrailingEmptySlots(loaded);

        int configured = plugin().getConfigYml().getInt("bag.size");
        int defaultCapacity = Math.max(
                configured > 0 ? configured : DEFAULT_CAPACITY,
                legacyPermissionCapacity(player)
        );
        if (!hasV2) {
            Integer legacyCapacity = playerProfile.read(requireCapacityKey());
            savedCapacity = legacyCapacity != null && legacyCapacity >= 0
                    ? Math.min(legacyCapacity, MAX_CAPACITY)
                    : Math.min(defaultCapacity, MAX_CAPACITY);
        }
        int rawSlotCount = legacyData.size() + currentData.size();
        int safeBaseCapacity = savedCapacity >= 0
                ? savedCapacity
                : Math.min(defaultCapacity, MAX_CAPACITY);
        int capacity = Math.max(safeBaseCapacity, decodeFailed ? rawSlotCount : loaded.size());
        capacity = Math.max(0, capacity);

        savedItems.put(playerId, loaded);
        savedCapacities.put(playerId, capacity);
        if (decodeFailed) {
            unreadableBags.add(playerId);
            plugin().getLogger().warning(
                    "Loaded " + player.getName()
                            + "'s talisman bag read-only because saved data could not be decoded"
            );
            return;
        }

        unreadableBags.remove(playerId);
        List<String> normalized = serialize(loaded);
        if (!hasV2
                || savedCapacity != capacity
                || !normalized.equals(currentData)) {
            writeState(player, capacity, loaded);
        }
    }

    private synchronized void writeState(Player player, int capacity, List<ItemStack> slots) {
        if (unreadableBags.contains(player.getUniqueId())) {
            throw new IllegalStateException("Refusing to overwrite unreadable talisman bag data");
        }
        List<ItemStack> normalized = copySlots(slots);
        trimTrailingEmptySlots(normalized);
        if (capacity < 0 || normalized.size() > capacity) {
            throw new IllegalArgumentException("Talisman bag slots exceed the saved capacity");
        }

        PlayerProfile playerProfile = profile(player);
        playerProfile.write(requireV2Key(), serializeV2(capacity, normalized));
        savedItems.put(player.getUniqueId(), normalized);
        savedCapacities.put(player.getUniqueId(), capacity);
    }

    private static void rollBackState(
            Player player,
            int oldCapacity,
            List<ItemStack> oldSlots,
            RuntimeException failure
    ) {
        try {
            unreadableBags.remove(player.getUniqueId());
            INSTANCE.writeState(player, oldCapacity, oldSlots);
        } catch (RuntimeException rollbackFailure) {
            failure.addSuppressed(rollbackFailure);
        }
    }

    private static void refreshTalismans(Player player) {
        try {
            TalismanChecks.clearCache(player);
            HolderProviderKt.forceRefreshHolders(DispatcherKt.toDispatcher(player));
        } catch (RuntimeException exception) {
            plugin().getLogger().log(
                    Level.WARNING,
                    "Could not immediately refresh talisman effects for " + player.getName(),
                    exception
            );
        }
    }

    private static ItemStack createBlockedItem() {
        try {
            ItemStack item = Items.lookup(plugin().getConfigYml().getString("bag.blocked-item")).getItem();
            if (isItem(item)) {
                ItemStack result = item.clone();
                List<String> extraLore = plugin().getConfigYml().getFormattedStrings("bag.blocked-item-lore");
                if (!extraLore.isEmpty()) {
                    ItemMeta meta = result.getItemMeta();
                    if (meta != null) {
                        List<String> lore = meta.hasLore() && meta.getLore() != null
                                ? new ArrayList<>(meta.getLore())
                                : new ArrayList<>();
                        lore.addAll(extraLore);
                        meta.setLore(lore);
                        result.setItemMeta(meta);
                    }
                }
                result.setAmount(1);
                return result;
            }
        } catch (RuntimeException ignored) {
            // Use a guaranteed vanilla fallback below.
        }
        return named(Material.BLACK_STAINED_GLASS_PANE, "&c该护符格尚未解锁");
    }

    private static void appendNormalized(List<ItemStack> destination, ItemStack item) {
        if (!isItem(item) || !isTalisman(item)) {
            return;
        }
        int amount = Math.max(1, item.getAmount());
        for (int count = 0; count < amount; count++) {
            destination.add(unit(item));
        }
    }

    private static List<String> serialize(List<ItemStack> slots) {
        List<String> data = new ArrayList<>(slots.size());
        for (ItemStack item : slots) {
            if (!isItem(item)) {
                data.add(requireEmptySlotData());
            } else {
                String snbt = Items.toSNBT(unit(item));
                if (snbt == null) {
                    throw new IllegalStateException("Could not serialize a talisman bag item");
                }
                data.add(snbt);
            }
        }
        return data;
    }

    private static List<String> serializeV2(int capacity, List<ItemStack> slots) {
        List<String> data = new ArrayList<>(slots.size() + 2);
        data.add(V2_HEADER);
        data.add(Integer.toString(capacity));
        data.addAll(serialize(slots));
        return data;
    }

    private static ItemStack[] cloneItems(ItemStack[] items) {
        ItemStack[] clones = new ItemStack[items.length];
        for (int index = 0; index < items.length; index++) {
            ItemStack item = items[index];
            clones[index] = isItem(item) ? item.clone() : null;
        }
        return clones;
    }

    private static ItemStack[] planInventoryReturn(
            ItemStack[] originalStorage,
            List<ItemStack> returned,
            int inventoryStackLimit
    ) {
        ItemStack[] planned = cloneItems(originalStorage);
        for (ItemStack returnedItem : returned) {
            ItemStack unit = unit(returnedItem);
            boolean placed = false;
            for (ItemStack existing : planned) {
                if (!isItem(existing) || !existing.isSimilar(unit)) {
                    continue;
                }
                int maxStackSize = Math.min(existing.getMaxStackSize(), inventoryStackLimit);
                if (existing.getAmount() < maxStackSize) {
                    existing.setAmount(existing.getAmount() + 1);
                    placed = true;
                    break;
                }
            }
            if (placed) {
                continue;
            }
            for (int index = 0; index < planned.length; index++) {
                if (!isItem(planned[index])) {
                    planned[index] = unit;
                    placed = true;
                    break;
                }
            }
            if (!placed) {
                return null;
            }
        }
        return planned;
    }

    private static List<ItemStack> copySlots(List<ItemStack> slots) {
        List<ItemStack> copy = new ArrayList<>(slots.size());
        for (ItemStack item : slots) {
            copy.add(isItem(item) ? unit(item) : null);
        }
        return copy;
    }

    private static void setFirstEmpty(List<ItemStack> slots, int capacity, ItemStack item) {
        for (int index = 0; index < capacity; index++) {
            if (index >= slots.size()) {
                slots.add(unit(item));
                return;
            }
            if (!isItem(slots.get(index))) {
                slots.set(index, unit(item));
                return;
            }
        }
        throw new IllegalStateException("No free talisman bag slot is available");
    }

    private static void setSlot(List<ItemStack> slots, int index, ItemStack item) {
        while (slots.size() <= index) {
            slots.add(null);
        }
        slots.set(index, isItem(item) ? unit(item) : null);
        trimTrailingEmptySlots(slots);
    }

    private static ItemStack getSlot(List<ItemStack> slots, int index) {
        return index >= 0 && index < slots.size() ? slots.get(index) : null;
    }

    private static int freeSlots(List<ItemStack> slots, int capacity) {
        return Math.max(0, capacity - occupiedSlots(slots));
    }

    private static int occupiedSlots(List<ItemStack> slots) {
        int count = 0;
        for (ItemStack item : slots) {
            if (isItem(item)) {
                count++;
            }
        }
        return count;
    }

    private static void trimTrailingEmptySlots(List<ItemStack> slots) {
        while (!slots.isEmpty() && !isItem(slots.get(slots.size() - 1))) {
            slots.remove(slots.size() - 1);
        }
    }

    private static int pageCount(int capacity) {
        return Math.max(1, (int) (((long) capacity + CONTENT_SIZE - 1L) / CONTENT_SIZE));
    }

    private static void setCursorAmount(Player player, ItemStack cursor, int amount) {
        if (amount <= 0) {
            player.setItemOnCursor(null);
        } else {
            ItemStack remainder = cursor.clone();
            remainder.setAmount(amount);
            player.setItemOnCursor(remainder);
        }
    }

    private static void setCurrentAmount(InventoryClickEvent event, ItemStack item, int amount) {
        if (amount <= 0) {
            event.setCurrentItem(null);
        } else {
            ItemStack remainder = item.clone();
            remainder.setAmount(amount);
            event.setCurrentItem(remainder);
        }
    }

    private static boolean isTalisman(ItemStack item) {
        if (!isItem(item)) {
            return false;
        }
        TalismanUtils.INSTANCE.convert(item);
        return TalismanChecks.getTalismanOnItem(item) != null;
    }

    private static boolean isItem(ItemStack item) {
        return item != null && !item.getType().isAir();
    }

    private static ItemStack unit(ItemStack item) {
        ItemStack copy = item.clone();
        copy.setAmount(1);
        return copy;
    }

    private static ItemStack named(Material material, String name, String... loreLines) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(color(name));
            if (loreLines.length > 0) {
                List<String> lore = new ArrayList<>(loreLines.length);
                for (String line : loreLines) {
                    lore.add(color(line));
                }
                meta.setLore(lore);
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    private static void deny(Player player, String message) {
        player.sendMessage(color(message));
        playSoundSafely(player, Sound.ENTITY_VILLAGER_NO, 0.5f, 1.1f);
    }

    private static void playSoundSafely(
            Player player,
            Sound sound,
            float volume,
            float pitch
    ) {
        try {
            player.playSound(player.getLocation(), sound, volume, pitch);
        } catch (RuntimeException exception) {
            plugin().getLogger().log(
                    Level.FINE,
                    "Could not play a talisman bag sound for " + player.getName(),
                    exception
            );
        }
    }

    private static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private static List<String> safeList(List<String> values) {
        return values == null ? Collections.emptyList() : new ArrayList<>(values);
    }

    private static PlayerProfile profile(Player player) {
        return ProfileExtensions.getProfile((OfflinePlayer) player);
    }

    private static TalismansPlugin plugin() {
        return TalismansPluginKt.getPlugin();
    }

    private static PersistentDataKey<List<String>> requireLegacyKey() {
        ensureInitialized();
        if (legacyKey == null) {
            throw new IllegalStateException("The talisman bag has not been initialized");
        }
        return legacyKey;
    }

    private static PersistentDataKey<List<String>> requireKey() {
        ensureInitialized();
        if (key == null) {
            throw new IllegalStateException("The talisman bag has not been initialized");
        }
        return key;
    }

    private static PersistentDataKey<Integer> requireCapacityKey() {
        ensureInitialized();
        if (capacityKey == null) {
            throw new IllegalStateException("The talisman bag has not been initialized");
        }
        return capacityKey;
    }

    private static PersistentDataKey<List<String>> requireV2Key() {
        ensureInitialized();
        if (v2Key == null) {
            throw new IllegalStateException("The talisman bag has not been initialized");
        }
        return v2Key;
    }

    private static String requireEmptySlotData() {
        ensureInitialized();
        if (emptySlotData == null) {
            throw new IllegalStateException("The talisman bag has not been initialized");
        }
        return emptySlotData;
    }

    private static void ensureInitialized() {
        if (legacyKey == null || key == null || v2Key == null
                || capacityKey == null || emptySlotData == null) {
            INSTANCE.update$core_plugin();
        }
    }

    private static int legacyPermissionCapacity(Player player) {
        int highest = 0;
        String prefix = "talismans.bagsize.";
        for (PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
            String name = permission.getPermission();
            if (!permission.getValue() || !name.startsWith(prefix)) {
                continue;
            }
            try {
                highest = Math.max(highest, Integer.parseInt(name.substring(prefix.length())));
            } catch (NumberFormatException ignored) {
                // Wildcards and malformed third-party permission nodes are ignored.
            }
        }
        return highest;
    }

    public record CapacityChange(int oldCapacity, int newCapacity, int returnedTalismans) {
        public int actualDelta() {
            return newCapacity - oldCapacity;
        }
    }

    public static final class InsufficientInventorySpaceException extends RuntimeException {
        private final int requiredSlots;

        private InsufficientInventorySpaceException(int requiredSlots) {
            super("The player's inventory cannot hold " + requiredSlots + " returned talismans");
            this.requiredSlots = requiredSlots;
        }

        public int getRequiredSlots() {
            return requiredSlots;
        }
    }

    private interface ItemSetter {
        void set(ItemStack item);
    }

    private static final class BagHolder implements InventoryHolder {
        private final int page;
        private final int pageCount;
        private final int capacity;
        private Inventory inventory;

        private BagHolder(int page, int pageCount, int capacity) {
            this.page = page;
            this.pageCount = pageCount;
            this.capacity = capacity;
        }

        @Override
        public Inventory getInventory() {
            return inventory;
        }
    }
}
