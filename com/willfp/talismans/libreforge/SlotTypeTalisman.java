package com.willfp.talismans.libreforge;

import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.collections.CollectionsKt;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.libreforge.slot.SlotType;
import com.willfp.talismans.talismans.util.TalismanChecks;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from SlotTypeTalisman.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0016J\u0016\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\u0010",
   d2 = {
         "Lcom/willfp/talismans/libreforge/SlotTypeTalisman;",
         "Lcom/willfp/libreforge/slot/SlotType;",
         "<init>",
         "()V",
         "getItems",
         "",
         "Lorg/bukkit/inventory/ItemStack;",
         "entity",
         "Lorg/bukkit/entity/LivingEntity;",
         "addToSlot",
         "",
         "player",
         "Lorg/bukkit/entity/Player;",
         "item",
         "getItemSlots",
         "",
         "core-plugin"
   }
)
public final class SlotTypeTalisman extends SlotType {
   @NotNull
   public static final SlotTypeTalisman INSTANCE = new SlotTypeTalisman();

   private SlotTypeTalisman() {
      super("talisman");
   }

   @NotNull
   public List<ItemStack> getItems(@NotNull LivingEntity entity) {
      Intrinsics.checkNotNullParameter(entity, "entity");
      return entity instanceof Player ? CollectionsKt.toList(TalismanChecks.getTalismanItemsOnPlayer((Player)entity)) : CollectionsKt.emptyList();
   }

   public boolean addToSlot(@NotNull Player player, @NotNull ItemStack item) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(item, "item");
      return false;
   }

   @NotNull
   public List<Integer> getItemSlots(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      return CollectionsKt.emptyList();
   }
}
