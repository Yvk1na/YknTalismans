package com.willfp.talismans.ecomponent.components;

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
   k = 1,
   xi = 48,
   d1 = "\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B!\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0004\b\b\u0010\tJ\b\u0010\n\u001a\u00020\u0005H\u0016J\b\u0010\u000b\u001a\u00020\u0005H\u0016J*\u0010\f\u001a\u0004\u0018\u00010\u00072\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013",
   d2 = {
         "Lcom/willfp/talismans/ecomponent/components/LineComponent;",
         "Lcom/willfp/talismans/ecomponent/AutofillComponent;",
         "direction",
         "Lcom/willfp/talismans/ecomponent/components/LineDirection;",
         "maxSize",
         "",
         "slot",
         "Lcom/willfp/eco/core/gui/slot/Slot;",
         "<init>",
         "(Lcom/willfp/talismans/ecomponent/components/LineDirection;ILcom/willfp/eco/core/gui/slot/Slot;)V",
         "getColumns",
         "getRows",
         "getSlotAt",
         "row",
         "column",
         "player",
         "Lorg/bukkit/entity/Player;",
         "menu",
         "Lcom/willfp/eco/core/gui/menu/Menu;",
         "eco-api"
   }
)
final class LineComponent extends AutofillComponent {
   @NotNull
   private final LineDirection direction;
   private final int maxSize;
   @Nullable
   private final Slot slot;

   public LineComponent(@NotNull LineDirection direction, int maxSize, @Nullable Slot slot) {
      Intrinsics.checkNotNullParameter(direction, "direction");
      super();
      this.direction = direction;
      this.maxSize = maxSize;
      this.slot = slot;
   }

   @Override
   public int getColumns() {
      return this.direction == LineDirection.HORIZONTAL ? RangesKt.coerceAtMost(this.getMaxColumns(), this.maxSize) : 1;
   }

   @Override
   public int getRows() {
      return this.direction == LineDirection.VERTICAL ? RangesKt.coerceAtMost(this.getMaxRows(), this.maxSize) : 1;
   }

   @Nullable
   public Slot getSlotAt(int row, int column, @NotNull Player player, @NotNull Menu menu) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(menu, "menu");
      return this.slot;
   }
}
