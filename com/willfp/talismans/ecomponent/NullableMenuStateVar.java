package com.willfp.talismans.ecomponent;

import com.willfp.eco.core.gui.menu.Menu;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from MenuStateVar.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00010\u0003B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0004\b\b\u0010\tJ\u0018\u0010\n\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u000b\u001a\u00020\fH\u0096\u0002¢\u0006\u0002\u0010\rJ \u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\u0010\u001a\u0004\u0018\u00018\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0011R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012",
   d2 = {
         "Lcom/willfp/talismans/ecomponent/NullableMenuStateVar;",
         "T",
         "",
         "Lcom/willfp/talismans/ecomponent/MenuStateVar;",
         "menu",
         "Lcom/willfp/eco/core/gui/menu/Menu;",
         "key",
         "",
         "<init>",
         "(Lcom/willfp/eco/core/gui/menu/Menu;Ljava/lang/String;)V",
         "get",
         "player",
         "Lorg/bukkit/entity/Player;",
         "(Lorg/bukkit/entity/Player;)Ljava/lang/Object;",
         "set",
         "",
         "value",
         "(Lorg/bukkit/entity/Player;Ljava/lang/Object;)V",
         "eco-api"
   }
)
final class NullableMenuStateVar<T> implements MenuStateVar<T> {
   @NotNull
   private final Menu menu;
   @NotNull
   private final String key;

   public NullableMenuStateVar(@NotNull Menu menu, @NotNull String key) {
      Intrinsics.checkNotNullParameter(menu, "menu");
      Intrinsics.checkNotNullParameter(key, "key");
      super();
      this.menu = menu;
      this.key = key;
   }

   @Nullable
   @Override
   public T get(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      return (T)this.menu.getState(player, this.key);
   }

   @Override
   public void set(@NotNull Player player, @Nullable T value) {
      Intrinsics.checkNotNullParameter(player, "player");
      this.menu.setState(player, this.key, value);
   }
}
