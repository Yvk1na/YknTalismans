package com.willfp.talismans.ecomponent;

import com.willfp.eco.core.gui.GUIComponent;
import com.willfp.eco.core.gui.slot.Slot;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from GUIExtensions.kt
@Metadata(
   mv = {2, 3, 0},
   k = 2,
   xi = 48,
   d1 = "\u0000,\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0014\u0010\n\u001a\u00020\u000b*\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0007*d\u0010\u0000\"/\u0012\u0004\u0012\u00020\u0002\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u00012/\u0012\u0004\u0012\u00020\u0002\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\u0004\u0012\b\b\u0005\u0012\u0004\b\b(\u0006\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0001¨\u0006\r",
   d2 = {
         "SlotAction",
         "Lcom/willfp/eco/libs/kotlin/Function4;",
         "Lorg/bukkit/entity/Player;",
         "Lorg/bukkit/event/inventory/InventoryClickEvent;",
         "Lcom/willfp/eco/libs/kotlin/ParameterName;",
         "name",
         "event",
         "Lcom/willfp/eco/core/gui/slot/Slot;",
         "Lcom/willfp/eco/core/gui/menu/Menu;",
         "",
         "orElse",
         "Lcom/willfp/eco/core/gui/GUIComponent;",
         "slot",
         "eco-api"
   }
)
public final class GUIExtensionsKt {
   @NotNull
   public static final GUIComponent orElse(@NotNull GUIComponent $this$orElse, @Nullable Slot slot) {
      Intrinsics.checkNotNullParameter($this$orElse, "<this>");
      return new OrElseGUIComponent($this$orElse, slot);
   }
}
