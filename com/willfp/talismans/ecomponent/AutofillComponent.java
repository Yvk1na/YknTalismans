package com.willfp.talismans.ecomponent;

import com.willfp.eco.core.gui.GUIComponent;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.MutablePropertyReference1;
import com.willfp.eco.libs.kotlin.jvm.internal.MutablePropertyReference1Impl;
import com.willfp.eco.libs.kotlin.jvm.internal.Reflection;
import com.willfp.eco.libs.kotlin.properties.Delegates;
import com.willfp.eco.libs.kotlin.properties.ReadWriteProperty;
import com.willfp.eco.libs.kotlin.reflect.KProperty;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from AutofillComponent.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0011\u001a\u00020\u0005H\u0016J\b\u0010\u0012\u001a\u00020\u0005H\u0016J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u0005H\u0016R+\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058D@BX\u0084\u008e\u0002¢\u0006\u0012\n\u0004\b\u000b\u0010\f\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR+\u0010\r\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00058D@BX\u0084\u008e\u0002¢\u0006\u0012\n\u0004\b\u0010\u0010\f\u001a\u0004\b\u000e\u0010\b\"\u0004\b\u000f\u0010\n¨\u0006\u0015",
   d2 = {
         "Lcom/willfp/talismans/ecomponent/AutofillComponent;",
         "Lcom/willfp/eco/core/gui/GUIComponent;",
         "<init>",
         "()V",
         "<set-?>",
         "",
         "maxRows",
         "getMaxRows",
         "()I",
         "setMaxRows",
         "(I)V",
         "maxRows$delegate",
         "Lcom/willfp/eco/libs/kotlin/properties/ReadWriteProperty;",
         "maxColumns",
         "getMaxColumns",
         "setMaxColumns",
         "maxColumns$delegate",
         "getRows",
         "getColumns",
         "init",
         "",
         "eco-api"
   }
)
public abstract class AutofillComponent implements GUIComponent {
   @NotNull
   private final ReadWriteProperty maxRows$delegate = Delegates.INSTANCE.notNull();
   @NotNull
   private final ReadWriteProperty maxColumns$delegate = Delegates.INSTANCE.notNull();

   protected final int getMaxRows() {
      return ((Number)this.maxRows$delegate.getValue(this, $$delegatedProperties[0])).intValue();
   }

   private final void setMaxRows(int var1) {
      this.maxRows$delegate.setValue(this, $$delegatedProperties[0], var1/* $VF was: <set-?> */);
   }

   protected final int getMaxColumns() {
      return ((Number)this.maxColumns$delegate.getValue(this, $$delegatedProperties[1])).intValue();
   }

   private final void setMaxColumns(int var1) {
      this.maxColumns$delegate.setValue(this, $$delegatedProperties[1], var1/* $VF was: <set-?> */);
   }

   public int getRows() {
      return this.getMaxRows();
   }

   public int getColumns() {
      return this.getMaxColumns();
   }

   public void init(int maxRows, int maxColumns) {
      this.setMaxRows(maxRows);
      this.setMaxColumns(maxColumns);
   }

   static {
      KProperty[] var0 = new KProperty[]{
         Reflection.mutableProperty1((MutablePropertyReference1)(new MutablePropertyReference1Impl(AutofillComponent.class, "maxRows", "getMaxRows()I", 0))),
         Reflection.mutableProperty1(
            (MutablePropertyReference1)(new MutablePropertyReference1Impl(AutofillComponent.class, "maxColumns", "getMaxColumns()I", 0))
         )
      };
      $$delegatedProperties = var0;
   }
}
