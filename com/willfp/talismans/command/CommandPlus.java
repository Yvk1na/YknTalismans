package com.willfp.talismans.command;

import com.willfp.eco.core.command.impl.Subcommand;
import com.willfp.talismans.TalismansPluginKt;
import com.willfp.talismans.bag.TalismanBag;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/** Adds persistent slots to the executing player's talisman bag. */
public final class CommandPlus extends Subcommand {
    public static final CommandPlus INSTANCE = new CommandPlus();

    private CommandPlus() {
        super(TalismansPluginKt.getPlugin(), "plus", "talismans.command.plus", true);
    }

    @Override
    public void onExecute(CommandSender sender, List<String> args) {
        Player player = (Player) sender;
        Integer amount = parsePositiveAmount(args);
        if (amount == null) {
            player.sendMessage(color("&c用法: /ykntalismans plus <正整数>"));
            return;
        }

        try {
            TalismanBag.CapacityChange change = TalismanBag.INSTANCE.adjustCapacity(player, amount);
            if (change.actualDelta() == 0) {
                player.sendMessage(color("&e护符袋容量未改变，已达到容量上限。"));
                return;
            }
            player.sendMessage(color(
                    "&a护符袋增加了 &f" + change.actualDelta()
                            + " &a格，当前容量为 &f" + change.newCapacity() + " &a格。"
            ));
        } catch (RuntimeException exception) {
            TalismansPluginKt.getPlugin().getLogger().log(
                    Level.SEVERE,
                    "Could not increase " + player.getName() + "'s talisman bag capacity",
                    exception
            );
            player.sendMessage(color("&c护符袋容量保存失败，请稍后再试。"));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, List<String> args) {
        return args.size() <= 1 ? List.of("1", "5", "9", "45") : List.of();
    }

    private static Integer parsePositiveAmount(List<String> args) {
        if (args.size() != 1) {
            return null;
        }
        try {
            int amount = Integer.parseInt(args.get(0));
            return amount > 0 ? amount : null;
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    private static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
