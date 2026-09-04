package com.willfp.talismans.bag;

import com.willfp.talismans.TalismansPluginKt;
import java.util.logging.Level;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/** Safe interaction adapter for depositing a held talisman into the bag. */
public final class TalismanBagDeposits {
    private static boolean failureLogged;

    private TalismanBagDeposits() {
    }

    public static boolean tryDeposit(Player player, EquipmentSlot hand, ItemStack eventItem) {
        try {
            return TalismanBag.INSTANCE.tryDeposit(player, hand, eventItem);
        } catch (RuntimeException exception) {
            if (!failureLogged) {
                failureLogged = true;
                TalismansPluginKt.getPlugin().getLogger().log(
                        Level.SEVERE,
                        "Could not deposit a held talisman; the held item was preserved",
                        exception
                );
            }
            return false;
        }
    }
}
