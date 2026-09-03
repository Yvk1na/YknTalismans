package com.willfp.talismans.talismans.util;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

/** Prevents talismans from being used as their underlying vanilla item. */
public final class BlockPlaceListener implements Listener {
    public static final BlockPlaceListener INSTANCE = new BlockPlaceListener();

    private BlockPlaceListener() {
    }

    /**
     * Deny only the held item's right-click action. The clicked block remains usable,
     * so a player can still open containers or doors while holding a talisman.
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onTalismanUse(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (isTalisman(event.getItem())) {
            event.setUseItemInHand(Event.Result.DENY);
        }
    }

    /** Defense in depth for consumables already being used when plugins reload. */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onTalismanConsume(PlayerItemConsumeEvent event) {
        if (isTalisman(event.getItem())) {
            event.setCancelled(true);
        }
    }

    /** Covers block placement initiated through paths that still reach BlockPlaceEvent. */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void onAttemptTalismanPlace(BlockPlaceEvent event) {
        if (isTalisman(event.getItemInHand())) {
            event.setCancelled(true);
        }
    }

    private static boolean isTalisman(ItemStack item) {
        return TalismanChecks.getTalismanOnItem(item) != null;
    }
}
