package com.willfp.talismans.ecomponent.components;

import com.willfp.eco.core.gui.GUIComponent;
import com.willfp.eco.core.gui.menu.Menu;
import com.willfp.eco.core.gui.slot.Slot;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.ranges.RangesKt;
import com.willfp.talismans.ecomponent.AutofillComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from Shapes.kt
@Metadata(
   mv = {2, 3, 0},
   k = 2,
   xi = 48,
   d1 = "\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\u001a\u0010\u0010\u0000\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u001a\u0010\u0010\u0006\u001a\u00020\u00012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u001a\u0018\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00052\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u001a\u000e\u0010\u0007\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u001a\u001e\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\n",
   d2 = {
         "verticalLine",
         "Lcom/willfp/eco/core/gui/GUIComponent;",
         "slot",
         "Lcom/willfp/eco/core/gui/slot/Slot;",
         "maxSize",
         "",
         "horizontalLine",
         "rectangle",
         "maxHeight",
         "maxWidth",
         "eco-api"
   }
)
public final class ShapesKt {
   @NotNull
   public static final GUIComponent verticalLine(@Nullable Slot slot) {
      return verticalLine(Integer.MAX_VALUE, slot);
   }

   @NotNull
   public static final GUIComponent verticalLine(int maxSize, @Nullable Slot slot) {
      return new LineComponent(LineDirection.VERTICAL, maxSize, slot);
   }

   @NotNull
   public static final GUIComponent horizontalLine(@Nullable Slot slot) {
      return horizontalLine(Integer.MAX_VALUE, slot);
   }

   @NotNull
   public static final GUIComponent horizontalLine(int maxSize, @Nullable Slot slot) {
      return new LineComponent(LineDirection.HORIZONTAL, maxSize, slot);
   }

   @NotNull
   public static final GUIComponent rectangle(@NotNull Slot slot) {
      Intrinsics.checkNotNullParameter(slot, "slot");
      return rectangle(Integer.MAX_VALUE, Integer.MAX_VALUE, slot);
   }

   @NotNull
   public static final GUIComponent rectangle(final int maxHeight, final int maxWidth, @NotNull final Slot slot) {
      Intrinsics.checkNotNullParameter(slot, "slot");
      return new AutofillComponent()      // $VF: Compiled from Shapes.kt
 {
         @Override
         public int getColumns() {
            return RangesKt.coerceAtMost(this.getMaxColumns(), maxWidth);
         }

         @Override
         public int getRows() {
            return RangesKt.coerceAtMost(this.getMaxRows(), maxHeight);
         }

         public Slot getSlotAt(int row, int column, Player player, Menu menu) {
            Intrinsics.checkNotNullParameter(player, "player");
            Intrinsics.checkNotNullParameter(menu, "menu");
            return slot;
         }
      };
   }
}
