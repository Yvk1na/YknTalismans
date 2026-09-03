package com.willfp.talismans.command;

import com.willfp.eco.core.command.impl.Subcommand;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.talismans.TalismansPluginKt;
import com.willfp.talismans.bag.TalismanBag;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from CommandBag.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0016¨\u0006\u000b",
   d2 = {
         "Lcom/willfp/talismans/command/CommandBag;",
         "Lcom/willfp/eco/core/command/impl/Subcommand;",
         "<init>",
         "()V",
         "onExecute",
         "",
         "sender",
         "Lorg/bukkit/command/CommandSender;",
         "args",
         "",
         "",
         "core-plugin"
   }
)
public final class CommandBag extends Subcommand {
   @NotNull
   public static final CommandBag INSTANCE = new CommandBag();

   private CommandBag() {
      super(TalismansPluginKt.getPlugin(), "bag", "talismans.command.bag", true);
   }

   public void onExecute(@NotNull CommandSender sender, @NotNull List<String> args) {
      Intrinsics.checkNotNullParameter(sender, "sender");
      Intrinsics.checkNotNullParameter(args, "args");
      TalismanBag.INSTANCE.open((Player)sender);
   }
}
