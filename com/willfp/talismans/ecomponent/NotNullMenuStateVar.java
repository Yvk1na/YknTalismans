package com.willfp.talismans.ecomponent;

import com.willfp.eco.core.gui.menu.Menu;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from MenuStateVar.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0002\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u001f\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00028\u0000¢\u0006\u0004\b\t\u0010\nJ\u0016\u0010\f\u001a\u00028\u00002\u0006\u0010\r\u001a\u00020\u000eH\u0096\u0002¢\u0006\u0002\u0010\u000fJ\u001e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010\u0013R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u00028\u0000X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000b¨\u0006\u0014",
   d2 = {
         "Lcom/willfp/talismans/ecomponent/NotNullMenuStateVar;",
         "T",
         "",
         "Lcom/willfp/talismans/ecomponent/MenuStateVar;",
         "menu",
         "Lcom/willfp/eco/core/gui/menu/Menu;",
         "key",
         "",
         "default",
         "<init>",
         "(Lcom/willfp/eco/core/gui/menu/Menu;Ljava/lang/String;Ljava/lang/Object;)V",
         "Ljava/lang/Object;",
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
final class NotNullMenuStateVar<T> implements MenuStateVar<T> {
   @NotNull
   private final Menu menu;
   @NotNull
   private final String key;
   @NotNull
   private final T default;

   public NotNullMenuStateVar(@NotNull Menu menu, @NotNull String key, @NotNull T var3) {
      Intrinsics.checkNotNullParameter(menu, "menu");
      Intrinsics.checkNotNullParameter(key, "key");
      Intrinsics.checkNotNullParameter(default, "default");
      super();
      this.menu = menu;
      this.key = key;
      this.default = (T)default;
   }

   @NotNull
   @Override
   public T get(@NotNull Player player) {
      Intrinsics.checkNotNullParameter(player, "player");
      Object var10000 = this.menu.getState(player, this.key);
      if (var10000 == null) {
         var10000 = this.default;
      }

      return (T)var10000;
   }

   @Override
   public void set(@NotNull Player player, @NotNull T value) {
      Intrinsics.checkNotNullParameter(player, "player");
      Intrinsics.checkNotNullParameter(value, "value");
      this.menu.setState(player, this.key, value);
   }
}
