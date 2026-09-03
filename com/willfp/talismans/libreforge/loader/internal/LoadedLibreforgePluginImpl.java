package com.willfp.talismans.libreforge.loader.internal;

import com.willfp.eco.core.config.updating.ConfigHandler;
import com.willfp.eco.core.registry.Registry;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.libreforge.LoadedLibreforgePlugin;
import com.willfp.libreforge.configs.LibreforgeConfigCategory;
import com.willfp.talismans.libreforge.loader.LibreforgePlugin;
import java.io.File;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from LoadedLibreforgePluginImpl.kt
@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\r\u0010\r\u001a\u00070\u000e¢\u0006\u0002\b\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0015H\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0016",
   d2 = {
         "Lcom/willfp/talismans/libreforge/loader/internal/LoadedLibreforgePluginImpl;",
         "Lcom/willfp/libreforge/LoadedLibreforgePlugin;",
         "plugin",
         "Lcom/willfp/talismans/libreforge/loader/LibreforgePlugin;",
         "<init>",
         "(Lcom/willfp/talismans/libreforge/loader/LibreforgePlugin;)V",
         "getPlugin",
         "()Lcom/willfp/talismans/libreforge/loader/LibreforgePlugin;",
         "categories",
         "Lcom/willfp/eco/core/registry/Registry;",
         "Lcom/willfp/libreforge/configs/LibreforgeConfigCategory;",
         "getCategories",
         "()Lcom/willfp/eco/core/registry/Registry;",
         "getID",
         "",
         "Lorg/jetbrains/annotations/NotNull;",
         "getConfigHandler",
         "Lcom/willfp/eco/core/config/updating/ConfigHandler;",
         "getDataFolder",
         "Ljava/io/File;",
         "getLogger",
         "Ljava/util/logging/Logger;",
         "loader"
   }
)
public final class LoadedLibreforgePluginImpl implements LoadedLibreforgePlugin {
   @NotNull
   private final LibreforgePlugin plugin;
   @NotNull
   private final Registry<LibreforgeConfigCategory> categories;

   public LoadedLibreforgePluginImpl(@NotNull LibreforgePlugin plugin) {
      Intrinsics.checkNotNullParameter(plugin, "plugin");
      super();
      this.plugin = plugin;
      this.categories = this.getPlugin().getCategories();
   }

   @NotNull
   public LibreforgePlugin getPlugin() {
      return this.plugin;
   }

   @NotNull
   public Registry<LibreforgeConfigCategory> getCategories() {
      return this.categories;
   }

   @NotNull
   public String getID() {
      String var10000 = this.getPlugin().getID();
      Intrinsics.checkNotNullExpressionValue(var10000, "getID(...)");
      return var10000;
   }

   @NotNull
   public ConfigHandler getConfigHandler() {
      ConfigHandler var10000 = this.getPlugin().getConfigHandler();
      Intrinsics.checkNotNullExpressionValue(var10000, "getConfigHandler(...)");
      return var10000;
   }

   @NotNull
   public File getDataFolder() {
      File var10000 = this.getPlugin().getDataFolder();
      Intrinsics.checkNotNullExpressionValue(var10000, "getDataFolder(...)");
      return var10000;
   }

   @NotNull
   public Logger getLogger() {
      Logger var10000 = this.getPlugin().getLogger();
      Intrinsics.checkNotNullExpressionValue(var10000, "getLogger(...)");
      return var10000;
   }
}
