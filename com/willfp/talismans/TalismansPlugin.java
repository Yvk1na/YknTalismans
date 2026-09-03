package com.willfp.talismans;

import com.willfp.eco.core.bstats.EcoMetricsChart;
import com.willfp.eco.core.bstats.EcoMetricsChart.SimplePie;
import com.willfp.eco.core.bstats.EcoMetricsChart.SingleLine;
import com.willfp.eco.core.command.impl.PluginCommand;
import com.willfp.eco.core.display.DisplayModule;
import com.willfp.eco.core.items.Items;
import com.willfp.eco.core.items.tag.ItemTag;
import com.willfp.eco.core.registry.Registrable;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.collections.ArraysKt;
import com.willfp.eco.libs.kotlin.collections.CollectionsKt;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.libreforge.HolderProviderKt;
import com.willfp.libreforge.conditions.Conditions;
import com.willfp.libreforge.slot.SlotTypes;
import com.willfp.talismans.bag.TalismanBag;
import com.willfp.talismans.command.CommandTalismans;
import com.willfp.talismans.display.TalismanDisplay;
import com.willfp.talismans.libreforge.ConditionHasTalisman;
import com.willfp.talismans.libreforge.SlotTypeTalisman;
import com.willfp.talismans.libreforge.loader.LibreforgePlugin;
import com.willfp.talismans.libreforge.loader.configs.ConfigCategory;
import com.willfp.talismans.talismans.TalismanTag;
import com.willfp.talismans.talismans.Talismans;
import com.willfp.talismans.talismans.util.BlockPlaceListener;
import com.willfp.talismans.talismans.util.DiscoverRecipeListener;
import com.willfp.talismans.talismans.util.TalismanChecks;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from TalismansPlugin.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0014J\b\u0010\u0006\u001a\u00020\u0005H\u0014J\b\u0010\u0007\u001a\u00020\u0005H\u0014J\u000e\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tH\u0016J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\tH\u0014J\u000e\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\tH\u0014J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\tH\u0014J\u000e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\tH\u0016¨\u0006\u0013",
   d2 = {
         "Lcom/willfp/talismans/TalismansPlugin;",
         "Lcom/willfp/talismans/libreforge/loader/LibreforgePlugin;",
         "<init>",
         "()V",
         "handleLoad",
         "",
         "handleEnable",
         "handleReload",
         "loadConfigCategories",
         "",
         "Lcom/willfp/talismans/libreforge/loader/configs/ConfigCategory;",
         "loadPluginCommands",
         "Lcom/willfp/eco/core/command/impl/PluginCommand;",
         "loadListeners",
         "Lorg/bukkit/event/Listener;",
         "loadDisplayModules",
         "Lcom/willfp/eco/core/display/DisplayModule;",
         "getCustomCharts",
         "Lcom/willfp/eco/core/bstats/EcoMetricsChart;",
         "core-plugin"
   }
)
@SourceDebugExtension(
   "SMAP\nTalismansPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TalismansPlugin.kt\ncom/willfp/talismans/TalismansPlugin\n+ 2 HolderProvider.kt\ncom/willfp/libreforge/HolderProviderKt\n*L\n1#1,120:1\n129#2,9:121\n155#2,6:130\n*S KotlinDebug\n*F\n+ 1 TalismansPlugin.kt\ncom/willfp/talismans/TalismansPlugin\n*L\n59#1:121,9\n63#1:130,6\n*E\n"
)
public final class TalismansPlugin extends LibreforgePlugin {
   public TalismansPlugin() {
      TalismansPluginKt.access$setPlugin$p(this);
      TalismanChecks.registerItemStackProvider(TalismansPlugin::_init_$lambda$0);
      TalismanChecks.registerItemStackProvider(TalismansPlugin::_init_$lambda$1);
      TalismanChecks.registerItemStackProvider(TalismansPlugin::_init_$lambda$2);
   }

   protected void handleLoad() {
      Items.registerTag((ItemTag)TalismanTag.INSTANCE);
      Conditions.INSTANCE.register((Registrable)ConditionHasTalisman.INSTANCE);
   }

   protected void handleEnable() {
      SlotTypes.INSTANCE.register((Registrable)SlotTypeTalisman.INSTANCE);
      int $i$f$registerSpecificHolderProvider = 0;
      HolderProviderKt.registerHolderProvider(new TalismansPlugin$handleEnable$$inlined$registerSpecificHolderProvider$1());
      $i$f$registerSpecificHolderProvider = 0;
      HolderProviderKt.registerRefreshFunction(new TalismansPlugin$handleEnable$$inlined$registerSpecificRefreshFunction$1());
   }

   protected void handleReload() {
      TalismanBag.INSTANCE.update$core_plugin();
      TalismanChecks.INSTANCE.reload$core_plugin();
   }

   @NotNull
   @Override
   public List<ConfigCategory> loadConfigCategories() {
      return CollectionsKt.listOf(Talismans.INSTANCE);
   }

   @NotNull
   protected List<PluginCommand> loadPluginCommands() {
      return CollectionsKt.listOf(CommandTalismans.INSTANCE);
   }

   @NotNull
   protected List<Listener> loadListeners() {
      Listener[] var1 = new Listener[]{BlockPlaceListener.INSTANCE, DiscoverRecipeListener.INSTANCE};
      return CollectionsKt.listOf(var1);
   }

   @NotNull
   protected List<DisplayModule> loadDisplayModules() {
      return CollectionsKt.listOf(TalismanDisplay.INSTANCE);
   }

   @NotNull
   public List<EcoMetricsChart> getCustomCharts() {
      EcoMetricsChart[] var1 = new EcoMetricsChart[]{
         new SingleLine("total_talismans", TalismansPlugin::getCustomCharts$lambda$0),
         new SimplePie("discover_recipes", TalismansPlugin::getCustomCharts$lambda$1),
         new SimplePie("read_inventory", TalismansPlugin::getCustomCharts$lambda$2),
         new SimplePie("read_enderchest", TalismansPlugin::getCustomCharts$lambda$3),
         new SimplePie("read_shulkerboxes", TalismansPlugin::getCustomCharts$lambda$4),
         new SimplePie("top_level_only", TalismansPlugin::getCustomCharts$lambda$5),
         new SimplePie("offhand_only", TalismansPlugin::getCustomCharts$lambda$6)
      };
      return CollectionsKt.listOf(var1);
   }

   private static final List _init_$lambda$0(Player it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return TalismanBag.INSTANCE.getTalismans(it);
   }

   private static final List _init_$lambda$1(TalismansPlugin this$0, Player it) {
      Intrinsics.checkNotNullParameter(it, "it");
      List var10000;
      if (this$0.getConfigYml().getBool("read-inventory") && !this$0.getConfigYml().getBool("offhand-only")) {
         ItemStack[] var2 = it.getInventory().getContents();
         Intrinsics.checkNotNullExpressionValue(var2, "getContents(...)");
         var10000 = ArraysKt.filterNotNull(var2);
      } else {
         var10000 = CollectionsKt.emptyList();
      }

      return var10000;
   }

   private static final List _init_$lambda$2(TalismansPlugin this$0, Player it) {
      Intrinsics.checkNotNullParameter(it, "it");
      List var10000;
      if (this$0.getConfigYml().getBool("read-enderchest") && !this$0.getConfigYml().getBool("offhand-only")) {
         Inventory var2 = it.getEnderChest();
         if (var2 != null) {
            ItemStack[] var3 = var2.getContents();
            if (var3 != null) {
               var10000 = ArraysKt.filterNotNull(var3);
               if (var10000 != null) {
                  return var10000;
               }
            }
         }

         var10000 = CollectionsKt.emptyList();
      } else {
         var10000 = CollectionsKt.emptyList();
      }

      return var10000;
   }

   private static final int getCustomCharts$lambda$0() {
      return Talismans.values().size();
   }

   private static final String getCustomCharts$lambda$1(TalismansPlugin this$0) {
      return this$0.getConfigYml().getBool("crafting.discover") ? "enabled" : "disabled";
   }

   private static final String getCustomCharts$lambda$2(TalismansPlugin this$0) {
      return this$0.getConfigYml().getBool("read-inventory") ? "enabled" : "disabled";
   }

   private static final String getCustomCharts$lambda$3(TalismansPlugin this$0) {
      return this$0.getConfigYml().getBool("read-enderchest") ? "enabled" : "disabled";
   }

   private static final String getCustomCharts$lambda$4(TalismansPlugin this$0) {
      return this$0.getConfigYml().getBool("read-shulkerboxes") ? "enabled" : "disabled";
   }

   private static final String getCustomCharts$lambda$5(TalismansPlugin this$0) {
      return this$0.getConfigYml().getBool("top-level-only") ? "enabled" : "disabled";
   }

   private static final String getCustomCharts$lambda$6(TalismansPlugin this$0) {
      return this$0.getConfigYml().getBool("offhand-only") ? "enabled" : "disabled";
   }
}
