package com.willfp.talismans.libreforge.loader.configs;

import com.willfp.eco.core.config.ConfigExtensions;
import com.willfp.eco.core.config.interfaces.Config;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.io.FilesKt;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.talismans.libreforge.loader.LibreforgePlugin;
import java.io.File;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from LegacyLocation.kt
@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\u0004\b\u0007\u0010\bJ\u0013\u0010\u000e\u001a\u00070\u000f¢\u0006\u0002\b\u00102\u0006\u0010\u0011\u001a\u00020\u0012J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006HÆ\u0003J-\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u001d",
   d2 = {
         "Lcom/willfp/talismans/libreforge/loader/configs/LegacyLocation;",
         "",
         "filename",
         "",
         "section",
         "alternativeDirectories",
         "",
         "<init>",
         "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;)V",
         "getFilename",
         "()Ljava/lang/String;",
         "getSection",
         "getAlternativeDirectories",
         "()Ljava/util/List;",
         "getConfig",
         "Lcom/willfp/eco/core/config/interfaces/Config;",
         "Lorg/jetbrains/annotations/NotNull;",
         "plugin",
         "Lcom/willfp/talismans/libreforge/loader/LibreforgePlugin;",
         "component1",
         "component2",
         "component3",
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
@SourceDebugExtension(
   "SMAP\nLegacyLocation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LegacyLocation.kt\ncom/willfp/libreforge/loader/configs/LegacyLocation\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,22:1\n1#2:23\n*E\n"
)
public final class LegacyLocation {
   @NotNull
   private final String filename;
   @NotNull
   private final String section;
   @NotNull
   private final List<String> alternativeDirectories;

   public LegacyLocation(@NotNull String filename, @NotNull String section, @NotNull List<String> alternativeDirectories) {
      Intrinsics.checkNotNullParameter(filename, "filename");
      Intrinsics.checkNotNullParameter(section, "section");
      Intrinsics.checkNotNullParameter(alternativeDirectories, "alternativeDirectories");
      super();
      this.filename = filename;
      this.section = section;
      this.alternativeDirectories = alternativeDirectories;
   }

   @NotNull
   public final String getFilename() {
      return this.filename;
   }

   @NotNull
   public final String getSection() {
      return this.section;
   }

   @NotNull
   public final List<String> getAlternativeDirectories() {
      return this.alternativeDirectories;
   }

   @NotNull
   public final Config getConfig(@NotNull LibreforgePlugin plugin) {
      Intrinsics.checkNotNullParameter(plugin, "plugin");
      File var10000 = plugin.getDataFolder();
      Intrinsics.checkNotNullExpressionValue(var10000, "getDataFolder(...)");
      File it = FilesKt.resolve(var10000, this.filename);
      int var3/* $VF was: $i$a$-let-LegacyLocation$getConfig$1 */ = 0;
      return it.exists() ? ConfigExtensions.readConfig(it) : ConfigExtensions.emptyConfig();
   }

   @NotNull
   public final String component1() {
      return this.filename;
   }

   @NotNull
   public final String component2() {
      return this.section;
   }

   @NotNull
   public final List<String> component3() {
      return this.alternativeDirectories;
   }

   @NotNull
   public final LegacyLocation copy(@NotNull String filename, @NotNull String section, @NotNull List<String> alternativeDirectories) {
      Intrinsics.checkNotNullParameter(filename, "filename");
      Intrinsics.checkNotNullParameter(section, "section");
      Intrinsics.checkNotNullParameter(alternativeDirectories, "alternativeDirectories");
      return new LegacyLocation(filename, section, alternativeDirectories);
   }

   @NotNull
   @Override
   public String toString() {
      return "LegacyLocation(filename=" + this.filename + ", section=" + this.section + ", alternativeDirectories=" + this.alternativeDirectories + ")";
   }

   @Override
   public int hashCode() {
      int result = this.filename.hashCode();
      result = result * 31 + this.section.hashCode();
      return result * 31 + this.alternativeDirectories.hashCode();
   }

   @Override
   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof LegacyLocation var2)) {
         return false;
      } else if (!Intrinsics.areEqual(this.filename, var2.filename)) {
         return false;
      } else {
         return !Intrinsics.areEqual(this.section, var2.section) ? false : Intrinsics.areEqual(this.alternativeDirectories, var2.alternativeDirectories);
      }
   }
}
