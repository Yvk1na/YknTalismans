package com.willfp.talismans.ecomponent;

import com.willfp.eco.libs.kotlin.Metadata;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from MenuStateVar.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u0016\u0010\u0003\u001a\u00028\u00002\u0006\u0010\u0004\u001a\u00020\u0005H¦\u0002¢\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00028\u0000H¦\u0002¢\u0006\u0002\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000bÀ\u0006\u0001",
   d2 = {
         "Lcom/willfp/talismans/ecomponent/MenuStateVar;",
         "T",
         "",
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
public interface MenuStateVar<T> {
   T get(@NotNull Player var1);

   void set(@NotNull Player var1, T var2);
}
