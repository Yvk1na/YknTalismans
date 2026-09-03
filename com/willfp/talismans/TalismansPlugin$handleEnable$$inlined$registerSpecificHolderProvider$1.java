package com.willfp.talismans;

import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.collections.CollectionsKt;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.libreforge.Dispatcher;
import com.willfp.libreforge.HolderProvider;
import com.willfp.libreforge.ProvidedHolder;
import com.willfp.talismans.talismans.util.TalismanChecks;
import java.util.Collection;
import org.bukkit.entity.Player;

// $VF: Compiled from HolderProvider.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u001a\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u0006H\u0016¨\u0006\u0007¸\u0006\u0000",
   d2 = {
         "com/willfp/libreforge/HolderProviderKt$registerSpecificHolderProvider$1",
         "Lcom/willfp/libreforge/HolderProvider;",
         "provide",
         "",
         "Lcom/willfp/libreforge/ProvidedHolder;",
         "dispatcher",
         "Lcom/willfp/libreforge/Dispatcher;",
         "common"
   }
)
@SourceDebugExtension(
   "SMAP\nHolderProvider.kt\nKotlin\n*S Kotlin\n*F\n+ 1 HolderProvider.kt\ncom/willfp/libreforge/HolderProviderKt$registerSpecificHolderProvider$1\n+ 2 Dispatcher.kt\ncom/willfp/libreforge/DispatcherKt\n+ 3 TalismansPlugin.kt\ncom/willfp/talismans/TalismansPlugin\n*L\n1#1,436:1\n40#2:437\n33#2:438\n60#3:439\n*S KotlinDebug\n*F\n+ 1 HolderProvider.kt\ncom/willfp/libreforge/HolderProviderKt$registerSpecificHolderProvider$1\n*L\n131#1:437\n132#1:438\n*E\n"
)
public final class TalismansPlugin$handleEnable$$inlined$registerSpecificHolderProvider$1 implements HolderProvider {
   public Collection<ProvidedHolder> provide(Dispatcher<?> dispatcher) {
      Intrinsics.checkNotNullParameter(dispatcher, "dispatcher");
      Dispatcher $this$isType$iv = dispatcher;
      int $i$f$isType = 0;
      Collection var9;
      if ($this$isType$iv.getDispatcher() instanceof Player) {
         $this$isType$iv = dispatcher;
         $i$f$isType = 0;
         Object var10000 = $this$isType$iv.getDispatcher();
         if (!(var10000 instanceof Player)) {
            var10000 = null;
         }

         Player var8 = (Player)var10000;
         Intrinsics.checkNotNull((Player)var10000);
         Player it = var8;
         int var5/* $VF was: $i$a$-registerSpecificHolderProvider-TalismansPlugin$handleEnable$1 */ = 0;
         var9 = TalismanChecks.getTalismansOnPlayer(it);
      } else {
         var9 = CollectionsKt.emptyList();
      }

      return var9;
   }
}
