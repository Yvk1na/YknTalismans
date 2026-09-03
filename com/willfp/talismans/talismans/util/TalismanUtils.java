package com.willfp.talismans.talismans.util;

import com.willfp.eco.core.fast.FastItemStackExtensions;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.collections.CollectionsKt;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.eco.libs.kotlin.text.StringsKt;
import com.willfp.talismans.TalismansPluginKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from TalismanUtils.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nJ\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eJ\u000e\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0006J\u000e\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u0006J\u0006\u0010\u0013\u001a\u00020\bR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014",
   d2 = {
         "Lcom/willfp/talismans/talismans/util/TalismanUtils;",
         "",
         "<init>",
         "()V",
         "TALISMAN_MATERIALS",
         "",
         "Lorg/bukkit/Material;",
         "convert",
         "",
         "itemStack",
         "Lorg/bukkit/inventory/ItemStack;",
         "getLimit",
         "",
         "player",
         "Lorg/bukkit/entity/Player;",
         "isTalismanMaterial",
         "",
         "material",
         "registerTalismanMaterial",
         "clearTalismanMaterials",
         "core-plugin"
   }
)
@SourceDebugExtension(
   "SMAP\nTalismanUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TalismanUtils.kt\ncom/willfp/talismans/talismans/util/TalismanUtils\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,70:1\n296#2,2:71\n1586#2:73\n1661#2,3:74\n*S KotlinDebug\n*F\n+ 1 TalismanUtils.kt\ncom/willfp/talismans/talismans/util/TalismanUtils\n*L\n21#1:71,2\n42#1:73\n42#1:74,3\n*E\n"
)
public final class TalismanUtils {
   @NotNull
   public static final TalismanUtils INSTANCE = new TalismanUtils();
   @NotNull
   private static final Set<Material> TALISMAN_MATERIALS = new LinkedHashSet<>();

   private TalismanUtils() {
   }

   public final void convert(@Nullable ItemStack itemStack) {
      if (itemStack != null) {
         Material var10001 = itemStack.getType();
         Intrinsics.checkNotNullExpressionValue(var10001, "getType(...)");
         if (this.isTalismanMaterial(var10001)) {
            NamespacedKey var10000 = FastItemStackExtensions.fast(itemStack).getPersistentDataContainer();
            Intrinsics.checkNotNullExpressionValue(var10000, "getPersistentDataContainer(...)");
            PersistentDataContainer container = var10000;
            Set var11 = container.getKeys();
            Intrinsics.checkNotNullExpressionValue(var11, "getKeys(...)");
            Iterable $this$firstOrNull$iv = var11;
            int $i$f$firstOrNull = 0;
            Iterator var7 = $this$firstOrNull$iv.iterator();

            while (true) {
               if (var7.hasNext()) {
                  Object element$iv = var7.next();
                  NamespacedKey it = (NamespacedKey)element$iv;
                  int var10/* $VF was: $i$a$-firstOrNull-TalismanUtils$convert$talismanKey$1 */ = 0;
                  if (!Intrinsics.areEqual(it.getNamespace(), "talismans")) {
                     continue;
                  }

                  var10000 = (NamespacedKey)element$iv;
                  break;
               }

               var10000 = null;
               break;
            }

            var10000 = var10000;
            if (var10000 != null) {
               NamespacedKey talismanKey = var10000;
               if (container.has(talismanKey, PersistentDataType.INTEGER)) {
                  Integer var14 = (Integer)container.get(talismanKey, PersistentDataType.INTEGER);
                  if (var14 != null) {
                     int level = var14;
                     container.remove(talismanKey);
                     container.set(
                        TalismansPluginKt.getPlugin().getNamespacedKeyFactory().create("talisman"),
                        PersistentDataType.STRING,
                        talismanKey.getKey() + "_" + level
                     );
                  }
               }
            }
         }
      }
   }

   public final int getLimit(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      int configLimit = TalismansPluginKt.getPlugin().getConfigYml().getInt("talisman-limit");
      if (configLimit > 0) {
         return configLimit;
      }

      String prefix = "talismans.limit.";
      int highest = -1;
      Set var10000 = player.getEffectivePermissions();
      Intrinsics.checkNotNullExpressionValue(var10000, "getEffectivePermissions(...)");
      Iterable $this$map$iv = var10000;
      int $i$f$map = 0;
      Iterable $this$mapTo$iv$iv = $this$map$iv;
      var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
      int $i$f$mapTo = 0;

      for (Object item$iv$iv : $this$mapTo$iv$iv) {
         PermissionAttachmentInfo it = (PermissionAttachmentInfo)item$iv$iv;
         Collection var15 = destination$iv$iv;
         int var14/* $VF was: $i$a$-map-TalismanUtils$getLimit$1 */ = 0;
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

   public final boolean isTalismanMaterial(@NotNull Material material) {
      Intrinsics.checkNotNullParameter(material, "material");
      return TALISMAN_MATERIALS.contains(material);
   }

   public final void registerTalismanMaterial(@NotNull Material material) {
      Intrinsics.checkNotNullParameter(material, "material");
      TALISMAN_MATERIALS.add(material);
   }

   public final void clearTalismanMaterials() {
      TALISMAN_MATERIALS.clear();
   }
}
