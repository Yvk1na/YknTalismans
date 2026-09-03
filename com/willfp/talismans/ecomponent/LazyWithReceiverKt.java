package com.willfp.talismans.ecomponent;

import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.functions.Function1;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from LazyWithReceiver.kt
@Metadata(
   mv = {2, 3, 0},
   k = 2,
   xi = 48,
   d1 = "\u0000\u0014\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a7\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00030\u0005¢\u0006\u0002\b\u0006¨\u0006\u0007",
   d2 = {
         "lazyWithReceiver",
         "Lcom/willfp/talismans/ecomponent/LazyWithReceiver;",
         "T",
         "R",
         "getter",
         "Lcom/willfp/eco/libs/kotlin/Function1;",
         "Lcom/willfp/eco/libs/kotlin/ExtensionFunctionType;",
         "eco-api"
   }
)
public final class LazyWithReceiverKt {
   @NotNull
   public static final <T, R> LazyWithReceiver<T, R> lazyWithReceiver(@NotNull Function1<? super T, ? extends R> getter) {
      Intrinsics.checkNotNullParameter(getter, "getter");
      return new LazyWithReceiver<>(LazyWithReceiverKt::lazyWithReceiver$lambda$0);
   }

   private static final Object lazyWithReceiver$lambda$0(Function1 $getter, Object $this$LazyWithReceiver) {
      return $getter.invoke($this$LazyWithReceiver);
   }
}
