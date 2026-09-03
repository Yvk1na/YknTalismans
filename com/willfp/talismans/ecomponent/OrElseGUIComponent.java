package com.willfp.talismans.ecomponent;

import com.willfp.eco.core.gui.GUIComponent;
import com.willfp.eco.core.gui.menu.Menu;
import com.willfp.eco.core.gui.slot.Slot;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from GUIExtensions.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0001\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0005\u0010\u0006J*\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\t\u0010\u000f\u001a\u00020\tH\u0096\u0001J\t\u0010\u0010\u001a\u00020\tH\u0096\u0001R\u000e\u0010\u0002\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0011",
   d2 = {
         "Lcom/willfp/talismans/ecomponent/OrElseGUIComponent;",
         "Lcom/willfp/eco/core/gui/GUIComponent;",
         "delegate",
         "slot",
         "Lcom/willfp/eco/core/gui/slot/Slot;",
         "<init>",
         "(Lcom/willfp/eco/core/gui/GUIComponent;Lcom/willfp/eco/core/gui/slot/Slot;)V",
         "getSlotAt",
         "row",
         "",
         "column",
         "player",
         "Lorg/bukkit/entity/Player;",
         "menu",
         "Lcom/willfp/eco/core/gui/menu/Menu;",
         "getColumns",
         "getRows",
         "eco-api"
   }
)
final class OrElseGUIComponent implements GUIComponent {
   @NotNull
   private final GUIComponent delegate;
   @Nullable
   private final Slot slot;

   public OrElseGUIComponent(@NotNull GUIComponent delegate, @Nullable Slot slot) {
      Intrinsics.checkNotNullParameter(delegate, "delegate");
      super();
      this.delegate = delegate;
      this.slot = slot;
   }

   @Nullable
   public Slot getSlotAt(int row, int column, @NotNull Player player, @NotNull Menu menu) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(menu, "menu");
      Slot var10000 = this.delegate.getSlotAt(row, column, player, menu);
      if (var10000 == null) {
         var10000 = this.slot;
      }

      return var10000;
   }

   public int getRows() {
      return this.delegate.getRows();
   }

   public int getColumns() {
      return this.delegate.getColumns();
   }
}
