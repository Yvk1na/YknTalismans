package com.willfp.talismans.libreforge.loader.internal.configs;

import com.willfp.eco.core.config.interfaces.Config;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.io.FilesKt;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.libreforge.configs.LibreforgeObjectConfig;
import com.willfp.talismans.libreforge.loader.configs.ConfigCategory;
import java.io.File;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from RegistrableConfig.kt
@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0080\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0004\b\n\u0010\u000bJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0019\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001b\u001a\u00020\tHÆ\u0003J3\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tHÆ\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010 \u001a\u00020!HÖ\u0001J\t\u0010\"\u001a\u00020\u0007HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0014\u001a\u00020\u0015¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006#",
   d2 = {
         "Lcom/willfp/talismans/libreforge/loader/internal/configs/RegistrableConfig;",
         "",
         "config",
         "Lcom/willfp/eco/core/config/interfaces/Config;",
         "file",
         "Ljava/io/File;",
         "id",
         "",
         "category",
         "Lcom/willfp/talismans/libreforge/loader/configs/ConfigCategory;",
         "<init>",
         "(Lcom/willfp/eco/core/config/interfaces/Config;Ljava/io/File;Ljava/lang/String;Lcom/willfp/talismans/libreforge/loader/configs/ConfigCategory;)V",
         "getConfig",
         "()Lcom/willfp/eco/core/config/interfaces/Config;",
         "getFile",
         "()Ljava/io/File;",
         "getId",
         "()Ljava/lang/String;",
         "getCategory",
         "()Lcom/willfp/talismans/libreforge/loader/configs/ConfigCategory;",
         "handle",
         "Lcom/willfp/libreforge/configs/LibreforgeObjectConfig;",
         "getHandle",
         "()Lcom/willfp/libreforge/configs/LibreforgeObjectConfig;",
         "component1",
         "component2",
         "component3",
         "component4",
         "copy",
         "equals",
         "",
         "other",
         "hashCode",
         "",
         "toString",
         "loader"
   }
)
public final class RegistrableConfig {
   @NotNull
   private final Config config;
   @Nullable
   private final File file;
   @NotNull
   private final String id;
   @NotNull
   private final ConfigCategory category;
   @NotNull
   private final LibreforgeObjectConfig handle;

   public RegistrableConfig(@NotNull Config config, @Nullable File file, @NotNull String id, @NotNull ConfigCategory category) {
      LibreforgeObjectConfig var10001;
      Config var10003;
      String var10004;
      label11: {
         Intrinsics.checkNotNullParameter(config, "config");
         Intrinsics.checkNotNullParameter(id, "id");
         Intrinsics.checkNotNullParameter(category, "category");
         super();
         this.config = config;
         this.file = file;
         this.id = id;
         this.category = category;
         var10001 = new LibreforgeObjectConfig;
         var10003 = this.config;
         if (this.file != null) {
            var10004 = FilesKt.readText$default(this.file, null, 1, null);
            if (var10004 != null) {
               break label11;
            }
         }

         var10004 = this.config.toPlaintext();
      }

      String var5 = var10004;
      Intrinsics.checkNotNull(var5);
      var10001./* $VF: Unable to resugar constructor */<init>(var10003, var5, this.id, this.category.getHandle$loader());
      this.handle = var10001;
   }

   @NotNull
   public final Config getConfig() {
      return this.config;
   }

   @Nullable
   public final File getFile() {
      return this.file;
   }

   @NotNull
   public final String getId() {
      return this.id;
   }

   @NotNull
   public final ConfigCategory getCategory() {
      return this.category;
   }

   @NotNull
   public final LibreforgeObjectConfig getHandle() {
      return this.handle;
   }

   @NotNull
   public final Config component1() {
      return this.config;
   }

   @Nullable
   public final File component2() {
      return this.file;
   }

   @NotNull
   public final String component3() {
      return this.id;
   }

   @NotNull
   public final ConfigCategory component4() {
      return this.category;
   }

   @NotNull
   public final RegistrableConfig copy(@NotNull Config config, @Nullable File file, @NotNull String id, @NotNull ConfigCategory category) {
      Intrinsics.checkNotNullParameter(config, "config");
      Intrinsics.checkNotNullParameter(id, "id");
      Intrinsics.checkNotNullParameter(category, "category");
      return new RegistrableConfig(config, file, id, category);
   }

   @NotNull
   @Override
   public String toString() {
      return "RegistrableConfig(config=" + this.config + ", file=" + this.file + ", id=" + this.id + ", category=" + this.category + ")";
   }

   @Override
   public int hashCode() {
      int result = this.config.hashCode();
      result = result * 31 + (this.file == null ? 0 : this.file.hashCode());
      result = result * 31 + this.id.hashCode();
      return result * 31 + this.category.hashCode();
   }

   @Override
   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof RegistrableConfig var2)) {
         return false;
      } else if (!Intrinsics.areEqual(this.config, var2.config)) {
         return false;
      } else if (!Intrinsics.areEqual(this.file, var2.file)) {
         return false;
      } else {
         return !Intrinsics.areEqual(this.id, var2.id) ? false : Intrinsics.areEqual(this.category, var2.category);
      }
   }
}
