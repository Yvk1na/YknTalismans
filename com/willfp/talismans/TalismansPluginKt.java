package com.willfp.talismans;

import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from TalismansPlugin.kt
@Metadata(
   mv = {2, 3, 0},
   k = 2,
   xi = 48,
   d1 = "\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\"\u001e\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0000\u001a\u00020\u0001@BX\u0080.¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0004¨\u0006\u0005",
   d2 = {"value", "Lcom/willfp/talismans/TalismansPlugin;", "plugin", "getPlugin", "()Lcom/willfp/talismans/TalismansPlugin;", "core-plugin"}
)
public final class TalismansPluginKt {
   private static TalismansPlugin plugin;

   @NotNull
   public static final TalismansPlugin getPlugin() {
      if (plugin != null) {
         return plugin;
      }

      Intrinsics.throwUninitializedPropertyAccessException("plugin");
      return null;
   }

   /** Java-visible equivalent of Kotlin's generated private-property accessor. */
   public static final void access$setPlugin$p(@NotNull TalismansPlugin value) {
      Intrinsics.checkNotNullParameter(value, "value");
      plugin = value;
   }
}
