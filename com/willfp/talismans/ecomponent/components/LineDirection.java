package com.willfp.talismans.ecomponent.components;

import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.enums.EnumEntries;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from Shapes.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0082\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006",
   d2 = {"Lcom/willfp/talismans/ecomponent/components/LineDirection;", "", "<init>", "(Ljava/lang/String;I)V", "VERTICAL", "HORIZONTAL", "eco-api"}
)
enum LineDirection {
   VERTICAL,
   HORIZONTAL;

   @NotNull
   public static EnumEntries<LineDirection> getEntries() {
      return $ENTRIES;
   }
}
