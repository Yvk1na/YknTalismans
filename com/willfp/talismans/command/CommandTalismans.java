package com.willfp.talismans.command;

import com.willfp.eco.core.command.CommandBase;
import com.willfp.eco.core.command.impl.PluginCommand;
import com.willfp.talismans.TalismansPluginKt;
import java.util.List;
import org.bukkit.command.CommandSender;

/** Root command for YknTalismans. */
public final class CommandTalismans extends PluginCommand {
    public static final CommandTalismans INSTANCE = new CommandTalismans();

    private CommandTalismans() {
        super(TalismansPluginKt.getPlugin(), "ykntalismans", "talismans.command.talismans", false);
    }

    @Override
    public void onExecute(CommandSender sender, List<String> args) {
        sender.sendMessage(getPlugin().getLangYml().getMessage("invalid-command"));
    }

    @Override
    public List<String> getAliases() {
        return List.of();
    }

    static {
        INSTANCE.addSubcommand((CommandBase) CommandReload.INSTANCE)
                .addSubcommand((CommandBase) CommandGive.INSTANCE)
                .addSubcommand((CommandBase) CommandBag.INSTANCE)
                .addSubcommand((CommandBase) CommandPlus.INSTANCE)
                .addSubcommand((CommandBase) CommandReduce.INSTANCE)
                .addSubcommand((CommandBase) CommandAdmin.INSTANCE);
    }
}
