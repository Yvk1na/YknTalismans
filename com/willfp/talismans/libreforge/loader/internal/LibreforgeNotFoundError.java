package com.willfp.talismans.libreforge.loader.internal;

import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from LibreforgeLoader.kt
@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0002\u0018\u00002\u00060\u0001j\u0002`\u0002B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t",
   d2 = {
         "Lcom/willfp/talismans/libreforge/loader/internal/LibreforgeNotFoundError;",
         "Ljava/lang/Error;",
         "Lcom/willfp/eco/libs/kotlin/Error;",
         "message",
         "",
         "<init>",
         "(Ljava/lang/String;)V",
         "getMessage",
         "()Ljava/lang/String;",
         "loader"
   }
)
final class LibreforgeNotFoundError extends Error {
   @NotNull
   private final String message;

   public LibreforgeNotFoundError(@NotNull String message) {
      Intrinsics.checkNotNullParameter(message, "message");
      super(message);
      this.message = message;
   }

   @NotNull
   @Override
   public String getMessage() {
      return this.message;
   }
}
