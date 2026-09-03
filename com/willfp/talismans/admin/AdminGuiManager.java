package com.willfp.talismans.admin;

import com.willfp.eco.core.items.Items;
import com.willfp.talismans.TalismansPlugin;
import com.willfp.talismans.TalismansPluginKt;
import com.willfp.talismans.talismans.Talisman;
import com.willfp.talismans.talismans.Talismans;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * Bukkit-inventory based administration UI. Recipe ingredients are templates:
 * placing an item in the editor never consumes the administrator's item.
 */
public final class AdminGuiManager implements Listener {
    private static final AdminGuiManager INSTANCE = new AdminGuiManager();
    private static final int MENU_SIZE = 54;
    private static final int PAGE_SIZE = 45;
    private static final int PREVIOUS_PAGE_SLOT = 45;
    private static final int PAGE_INFO_SLOT = 49;
    private static final int NEXT_PAGE_SLOT = 53;
    private static final int[] RECIPE_SLOTS = {10, 11, 12, 19, 20, 21, 28, 29, 30};
    private static final int OUTPUT_SLOT = 25;
    private static final int RESET_SLOT = 46;
    private static final int CANCEL_SLOT = 49;
    private static final int SAVE_SLOT = 52;
    private static boolean registered;

    private AdminGuiManager() {
    }

    public static synchronized void ensureRegistered() {
        if (registered) {
            return;
        }

        Bukkit.getPluginManager().registerEvents(INSTANCE, plugin());
        registered = true;
    }

    public static void openList(Player player) {
        openList(player, 0);
    }

    private static TalismansPlugin plugin() {
        return TalismansPluginKt.getPlugin();
    }

    private static void openList(Player player, int requestedPage) {
        List<Talisman> talismans = Talismans.values();
        int pageCount = Math.max(1, (talismans.size() + PAGE_SIZE - 1) / PAGE_SIZE);
        int page = Math.max(0, Math.min(requestedPage, pageCount - 1));
        ListHolder holder = new ListHolder(page, pageCount);
        Inventory inventory = Bukkit.createInventory(
                holder,
                MENU_SIZE,
                ChatColor.DARK_AQUA + "护符管理 " + ChatColor.GRAY + "(" + (page + 1) + "/" + pageCount + ")"
        );
        holder.inventory = inventory;
        fill(inventory);

        int fromIndex = page * PAGE_SIZE;
        int toIndex = Math.min(fromIndex + PAGE_SIZE, talismans.size());
        for (int index = fromIndex; index < toIndex; index++) {
            Talisman talisman = talismans.get(index);
            int slot = index - fromIndex;
            holder.talismansBySlot.put(slot, talisman);
            inventory.setItem(slot, listIcon(talisman));
        }

        if (page > 0) {
            inventory.setItem(PREVIOUS_PAGE_SLOT, named(Material.ARROW, "&e上一页", "&7点击查看上一页"));
        }
        inventory.setItem(
                PAGE_INFO_SLOT,
                named(Material.BOOK, "&b护符列表", "&7共 &f" + talismans.size() + " &7个护符", "", "&e左键 &7获得 1 个", "&eShift+左键 &7获得一组", "&e右键 &7编辑配方")
        );
        if (page + 1 < pageCount) {
            inventory.setItem(NEXT_PAGE_SLOT, named(Material.ARROW, "&e下一页", "&7点击查看下一页"));
        }

        player.openInventory(inventory);
    }

    private static ItemStack listIcon(Talisman talisman) {
        ItemStack icon = talisman.getItemStack();
        icon.setAmount(1);
        ItemMeta meta = icon.getItemMeta();
        if (meta != null) {
            List<String> lore = meta.hasLore() && meta.getLore() != null
                    ? new ArrayList<>(meta.getLore())
                    : new ArrayList<>();
            lore.add("");
            lore.add(color("&e左键 &7获得 1 个"));
            lore.add(color("&eShift+左键 &7获得一组"));
            lore.add(color("&e右键 &7编辑合成配方"));
            lore.add(color("&8ID: " + talisman.getID()));
            meta.setLore(lore);
            icon.setItemMeta(meta);
        }
        return icon;
    }

    private static void openEditor(Player player, Talisman talisman, int returnPage) {
        String[] savedTokens = readRecipeTokens(talisman);
        EditorHolder holder = new EditorHolder(talisman, returnPage, savedTokens);
        Inventory inventory = Bukkit.createInventory(
                holder,
                MENU_SIZE,
                ChatColor.DARK_AQUA + "编辑配方: " + ChatColor.RESET + ChatColor.stripColor(color(talisman.getName()))
        );
        holder.inventory = inventory;
        fill(inventory);
        renderRecipe(holder, savedTokens);

        ItemStack output = talisman.getItemStack();
        ItemMeta outputMeta = output.getItemMeta();
        if (outputMeta != null) {
            List<String> lore = outputMeta.hasLore() && outputMeta.getLore() != null
                    ? new ArrayList<>(outputMeta.getLore())
                    : new ArrayList<>();
            lore.add("");
            lore.add(color("&7合成结果（不可取出）"));
            outputMeta.setLore(lore);
            output.setItemMeta(outputMeta);
        }
        inventory.setItem(OUTPUT_SLOT, output);
        inventory.setItem(RESET_SLOT, named(Material.CLOCK, "&e重置", "&7恢复为打开编辑器时的配方"));
        inventory.setItem(CANCEL_SLOT, named(Material.BARRIER, "&c取消", "&7放弃修改并返回护符列表"));
        inventory.setItem(SAVE_SLOT, named(Material.LIME_DYE, "&a保存", "&7保存配方并立即重载插件", "", "&8操作提示：", "&7先从背包拿起物品，再点击左侧格子", "&7Shift 点击背包物品可快速添加"));
        player.openInventory(inventory);
    }

    private static void renderRecipe(EditorHolder holder, String[] tokens) {
        System.arraycopy(tokens, 0, holder.tokens, 0, RECIPE_SLOTS.length);
        for (int index = 0; index < RECIPE_SLOTS.length; index++) {
            holder.inventory.setItem(RECIPE_SLOTS[index], displayIngredient(tokens[index]));
        }
    }

    private static ItemStack displayIngredient(String token) {
        if (isEmptyToken(token)) {
            return null;
        }

        try {
            ItemStack item = Items.lookup(token).getItem();
            if (item == null || item.getType().isAir()) {
                return null;
            }
            item = item.clone();
            item.setAmount(1);
            return item;
        } catch (RuntimeException exception) {
            Material material = Material.matchMaterial(token);
            return material == null || material.isAir() ? null : new ItemStack(material);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory top = event.getView().getTopInventory();
        InventoryHolder inventoryHolder = top.getHolder();
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (inventoryHolder instanceof ListHolder holder) {
            handleListClick(event, player, holder);
        } else if (inventoryHolder instanceof EditorHolder holder) {
            handleEditorClick(event, player, holder);
        }
    }

    private void handleListClick(InventoryClickEvent event, Player player, ListHolder holder) {
        event.setCancelled(true);
        int rawSlot = event.getRawSlot();
        if (rawSlot < 0 || rawSlot >= MENU_SIZE) {
            return;
        }

        Talisman talisman = holder.talismansBySlot.get(rawSlot);
        if (talisman != null) {
            if (event.isRightClick()) {
                Bukkit.getScheduler().runTask(plugin(), () -> openEditor(player, talisman, holder.page));
            } else if (event.isLeftClick()) {
                giveTalisman(player, talisman, event.isShiftClick());
            }
            return;
        }

        if (rawSlot == PREVIOUS_PAGE_SLOT && holder.page > 0) {
            Bukkit.getScheduler().runTask(plugin(), () -> openList(player, holder.page - 1));
        } else if (rawSlot == NEXT_PAGE_SLOT && holder.page + 1 < holder.pageCount) {
            Bukkit.getScheduler().runTask(plugin(), () -> openList(player, holder.page + 1));
        }
    }

    private static void giveTalisman(Player player, Talisman talisman, boolean fullStack) {
        ItemStack item = talisman.getItemStack();
        item.setAmount(fullStack ? item.getMaxStackSize() : 1);
        Map<Integer, ItemStack> overflow = player.getInventory().addItem(item);
        for (ItemStack leftover : overflow.values()) {
            player.getWorld().dropItemNaturally(player.getLocation(), leftover);
        }
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 0.7f, 1.2f);
    }

    private void handleEditorClick(InventoryClickEvent event, Player player, EditorHolder holder) {
        int rawSlot = event.getRawSlot();
        if (rawSlot >= MENU_SIZE) {
            if (event.isShiftClick() && isItem(event.getCurrentItem())) {
                event.setCancelled(true);
                copyIntoFirstEmpty(holder, event.getCurrentItem());
            } else if (event.getClick() == ClickType.DOUBLE_CLICK) {
                event.setCancelled(true);
            }
            return;
        }

        event.setCancelled(true);
        if (rawSlot < 0) {
            return;
        }

        int recipeIndex = recipeIndex(rawSlot);
        if (recipeIndex >= 0) {
            ItemStack source = event.getCursor();
            if (event.getClick() == ClickType.NUMBER_KEY && event.getHotbarButton() >= 0) {
                source = player.getInventory().getItem(event.getHotbarButton());
            }

            if (isItem(source)) {
                setIngredient(holder, recipeIndex, source);
            } else {
                clearIngredient(holder, recipeIndex);
            }
            return;
        }

        if (rawSlot == RESET_SLOT) {
            renderRecipe(holder, holder.savedTokens);
            player.sendMessage(color("&e配方已重置为打开编辑器时的内容。"));
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 0.7f, 0.9f);
        } else if (rawSlot == CANCEL_SLOT) {
            Bukkit.getScheduler().runTask(plugin(), () -> openList(player, holder.returnPage));
        } else if (rawSlot == SAVE_SLOT) {
            saveRecipe(player, holder);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        InventoryHolder inventoryHolder = event.getView().getTopInventory().getHolder();
        if (!(inventoryHolder instanceof EditorHolder holder)) {
            return;
        }

        boolean touchesEditor = false;
        for (int rawSlot : event.getRawSlots()) {
            if (rawSlot < MENU_SIZE) {
                touchesEditor = true;
                break;
            }
        }
        if (!touchesEditor) {
            return;
        }

        event.setCancelled(true);
        if (!isItem(event.getOldCursor())) {
            return;
        }
        for (int rawSlot : event.getRawSlots()) {
            int index = recipeIndex(rawSlot);
            if (index >= 0) {
                setIngredient(holder, index, event.getOldCursor());
            }
        }
    }

    private static void copyIntoFirstEmpty(EditorHolder holder, ItemStack source) {
        for (int index = 0; index < RECIPE_SLOTS.length; index++) {
            if (!isItem(holder.inventory.getItem(RECIPE_SLOTS[index]))) {
                setIngredient(holder, index, source);
                return;
            }
        }
    }

    private static void setIngredient(EditorHolder holder, int index, ItemStack source) {
        ItemStack copy = source.clone();
        copy.setAmount(1);
        holder.inventory.setItem(RECIPE_SLOTS[index], copy);
        String lookup = Items.toLookupString(copy);
        if (lookup == null || lookup.isBlank()) {
            lookup = copy.getType().getKey().getKey();
        }
        holder.tokens[index] = lookup;
    }

    private static void clearIngredient(EditorHolder holder, int index) {
        holder.inventory.setItem(RECIPE_SLOTS[index], null);
        holder.tokens[index] = "air";
    }

    private static void saveRecipe(Player player, EditorHolder holder) {
        File recipeFile = new File(new File(plugin().getDataFolder(), "talismans"), holder.talisman.getID() + ".yml");
        if (!recipeFile.isFile()) {
            player.sendMessage(color("&c无法保存：未找到配置文件 " + recipeFile.getName()));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.8f, 1.0f);
            return;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(recipeFile);
        config.set("craftable", true);
        if (!config.contains("crafting-permission")) {
            config.set("crafting-permission", Collections.emptyList());
        }
        config.set("recipe", Arrays.asList(holder.tokens.clone()));
        try {
            config.save(recipeFile);
        } catch (IOException exception) {
            plugin().getLogger().severe("Failed to save recipe for " + holder.talisman.getID() + ": " + exception.getMessage());
            player.sendMessage(color("&c配方保存失败，请查看控制台。"));
            player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_NO, 0.8f, 1.0f);
            return;
        }

        player.closeInventory();
        player.sendMessage(color("&a已保存护符 &f" + holder.talisman.getID() + " &a的合成配方，正在重载……"));
        Bukkit.getScheduler().runTask(plugin(), () -> {
            plugin().reload();
            if (player.isOnline()) {
                player.sendMessage(color("&a配方已生效。"));
                openList(player, holder.returnPage);
                player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 0.7f, 1.2f);
            }
        });
    }

    private static String[] readRecipeTokens(Talisman talisman) {
        String[] tokens = new String[RECIPE_SLOTS.length];
        Arrays.fill(tokens, "air");
        List<String> configured = talisman.getConfig().getStrings("recipe");
        for (int index = 0; index < Math.min(tokens.length, configured.size()); index++) {
            String token = configured.get(index);
            tokens[index] = token == null || token.isBlank() ? "air" : token;
        }
        return tokens;
    }

    private static int recipeIndex(int rawSlot) {
        for (int index = 0; index < RECIPE_SLOTS.length; index++) {
            if (RECIPE_SLOTS[index] == rawSlot) {
                return index;
            }
        }
        return -1;
    }

    private static boolean isItem(ItemStack item) {
        return item != null && !item.getType().isAir();
    }

    private static boolean isEmptyToken(String token) {
        return token == null || token.isBlank() || token.equalsIgnoreCase("air") || token.equalsIgnoreCase("minecraft:air");
    }

    private static void fill(Inventory inventory) {
        ItemStack filler = named(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " ");
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            inventory.setItem(slot, filler);
        }
    }

    private static ItemStack named(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(color(name));
            if (lore.length > 0) {
                List<String> coloredLore = new ArrayList<>(lore.length);
                for (String line : lore) {
                    coloredLore.add(color(line));
                }
                meta.setLore(coloredLore);
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    private static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private static final class ListHolder implements InventoryHolder {
        private final int page;
        private final int pageCount;
        private final Map<Integer, Talisman> talismansBySlot = new HashMap<>();
        private Inventory inventory;

        private ListHolder(int page, int pageCount) {
            this.page = page;
            this.pageCount = pageCount;
        }

        @Override
        public Inventory getInventory() {
            return inventory;
        }
    }

    private static final class EditorHolder implements InventoryHolder {
        private final Talisman talisman;
        private final int returnPage;
        private final String[] savedTokens;
        private final String[] tokens = new String[RECIPE_SLOTS.length];
        private Inventory inventory;

        private EditorHolder(Talisman talisman, int returnPage, String[] savedTokens) {
            this.talisman = talisman;
            this.returnPage = returnPage;
            this.savedTokens = savedTokens.clone();
        }

        @Override
        public Inventory getInventory() {
            return inventory;
        }
    }
}
