package com.willfp.talismans.libreforge;

import com.willfp.eco.core.config.interfaces.Config;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.Unit;
import com.willfp.eco.libs.kotlin.collections.CollectionsKt;
import com.willfp.eco.libs.kotlin.collections.SetsKt;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.eco.util.ListUtilsExtensions;
import com.willfp.libreforge.ArgType;
import com.willfp.libreforge.ConfigArguments;
import com.willfp.libreforge.ConfigArgumentsBuilder;
import com.willfp.libreforge.ConfigArgumentsKt;
import com.willfp.libreforge.Dispatcher;
import com.willfp.libreforge.ItemProvidedHolder;
import com.willfp.libreforge.NoCompileData;
import com.willfp.libreforge.ProvidedHolder;
import com.willfp.libreforge.conditions.Condition;
import com.willfp.talismans.talismans.Talisman;
import com.willfp.talismans.talismans.util.TalismanChecks;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from ConditionHasTalisman.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0003\u0010\u0004J,\u0010\u0011\u001a\u00020\u00122\n\u0010\u0013\u001a\u0006\u0012\u0002\b\u00030\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0002H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000eX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001a",
   d2 = {
         "Lcom/willfp/talismans/libreforge/ConditionHasTalisman;",
         "Lcom/willfp/libreforge/conditions/Condition;",
         "Lcom/willfp/libreforge/NoCompileData;",
         "<init>",
         "()V",
         "description",
         "",
         "getDescription",
         "()Ljava/lang/String;",
         "categories",
         "",
         "getCategories",
         "()Ljava/util/Set;",
         "arguments",
         "Lcom/willfp/libreforge/ConfigArguments;",
         "getArguments",
         "()Lcom/willfp/libreforge/ConfigArguments;",
         "isMet",
         "",
         "dispatcher",
         "Lcom/willfp/libreforge/Dispatcher;",
         "config",
         "Lcom/willfp/eco/core/config/interfaces/Config;",
         "holder",
         "Lcom/willfp/libreforge/ProvidedHolder;",
         "compileData",
         "core-plugin"
   }
)
@SourceDebugExtension(
   "SMAP\nConditionHasTalisman.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ConditionHasTalisman.kt\ncom/willfp/talismans/libreforge/ConditionHasTalisman\n+ 2 Dispatcher.kt\ncom/willfp/libreforge/DispatcherKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,46:1\n33#2:47\n1586#3:48\n1661#3,3:49\n812#3,12:52\n1586#3:64\n1661#3,3:65\n*S KotlinDebug\n*F\n+ 1 ConditionHasTalisman.kt\ncom/willfp/talismans/libreforge/ConditionHasTalisman\n*L\n37#1:47\n40#1:48\n40#1:49,3\n41#1:52,12\n42#1:64\n42#1:65,3\n*E\n"
)
public final class ConditionHasTalisman extends Condition<NoCompileData> {
   @NotNull
   public static final ConditionHasTalisman INSTANCE = new ConditionHasTalisman();
   @NotNull
   private static final String description = "Passes when the player has the specified talisman.";
   @NotNull
   private static final Set<String> categories = SetsKt.setOf("inventory");
   @NotNull
   private static final ConfigArguments arguments = ConfigArgumentsKt.arguments(ConditionHasTalisman::arguments$lambda$0);

   private ConditionHasTalisman() {
      super("has_talisman");
   }

   @NotNull
   public String getDescription() {
      return description;
   }

   @NotNull
   public Set<String> getCategories() {
      return categories;
   }

   @NotNull
   public ConfigArguments getArguments() {
      return arguments;
   }

   public boolean isMet(@NotNull Dispatcher<?> dispatcher, @NotNull Config config, @NotNull ProvidedHolder holder, @NotNull NoCompileData compileData) {
      Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
      Intrinsics.checkNotNullParameter(config, "config");
      Intrinsics.checkNotNullParameter(holder, "holder");
      Intrinsics.checkNotNullParameter(compileData, "compileData");
      Dispatcher $this$get$iv = dispatcher;
      int $i$f$get = 0;
      Player var10000 = (Player)$this$get$iv.getDispatcher();
      if (!(var10000 instanceof Player)) {
         var10000 = null;
      }

      var10000 = var10000;
      if (var10000 == null) {
         return false;
      }

      Player player = var10000;
      Iterable $this$map$iv = TalismanChecks.getTalismansOnPlayer(player);
      int $i$f$map = 0;
      Iterable var21 = $this$map$iv;
      var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
      int $i$f$mapTo = 0;

      for (Object item$iv$iv : var21) {
         ItemProvidedHolder it = (ItemProvidedHolder)item$iv$iv;
         Collection var15 = destination$iv$iv;
         int var14/* $VF was: $i$a$-map-ConditionHasTalisman$isMet$1 */ = 0;
         var15.add(it.getHolder());
      }

      $this$map$iv = (Iterable & List)destination$iv$iv;
      int $i$f$filterIsInstance = 0;
      Iterable var22 = $this$map$iv;
      destination$iv$iv = new ArrayList();
      $i$f$mapTo = 0;

      for (Object element$iv$iv : var22) {
         if (element$iv$iv instanceof Talisman) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      $this$map$iv = (Iterable & List)destination$iv$iv;
      int $i$f$mapx = 0;
      Iterable var23 = $this$map$iv;
      destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
      $i$f$mapTo = 0;

      for (Object item$iv$iv : var23) {
         Talisman var32 = (Talisman)item$iv$iv;
         Collection var34 = destination$iv$iv;
         int var33/* $VF was: $i$a$-map-ConditionHasTalisman$isMet$2 */ = 0;
         var34.add(var32.getId().getKey());
      }

      var var36 = (Iterable & List)destination$iv$iv;
      String var10001 = config.getString("talisman");
      Intrinsics.checkNotNullExpressionValue(var10001, "getString(...)");
      return ListUtilsExtensions.containsIgnoreCase(var36, var10001);
   }

   private static final Unit arguments$lambda$0(ConfigArgumentsBuilder $this$arguments) {
      Intrinsics.checkNotNullParameter($this$arguments, "$this$arguments");
      ConfigArgumentsBuilder.require$default(
         $this$arguments,
         "talisman",
         "You must specify the talisman!",
         "The id of the talisman to check for.",
         ArgType.STRING,
         null,
         null,
         "experience_1",
         48,
         null
      );
      return Unit.INSTANCE;
   }
}
