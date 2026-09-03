package com.willfp.talismans.ecomponent;

import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.functions.Function1;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.eco.libs.kotlin.reflect.KProperty;
import java.util.Map;
import java.util.WeakHashMap;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from LazyWithReceiver.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000*\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u0004\b\u0001\u0010\u00022\u00020\u0003B\"\b\u0000\u0012\u0017\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\u0002\b\u0006¢\u0006\u0004\b\u0007\u0010\bJ\"\u0010\r\u001a\u00028\u00012\u0006\u0010\u000e\u001a\u00028\u00002\n\u0010\u000f\u001a\u0006\u0012\u0002\b\u00030\u0010H\u0086\u0002¢\u0006\u0002\u0010\u0011R\"\u0010\u0004\u001a\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\u0002\b\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012",
   d2 = {
         "Lcom/willfp/talismans/ecomponent/LazyWithReceiver;",
         "T",
         "R",
         "",
         "initializer",
         "Lcom/willfp/eco/libs/kotlin/Function1;",
         "Lcom/willfp/eco/libs/kotlin/ExtensionFunctionType;",
         "<init>",
         "(Lcom/willfp/eco/libs/kotlin/jvm/functions/Function1;)V",
         "getInitializer",
         "()Lcom/willfp/eco/libs/kotlin/jvm/functions/Function1;",
         "values",
         "Ljava/util/WeakHashMap;",
         "getValue",
         "ref",
         "property",
         "Lcom/willfp/eco/libs/kotlin/reflect/KProperty;",
         "(Ljava/lang/Object;Lcom/willfp/eco/libs/kotlin/reflect/KProperty;)Ljava/lang/Object;",
         "eco-api"
   }
)
@SourceDebugExtension(
   "SMAP\nLazyWithReceiver.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LazyWithReceiver.kt\ncom/willfp/ecomponent/LazyWithReceiver\n+ 2 Maps.kt\nkotlin/collections/MapsKt__MapsKt\n*L\n1#1,20:1\n383#2,7:21\n*S KotlinDebug\n*F\n+ 1 LazyWithReceiver.kt\ncom/willfp/ecomponent/LazyWithReceiver\n*L\n12#1:21,7\n*E\n"
)
public final class LazyWithReceiver<T, R> {
   @NotNull
   private final Function1<T, R> initializer;
   @NotNull
   private final WeakHashMap<T, R> values;

   public LazyWithReceiver(@NotNull Function1<? super T, ? extends R> initializer) {
      Intrinsics.checkNotNullParameter(initializer, "initializer");
      super();
      this.initializer = initializer;
      this.values = new WeakHashMap<>();
   }

   @NotNull
   public final Function1<T, R> getInitializer() {
      return this.initializer;
   }

   // $VF: Extended synchronized range to monitorexit
   public final R getValue(T ref, @NotNull KProperty<?> property) {
      Intrinsics.checkNotNullParameter(property, "property");
      synchronized (this.values) {
         int var4/* $VF was: $i$a$-synchronized-LazyWithReceiver$getValue$1 */ = 0;
         Map $this$getOrPut$iv = this.values;
         Object key$iv = ref;
         int $i$f$getOrPut = 0;
         Object value$iv = $this$getOrPut$iv.get(key$iv);
         Object var10000;
         if (value$iv == null) {
            int var9/* $VF was: $i$a$-getOrPut-LazyWithReceiver$getValue$1$1 */ = 0;
            Object answer$iv = this.initializer.invoke(ref);
            $this$getOrPut$iv.put(key$iv, answer$iv);
            var10000 = answer$iv;
         } else {
            var10000 = value$iv;
         }

         return (R)var10000;
      }
   }
}
