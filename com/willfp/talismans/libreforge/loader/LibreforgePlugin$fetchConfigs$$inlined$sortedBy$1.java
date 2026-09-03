package com.willfp.talismans.libreforge.loader;

import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.comparisons.ComparisonsKt;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.talismans.libreforge.loader.internal.configs.RegistrableConfig;
import java.util.Comparator;

// $VF: Compiled from Comparisons.kt
@Metadata(mv = {2, 2, 0}, k = 3, xi = 48)
@SourceDebugExtension(
   "SMAP\nComparisons.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Comparisons.kt\nkotlin/comparisons/ComparisonsKt__ComparisonsKt$compareBy$2\n+ 2 LibreforgePlugin.kt\ncom/willfp/libreforge/loader/LibreforgePlugin\n*L\n1#1,328:1\n188#2:329\n*E\n"
)
public final class LibreforgePlugin$fetchConfigs$$inlined$sortedBy$1<T> implements Comparator {
   @Override
   public final int compare(T a, T b) {
      RegistrableConfig it = (RegistrableConfig)a;
      int var4/* $VF was: $i$a$-sortedBy-LibreforgePlugin$fetchConfigs$2 */ = 0;
      Comparable var10000 = it.getId();
      it = (RegistrableConfig)b;
      Comparable var5 = var10000;
      var4/* $VF was: $i$a$-sortedBy-LibreforgePlugin$fetchConfigs$2 */ = 0;
      return ComparisonsKt.compareValues(var5, it.getId());
   }
}
