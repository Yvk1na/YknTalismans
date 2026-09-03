package com.willfp.talismans.ecomponent.components;

import com.willfp.eco.core.gui.GUIHelperExtensions;
import com.willfp.eco.core.gui.menu.Menu;
import com.willfp.eco.core.gui.slot.Slot;
import com.willfp.eco.core.map.DefaultMap;
import com.willfp.eco.core.map.DefaultMapExtensions;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.collections.ArraysKt;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.MutablePropertyReference1;
import com.willfp.eco.libs.kotlin.jvm.internal.MutablePropertyReference1Impl;
import com.willfp.eco.libs.kotlin.jvm.internal.Reflection;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.eco.libs.kotlin.properties.Delegates;
import com.willfp.eco.libs.kotlin.properties.ReadWriteProperty;
import com.willfp.eco.libs.kotlin.reflect.KProperty;
import com.willfp.talismans.ecomponent.AutofillComponent;
import com.willfp.talismans.ecomponent.GUIPosition;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from LevelComponent.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u0010\u0010\u0018\u001a\u00020\n2\u0006\u0010\u0019\u001a\u00020\tH\u0002J*\u0010\u001a\u001a\u0004\u0018\u00010\n2\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\u0016J\b\u00100\u001a\u000201H\u0002J\u000e\u00102\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\tJ(\u00103\u001a\u0002042\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010\u0019\u001a\u00020\t2\u0006\u00105\u001a\u000206H&J\u0018\u00107\u001a\u0002062\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0019\u001a\u00020\tH&R[\u0010\u0004\u001aO\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\u00060\u0006\u00128\u00126\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\t0\t\u0012\u0006\u0012\u0004\u0018\u00010\n \u0007*\u001a\u0012\f\u0012\n \u0007*\u0004\u0018\u00010\t0\t\u0012\u0006\u0012\u0004\u0018\u00010\n\u0018\u00010\u000b0\b0\u0005¢\u0006\u0002\b\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e¢\u0006\u0002\n\u0000R\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R+\u0010\"\u001a\u00020\t2\u0006\u0010!\u001a\u00020\t8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b&\u0010'\u001a\u0004\b#\u0010\u0017\"\u0004\b$\u0010%R\u0011\u0010(\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b)\u0010\u0017R+\u0010*\u001a\u00020\t2\u0006\u0010!\u001a\u00020\t8B@BX\u0082\u008e\u0002¢\u0006\u0012\n\u0004\b-\u0010'\u001a\u0004\b+\u0010\u0017\"\u0004\b,\u0010%R\u0011\u0010.\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b/\u0010\u0017¨\u00068",
   d2 = {
         "Lcom/willfp/talismans/ecomponent/components/LevelComponent;",
         "Lcom/willfp/talismans/ecomponent/AutofillComponent;",
         "<init>",
         "()V",
         "slots",
         "Lcom/willfp/eco/core/map/DefaultMap;",
         "Lcom/willfp/talismans/ecomponent/GUIPosition;",
         "com.willfp.eco.libs.kotlin.jvm.PlatformType",
         "",
         "",
         "Lcom/willfp/eco/core/gui/slot/Slot;",
         "",
         "Lorg/jetbrains/annotations/NotNull;",
         "progressionSlots",
         "isBuilt",
         "",
         "pattern",
         "",
         "",
         "getPattern",
         "()Ljava/util/List;",
         "maxLevel",
         "getMaxLevel",
         "()I",
         "buildSlot",
         "level",
         "getSlotAt",
         "row",
         "column",
         "player",
         "Lorg/bukkit/entity/Player;",
         "menu",
         "Lcom/willfp/eco/core/gui/menu/Menu;",
         "<set-?>",
         "_levelsPerPage",
         "get_levelsPerPage",
         "set_levelsPerPage",
         "(I)V",
         "_levelsPerPage$delegate",
         "Lcom/willfp/eco/libs/kotlin/properties/ReadWriteProperty;",
         "levelsPerPage",
         "getLevelsPerPage",
         "_pages",
         "get_pages",
         "set_pages",
         "_pages$delegate",
         "pages",
         "getPages",
         "build",
         "",
         "getPageOf",
         "getLevelItem",
         "Lorg/bukkit/inventory/ItemStack;",
         "levelState",
         "Lcom/willfp/talismans/ecomponent/components/LevelState;",
         "getLevelState",
         "eco-api"
   }
)
@SourceDebugExtension(
   "SMAP\nLevelComponent.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LevelComponent.kt\ncom/willfp/ecomponent/components/LevelComponent\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,140:1\n383#2,7:141\n*S KotlinDebug\n*F\n+ 1 LevelComponent.kt\ncom/willfp/ecomponent/components/LevelComponent\n*L\n63#1:141,7\n*E\n"
)
public abstract class LevelComponent extends AutofillComponent {
   @NotNull
   private final DefaultMap<GUIPosition, Map<Integer, Slot>> slots = DefaultMapExtensions.nestedMap();
   @NotNull
   private final Map<GUIPosition, Integer> progressionSlots = new LinkedHashMap<>();
   private boolean isBuilt;
   @NotNull
   private final ReadWriteProperty _levelsPerPage$delegate = Delegates.INSTANCE.notNull();
   @NotNull
   private final ReadWriteProperty _pages$delegate = Delegates.INSTANCE.notNull();

   @NotNull
   public abstract List<String> getPattern();

   public abstract int getMaxLevel();

   private final Slot buildSlot(int level) {
      return GUIHelperExtensions.slot(LevelComponent::buildSlot$lambda$0);
   }

   @Nullable
   public Slot getSlotAt(int row, int column, @NotNull Player player, @NotNull Menu menu) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(menu, "menu");
      if (!this.isBuilt) {
         this.build();
      }

      GUIPosition position = new GUIPosition(row, column);
      Object var10000 = this.slots.get(position);
      Intrinsics.checkNotNullExpressionValue(var10000, "get(...)");
      Map $this$getOrPut$iv = (Map)var10000;
      Object key$iv = menu.getPage(player);
      int $i$f$getOrPut = 0;
      Object value$iv = $this$getOrPut$iv.get(key$iv);
      if (value$iv == null) {
         int var10/* $VF was: $i$a$-getOrPut-LevelComponent$getSlotAt$1 */ = 0;
         Integer var15 = this.progressionSlots.get(position);
         if (var15 == null) {
            return null;
         }

         int offset = var15;
         int base = (menu.getPage(player) - 1) * this.getLevelsPerPage();
         int level = offset + base;
         Object answer$iv = level > this.getMaxLevel() ? null : this.buildSlot(level);
         $this$getOrPut$iv.put(key$iv, answer$iv);
         var10000 = answer$iv;
      } else {
         var10000 = value$iv;
      }

      return (Slot)var10000;
   }

   private final int get_levelsPerPage() {
      return ((Number)this._levelsPerPage$delegate.getValue(this, $$delegatedProperties[0])).intValue();
   }

   private final void set_levelsPerPage(int var1) {
      this._levelsPerPage$delegate.setValue(this, $$delegatedProperties[0], var1/* $VF was: <set-?> */);
   }

   public final int getLevelsPerPage() {
      if (!this.isBuilt) {
         this.build();
      }

      return this.get_levelsPerPage();
   }

   private final int get_pages() {
      return ((Number)this._pages$delegate.getValue(this, $$delegatedProperties[1])).intValue();
   }

   private final void set_pages(int var1) {
      this._pages$delegate.setValue(this, $$delegatedProperties[1], var1/* $VF was: <set-?> */);
   }

   public final int getPages() {
      if (!this.isBuilt) {
         this.build();
      }

      return this.get_pages();
   }

   private final void build() {
      this.isBuilt = true;
      int x = 0;

      for (String row : this.getPattern()) {
         x++;
         int y = 0;
         int var5 = 0;

         for (int var6 = row.length(); var5 < var6; var5++) {
            char char = row.charAt(var5);
            y++;
            if (char != '0') {
               int pos = ArraysKt.indexOf(LevelComponentKt.access$getProgressionOrder$p(), char);
               if (pos != -1) {
                  this.progressionSlots.put(new GUIPosition(x, y), pos + 1);
               }
            }
         }
      }

      this.set_levelsPerPage(this.progressionSlots.size());
      this.set_pages((int)Math.ceil((double)this.getMaxLevel() / this.getLevelsPerPage()));
   }

   public final int getPageOf(int level) {
      if (!this.isBuilt) {
         this.build();
      }

      return (int)Math.ceil((double)level / this.getLevelsPerPage());
   }

   @NotNull
   public abstract ItemStack getLevelItem(@NotNull Player var1, @NotNull Menu var2, int var3, @NotNull LevelState var4);

   @NotNull
   public abstract LevelState getLevelState(@NotNull Player var1, int var2);

   private static final ItemStack buildSlot$lambda$0(LevelComponent this$0, int $level, Player player, Menu menu) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(menu, "menu");
      return this$0.getLevelItem(player, menu, $level, this$0.getLevelState(player, $level));
   }

   static {
      KProperty[] var0 = new KProperty[]{
         Reflection.mutableProperty1(
            (MutablePropertyReference1)(new MutablePropertyReference1Impl(LevelComponent.class, "_levelsPerPage", "get_levelsPerPage()I", 0))
         ),
         Reflection.mutableProperty1((MutablePropertyReference1)(new MutablePropertyReference1Impl(LevelComponent.class, "_pages", "get_pages()I", 0)))
      };
      $$delegatedProperties = var0;
   }
}
