package com.willfp.talismans.libreforge.loader.configs;

import com.willfp.eco.core.registry.Registrable;
import com.willfp.eco.core.registry.Registry;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from RegistrableCategory.kt
@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\"\n\u0000\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0017\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u001a\u0010\r\u001a\u0004\u0018\u00018\u00002\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005H\u0086\u0002¢\u0006\u0002\u0010\u000eJ\u0017\u0010\u000f\u001a\u0004\u0018\u00018\u00002\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u000eJ\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00028\u00000\u0011R\u001a\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0012",
   d2 = {
         "Lcom/willfp/talismans/libreforge/loader/configs/RegistrableCategory;",
         "T",
         "Lcom/willfp/eco/core/registry/Registrable;",
         "Lcom/willfp/talismans/libreforge/loader/configs/ConfigCategory;",
         "id",
         "",
         "directory",
         "<init>",
         "(Ljava/lang/String;Ljava/lang/String;)V",
         "registry",
         "Lcom/willfp/eco/core/registry/Registry;",
         "getRegistry",
         "()Lcom/willfp/eco/core/registry/Registry;",
         "get",
         "(Ljava/lang/String;)Lcom/willfp/eco/core/registry/Registrable;",
         "getByID",
         "values",
         "",
         "loader"
   }
)
@SourceDebugExtension(
   "SMAP\nRegistrableCategory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RegistrableCategory.kt\ncom/willfp/libreforge/loader/configs/RegistrableCategory\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,33:1\n1#2:34\n*E\n"
)
public abstract class RegistrableCategory<T extends Registrable> extends ConfigCategory {
   @NotNull
   private final Registry<T> registry;

   public RegistrableCategory(@NotNull String id, @NotNull String directory) {
      Intrinsics.checkNotNullParameter(id, "id");
      Intrinsics.checkNotNullParameter(directory, "directory");
      super(id, directory);
      this.registry = new Registry();
   }

   @NotNull
   protected final Registry<T> getRegistry() {
      return this.registry;
   }

   @Nullable
   public final T get(@Nullable String id) {
      return this.getByID(id);
   }

   @Nullable
   public final T getByID(@Nullable String id) {
      Registrable var10000;
      if (id != null) {
         int var3/* $VF was: $i$a$-let-RegistrableCategory$getByID$1 */ = 0;
         var10000 = this.registry.get(id);
      } else {
         var10000 = null;
      }

      return (T)var10000;
   }

   @NotNull
   public final Set<T> values() {
      Set var10000 = this.registry.values();
      Intrinsics.checkNotNullExpressionValue(var10000, "values(...)");
      return var10000;
   }
}
