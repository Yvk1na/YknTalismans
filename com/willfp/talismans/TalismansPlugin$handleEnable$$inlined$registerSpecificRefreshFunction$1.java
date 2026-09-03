package com.willfp.talismans;

import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.Unit;
import com.willfp.eco.libs.kotlin.jvm.functions.Function1;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.libreforge.Dispatcher;
import com.willfp.talismans.talismans.util.TalismanChecks;
import org.bukkit.entity.Player;

// $VF: Compiled from HolderProvider.kt
@Metadata(mv = {2, 3, 0}, k = 3, xi = 48)
@SourceDebugExtension(
   "SMAP\nHolderProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HolderProvider.kt\ncom/willfp/libreforge/HolderProviderKt$registerSpecificRefreshFunction$1\n+ 2 Dispatcher.kt\ncom/willfp/libreforge/DispatcherKt\n+ 3 TalismansPlugin.kt\ncom/willfp/talismans/TalismansPlugin\n*L\n1#1,436:1\n33#2:437\n64#3,2:438\n*S KotlinDebug\n*F\n+ 1 HolderProvider.kt\ncom/willfp/libreforge/HolderProviderKt$registerSpecificRefreshFunction$1\n*L\n156#1:437\n*E\n"
)
public final class TalismansPlugin$handleEnable$$inlined$registerSpecificRefreshFunction$1 implements Function1<Dispatcher<?>, Unit> {
   public final void invoke(Dispatcher<?> it) {
      Intrinsics.checkNotNullParameter(it, "it");
      Dispatcher $this$get$iv = it;
      int $i$f$get = 0;
      Player var10000 = (Player)$this$get$iv.getDispatcher();
      if (!(var10000 instanceof Player)) {
         var10000 = null;
      }

      var10000 = var10000;
      if (var10000 != null) {
         Object t = var10000;
         int var5/* $VF was: $i$a$-let-HolderProviderKt$registerSpecificRefreshFunction$1$1 */ = 0;
         Player itx = (Player)t;
         int var7/* $VF was: $i$a$-registerSpecificRefreshFunction-TalismansPlugin$handleEnable$2 */ = 0;
         TalismanChecks.clearCache(itx);
      }
   }
}
