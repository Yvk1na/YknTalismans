package com.willfp.talismans.ecomponent.components;

import com.willfp.eco.core.gui.GUIComponent;
import com.willfp.eco.core.gui.GUIHelperExtensions;
import com.willfp.eco.core.gui.menu.Menu;
import com.willfp.eco.core.gui.page.PageChanger;
import com.willfp.eco.core.gui.page.PageChanger.Direction;
import com.willfp.eco.core.gui.slot.Slot;
import com.willfp.eco.core.gui.slot.SlotBuilder;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.Unit;
import com.willfp.eco.libs.kotlin.jvm.functions.Function4;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.talismans.ecomponent.GUIExtensionsKt;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from PageChangerWithDefault.kt
@Metadata(
   mv = {2, 3, 0},
   k = 2,
   xi = 48,
   d1 = "\u0000:\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001aO\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u000527\u0010\u0006\u001a3\u0012\u0004\u0012\u00020\b\u0012\u0013\u0012\u00110\t¢\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\u0007j\u0002`\u0010¨\u0006\u0011",
   d2 = {
         "pageChangerWithDefault",
         "Lcom/willfp/eco/core/gui/GUIComponent;",
         "item",
         "Lorg/bukkit/inventory/ItemStack;",
         "direction",
         "Lcom/willfp/eco/core/gui/page/PageChanger$Direction;",
         "action",
         "Lcom/willfp/eco/libs/kotlin/Function4;",
         "Lorg/bukkit/entity/Player;",
         "Lorg/bukkit/event/inventory/InventoryClickEvent;",
         "Lcom/willfp/eco/libs/kotlin/ParameterName;",
         "name",
         "event",
         "Lcom/willfp/eco/core/gui/slot/Slot;",
         "Lcom/willfp/eco/core/gui/menu/Menu;",
         "",
         "Lcom/willfp/talismans/ecomponent/SlotAction;",
         "eco-api"
   }
)
public final class PageChangerWithDefaultKt {
   @NotNull
   public static final GUIComponent pageChangerWithDefault(
      @NotNull ItemStack item,
      @NotNull Direction direction,
      @NotNull Function4<? super Player, ? super InventoryClickEvent, ? super Slot, ? super Menu, Unit> action
   ) {
      Intrinsics.checkNotNullParameter(item, "item");
      Intrinsics.checkNotNullParameter(direction, "direction");
      Intrinsics.checkNotNullParameter(action, "action");
      return GUIExtensionsKt.orElse(
         (GUIComponent)(new PageChanger(item, direction)), GUIHelperExtensions.slot(item, PageChangerWithDefaultKt::pageChangerWithDefault$lambda$0)
      );
   }

   private static final Unit pageChangerWithDefault$lambda$0(Function4 $action, SlotBuilder $this$slot) {
      Intrinsics.checkNotNullParameter($this$slot, "$this$slot");
      GUIHelperExtensions.onLeftClick($this$slot, $action);
      return Unit.INSTANCE;
   }
}
