package com.willfp.talismans.ecomponent;

import com.willfp.eco.core.gui.menu.Menu;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from MenuStateVar.kt
@Metadata(
   mv = {2, 3, 0},
   k = 2,
   xi = 48,
   d1 = "\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a(\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007\u001a3\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u0002H\u0002¢\u0006\u0002\u0010\t\u001a,\u0010\u0000\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00010\n\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0007\u001a7\u0010\u0000\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\n\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u0002H\u0002¢\u0006\u0002\u0010\u000b\u001a$\u0010\u0000\u001a\u0016\u0012\u0004\u0012\u00020\u0005\u0012\f\u0012\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00010\n\"\b\b\u0000\u0010\u0002*\u00020\u0003\u001a/\u0010\u0000\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00010\n\"\b\b\u0000\u0010\u0002*\u00020\u00032\u0006\u0010\b\u001a\u0002H\u0002¢\u0006\u0002\u0010\f¨\u0006\r",
   d2 = {
         "menuStateVar",
         "Lcom/willfp/talismans/ecomponent/MenuStateVar;",
         "T",
         "",
         "menu",
         "Lcom/willfp/eco/core/gui/menu/Menu;",
         "key",
         "",
         "default",
         "(Lcom/willfp/eco/core/gui/menu/Menu;Ljava/lang/String;Ljava/lang/Object;)Lcom/willfp/talismans/ecomponent/MenuStateVar;",
         "Lcom/willfp/talismans/ecomponent/LazyWithReceiver;",
         "(Ljava/lang/String;Ljava/lang/Object;)Lcom/willfp/talismans/ecomponent/LazyWithReceiver;",
         "(Ljava/lang/Object;)Lcom/willfp/talismans/ecomponent/LazyWithReceiver;",
         "eco-api"
   }
)
public final class MenuStateVarKt {
   @NotNull
   public static final <T> MenuStateVar<T> menuStateVar(@NotNull Menu menu, @NotNull String key) {
      Intrinsics.checkNotNullParameter(menu, "menu");
      Intrinsics.checkNotNullParameter(key, "key");
      return new NullableMenuStateVar<>(menu, key);
   }

   @NotNull
   public static final <T> MenuStateVar<T> menuStateVar(@NotNull Menu menu, @NotNull String key, @NotNull T var2) {
      Intrinsics.checkNotNullParameter(menu, "menu");
      Intrinsics.checkNotNullParameter(key, "key");
      Intrinsics.checkNotNullParameter(default, "default");
      return new NotNullMenuStateVar<>(menu, key, (T)default);
   }

   @NotNull
   public static final <T> LazyWithReceiver<Menu, MenuStateVar<T>> menuStateVar(@NotNull String key) {
      Intrinsics.checkNotNullParameter(key, "key");
      return LazyWithReceiverKt.lazyWithReceiver(MenuStateVarKt::menuStateVar$lambda$0);
   }

   @NotNull
   public static final <T> LazyWithReceiver<Menu, MenuStateVar<T>> menuStateVar(@NotNull String key, @NotNull T var1) {
      Intrinsics.checkNotNullParameter(key, "key");
      Intrinsics.checkNotNullParameter(default, "default");
      return LazyWithReceiverKt.lazyWithReceiver(MenuStateVarKt::menuStateVar$lambda$1);
   }

   @NotNull
   public static final <T> LazyWithReceiver<Menu, MenuStateVar<T>> menuStateVar() {
      return LazyWithReceiverKt.lazyWithReceiver(MenuStateVarKt::menuStateVar$lambda$2);
   }

   @NotNull
   public static final <T> LazyWithReceiver<Menu, MenuStateVar<T>> menuStateVar(@NotNull T var0) {
      Intrinsics.checkNotNullParameter(default, "default");
      return LazyWithReceiverKt.lazyWithReceiver(MenuStateVarKt::menuStateVar$lambda$3);
   }

   private static final MenuStateVar menuStateVar$lambda$0(String $key, Menu $this$lazyWithReceiver) {
      Intrinsics.checkNotNullParameter($this$lazyWithReceiver, "$this$lazyWithReceiver");
      return menuStateVar($this$lazyWithReceiver, $key);
   }

   private static final MenuStateVar menuStateVar$lambda$1(String $key, Object $default, Menu $this$lazyWithReceiver) {
      Intrinsics.checkNotNullParameter($this$lazyWithReceiver, "$this$lazyWithReceiver");
      return menuStateVar($this$lazyWithReceiver, $key, $default);
   }

   private static final MenuStateVar menuStateVar$lambda$2(Menu $this$lazyWithReceiver) {
      Intrinsics.checkNotNullParameter($this$lazyWithReceiver, "$this$lazyWithReceiver");
      String var10001 = UUID.randomUUID().toString();
      Intrinsics.checkNotNullExpressionValue(var10001, "toString(...)");
      return menuStateVar($this$lazyWithReceiver, var10001);
   }

   private static final MenuStateVar menuStateVar$lambda$3(Object $default, Menu $this$lazyWithReceiver) {
      Intrinsics.checkNotNullParameter($this$lazyWithReceiver, "$this$lazyWithReceiver");
      String var10001 = UUID.randomUUID().toString();
      Intrinsics.checkNotNullExpressionValue(var10001, "toString(...)");
      return menuStateVar($this$lazyWithReceiver, var10001, $default);
   }
}
