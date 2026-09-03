package com.willfp.talismans.bag;

import com.willfp.eco.core.data.PlayerProfile;
import com.willfp.eco.core.data.ProfileExtensions;
import com.willfp.eco.core.data.keys.PersistentDataKey;
import com.willfp.eco.core.data.keys.PersistentDataKeyType;
import com.willfp.eco.core.drops.DropQueue;
import com.willfp.eco.core.gui.GUIHelperExtensions;
import com.willfp.eco.core.gui.menu.Menu;
import com.willfp.eco.core.gui.menu.MenuBuilder;
import com.willfp.eco.core.gui.slot.SlotBuilder;
import com.willfp.eco.core.integrations.placeholder.PlaceholderManager;
import com.willfp.eco.core.items.Items;
import com.willfp.eco.core.items.builder.ItemStackBuilder;
import com.willfp.eco.core.placeholder.PlayerPlaceholder;
import com.willfp.eco.core.placeholder.RegistrablePlaceholder;
import com.willfp.eco.core.recipe.parts.EmptyTestableItem;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.Unit;
import com.willfp.eco.libs.kotlin.collections.CollectionsKt;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.eco.libs.kotlin.text.StringsKt;
import com.willfp.eco.util.MenuUtils;
import com.willfp.talismans.TalismansPluginKt;
import com.willfp.talismans.ecomponent.MenuStateVar;
import com.willfp.talismans.talismans.util.TalismanChecks;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from TalismanBag.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\r\u0010\u0015\u001a\u00020\u0016H\u0000¢\u0006\u0002\b\u0017J\u000e\u0010\u0018\u001a\u00020\u00162\u0006\u0010\u0019\u001a\u00020\u0012J\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000e0\n2\u0006\u0010\u0019\u001a\u00020\u0012R\u001a\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tX\u0082.¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\n0\tX\u0082.¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n\u0000R \u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\n0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0011\u001a\u00020\u0006*\u00020\u00128BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014¨\u0006\u001b",
   d2 = {
         "Lcom/willfp/talismans/bag/TalismanBag;",
         "",
         "<init>",
         "()V",
         "menus",
         "",
         "",
         "Lcom/willfp/eco/core/gui/menu/Menu;",
         "legacyKey",
         "Lcom/willfp/eco/core/data/keys/PersistentDataKey;",
         "",
         "",
         "key",
         "emptyItem",
         "Lorg/bukkit/inventory/ItemStack;",
         "savedItems",
         "Ljava/util/UUID;",
         "bagSize",
         "Lorg/bukkit/entity/Player;",
         "getBagSize",
         "(Lorg/bukkit/entity/Player;)I",
         "update",
         "",
         "update$core_plugin",
         "open",
         "player",
         "getTalismans",
         "core-plugin"
   }
)
@SourceDebugExtension(
   "SMAP\nTalismanBag.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TalismanBag.kt\ncom/willfp/talismans/bag/TalismanBag\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,179:1\n1586#2:180\n1661#2,3:181\n1586#2:184\n1661#2,3:185\n832#2:188\n862#2,2:189\n777#2:191\n873#2,2:192\n1642#2,10:194\n1915#2:204\n1916#2:206\n1652#2:207\n777#2:208\n873#2,2:209\n1586#2:211\n1661#2,3:212\n1642#2,10:215\n1915#2:225\n1916#2:227\n1652#2:228\n832#2:229\n862#2,2:230\n777#2:232\n873#2,2:233\n1586#2:235\n1661#2,3:236\n832#2:239\n862#2,2:240\n1586#2:242\n1661#2,3:243\n777#2:246\n873#2,2:247\n1#3:205\n1#3:226\n*S KotlinDebug\n*F\n+ 1 TalismanBag.kt\ncom/willfp/talismans/bag/TalismanBag\n*L\n47#1:180\n47#1:181,3\n165#1:184\n165#1:185,3\n166#1:188\n166#1:189,2\n167#1:191\n167#1:192,2\n170#1:194,10\n170#1:204\n170#1:206\n170#1:207\n171#1:208\n171#1:209,2\n112#1:211\n112#1:212,3\n113#1:215,10\n113#1:225\n113#1:227\n113#1:228\n117#1:229\n117#1:230,2\n120#1:232\n120#1:233,2\n124#1:235\n124#1:236,3\n131#1:239\n131#1:240,2\n135#1:242\n135#1:243,3\n138#1:246\n138#1:247,2\n170#1:205\n113#1:226\n*E\n"
)
public final class TalismanBag {
   @NotNull
   public static final TalismanBag INSTANCE = new TalismanBag();
   @NotNull
   private static final Map<Integer, Menu> menus = new LinkedHashMap<>();
   private static PersistentDataKey<List<String>> legacyKey;
   private static PersistentDataKey<List<String>> key;
   private static ItemStack emptyItem;
   @NotNull
   private static final Map<UUID, List<ItemStack>> savedItems = new LinkedHashMap<>();

   private TalismanBag() {
   }

   private final int getBagSize(Player $this$bagSize) {
      int configSize = TalismansPluginKt.getPlugin().getConfigYml().getInt("bag.size");
      if (configSize > 0) {
         return configSize;
      }

      String prefix = "talismans.bagsize.";
      int highest = -1;
      Set var10000 = $this$bagSize.getEffectivePermissions();
      Intrinsics.checkNotNullExpressionValue(var10000, "getEffectivePermissions(...)");
      Iterable $this$map$iv = var10000;
      int $i$f$map = 0;
      Iterable $this$mapTo$iv$iv = $this$map$iv;
      var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
      int $i$f$mapTo = 0;

      for (Object item$iv$iv : $this$mapTo$iv$iv) {
         PermissionAttachmentInfo it = (PermissionAttachmentInfo)item$iv$iv;
         Collection var15 = destination$iv$iv;
         int var14/* $VF was: $i$a$-map-TalismanBag$bagSize$1 */ = 0;
         var15.add(it.getPermission());
      }

      for (Object var18 : (List)destination$iv$iv) {
         Intrinsics.checkNotNullExpressionValue(var18, "next(...)");
         String permission = (String)var18;
         if (StringsKt.startsWith$default(permission, prefix, false, 2, null)) {
            String var19 = permission.substring(StringsKt.lastIndexOf$default(permission, ".", 0, false, 6, null) + 1);
            Intrinsics.checkNotNullExpressionValue(var19, "substring(...)");
            $i$f$map = Integer.parseInt(var19);
            if ($i$f$map > highest) {
               highest = $i$f$map;
            }
         }
      }

      return highest < 0 ? 10000 : highest;
   }

   public final void update$core_plugin() {
      legacyKey = new PersistentDataKey(
         TalismansPluginKt.getPlugin().getNamespacedKeyFactory().create("talisman_bag"), PersistentDataKeyType.STRING_LIST, CollectionsKt.emptyList()
      );
      key = new PersistentDataKey(
         TalismansPluginKt.getPlugin().getNamespacedKeyFactory().create("bag"), PersistentDataKeyType.STRING_LIST, CollectionsKt.emptyList()
      );
      ItemStack var10000 = ((ItemStackBuilder)new ItemStackBuilder(Items.lookup(TalismansPluginKt.getPlugin().getConfigYml().getString("bag.blocked-item")))
            .addLoreLines(TalismansPluginKt.getPlugin().getConfigYml().getStrings("bag.blocked-item-lore")))
         .build();
      Intrinsics.checkNotNullExpressionValue(var10000, "build(...)");
      emptyItem = var10000;

      for (int rows = 1; rows < 7; rows++) {
         menus.put(rows, GUIHelperExtensions.menu(rows, TalismanBag::update$lambda$0));
      }

      PlaceholderManager.registerPlaceholder(
         (RegistrablePlaceholder)(new PlayerPlaceholder(TalismansPluginKt.getPlugin(), "bagsize", TalismanBag::update$lambda$1))
      );
   }

   public final void open(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      int bagRows = Math.min(6, (int)Math.ceil(this.getBagSize(player) / 9.0));
      Object var10000 = menus.get(bagRows);
      Intrinsics.checkNotNull(var10000);
      ((Menu)var10000).open(player);
   }

   @NotNull
   public final List<ItemStack> getTalismans(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      if (!savedItems.containsKey(player.getUniqueId())) {
         PlayerProfile var10000 = ProfileExtensions.getProfile((OfflinePlayer)player);
         PersistentDataKey var10001 = legacyKey;
         if (legacyKey == null) {
            Intrinsics.throwUninitializedPropertyAccessException("legacyKey");
            var10001 = null;
         }

         Object var56 = var10000.read(var10001);
         Intrinsics.checkNotNullExpressionValue(var56, "read(...)");
         List items = (Iterable)var56;
         int $i$f$map = 0;
         Iterable $i$f$filter = items;
         var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(items, 10));
         int $i$f$mapTo = 0;

         for (Object item$iv$iv : $i$f$filter) {
            String element$iv$iv = (String)item$iv$iv;
            Collection var19 = destination$iv$iv;
            int var11/* $VF was: $i$a$-map-TalismanBag$getTalismans$legacyItems$1 */ = 0;
            var19.add(Items.lookup(element$iv$iv).getItem());
         }

         items = (Iterable & List)destination$iv$iv;
         $i$f$map = 0;
         $i$f$filter = items;
         destination$iv$iv = new ArrayList();
         $i$f$mapTo = 0;

         for (Object element$iv$iv : $i$f$filter) {
            ItemStack it = (ItemStack)element$iv$iv;
            int var51/* $VF was: $i$a$-filterNot-TalismanBag$getTalismans$legacyItems$2 */ = 0;
            if (!new EmptyTestableItem().matches(it)) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         items = (Iterable & List)destination$iv$iv;
         $i$f$map = 0;
         $i$f$filter = items;
         destination$iv$iv = new ArrayList();
         $i$f$mapTo = 0;

         for (Object element$iv$iv : $i$f$filter) {
            ItemStack it = (ItemStack)element$iv$iv;
            int var52/* $VF was: $i$a$-filter-TalismanBag$getTalismans$legacyItems$3 */ = 0;
            if (TalismanChecks.getTalismanOnItem(it) != null) {
               destination$iv$iv.add(element$iv$iv);
            }
         }

         List legacyItems = (List)destination$iv$iv;
         PlayerProfile var57 = ProfileExtensions.getProfile((OfflinePlayer)player);
         var10001 = key;
         if (key == null) {
            Intrinsics.throwUninitializedPropertyAccessException("key");
            var10001 = null;
         }

         Object var58 = var57.read(var10001);
         Intrinsics.checkNotNullExpressionValue(var58, "read(...)");
         Iterable var25 = (Iterable)var58;
         int $i$f$mapNotNull = 0;
         destination$iv$iv = var25;
         var destination$iv$ivx = new ArrayList();
         int $i$f$mapNotNullTo = 0;
         Iterable $this$forEach$iv$iv$iv = destination$iv$iv;
         int $i$f$forEach = 0;

         for (Object element$iv$iv$iv : $this$forEach$iv$iv$iv) {
            Object element$iv$iv = element$iv$iv$iv;
            int var14/* $VF was: $i$a$-forEach-CollectionsKt___CollectionsKt$mapNotNullTo$1$iv$iv */ = 0;
            String it = (String)element$iv$iv;
            int var16/* $VF was: $i$a$-mapNotNull-TalismanBag$getTalismans$items$1 */ = 0;
            ItemStack var59 = Items.fromSNBT(it);
            if (var59 != null) {
               Object it$iv$iv = var59;
               int var18/* $VF was: $i$a$-let-CollectionsKt___CollectionsKt$mapNotNullTo$1$1$iv$iv */ = 0;
               destination$iv$ivx.add(it$iv$iv);
            }
         }

         var var26 = (Iterable & List)destination$iv$ivx;
         int $i$f$filterx = 0;
         destination$iv$iv = var26;
         var destination$iv$ivxx = new ArrayList();
         $i$f$mapNotNullTo = 0;

         for (Object element$iv$iv : destination$iv$iv) {
            ItemStack it = (ItemStack)element$iv$iv;
            int var55/* $VF was: $i$a$-filter-TalismanBag$getTalismans$items$2 */ = 0;
            if (TalismanChecks.getTalismanOnItem(it) != null) {
               destination$iv$ivxx.add(element$iv$iv);
            }
         }

         items = (List)destination$iv$ivxx;
         savedItems.put(player.getUniqueId(), CollectionsKt.toList(CollectionsKt.plus(legacyItems, items)));
      }

      List var60 = savedItems.get(player.getUniqueId());
      if (var60 == null) {
         var60 = CollectionsKt.emptyList();
      }

      return var60;
   }

   private static final boolean update$lambda$0$1$0(int $row, int $column, Player it) {
      int var10000 = MenuUtils.rowColumnToSlot($row, $column);
      TalismanBag var10001 = INSTANCE;
      Intrinsics.checkNotNull(it);
      return var10000 >= var10001.getBagSize(it);
   }

   private static final boolean update$lambda$0$1$1(Player var0, Menu var1, ItemStack itemStack) {
      Intrinsics.checkNotNullParameter(var0, "<unused var>");
      Intrinsics.checkNotNullParameter(var1, "<unused var>");
      return TalismanChecks.getTalismanOnItem(itemStack) != null;
   }

   private static final ItemStack update$lambda$0$0(int $row, int $column, Player player, Menu menu) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(menu, "menu");
      int bagSize = INSTANCE.getBagSize(player);
      int index = MenuUtils.rowColumnToSlot($row, $column);
      ItemStack var10000;
      if (index >= bagSize) {
         var10000 = emptyItem;
         if (emptyItem == null) {
            Intrinsics.throwUninitializedPropertyAccessException("emptyItem");
            var10000 = null;
         }
      } else {
         var10000 = (ItemStack)CollectionsKt.getOrNull((List)TalismanBagKt.access$getTalismanBag(menu).get(player), index);
         if (var10000 != null) {
            var10000 = var10000.clone();
            if (var10000 != null) {
               return var10000;
            }
         }

         var10000 = new ItemStack(Material.AIR);
      }

      return var10000;
   }

   private static final Unit update$lambda$0$1(int $row, int $column, SlotBuilder $this$slot) {
      Intrinsics.checkNotNullParameter($this$slot, "$this$slot");
      $this$slot.setCaptive(true);
      $this$slot.notCaptiveFor(TalismanBag::update$lambda$0$1$0);
      $this$slot.setCaptiveFilter(TalismanBag::update$lambda$0$1$1);
      return Unit.INSTANCE;
   }

   private static final void update$lambda$0$2(Player player, Menu menu) {
      Intrinsics.checkNotNull(menu);
      MenuStateVar var10000 = TalismanBagKt.access$getTalismanBag(menu);
      Intrinsics.checkNotNull(player);
      if (((List)var10000.get(player)).isEmpty()) {
         MenuStateVar items = TalismanBagKt.access$getTalismanBag(menu);
         Collection var10002 = (Collection)items.get(player);
         PlayerProfile var10003 = ProfileExtensions.getProfile((OfflinePlayer)player);
         PersistentDataKey var10004 = legacyKey;
         if (legacyKey == null) {
            Intrinsics.throwUninitializedPropertyAccessException("legacyKey");
            var10004 = null;
         }

         Object var75 = var10003.read(var10004);
         Intrinsics.checkNotNullExpressionValue(var75, "read(...)");
         Iterable toWrite = (Iterable)var75;
         Collection var20 = var10002;
         Player var19 = player;
         MenuStateVar var18 = items;
         int $i$f$map = 0;
         Iterable $i$f$mapx = toWrite;
         var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault(toWrite, 10));
         int $i$f$mapTo = 0;

         for (Object item$iv$iv : $i$f$mapx) {
            String item$iv$ivx = (String)item$iv$iv;
            Collection var21 = destination$iv$iv;
            int var11/* $VF was: $i$a$-map-TalismanBag$update$1$3$1 */ = 0;
            var21.add(Items.lookup(item$iv$ivx).getItem());
         }

         List var68 = (List)destination$iv$iv;
         var18.set(var19, CollectionsKt.plus(var20, var68));
         items = TalismanBagKt.access$getTalismanBag(menu);
         var10002 = (Collection)items.get(player);
         PlayerProfile var76 = ProfileExtensions.getProfile((OfflinePlayer)player);
         var10004 = key;
         if (key == null) {
            Intrinsics.throwUninitializedPropertyAccessException("key");
            var10004 = null;
         }

         Object var77 = var76.read(var10004);
         Intrinsics.checkNotNullExpressionValue(var77, "read(...)");
         toWrite = (Iterable)var77;
         var20 = var10002;
         var19 = player;
         var18 = items;
         $i$f$map = 0;
         $i$f$mapx = toWrite;
         destination$iv$iv = new ArrayList();
         $i$f$mapTo = 0;
         Iterable $this$forEach$iv$iv$iv = $i$f$mapx;
         int $i$f$forEach = 0;

         for (Object element$iv$iv$iv : $this$forEach$iv$iv$iv) {
            Object element$iv$iv = element$iv$iv$iv;
            int var13/* $VF was: $i$a$-forEach-CollectionsKt___CollectionsKt$mapNotNullTo$1$iv$iv */ = 0;
            String it = (String)element$iv$iv;
            int var15/* $VF was: $i$a$-mapNotNull-TalismanBag$update$1$3$2 */ = 0;
            ItemStack var70 = Items.fromSNBT(it);
            if (var70 != null) {
               Object it$iv$iv = var70;
               int var17/* $VF was: $i$a$-let-CollectionsKt___CollectionsKt$mapNotNullTo$1$1$iv$iv */ = 0;
               destination$iv$iv.add(it$iv$iv);
            }
         }

         var68 = (List)destination$iv$iv;
         var18.set(var19, CollectionsKt.plus(var20, var68));
      }

      List var71 = menu.getCaptiveItems(player);
      Intrinsics.checkNotNullExpressionValue(var71, "getCaptiveItems(...)");
      List toWrite = var71;
      int $i$f$filterNot = 0;
      Iterable var32 = toWrite;
      var destination$iv$iv = new ArrayList();
      int $i$f$filterNotTo = 0;

      for (Object element$iv$iv : var32) {
         ItemStack it = (ItemStack)element$iv$iv;
         int var56/* $VF was: $i$a$-filterNot-TalismanBag$update$1$3$items$1 */ = 0;
         if (!new EmptyTestableItem().matches(it)) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      List items = (List)destination$iv$iv;
      Iterable var29 = items;
      int $i$f$filter = 0;
      Iterable var37 = var29;
      var destination$iv$ivx = new ArrayList();
      int $i$f$filterTo = 0;

      for (Object element$iv$iv : var37) {
         ItemStack it = (ItemStack)element$iv$iv;
         int var59/* $VF was: $i$a$-filter-TalismanBag$update$1$3$toWrite$1 */ = 0;
         if (TalismanChecks.getTalismanOnItem(it) != null) {
            destination$iv$ivx.add(element$iv$iv);
         }
      }

      toWrite = (List)destination$iv$ivx;
      savedItems.put(player.getUniqueId(), CollectionsKt.toList(toWrite));
      PlayerProfile var72 = ProfileExtensions.getProfile((OfflinePlayer)player);
      PersistentDataKey var10001 = key;
      if (key == null) {
         Intrinsics.throwUninitializedPropertyAccessException("key");
         var10001 = null;
      }

      Iterable var30 = toWrite;
      PersistentDataKey var64 = var10001;
      PlayerProfile var62 = var72;
      int $i$f$map = 0;
      var37 = var30;
      var destination$iv$ivxx = new ArrayList(CollectionsKt.collectionSizeOrDefault(var30, 10));
      $i$f$filterTo = 0;

      for (Object item$iv$iv : var37) {
         ItemStack var58 = (ItemStack)item$iv$iv;
         Collection var66 = destination$iv$ivxx;
         int var60/* $VF was: $i$a$-map-TalismanBag$update$1$3$3 */ = 0;
         String var73 = Items.toSNBT(var58);
         Intrinsics.checkNotNullExpressionValue(var73, "toSNBT(...)");
         var66.add(var73);
      }

      List var67 = (List)destination$iv$ivxx;
      var62.write(var64, var67);
   }

   private static final void update$lambda$0$3(InventoryCloseEvent event, Menu menu) {
      Intrinsics.checkNotNullParameter(event, "event");
      Intrinsics.checkNotNullParameter(menu, "menu");
      HumanEntity var10000 = event.getPlayer();
      Intrinsics.checkNotNull(var10000, "null cannot be cast to non-null type org.bukkit.entity.Player");
      Player player = (Player)var10000;
      List var37 = menu.getCaptiveItems(player);
      Intrinsics.checkNotNullExpressionValue(var37, "getCaptiveItems(...)");
      List toWrite = var37;
      int $i$f$filterNot = 0;
      Iterable $this$filter$iv = toWrite;
      var destination$iv$iv = new ArrayList();
      int $i$f$filterNotTo = 0;

      for (Object element$iv$iv : $this$filter$iv) {
         ItemStack it = (ItemStack)element$iv$iv;
         int var12/* $VF was: $i$a$-filterNot-TalismanBag$update$1$4$items$1 */ = 0;
         if (!new EmptyTestableItem().matches(it)) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      List items = (List)destination$iv$iv;
      List var38 = savedItems.get(player.getUniqueId());
      if (var38 == null) {
         var38 = CollectionsKt.emptyList();
      }

      toWrite = var38;
      PlayerProfile var39 = ProfileExtensions.getProfile((OfflinePlayer)player);
      PersistentDataKey var10001 = key;
      if (key == null) {
         Intrinsics.throwUninitializedPropertyAccessException("key");
         var10001 = null;
      }

      Iterable var19 = toWrite;
      PersistentDataKey var16 = var10001;
      PlayerProfile var15 = var39;
      int $i$f$map = 0;
      Iterable var23 = var19;
      var destination$iv$ivx = new ArrayList(CollectionsKt.collectionSizeOrDefault(var19, 10));
      int $i$f$mapTo = 0;

      for (Object item$iv$iv : var23) {
         ItemStack var33 = (ItemStack)item$iv$iv;
         Collection var17 = destination$iv$ivx;
         int var13/* $VF was: $i$a$-map-TalismanBag$update$1$4$1 */ = 0;
         String var40 = Items.toSNBT(var33);
         Intrinsics.checkNotNullExpressionValue(var40, "toSNBT(...)");
         var17.add(var40);
      }

      List var36 = (List)destination$iv$ivx;
      var15.write(var16, var36);
      PlayerProfile var41 = ProfileExtensions.getProfile((OfflinePlayer)player);
      var10001 = legacyKey;
      if (legacyKey == null) {
         Intrinsics.throwUninitializedPropertyAccessException("legacyKey");
         var10001 = null;
      }

      var41.write(var10001, CollectionsKt.emptyList());
      $this$filter$iv = items;
      int $i$f$filter = 0;
      Iterable var26 = $this$filter$iv;
      var destination$iv$ivxx = new ArrayList();
      int $i$f$filterTo = 0;

      for (Object element$iv$iv : var26) {
         ItemStack it = (ItemStack)element$iv$iv;
         int var14/* $VF was: $i$a$-filter-TalismanBag$update$1$4$toDrop$1 */ = 0;
         if (TalismanChecks.getTalismanOnItem(it) == null) {
            destination$iv$ivxx.add(element$iv$iv);
         }
      }

      List toDrop = (List)destination$iv$ivxx;
      new DropQueue(player).setLocation(player.getEyeLocation()).forceTelekinesis().addItems(toDrop).push();
   }

   private static final Unit update$lambda$0(int $rows, MenuBuilder $this$menu) {
      Intrinsics.checkNotNullParameter($this$menu, "$this$menu");
      $this$menu.setTitle(TalismansPluginKt.getPlugin().getConfigYml().getFormattedString("bag.title"));
      $this$menu.allowChangingHeldItem();
      int row = 1;
      if (row <= $rows) {
         while (true) {
            for (int column = 1; column < 10; column++) {
               $this$menu.setSlot(row, column, GUIHelperExtensions.slot(TalismanBag::update$lambda$0$0, TalismanBag::update$lambda$0$1));
            }

            if (row == $rows) {
               break;
            }

            row++;
         }
      }

      $this$menu.onRender(TalismanBag::update$lambda$0$2);
      $this$menu.onClose(TalismanBag::update$lambda$0$3);
      return Unit.INSTANCE;
   }

   private static final String update$lambda$1(Player it) {
      Intrinsics.checkNotNullParameter(it, "it");
      return String.valueOf(INSTANCE.getBagSize(it));
   }
}
