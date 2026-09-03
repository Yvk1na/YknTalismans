package com.willfp.talismans.ecomponent.components;

import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from LevelComponent.kt
@Metadata(
   mv = {2, 3, 0},
   k = 2,
   xi = 48,
   d1 = "\u0000\b\n\u0000\n\u0002\u0010\u0019\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0002",
   d2 = {"progressionOrder", "", "eco-api"}
)
public final class LevelComponentKt {
   @NotNull
   private static final char[] progressionOrder;

   static {
      char[] var10000 = "123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
      Intrinsics.checkNotNullExpressionValue(var10000, "toCharArray(...)");
      progressionOrder = var10000;
   }
}
