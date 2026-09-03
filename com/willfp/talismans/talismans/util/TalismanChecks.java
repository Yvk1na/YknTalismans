package com.willfp.talismans.talismans.util;

import com.willfp.eco.core.cache.EcoCache;
import com.willfp.eco.core.fast.FastItemStackExtensions;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.collections.ArraysKt;
import com.willfp.eco.libs.kotlin.collections.CollectionsKt;
import com.willfp.eco.libs.kotlin.collections.MapsKt;
import com.willfp.eco.libs.kotlin.jvm.JvmStatic;
import com.willfp.eco.libs.kotlin.jvm.functions.Function1;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.eco.libs.kotlin.ranges.RangesKt;
import com.willfp.libreforge.ItemProvidedHolder;
import com.willfp.talismans.TalismansPluginKt;
import com.willfp.talismans.talismans.Talisman;
import com.willfp.talismans.talismans.Talismans;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.ShulkerBox;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from TalismanChecks.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\u0012\u001a\u00020\u00102\b\u0010\u0012\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u0014\u0010\u0015\u001a\u0004\u0018\u00010\u00142\b\u0010\u0012\u001a\u0004\u0018\u00010\nH\u0007J3\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\n0\u00072\u0006\u0010\u0017\u001a\u00020\u00062\u0016\u0010\u0018\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\n0\u0019\"\u0004\u0018\u00010\nH\u0007¢\u0006\u0002\u0010\u001aJ3\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\u0017\u001a\u00020\u00062\u0016\u0010\u0018\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\n0\u0019\"\u0004\u0018\u00010\nH\u0007¢\u0006\u0002\u0010\u001aJ\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u0017\u001a\u00020\u0006H\u0007J\"\u0010\u001e\u001a\u00020\u001d2\u0018\u0010\u001f\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u000e0\rH\u0007J\u0018\u0010 \u001a\u00020\u00102\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\r\u0010!\u001a\u00020\u001dH\u0000¢\u0006\u0002\b\"R \u0010\u0004\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R \u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R&\u0010\u000b\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\u000e0\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006#",
   d2 = {
         "Lcom/willfp/talismans/talismans/util/TalismanChecks;",
         "",
         "<init>",
         "()V",
         "CACHED_TALISMANS",
         "Lcom/willfp/eco/core/cache/EcoCache;",
         "Lorg/bukkit/entity/Player;",
         "",
         "Lcom/willfp/libreforge/ItemProvidedHolder;",
         "CACHED_TALISMAN_ITEMS",
         "Lorg/bukkit/inventory/ItemStack;",
         "PROVIDERS",
         "",
         "Ljava/util/function/Function;",
         "",
         "readShulkerBoxes",
         "",
         "offhandOnly",
         "item",
         "talisman",
         "Lcom/willfp/talismans/talismans/Talisman;",
         "getTalismanOnItem",
         "getTalismanItemsOnPlayer",
         "player",
         "extra",
         "",
         "(Lorg/bukkit/entity/Player;[Lorg/bukkit/inventory/ItemStack;)Ljava/util/Set;",
         "getTalismansOnPlayer",
         "clearCache",
         "",
         "registerItemStackProvider",
         "provider",
         "hasTalisman",
         "reload",
         "reload$core_plugin",
         "core-plugin"
   }
)
@SourceDebugExtension(
   "SMAP\nTalismanChecks.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TalismanChecks.kt\ncom/willfp/talismans/talismans/util/TalismanChecks\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n+ 4 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,222:1\n1586#2:223\n1661#2,3:224\n1300#2,2:227\n1315#2,4:229\n507#3,7:233\n129#4:240\n158#4,3:241\n*S KotlinDebug\n*F\n+ 1 TalismanChecks.kt\ncom/willfp/talismans/talismans/util/TalismanChecks\n*L\n210#1:223\n210#1:224,3\n168#1:227,2\n168#1:229,4\n169#1:233,7\n170#1:240\n170#1:241,3\n*E\n"
)
public final class TalismanChecks {
   @NotNull
   public static final TalismanChecks INSTANCE = new TalismanChecks();
   @NotNull
   private static final EcoCache<Player, Set<ItemProvidedHolder>> CACHED_TALISMANS;
   @NotNull
   private static final EcoCache<Player, Set<ItemStack>> CACHED_TALISMAN_ITEMS;
   @NotNull
   private static final Set<Function<Player, List<ItemStack>>> PROVIDERS = new HashSet<>();
   private static boolean readShulkerBoxes = true;
   private static boolean offhandOnly;

   private TalismanChecks() {
   }

   @JvmStatic
   public static final boolean item(@Nullable ItemStack item, @NotNull Talisman talisman) {
      Intrinsics.checkNotNullParameter(talisman, "talisman");
      if (item == null) {
         return false;
      }

      ItemMeta var10000 = item.getItemMeta();
      if (var10000 == null) {
         return false;
      }

      ItemMeta meta = var10000;
      PersistentDataContainer var4 = meta.getPersistentDataContainer();
      Intrinsics.checkNotNullExpressionValue(var4, "getPersistentDataContainer(...)");
      PersistentDataContainer container = var4;
      return container.has(talisman.getId(), PersistentDataType.INTEGER);
   }

   @JvmStatic
   @Nullable
   public static final Talisman getTalismanOnItem(@Nullable ItemStack item) {
      if (item == null) {
         return null;
      }

      TalismanUtils var10000 = TalismanUtils.INSTANCE;
      Material var10001 = item.getType();
      Intrinsics.checkNotNullExpressionValue(var10001, "getType(...)");
      if (!var10000.isTalismanMaterial(var10001)) {
         return null;
      }

      PersistentDataContainer var3 = FastItemStackExtensions.fast(item).getPersistentDataContainer();
      Intrinsics.checkNotNullExpressionValue(var3, "getPersistentDataContainer(...)");
      PersistentDataContainer container = var3;
      String var4 = (String)container.get(TalismansPluginKt.getPlugin().getNamespacedKeyFactory().create("talisman"), PersistentDataType.STRING);
      if (var4 == null) {
         return null;
      }

      String id = var4;
      return Talismans.getByID(id);
   }

   @JvmStatic
   @NotNull
   public static final Set<ItemStack> getTalismanItemsOnPlayer(@NotNull Player player, @NotNull ItemStack... extra) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(extra, "extra");
      Object var10000 = CACHED_TALISMAN_ITEMS.get(player, TalismanChecks::getTalismanItemsOnPlayer$lambda$1);
      Intrinsics.checkNotNullExpressionValue(var10000, "get(...)");
      return (Set<ItemStack>)var10000;
   }

   @JvmStatic
   @NotNull
   public static final Set<ItemProvidedHolder> getTalismansOnPlayer(@NotNull Player player, @NotNull ItemStack... extra) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(extra, "extra");
      Object var10000 = CACHED_TALISMANS.get(player, TalismanChecks::getTalismansOnPlayer$lambda$1);
      Intrinsics.checkNotNullExpressionValue(var10000, "get(...)");
      return (Set<ItemProvidedHolder>)var10000;
   }

   @JvmStatic
   public static final void clearCache(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      CACHED_TALISMAN_ITEMS.invalidate(player);
      CACHED_TALISMANS.invalidate(player);
   }

   @JvmStatic
   public static final void registerItemStackProvider(@NotNull Function<Player, List<ItemStack>> provider) {
      Intrinsics.checkNotNullParameter(provider, "provider");
      PROVIDERS.add(provider);
   }

   @JvmStatic
   public static final boolean hasTalisman(@NotNull Player player, @NotNull Talisman talisman) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(talisman, "talisman");
      Iterable $this$map$iv = getTalismansOnPlayer(player);
      int $i$f$map = 0;
      Iterable $this$mapTo$iv$iv = $this$map$iv;
      var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
      int $i$f$mapTo = 0;

      for (Object item$iv$iv : $this$mapTo$iv$iv) {
         ItemProvidedHolder it = (ItemProvidedHolder)item$iv$iv;
         Collection var11 = destination$iv$iv;
         int var10/* $VF was: $i$a$-map-TalismanChecks$hasTalisman$1 */ = 0;
         var11.add(it.getHolder());
      }

      return ((List)destination$iv$iv).contains(talisman);
   }

   public final void reload$core_plugin() {
      readShulkerBoxes = TalismansPluginKt.getPlugin().getConfigYml().getBool("read-shulkerboxes");
      offhandOnly = TalismansPluginKt.getPlugin().getConfigYml().getBool("offhand-only");
   }

   private static final Set getTalismanItemsOnPlayer$lambda$0(ItemStack[] $extra, Player it) {
      List contents = new ArrayList();
      List rawContents = new ArrayList();
      if (offhandOnly) {
         rawContents.clear();
         rawContents.add(it.getInventory().getItemInOffHand());
      }

      CollectionsKt.addAll(rawContents, $extra);

      for (Function provider : PROVIDERS) {
         Object var10001 = provider.apply(it);
         Intrinsics.checkNotNullExpressionValue(var10001, "apply(...)");
         rawContents.addAll((Collection)var10001);
      }

      for (ItemStack rawContent : rawContents) {
         if (rawContent != null) {
            if (readShulkerBoxes) {
               ItemMeta meta = rawContent.getItemMeta();
               if (meta instanceof BlockStateMeta) {
                  if (!((BlockStateMeta)meta).hasBlockState()) {
                     continue;
                  }

                  BlockState var10000 = ((BlockStateMeta)meta).getBlockState();
                  Intrinsics.checkNotNullExpressionValue(var10000, "getBlockState(...)");
                  BlockState state = var10000;
                  if (state instanceof ShulkerBox) {
                     ItemStack[] var19 = ((ShulkerBox)state).getInventory().getContents();
                     Intrinsics.checkNotNullExpressionValue(var19, "getContents(...)");
                     contents.addAll(ArraysKt.filterNotNull(var19));
                     continue;
                  }
               }
            }

            contents.add(rawContent);
         }
      }

      Map items = new LinkedHashMap();

      for (ItemStack itemStack : contents) {
         TalismanUtils.INSTANCE.convert(itemStack);
         Talisman var17 = getTalismanOnItem(itemStack);
         if (var17 != null) {
            Talisman talis = var17;
            int var18 = items.size();
            TalismanUtils var20 = TalismanUtils.INSTANCE;
            Intrinsics.checkNotNull(it);
            if (var18 >= var20.getLimit(it)) {
               break;
            }

            items.put(talis, itemStack);
         }
      }

      if (TalismansPluginKt.getPlugin().getConfigYml().getBool("top-level-only")) {
         Iterator var13 = MapsKt.toMap(items).entrySet().iterator();

         while (var13.hasNext()) {
            Talisman talisman = (Talisman)((Entry)var13.next()).getKey();

            for (Talisman lowerLevel = talisman.getLowerLevel(); lowerLevel != null; lowerLevel = lowerLevel.getLowerLevel()) {
               items.remove(lowerLevel);
            }
         }
      }

      return CollectionsKt.toSet(items.values());
   }

   private static final Set getTalismanItemsOnPlayer$lambda$1(Function1 $tmp0, Object p0) {
      return (Set)$tmp0.invoke(p0);
   }

   private static final Set getTalismansOnPlayer$lambda$0(Player $player, ItemStack[] $extra, Player it) {
      Iterable $this$map$iv = getTalismanItemsOnPlayer($player, Arrays.copyOf($extra, $extra.length));
      int $i$f$associateWith = 0;
      LinkedHashMap result$iv = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10)), 16));
      Collection destination$iv$iv = $this$map$iv;
      Map destination$iv$ivx = result$iv;
      int $i$f$associateWithTo = 0;

      for (Object element$iv$iv : destination$iv$iv) {
         ItemStack var11 = (ItemStack)element$iv$iv;
         Object var15 = element$iv$iv;
         Map var14 = destination$iv$ivx;
         int var12/* $VF was: $i$a$-associateWith-TalismanChecks$getTalismansOnPlayer$1$1 */ = 0;
         Talisman var16 = getTalismanOnItem(var11);
         var14.put(var15, var16);
      }

      Map var17 = destination$iv$ivx;
      $i$f$associateWith = 0;
      result$iv = new LinkedHashMap();

      for (Entry entry$iv : var17.entrySet()) {
         Talisman itx = (Talisman)entry$iv.getValue();
         int var29/* $VF was: $i$a$-filterValues-TalismanChecks$getTalismansOnPlayer$1$2 */ = 0;
         if (itx != null) {
            result$iv.put(entry$iv.getKey(), entry$iv.getValue());
         }
      }

      Map var18 = result$iv;
      $i$f$associateWith = 0;
      result$iv = var18;
      destination$iv$iv = new ArrayList(var18.size());
      int $i$f$mapTo = 0;

      for (Entry item$iv$iv : result$iv.entrySet()) {
         Entry var31 = item$iv$iv;
         Collection var34 = destination$iv$iv;
         int var32/* $VF was: $i$a$-map-TalismanChecks$getTalismansOnPlayer$1$3 */ = 0;
         ItemStack itemStack = (ItemStack)var31.getKey();
         Talisman talisman = (Talisman)var31.getValue();
         Intrinsics.checkNotNull(talisman);
         var34.add(new ItemProvidedHolder(talisman, itemStack));
      }

      return CollectionsKt.toSet((Iterable & List)destination$iv$iv);
   }

   private static final Set getTalismansOnPlayer$lambda$1(Function1 $tmp0, Object p0) {
      return (Set)$tmp0.invoke(p0);
   }

   static {
      EcoCache var10000 = EcoCache.builder().expireAfterWrite(Duration.ofSeconds(2L)).build();
      Intrinsics.checkNotNullExpressionValue(var10000, "build(...)");
      CACHED_TALISMANS = var10000;
      var10000 = EcoCache.builder().expireAfterWrite(Duration.ofSeconds(2L)).build();
      Intrinsics.checkNotNullExpressionValue(var10000, "build(...)");
      CACHED_TALISMAN_ITEMS = var10000;
   }
}
