package com.willfp.talismans.talismans.util;

import io.papermc.paper.event.block.PlayerShearBlockEvent;
import io.papermc.paper.event.entity.EntityLoadCrossbowEvent;
import com.willfp.eco.core.recipe.recipes.CraftingRecipe;
import com.willfp.talismans.bag.TalismanBagDeposits;
import com.willfp.talismans.talismans.Talisman;
import com.willfp.talismans.talismans.Talismans;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Crafter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.CrafterCraftEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.BrewingStandFuelEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareGrindstoneEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEntityEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.event.player.PlayerRiptideEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.AbstractHorseInventory;
import org.bukkit.inventory.ArmoredHorseInventory;
import org.bukkit.inventory.CampfireRecipe;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.LlamaInventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * Prevents talismans from being consumed or operated as their underlying
 * vanilla item. Normal containers and talisman crafting inventories remain
 * usable, while functional inventories reject talismans.
 */
public final class BlockPlaceListener implements Listener {
    public static final BlockPlaceListener INSTANCE = new BlockPlaceListener();

    private static final Set<InventoryType> BLOCKED_DESTINATIONS = EnumSet.of(
            InventoryType.FURNACE,
            InventoryType.BLAST_FURNACE,
            InventoryType.SMOKER,
            InventoryType.BREWING,
            InventoryType.ENCHANTING,
            InventoryType.ANVIL,
            InventoryType.SMITHING,
            InventoryType.SMITHING_NEW,
            InventoryType.GRINDSTONE,
            InventoryType.STONECUTTER,
            InventoryType.LOOM,
            InventoryType.CARTOGRAPHY,
            InventoryType.BEACON,
            InventoryType.MERCHANT,
            InventoryType.COMPOSTER,
            InventoryType.LECTERN,
            InventoryType.JUKEBOX
    );

    private static final Set<InventoryAction> CURRENT_ITEM_BUNDLE_ACTIONS = EnumSet.of(
            InventoryAction.PICKUP_FROM_BUNDLE,
            InventoryAction.PLACE_ALL_INTO_BUNDLE,
            InventoryAction.PLACE_SOME_INTO_BUNDLE
    );

    private static final Set<InventoryAction> CURSOR_BUNDLE_ACTIONS = EnumSet.of(
            InventoryAction.PICKUP_ALL_INTO_BUNDLE,
            InventoryAction.PICKUP_SOME_INTO_BUNDLE,
            InventoryAction.PLACE_FROM_BUNDLE
    );

    private BlockPlaceListener() {
    }

    /**
     * A right-click deposits the held talisman when the bag has a free slot.
     * If the bag is full, only unsafe vanilla uses of the underlying item are
     * denied and ordinary block interaction remains available.
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onTalismanUse(PlayerInteractEvent event) {
        Action action = event.getAction();
        if ((action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
                && isTalisman(event.getItem())) {
            boolean depositAllowed = event.useItemInHand() != Event.Result.DENY;
            event.setUseItemInHand(Event.Result.DENY);
            if (depositAllowed && TalismanBagDeposits.tryDeposit(
                    event.getPlayer(),
                    event.getHand(),
                    event.getItem()
            )) {
                // This click belongs to the deposit and must not also operate a
                // container, door, or another block underneath the player.
                event.setUseInteractedBlock(Event.Result.DENY);
            } else if (action == Action.RIGHT_CLICK_BLOCK
                    && event.getClickedBlock() != null
                    && blockConsumesHeldItem(event.getClickedBlock().getType(), event.getItem())) {
                event.setUseInteractedBlock(Event.Result.DENY);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onTalismanConsume(PlayerItemConsumeEvent event) {
        if (isTalisman(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onAttemptTalismanPlace(BlockPlaceEvent event) {
        if (isTalisman(event.getItemInHand())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityPlace(EntityPlaceEvent event) {
        Player player = event.getPlayer();
        if (player != null && isTalisman(itemInHand(player, event.getHand()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onHangingPlace(HangingPlaceEvent event) {
        if (isTalisman(event.getItemStack())) {
            event.setCancelled(true);
        }
    }

    /** Reject manual insertion into processing inventories and armor slots. */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory top = event.getView().getTopInventory();
        int rawSlot = event.getRawSlot();
        boolean clickedTop = rawSlot >= 0 && rawSlot < top.getSize();

        if ((CURRENT_ITEM_BUNDLE_ACTIONS.contains(event.getAction())
                && isTalisman(event.getCurrentItem()))
                || (CURSOR_BUNDLE_ACTIONS.contains(event.getAction())
                && isTalisman(event.getCursor()))) {
            event.setCancelled(true);
            return;
        }

        if (BLOCKED_DESTINATIONS.contains(top.getType())) {
            if (clickedTop
                    && event.getView().getSlotType(rawSlot) == InventoryType.SlotType.RESULT
                    && hasTalismanInput(event.getView())) {
                event.setCancelled(true);
                return;
            }

            if (clickedTop && isIncomingTalisman(event)) {
                event.setCancelled(true);
                return;
            }

            if (!clickedTop
                    && event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY
                    && isTalisman(event.getCurrentItem())) {
                event.setCancelled(true);
                return;
            }
        }

        if (rawSlot >= 0
                && event.getView().getSlotType(rawSlot) == InventoryType.SlotType.ARMOR
                && isIncomingTalisman(event)) {
            event.setCancelled(true);
            return;
        }

        if (clickedTop && isHorseEquipmentSlot(top, rawSlot) && isIncomingTalisman(event)) {
            event.setCancelled(true);
            return;
        }

        if (!clickedTop
                && event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY
                && isHorseEquipment(top, event.getCurrentItem())) {
            event.setCancelled(true);
            return;
        }

        // Shift-clicking an equippable component from the player's own inventory
        // equips it without a right-click event, so guard that path separately.
        if ((top.getType() == InventoryType.CRAFTING || top.getType() == InventoryType.PLAYER)
                && !clickedTop
                && event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY
                && isPlayerArmorTalisman(event.getCurrentItem())) {
            event.setCancelled(true);
            return;
        }

    }

    /** Reject drag distribution into processing inventories or armor slots. */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event) {
        Inventory top = event.getView().getTopInventory();
        for (Map.Entry<Integer, ItemStack> entry : event.getNewItems().entrySet()) {
            int rawSlot = entry.getKey();
            if (!isTalisman(entry.getValue())) {
                continue;
            }

            if ((rawSlot < top.getSize() && BLOCKED_DESTINATIONS.contains(top.getType()))
                    || event.getView().getSlotType(rawSlot) == InventoryType.SlotType.ARMOR
                    || isHorseEquipmentSlot(top, rawSlot)) {
                event.setCancelled(true);
                return;
            }
        }
    }

    /** Prevent hoppers and other transfer inventories from feeding processors. */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryMove(InventoryMoveItemEvent event) {
        if (isTalisman(event.getItem())
                && BLOCKED_DESTINATIONS.contains(event.getDestination().getType())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBrew(BrewEvent event) {
        if (containsTalisman(event.getContents())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBrewingFuel(BrewingStandFuelEvent event) {
        if (isTalisman(event.getFuel())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCook(BlockCookEvent event) {
        if (isTalisman(event.getSource())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onFurnaceFuel(FurnaceBurnEvent event) {
        if (isTalisman(event.getFuel())) {
            event.setCancelled(true);
        }
    }

    /** Talisman ingredients are valid only when the crafted result is a talisman. */
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrepareCraft(PrepareItemCraftEvent event) {
        if (containsTalisman(event.getInventory().getMatrix())
                && (!isTalismanRecipe(event.getRecipe())
                || !isTalisman(event.getInventory().getResult()))) {
            event.getInventory().setResult(null);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onCraft(CraftItemEvent event) {
        if (containsTalisman(event.getInventory().getMatrix())
                && (!isTalismanRecipe(event.getRecipe())
                || !isTalisman(event.getInventory().getResult()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCrafterCraft(CrafterCraftEvent event) {
        if (event.getBlock().getState() instanceof Crafter crafter
                && containsTalisman(crafter.getInventory())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPrepareEnchant(PrepareItemEnchantEvent event) {
        if (isTalisman(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEnchant(EnchantItemEvent event) {
        if (isTalisman(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        if (containsTalisman(event.getInventory())) {
            event.setResult(null);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrepareGrindstone(PrepareGrindstoneEvent event) {
        if (containsTalisman(event.getInventory())) {
            event.setResult(null);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPrepareSmithing(PrepareSmithingEvent event) {
        if (containsTalisman(event.getInventory())) {
            event.setResult(null);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDispense(BlockDispenseEvent event) {
        if (isTalisman(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        if (isTalisman(itemInHand(event.getPlayer(), event.getHand()))) {
            event.setCancelled(true);
        }
    }

    // PlayerInteractAtEntityEvent has its own HandlerList and must be guarded too.
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteractAtEntity(PlayerInteractAtEntityEvent event) {
        if (isTalisman(itemInHand(event.getPlayer(), event.getHand()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
        if (isTalisman(event.getPlayerItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onShearEntity(PlayerShearEntityEvent event) {
        if (isTalisman(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onShearBlock(PlayerShearBlockEvent event) {
        if (isTalisman(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBucketFill(PlayerBucketFillEvent event) {
        if (isTalisman(event.getItemStack())
                || isTalisman(itemInHand(event.getPlayer(), event.getHand()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        if (isTalisman(event.getItemStack())
                || isTalisman(itemInHand(event.getPlayer(), event.getHand()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBucketEntity(PlayerBucketEntityEvent event) {
        if (isTalisman(event.getOriginalBucket())
                || isTalisman(itemInHand(event.getPlayer(), event.getHand()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLeash(PlayerLeashEntityEvent event) {
        if (isTalisman(itemInHand(event.getPlayer(), event.getHand()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onUnleash(PlayerUnleashEntityEvent event) {
        if (isTalisman(itemInHand(event.getPlayer(), event.getHand()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player
                && isTalisman(player.getInventory().getItemInMainHand())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockDamage(BlockDamageEvent event) {
        if (isTalisman(event.getItemInHand())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if (isTalisman(event.getPlayer().getInventory().getItemInMainHand())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemDamage(PlayerItemDamageEvent event) {
        if (isTalisman(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onItemMend(PlayerItemMendEvent event) {
        if (isTalisman(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onShootBow(EntityShootBowEvent event) {
        if (isTalisman(event.getBow()) || isTalisman(event.getConsumable())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onLoadCrossbow(EntityLoadCrossbowEvent event) {
        if (isTalisman(event.getCrossbow())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onRiptide(PlayerRiptideEvent event) {
        if (isTalisman(event.getItem())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onResurrect(EntityResurrectEvent event) {
        if (event.getEntity() instanceof Player player
                && isTalisman(itemInHand(player, event.getHand()))) {
            event.setCancelled(true);
        }
    }

    private static boolean isIncomingTalisman(InventoryClickEvent event) {
        if (isTalisman(event.getCursor())) {
            return true;
        }

        if (event.getClick() == ClickType.NUMBER_KEY
                || event.getAction() == InventoryAction.HOTBAR_SWAP
                || event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD) {
            int hotbarButton = event.getHotbarButton();
            if (hotbarButton >= 0
                    && event.getWhoClicked() instanceof Player player
                    && isTalisman(player.getInventory().getItem(hotbarButton))) {
                return true;
            }
        }

        return event.getClick() == ClickType.SWAP_OFFHAND
                && event.getWhoClicked() instanceof Player player
                && isTalisman(player.getInventory().getItemInOffHand());
    }

    private static boolean isPlayerArmorTalisman(ItemStack item) {
        if (!isTalisman(item)) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasEquippable()) {
            return false;
        }

        EquipmentSlot slot = meta.getEquippable().getSlot();
        return slot == EquipmentSlot.HEAD
                || slot == EquipmentSlot.CHEST
                || slot == EquipmentSlot.LEGS
                || slot == EquipmentSlot.FEET;
    }

    /**
     * Some blocks consume or transform the held stack from their own block-use
     * code, so denying only the item's use is not sufficient for those exact
     * material combinations. Keep this narrow so a talisman does not prevent
     * ordinary use of a cake, lectern, charged anchor, or other clicked block.
     */
    private static boolean blockConsumesHeldItem(Material blockType, ItemStack item) {
        Material itemType = item.getType();

        if (blockType == Material.COMPOSTER) {
            return itemType.isCompostable();
        }
        if (blockType == Material.JUKEBOX) {
            return itemType.isRecord();
        }
        if (blockType == Material.LECTERN) {
            return Tag.ITEMS_LECTERN_BOOKS.isTagged(itemType);
        }
        if (blockType == Material.CAMPFIRE || blockType == Material.SOUL_CAMPFIRE) {
            return isCampfireIngredient(item)
                    || Tag.ITEMS_SHOVELS.isTagged(itemType)
                    || itemType == Material.FLINT_AND_STEEL
                    || itemType == Material.FIRE_CHARGE;
        }
        if (blockType == Material.RESPAWN_ANCHOR) {
            return itemType == Material.GLOWSTONE;
        }
        if (blockType == Material.END_PORTAL_FRAME) {
            return itemType == Material.ENDER_EYE;
        }
        if (blockType == Material.CAULDRON
                || blockType == Material.WATER_CAULDRON
                || blockType == Material.LAVA_CAULDRON
                || blockType == Material.POWDER_SNOW_CAULDRON) {
            return isCauldronItem(itemType);
        }
        if (blockType == Material.FLOWER_POT) {
            return Material.matchMaterial("POTTED_" + itemType.name()) != null;
        }
        if (blockType == Material.BEEHIVE || blockType == Material.BEE_NEST) {
            return itemType == Material.SHEARS || itemType == Material.GLASS_BOTTLE;
        }
        if (blockType == Material.LODESTONE) {
            return itemType == Material.COMPASS;
        }
        if (blockType == Material.VAULT) {
            return itemType == Material.TRIAL_KEY || itemType == Material.OMINOUS_TRIAL_KEY;
        }
        if (blockType == Material.CAKE) {
            return Tag.ITEMS_CANDLES.isTagged(itemType);
        }

        return (Tag.CANDLES.isTagged(blockType) || Tag.CANDLE_CAKES.isTagged(blockType))
                && (itemType == Material.FLINT_AND_STEEL || itemType == Material.FIRE_CHARGE);
    }

    private static boolean isCampfireIngredient(ItemStack item) {
        var recipes = Bukkit.recipeIterator();
        while (recipes.hasNext()) {
            if (recipes.next() instanceof CampfireRecipe recipe
                    && recipe.getInputChoice().test(item)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isCauldronItem(Material itemType) {
        return switch (itemType) {
            case WATER_BUCKET, LAVA_BUCKET, POWDER_SNOW_BUCKET, BUCKET,
                    GLASS_BOTTLE, POTION,
                    LEATHER_HELMET, LEATHER_CHESTPLATE, LEATHER_LEGGINGS,
                    LEATHER_BOOTS, LEATHER_HORSE_ARMOR, WOLF_ARMOR -> true;
            default -> Tag.ITEMS_BANNERS.isTagged(itemType)
                    || Tag.ITEMS_SHULKER_BOXES.isTagged(itemType);
        };
    }

    private static boolean isHorseEquipmentSlot(Inventory top, int rawSlot) {
        if (!(top instanceof AbstractHorseInventory) || rawSlot < 0 || rawSlot >= top.getSize()) {
            return false;
        }

        return rawSlot == 0
                || (rawSlot == 1
                && (top instanceof ArmoredHorseInventory || top instanceof LlamaInventory));
    }

    private static boolean isHorseEquipment(Inventory top, ItemStack item) {
        if (!(top instanceof AbstractHorseInventory) || !isTalisman(item)) {
            return false;
        }

        Material material = item.getType();
        if (material == Material.SADDLE) {
            return true;
        }
        if (top instanceof ArmoredHorseInventory && material.name().endsWith("_HORSE_ARMOR")) {
            return true;
        }
        return top instanceof LlamaInventory && Tag.WOOL_CARPETS.isTagged(material);
    }

    private static ItemStack itemInHand(Player player, EquipmentSlot hand) {
        return hand == null ? null : player.getInventory().getItem(hand);
    }

    private static boolean containsTalisman(Inventory inventory) {
        return containsTalisman(inventory.getContents());
    }

    private static boolean hasTalismanInput(InventoryView view) {
        Inventory top = view.getTopInventory();
        for (int rawSlot = 0; rawSlot < top.getSize(); rawSlot++) {
            if (view.getSlotType(rawSlot) != InventoryType.SlotType.RESULT
                    && isTalisman(top.getItem(rawSlot))) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsTalisman(ItemStack[] items) {
        for (ItemStack item : items) {
            if (isTalisman(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Match the registered eco recipe key rather than trusting copied PDC on a
     * vanilla repair result. Both eco keys are accepted because shaped recipe
     * adapters may expose either one through Bukkit's recipe event.
     */
    private static boolean isTalismanRecipe(Recipe recipe) {
        if (!(recipe instanceof Keyed keyed)) {
            return false;
        }

        NamespacedKey eventKey = keyed.getKey();
        for (Talisman talisman : Talismans.values()) {
            CraftingRecipe talismanRecipe = talisman.getRecipe();
            if (talismanRecipe == null) {
                continue;
            }

            if (eventKey.equals(talismanRecipe.getKey())
                    || eventKey.equals(talismanRecipe.getDisplayedKey())) {
                return true;
            }
        }
        return false;
    }

    private static boolean isTalisman(ItemStack item) {
        TalismanUtils.INSTANCE.convert(item);
        return TalismanChecks.getTalismanOnItem(item) != null;
    }
}
