package com.willfp.talismans.talismans;

import com.willfp.eco.core.items.tag.CustomItemTag;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.collections.CollectionsKt;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.random.Random;
import com.willfp.talismans.TalismansPluginKt;
import com.willfp.talismans.talismans.util.TalismanChecks;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from TalismanTag.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016J\b\u0010\b\u001a\u00020\u0007H\u0016¨\u0006\t",
   d2 = {
         "Lcom/willfp/talismans/talismans/TalismanTag;",
         "Lcom/willfp/eco/core/items/tag/CustomItemTag;",
         "<init>",
         "()V",
         "matches",
         "",
         "p0",
         "Lorg/bukkit/inventory/ItemStack;",
         "getExampleItem",
         "core-plugin"
   }
)
public final class TalismanTag extends CustomItemTag {
   @NotNull
   public static final TalismanTag INSTANCE = new TalismanTag();

   private TalismanTag() {
      super(TalismansPluginKt.getPlugin().createNamespacedKey("talisman"));
   }

   public boolean matches(@NotNull ItemStack p0) {
      Intrinsics.checkNotNullParameter(p0, "p0");
      return TalismanChecks.getTalismanOnItem(p0) != null;
   }

   @NotNull
   public ItemStack getExampleItem() {
      return ((Talisman)CollectionsKt.random(Talismans.values(), (Random)Random.Default)).getItemStack();
   }
}
