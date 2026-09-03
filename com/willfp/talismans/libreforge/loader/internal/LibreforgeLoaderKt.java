package com.willfp.talismans.libreforge.loader.internal;

import com.willfp.eco.core.data.ExternalDataStore;
import com.willfp.eco.core.data.ExternalDataStoreExtensions;
import com.willfp.eco.core.version.Version;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.io.ByteStreamsKt;
import com.willfp.eco.libs.kotlin.io.CloseableKt;
import com.willfp.eco.libs.kotlin.io.FilesKt;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.talismans.libreforge.loader.LibreforgePlugin;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from LibreforgeLoader.kt
@Metadata(
   mv = {2, 2, 0},
   k = 2,
   xi = 48,
   d1 = "\u0000 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0000\u001a\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0000\u001a\u0010\u0010\n\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000\"\u000e\u0010\u0002\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000b",
   d2 = {
         "HIGHEST_LIBREFORGE_VERSION_KEY",
         "",
         "HIGHEST_LIBREFORGE_VERSION_CLASSLOADER_KEY",
         "checkHighestVersion",
         "",
         "plugin",
         "Lcom/willfp/talismans/libreforge/loader/LibreforgePlugin;",
         "tryLoadForceVersion",
         "pluginFolder",
         "Ljava/io/File;",
         "loadHighestLibreforgeVersion",
         "loader"
   }
)
@SourceDebugExtension(
   "SMAP\nLibreforgeLoader.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LibreforgeLoader.kt\ncom/willfp/libreforge/loader/internal/LibreforgeLoaderKt\n+ 2 ExternalDataStore.kt\ncom/willfp/eco/core/data/ExternalDataStoreExtensions\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,80:1\n34#2:81\n26#2:82\n18#2:87\n18#2:88\n13225#3,2:83\n13225#3,2:85\n*S KotlinDebug\n*F\n+ 1 LibreforgeLoader.kt\ncom/willfp/libreforge/loader/internal/LibreforgeLoaderKt\n*L\n24#1:81\n24#1:82\n57#1:87\n60#1:88\n35#1:83,2\n55#1:85,2\n*E\n"
)
public final class LibreforgeLoaderKt {
   @NotNull
   private static final String HIGHEST_LIBREFORGE_VERSION_KEY = "highest-libreforge-version";
   @NotNull
   private static final String HIGHEST_LIBREFORGE_VERSION_CLASSLOADER_KEY = "highest-libreforge-version-classloader";

   public static final void checkHighestVersion(@NotNull LibreforgePlugin plugin) {
      Intrinsics.checkNotNullParameter(plugin, "plugin");
      String key$iv = "highest-libreforge-version";
      int $i$f$readExternalData = 0;
      String key$iv$iv = key$iv;
      int var5/* $VF was: $i$a$-readExternalData-LibreforgeLoaderKt$checkHighestVersion$currentHighestVersion$1 */ = 0;
      Object default$iv$iv = new Version("0.0.0");
      int $i$f$readExternalDatax = 0;
      Object var10000 = ExternalDataStore.get(key$iv$iv, Version.class);
      if (var10000 == null) {
         var10000 = default$iv$iv;
      }

      Version currentHighestVersion = (Version)var10000;
      if (plugin.getLibreforgeVersion().compareTo(currentHighestVersion) > 0) {
         ExternalDataStoreExtensions.writeExternalData("highest-libreforge-version", plugin.getLibreforgeVersion());
         ClassLoader var10001 = plugin.getClass().getClassLoader();
         Intrinsics.checkNotNullExpressionValue(var10001, "getClassLoader(...)");
         ExternalDataStoreExtensions.writeExternalData("highest-libreforge-version-classloader", var10001);
      }
   }

   public static final void tryLoadForceVersion(@NotNull File pluginFolder) {
      Intrinsics.checkNotNullParameter(pluginFolder, "pluginFolder");
      Plugin[] var10000 = Bukkit.getPluginManager().getPlugins();
      Intrinsics.checkNotNullExpressionValue(var10000, "getPlugins(...)");
      Object[] $this$any$iv = var10000;
      int $i$f$any = 0;
      int var3 = 0;
      int file = $this$any$iv.length;

      while (true) {
         if (var3 >= file) {
            var12 = false;
            break;
         }

         Object element$iv = $this$any$iv[var3];
         Plugin it = (Plugin)element$iv;
         int var7/* $VF was: $i$a$-any-LibreforgeLoaderKt$tryLoadForceVersion$1 */ = 0;
         if (Intrinsics.areEqual(it.getName(), "libreforge")) {
            var12 = true;
            break;
         }

         var3++;
      }

      if (!var12) {
         File libreforgeFolder = FilesKt.resolve(pluginFolder, "libreforge");
         File versionsFolder = FilesKt.resolve(libreforgeFolder, "versions");
         if (versionsFolder.exists()) {
            for (File filex : FilesKt.walk$default(versionsFolder, null, 1, null)) {
               if (Intrinsics.areEqual(filex.getName(), "libreforge.jar")) {
                  Bukkit.getLogger().info("[libreforge] Found generic libreforge.jar!");
                  Bukkit.getLogger().info("[libreforge] This version will be loaded instead of any versions bundled with plugins.");
                  Bukkit.getPluginManager().loadPlugin(filex);
               }
            }
         }
      }
   }

   public static final void loadHighestLibreforgeVersion(@NotNull File pluginFolder) {
      Intrinsics.checkNotNullParameter(pluginFolder, "pluginFolder");
      Plugin[] var10000 = Bukkit.getPluginManager().getPlugins();
      Intrinsics.checkNotNullExpressionValue(var10000, "getPlugins(...)");
      Object[] $this$any$iv = var10000;
      int $i$f$any = 0;
      int libreforgeFolder = 0;
      int $i$f$readExternalData = $this$any$iv.length;

      while (true) {
         if (libreforgeFolder >= $i$f$readExternalData) {
            var41 = false;
            break;
         }

         Object element$iv = $this$any$iv[libreforgeFolder];
         Plugin it = (Plugin)element$iv;
         int var7/* $VF was: $i$a$-any-LibreforgeLoaderKt$loadHighestLibreforgeVersion$1 */ = 0;
         if (Intrinsics.areEqual(it.getName(), "libreforge")) {
            var41 = true;
            break;
         }

         libreforgeFolder++;
      }

      if (!var41) {
         String key$iv = "highest-libreforge-version-classloader";
         $i$f$readExternalData = 0;
         ClassLoader var42 = (ClassLoader)ExternalDataStore.get(key$iv, ClassLoader.class);
         if (var42 == null) {
            throw new LibreforgeNotFoundError("No libreforge plugin classloader found");
         }

         ClassLoader classLoader = var42;
         String key$ivx = "highest-libreforge-version";
         int $i$f$readExternalDatax = 0;
         Version var43 = (Version)ExternalDataStore.get(key$ivx, Version.class);
         if (var43 == null) {
            throw new LibreforgeNotFoundError("No libreforge version found");
         }

         Version version = var43;
         File libreforgeFolderx = FilesKt.resolve(pluginFolder, "libreforge");
         File versionsFolder = FilesKt.resolve(libreforgeFolderx, "versions");
         versionsFolder.mkdirs();
         File libreforgeJar = FilesKt.resolve(versionsFolder, "libreforge-" + version + ".jar");
         String libreforgeResourceName = "libreforge-" + version + "-shadow.jar";
         var var39 = new FileOutputStream(libreforgeJar);
         Throwable var8 = null;

         try {
            FileOutputStream outputStream = var39;
            int var11/* $VF was: $i$a$-use-LibreforgeLoaderKt$loadHighestLibreforgeVersion$2 */ = 0;
            var var12 = classLoader.getResourceAsStream(libreforgeResourceName);
            Throwable var13 = null;

            try {
               InputStream inputStream = var12;
               int var16/* $VF was: $i$a$-use-LibreforgeLoaderKt$loadHighestLibreforgeVersion$2$1 */ = 0;
               if (inputStream == null) {
                  throw new LibreforgeNotFoundError("libreforge wasn't found in the plugin jar");
               }

               InputStream var40 = ByteStreamsKt.copyTo$default(inputStream, outputStream, 0, 2, null);
            } catch (Throwable var25) {
               var13 = var25;
               throw var25;
            } finally {
               CloseableKt.closeFinally(var12, var13);
            }
         } catch (Throwable var27) {
            var8 = var27;
            throw var27;
         } finally {
            CloseableKt.closeFinally(var39, var8);
         }

         Intrinsics.checkNotNull(
            Bukkit.getPluginManager().loadPlugin(libreforgeJar), "null cannot be cast to non-null type com.willfp.libreforge.LibreforgeSpigotPlugin"
         );
      }
   }
}
