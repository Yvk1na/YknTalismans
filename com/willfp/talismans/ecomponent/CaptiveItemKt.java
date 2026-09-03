package com.willfp.talismans.ecomponent;

import com.willfp.eco.core.gui.GUIComponent;
import com.willfp.eco.core.gui.menu.Menu;
import com.willfp.eco.core.gui.menu.MenuBuilder;
import com.willfp.eco.core.gui.slot.Slot;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from CaptiveItem.kt
@Metadata(
   mv = {2, 3, 0},
   k = 2,
   xi = 48,
   d1 = "\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a.\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u001a.\u0010\n\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u001a6\u0010\n\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\t\u001a\"\u0010\u000e\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u000f\u001a\u00020\t¨\u0006\u0010",
   d2 = {
         "setSlot",
         "",
         "Lcom/willfp/eco/core/gui/menu/MenuBuilder;",
         "row",
         "",
         "column",
         "slot",
         "Lcom/willfp/eco/core/gui/slot/Slot;",
         "bindCaptive",
         "Lcom/willfp/talismans/ecomponent/CaptiveItem;",
         "addComponent",
         "layer",
         "component",
         "Lcom/willfp/eco/core/gui/GUIComponent;",
         "bind",
         "captiveItem",
         "eco-api"
   }
)
public final class CaptiveItemKt {
   public static final void setSlot(@NotNull MenuBuilder $this$setSlot, int row, int column, @NotNull Slot slot, @Nullable CaptiveItem bindCaptive) {
      Intrinsics.checkNotNullParameter($this$setSlot, "<this>");
      Intrinsics.checkNotNullParameter(slot, "slot");
      addComponent($this$setSlot, row, column, slot, bindCaptive);
   }

   public static final void addComponent(@NotNull MenuBuilder $this$addComponent, int row, int column, @NotNull Slot slot, @Nullable CaptiveItem bindCaptive) {
      Intrinsics.checkNotNullParameter($this$addComponent, "<this>");
      Intrinsics.checkNotNullParameter(slot, "slot");
      addComponent($this$addComponent, 2, row, column, (GUIComponent)slot, bindCaptive);
   }

   public static final void addComponent(
      @NotNull MenuBuilder $this$addComponent, int layer, int row, int column, @NotNull GUIComponent component, @Nullable CaptiveItem bindCaptive
   ) {
      Intrinsics.checkNotNullParameter($this$addComponent, "<this>");
      Intrinsics.checkNotNullParameter(component, "component");
      $this$addComponent.addComponent(layer, row, column, component);
      if (bindCaptive != null) {
         bind($this$addComponent, row, column, bindCaptive);
      }
   }

   public static final void bind(@NotNull MenuBuilder $this$bind, int row, int column, @NotNull CaptiveItem captiveItem) {
      Intrinsics.checkNotNullParameter($this$bind, "<this>");
      Intrinsics.checkNotNullParameter(captiveItem, "captiveItem");
      $this$bind.onBuild(CaptiveItemKt::bind$lambda$0);
   }

   private static final void bind$lambda$0(CaptiveItem $captiveItem, int $row, int $column, Menu it) {
      Intrinsics.checkNotNull(it);
      $captiveItem.bind$eco_api(it, $row, $column);
   }
}
