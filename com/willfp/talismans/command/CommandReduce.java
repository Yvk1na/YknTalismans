package com.willfp.talismans.command;

import com.willfp.eco.core.command.impl.Subcommand;
import com.willfp.talismans.TalismansPluginKt;
import com.willfp.talismans.bag.TalismanBag;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/** Removes persistent slots from the executing player's talisman bag. */
public final class CommandReduce extends Subcommand {
    public static final CommandReduce INSTANCE = new CommandReduce();

    private CommandReduce() {
        super(TalismansPluginKt.getPlugin(), "reduce", "talismans.command.reduce", true);
    }

    @Override
    public void onExecute(CommandSender sender, List<String> args) {
        Player player = (Player) sender;
        Integer amount = parsePositiveAmount(args);
        if (amount == null) {
            player.sendMessage(color("&c用法: /ykntalismans reduce <正整数>"));
            return;
        }

        try {
            TalismanBag.CapacityChange change = TalismanBag.INSTANCE.adjustCapacity(player, -amount);
            int removed = -change.actualDelta();
            if (removed == 0) {
                player.sendMessage(color("&e护符袋容量已为 0 格。"));
                return;
            }
            player.sendMessage(color(
                    "&a护符袋减少了 &f" + removed
                            + " &a格，当前容量为 &f" + change.newCapacity() + " &a格。"
            ));
            if (change.returnedTalismans() > 0) {
                player.sendMessage(color(
                        "&e被移除格子中的 &f" + change.returnedTalismans()
                                + " &e个护符已安全退回背包。"
                ));
            }
        } catch (TalismanBag.InsufficientInventorySpaceException exception) {
            player.sendMessage(color(
                    "&c无法缩减：需要先在背包中腾出足够空间，以接收 "
                            + exception.getRequiredSlots() + " 个护符。"
            ));
        } catch (RuntimeException exception) {
            TalismansPluginKt.getPlugin().getLogger().log(
                    Level.SEVERE,
                    "Could not reduce " + player.getName() + "'s talisman bag capacity",
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
