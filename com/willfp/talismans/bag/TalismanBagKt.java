package com.willfp.talismans.bag;

import com.willfp.eco.core.gui.menu.Menu;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.collections.CollectionsKt;
import com.willfp.eco.libs.kotlin.jvm.internal.PropertyReference1;
import com.willfp.eco.libs.kotlin.jvm.internal.PropertyReference1Impl;
import com.willfp.eco.libs.kotlin.jvm.internal.Reflection;
import com.willfp.eco.libs.kotlin.reflect.KProperty;
import com.willfp.talismans.ecomponent.LazyWithReceiver;
import com.willfp.talismans.ecomponent.MenuStateVar;
import com.willfp.talismans.ecomponent.MenuStateVarKt;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from TalismanBag.kt
@Metadata(
   mv = {2, 3, 0},
   k = 2,
   xi = 48,
   d1 = "\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\"+\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001*\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006¨\u0006\t",
   d2 = {
         "talismanBag",
         "Lcom/willfp/talismans/ecomponent/MenuStateVar;",
         "",
         "Lorg/bukkit/inventory/ItemStack;",
         "Lcom/willfp/eco/core/gui/menu/Menu;",
         "getTalismanBag",
         "(Lcom/willfp/eco/core/gui/menu/Menu;)Lcom/willfp/talismans/ecomponent/MenuStateVar;",
         "talismanBag$delegate",
         "Lcom/willfp/talismans/ecomponent/LazyWithReceiver;",
         "core-plugin"
   }
)
public final class TalismanBagKt {
   @NotNull
   private static final LazyWithReceiver talismanBag$delegate = MenuStateVarKt.menuStateVar(CollectionsKt.emptyList());

   private static final MenuStateVar<List<ItemStack>> getTalismanBag(Menu $this$talismanBag) {
      return (MenuStateVar<List<ItemStack>>)talismanBag$delegate.getValue($this$talismanBag, $$delegatedProperties[0]);
   }

   static {
      KProperty[] var0 = new KProperty[]{
         Reflection.property1(
            (PropertyReference1)(
               new PropertyReference1Impl(
                  TalismanBagKt.class, "talismanBag", "getTalismanBag(Lcom/willfp/eco/core/gui/menu/Menu;)Lcom/willfp/talismans/ecomponent/MenuStateVar;", 1
               )
            )
         )
      };
      $$delegatedProperties = var0;
   }
}
