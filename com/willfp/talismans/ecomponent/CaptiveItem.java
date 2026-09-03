package com.willfp.talismans.ecomponent;

import com.willfp.eco.core.gui.menu.Menu;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.MutablePropertyReference1;
import com.willfp.eco.libs.kotlin.jvm.internal.MutablePropertyReference1Impl;
import com.willfp.eco.libs.kotlin.jvm.internal.Reflection;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.eco.libs.kotlin.properties.Delegates;
import com.willfp.eco.libs.kotlin.properties.ReadWriteProperty;
import com.willfp.eco.libs.kotlin.reflect.KProperty;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from CaptiveItem.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J%\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0011\u001a\u00020\tH\u0000¢\u0006\u0002\b\u0017J\u0013\u0010\u0018\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0086\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082.¢\u0006\u0002\n\u0000R+\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR+\u0010\u0011\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b\u0014\u0010\u0010\u001a\u0004\b\u0012\u0010\f\"\u0004\b\u0013\u0010\u000e¨\u0006\u001c",
   d2 = {
         "Lcom/willfp/talismans/ecomponent/CaptiveItem;",
         "",
         "<init>",
         "()V",
         "isBound",
         "",
         "menu",
         "Lcom/willfp/eco/core/gui/menu/Menu;",
         "<set-?>",
         "",
         "row",
         "getRow",
         "()I",
         "setRow",
         "(I)V",
         "row$delegate",
         "Lcom/willfp/eco/libs/kotlin/properties/ReadWriteProperty;",
         "column",
         "getColumn",
         "setColumn",
         "column$delegate",
         "bind",
         "",
         "bind$eco_api",
         "get",
         "Lorg/bukkit/inventory/ItemStack;",
         "player",
         "Lorg/bukkit/entity/Player;",
         "eco-api"
   }
)
@SourceDebugExtension(
   "SMAP\nCaptiveItem.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CaptiveItem.kt\ncom/willfp/ecomponent/CaptiveItem\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,94:1\n1#2:95\n*E\n"
)
public final class CaptiveItem {
   private boolean isBound;
   private Menu menu;
   @NotNull
   private final ReadWriteProperty row$delegate = Delegates.INSTANCE.notNull();
   @NotNull
   private final ReadWriteProperty column$delegate = Delegates.INSTANCE.notNull();

   private final int getRow() {
      return ((Number)this.row$delegate.getValue(this, $$delegatedProperties[0])).intValue();
   }

   private final void setRow(int var1) {
      this.row$delegate.setValue(this, $$delegatedProperties[0], var1/* $VF was: <set-?> */);
   }

   private final int getColumn() {
      return ((Number)this.column$delegate.getValue(this, $$delegatedProperties[1])).intValue();
   }

   private final void setColumn(int var1) {
      this.column$delegate.setValue(this, $$delegatedProperties[1], var1/* $VF was: <set-?> */);
   }

   public final void bind$eco_api(@NotNull Menu menu, int row, int column) {
      Intrinsics.checkNotNullParameter(menu, "menu");
      if (this.isBound) {
         int var4/* $VF was: $i$a$-require-CaptiveItem$bind$1 */ = 0;
         String var5 = "Already bound!";
         throw new IllegalArgumentException(var5.toString());
      }

      this.menu = menu;
      this.setRow(row);
      this.setColumn(column);
      this.isBound = true;
   }

   @Nullable
   public final ItemStack get(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      if (!this.isBound) {
         return null;
      }

      Menu var10000 = this.menu;
      if (this.menu == null) {
         Intrinsics.throwUninitializedPropertyAccessException("menu");
         var10000 = null;
      }

      return var10000.getCaptiveItem(player, this.getRow(), this.getColumn());
   }

   static {
      KProperty[] var0 = new KProperty[]{
         Reflection.mutableProperty1((MutablePropertyReference1)(new MutablePropertyReference1Impl(CaptiveItem.class, "row", "getRow()I", 0))),
         Reflection.mutableProperty1((MutablePropertyReference1)(new MutablePropertyReference1Impl(CaptiveItem.class, "column", "getColumn()I", 0)))
      };
      $$delegatedProperties = var0;
   }
}
