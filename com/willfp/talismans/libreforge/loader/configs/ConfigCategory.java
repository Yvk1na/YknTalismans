package com.willfp.talismans.libreforge.loader.configs;

import com.willfp.eco.core.EcoPlugin;
import com.willfp.eco.core.config.interfaces.Config;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.libreforge.configs.LibreforgeConfigCategory;
import com.willfp.talismans.libreforge.loader.LibreforgePlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from ConfigCategory.kt
@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0000¢\u0006\u0002\b\u001dJ\u0010\u0010\u001e\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001fH&J \u0010 \u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001f2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010!\u001a\u00020\"H&J\u0010\u0010#\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001fH\u0016J\u0010\u0010$\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001fH\u0016J \u0010%\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001f2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010!\u001a\u00020\"H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0016\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000fX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u000fX\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u001e\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0014\u001a\u00020\u0015@BX\u0080.¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018¨\u0006&",
   d2 = {
         "Lcom/willfp/talismans/libreforge/loader/configs/ConfigCategory;",
         "",
         "id",
         "",
         "directory",
         "<init>",
         "(Ljava/lang/String;Ljava/lang/String;)V",
         "getId",
         "()Ljava/lang/String;",
         "getDirectory",
         "legacyLocation",
         "Lcom/willfp/talismans/libreforge/loader/configs/LegacyLocation;",
         "getLegacyLocation",
         "()Lcom/willfp/talismans/libreforge/loader/configs/LegacyLocation;",
         "supportsSharing",
         "",
         "getSupportsSharing",
         "()Z",
         "shouldPreload",
         "getShouldPreload",
         "value",
         "Lcom/willfp/libreforge/configs/LibreforgeConfigCategory;",
         "handle",
         "getHandle$loader",
         "()Lcom/willfp/libreforge/configs/LibreforgeConfigCategory;",
         "makeHandle",
         "",
         "plugin",
         "Lcom/willfp/eco/core/EcoPlugin;",
         "makeHandle$loader",
         "clear",
         "Lcom/willfp/talismans/libreforge/loader/LibreforgePlugin;",
         "acceptConfig",
         "config",
         "Lcom/willfp/eco/core/config/interfaces/Config;",
         "beforeReload",
         "afterReload",
         "acceptPreloadConfig",
         "loader"
   }
)
public abstract class ConfigCategory {
   @NotNull
   private final String id;
   @NotNull
   private final String directory;
   @Nullable
   private final LegacyLocation legacyLocation;
   private final boolean supportsSharing;
   private final boolean shouldPreload;
   private LibreforgeConfigCategory handle;

   public ConfigCategory(@NotNull String id, @NotNull String directory) {
      Intrinsics.checkNotNullParameter(id, "id");
      Intrinsics.checkNotNullParameter(directory, "directory");
      super();
      this.id = id;
      this.directory = directory;
      this.supportsSharing = true;
   }

   @NotNull
   public final String getId() {
      return this.id;
   }

   @NotNull
   public final String getDirectory() {
      return this.directory;
   }

   @Nullable
   public LegacyLocation getLegacyLocation() {
      return this.legacyLocation;
   }

   public boolean getSupportsSharing() {
      return this.supportsSharing;
   }

   public boolean getShouldPreload() {
      return this.shouldPreload;
   }

   @NotNull
   public final LibreforgeConfigCategory getHandle$loader() {
      if (this.handle != null) {
         return this.handle;
      }

      Intrinsics.throwUninitializedPropertyAccessException("handle");
      return null;
   }

   public final void makeHandle$loader(@NotNull EcoPlugin plugin) {
      Intrinsics.checkNotNullParameter(plugin, "plugin");
      this.handle = new LibreforgeConfigCategory(this.id, this.directory, this.getSupportsSharing(), plugin);
   }

   public abstract void clear(@NotNull LibreforgePlugin var1);

   public abstract void acceptConfig(@NotNull LibreforgePlugin var1, @NotNull String var2, @NotNull Config var3);

   public void beforeReload(@NotNull LibreforgePlugin plugin) {
      Intrinsics.checkNotNullParameter(plugin, "plugin");
   }

   public void afterReload(@NotNull LibreforgePlugin plugin) {
      Intrinsics.checkNotNullParameter(plugin, "plugin");
   }

   public void acceptPreloadConfig(@NotNull LibreforgePlugin plugin, @NotNull String id, @NotNull Config config) {
      Intrinsics.checkNotNullParameter(plugin, "plugin");
      Intrinsics.checkNotNullParameter(id, "id");
      Intrinsics.checkNotNullParameter(config, "config");
      this.acceptConfig(plugin, id, config);
   }
}
