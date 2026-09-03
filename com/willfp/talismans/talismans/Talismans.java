package com.willfp.talismans.talismans;

import com.google.common.collect.ImmutableList;
import com.willfp.eco.core.config.interfaces.Config;
import com.willfp.eco.core.registry.Registry;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.jvm.JvmStatic;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.talismans.libreforge.loader.LibreforgePlugin;
import com.willfp.talismans.libreforge.loader.configs.ConfigCategory;
import com.willfp.talismans.libreforge.loader.configs.LegacyLocation;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from Talismans.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\fH\u0007J\u0012\u0010\r\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J \u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0018",
   d2 = {
         "Lcom/willfp/talismans/talismans/Talismans;",
         "Lcom/willfp/talismans/libreforge/loader/configs/ConfigCategory;",
         "<init>",
         "()V",
         "registry",
         "Lcom/willfp/eco/core/registry/Registry;",
         "Lcom/willfp/talismans/talismans/Talisman;",
         "legacyLocation",
         "Lcom/willfp/talismans/libreforge/loader/configs/LegacyLocation;",
         "getLegacyLocation",
         "()Lcom/willfp/talismans/libreforge/loader/configs/LegacyLocation;",
         "values",
         "",
         "getByID",
         "name",
         "",
         "clear",
         "",
         "plugin",
         "Lcom/willfp/talismans/libreforge/loader/LibreforgePlugin;",
         "acceptConfig",
         "id",
         "config",
         "Lcom/willfp/eco/core/config/interfaces/Config;",
         "core-plugin"
   }
)
public final class Talismans extends ConfigCategory {
   @NotNull
   public static final Talismans INSTANCE = new Talismans();
   @NotNull
   private static final Registry<Talisman> registry = new Registry();
   @NotNull
   private static final LegacyLocation legacyLocation = new LegacyLocation("talismans.yml", "talismans", null, 4, null);

   private Talismans() {
      super("talisman", "talismans");
   }

   @NotNull
   @Override
   public LegacyLocation getLegacyLocation() {
      return legacyLocation;
   }

   @JvmStatic
   @NotNull
   public static final List<Talisman> values() {
      ImmutableList var10000 = ImmutableList.copyOf(registry.values());
      Intrinsics.checkNotNullExpressionValue(var10000, "copyOf(...)");
      return (List<Talisman>)var10000;
   }

   @JvmStatic
   @Nullable
   public static final Talisman getByID(@NotNull String name) {
      Intrinsics.checkNotNullParameter(name, "name");
      return (Talisman)registry.get(name);
   }

   @Override
   public void clear(@NotNull LibreforgePlugin plugin) {
      Intrinsics.checkNotNullParameter(plugin, "plugin");
      registry.clear();
   }

   @Override
   public void acceptConfig(@NotNull LibreforgePlugin plugin, @NotNull String id, @NotNull Config config) {
      Intrinsics.checkNotNullParameter(plugin, "plugin");
      Intrinsics.checkNotNullParameter(id, "id");
      Intrinsics.checkNotNullParameter(config, "config");
      registry.register(new Talisman(id, config));
   }
}
