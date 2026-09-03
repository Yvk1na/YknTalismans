package com.willfp.talismans.command;

import com.willfp.eco.core.command.impl.Subcommand;
import com.willfp.talismans.TalismansPluginKt;
import com.willfp.talismans.admin.AdminGuiManager;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/** Opens the in-game talisman administration menu. */
public final class CommandAdmin extends Subcommand {
    public static final CommandAdmin INSTANCE = new CommandAdmin();

    private CommandAdmin() {
        super(TalismansPluginKt.getPlugin(), "admin", "talismans.command.admin", true);
        AdminGuiManager.ensureRegistered();
    }

    @Override
    public void onExecute(CommandSender sender, List<String> args) {
        AdminGuiManager.openList((Player) sender);
    }
}
