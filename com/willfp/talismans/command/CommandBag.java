package com.willfp.talismans.command;

import com.willfp.eco.core.command.impl.Subcommand;
import com.willfp.talismans.TalismansPluginKt;
import com.willfp.talismans.bag.TalismanBag;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/** Opens the player's talisman bag. */
public final class CommandBag extends Subcommand {
    public static final CommandBag INSTANCE = new CommandBag();

    private CommandBag() {
        super(TalismansPluginKt.getPlugin(), "bag", "talismans.command.bag", true);
    }

    @Override
    public void onExecute(CommandSender sender, List<String> args) {
        TalismanBag.INSTANCE.open((Player) sender);
    }
}
