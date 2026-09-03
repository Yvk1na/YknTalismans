package com.willfp.talismans.command;

import com.willfp.eco.core.command.impl.Subcommand;
import com.willfp.eco.core.drops.DropQueue;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.collections.CollectionsKt;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.eco.libs.kotlin.text.StringsKt;
import com.willfp.talismans.TalismansPluginKt;
import com.willfp.talismans.talismans.Talisman;
import com.willfp.talismans.talismans.Talismans;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from CommandGive.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016J$\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\r",
   d2 = {
         "Lcom/willfp/talismans/command/CommandGive;",
         "Lcom/willfp/eco/core/command/impl/Subcommand;",
         "<init>",
         "()V",
         "numbers",
         "",
         "",
         "onExecute",
         "",
         "sender",
         "Lorg/bukkit/command/CommandSender;",
         "args",
         "tabComplete",
         "core-plugin"
   }
)
@SourceDebugExtension(
   "SMAP\nCommandGive.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CommandGive.kt\ncom/willfp/talismans/command/CommandGive\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,113:1\n1586#2:114\n1661#2,3:115\n1586#2:118\n1661#2,3:119\n1586#2:122\n1661#2,3:123\n*S KotlinDebug\n*F\n+ 1 CommandGive.kt\ncom/willfp/talismans/command/CommandGive\n*L\n81#1:114\n81#1:115,3\n87#1:118\n87#1:119,3\n95#1:122\n95#1:123,3\n*E\n"
)
public final class CommandGive extends Subcommand {
   @NotNull
   public static final CommandGive INSTANCE = new CommandGive();
   @NotNull
   private static final List<String> numbers;

   private CommandGive() {
      super(TalismansPluginKt.getPlugin(), "give", "talismans.command.give", false);
   }

   public void onExecute(@NotNull CommandSender sender, @NotNull List<String> args) {
      Intrinsics.checkNotNullParameter(sender, "sender");
      Intrinsics.checkNotNullParameter(args, "args");
      if (args.isEmpty()) {
         sender.sendMessage(this.getPlugin().getLangYml().getMessage("needs-player"));
      } else if (args.size() == 1) {
         sender.sendMessage(this.getPlugin().getLangYml().getMessage("needs-talisman"));
      } else {
         int amount = 1;
         if (args.size() > 2) {
            Object var10000 = args.get(2);
            Intrinsics.checkNotNullExpressionValue(var10000, "get(...)");
            Integer var12 = StringsKt.toIntOrNull((String)var10000);
            amount = var12 != null ? var12 : 1;
         }

         String var13 = (String)args.get(0);
         Intrinsics.checkNotNullExpressionValue(var13, "get(...)");
         String receiverName = var13;
         Player receiver = Bukkit.getPlayer(receiverName);
         if (receiver == null) {
            sender.sendMessage(this.getPlugin().getLangYml().getMessage("invalid-player"));
         } else {
            var13 = args.get(1);
            Intrinsics.checkNotNullExpressionValue(var13, "get(...)");
            String talismanName = (String)var13;
            Talisman talisman = Talismans.getByID(talismanName);
            if (talisman == null) {
               sender.sendMessage(this.getPlugin().getLangYml().getMessage("invalid-talisman"));
            } else {
               String message = this.getPlugin().getLangYml().getMessage("give-success");
               String itemStack = message;
               Intrinsics.checkNotNull(itemStack);
               var13 = StringsKt.replace$default(itemStack, "%talisman%", talisman.getName(), false, 4, null);
               String var10002 = receiver.getName();
               Intrinsics.checkNotNullExpressionValue(var10002, "getName(...)");
               message = StringsKt.replace$default(var13, "%recipient%", var10002, false, 4, null);
               sender.sendMessage(message);
               ItemStack itemStackx = talisman.getItemStack();
               itemStackx.setAmount(amount);
               new DropQueue(receiver).addItem(itemStackx).forceTelekinesis().push();
            }
         }
      }
   }

   @NotNull
   public List<String> tabComplete(@NotNull CommandSender sender, @NotNull List<String> args) {
      Intrinsics.checkNotNullParameter(sender, "sender");
      Intrinsics.checkNotNullParameter(args, "args");
      List completions = new ArrayList();
      if (args.isEmpty()) {
         Iterable $this$map$iv = Talismans.values();
         int $i$f$map = 0;
         Iterable var20 = $this$map$iv;
         var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
         int $i$f$mapTo = 0;

         for (Object item$iv$iv : var20) {
            Talisman var30 = (Talisman)item$iv$iv;
            Collection var34 = destination$iv$iv;
            int var32/* $VF was: $i$a$-map-CommandGive$tabComplete$1 */ = 0;
            var34.add(var30.getId().getKey());
         }

         return (List<String>)destination$iv$iv;
      } else {
         if (args.size() == 1) {
            String var10000 = (String)args.get(0);
            Collection var10001 = Bukkit.getOnlinePlayers();
            Intrinsics.checkNotNullExpressionValue(var10001, "getOnlinePlayers(...)");
            Iterable $this$map$iv = var10001;
            String var13 = var10000;
            int $i$f$map = 0;
            Iterable $this$mapTo$iv$iv = $this$map$iv;
            var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            int $i$f$mapTo = 0;

            for (Object item$iv$iv : $this$mapTo$iv$iv) {
               Player it = (Player)item$iv$iv;
               Collection var14 = destination$iv$iv;
               int var12/* $VF was: $i$a$-map-CommandGive$tabComplete$2 */ = 0;
               var14.add(it.getName());
            }

            StringUtil.copyPartialMatches(var13, (Iterable & List)destination$iv$iv, completions);
         }

         if (args.size() == 2) {
            String var36 = (String)args.get(1);
            Iterable var15 = Talismans.values();
            String var33 = var36;
            int $i$f$map = 0;
            Iterable var19 = var15;
            var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(var15, 10));
            int $i$f$mapTo = 0;

            for (Object item$iv$iv : var19) {
               Talisman var29 = (Talisman)item$iv$iv;
               Collection var35 = destination$iv$iv;
               int var31/* $VF was: $i$a$-map-CommandGive$tabComplete$3 */ = 0;
               var35.add(var29.getId().getKey());
            }

            StringUtil.copyPartialMatches(var33, (Iterable & List)destination$iv$iv, completions);
         }

         if (args.size() == 3) {
            StringUtil.copyPartialMatches((String)args.get(2), numbers, completions);
         }

         return completions;
      }
   }

   static {
      String[] var0 = new String[]{"1", "2", "3", "4", "5", "10", "32", "64"};
      numbers = CollectionsKt.listOf(var0);
   }
}
