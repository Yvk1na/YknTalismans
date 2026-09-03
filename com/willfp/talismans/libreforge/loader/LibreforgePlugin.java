package com.willfp.talismans.libreforge.loader;

import com.willfp.eco.core.EcoPlugin;
import com.willfp.eco.core.LifecyclePosition;
import com.willfp.eco.core.PluginProps;
import com.willfp.eco.core.config.ConfigExtensions;
import com.willfp.eco.core.config.interfaces.Config;
import com.willfp.eco.core.registry.Registrable;
import com.willfp.eco.core.registry.Registry;
import com.willfp.eco.core.version.Version;
import com.willfp.eco.libs.kotlin.Deprecated;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.Unit;
import com.willfp.eco.libs.kotlin.collections.CollectionsKt;
import com.willfp.eco.libs.kotlin.io.CloseableKt;
import com.willfp.eco.libs.kotlin.io.FilesKt;
import com.willfp.eco.libs.kotlin.jvm.functions.Function0;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.eco.libs.kotlin.ranges.RangesKt;
import com.willfp.eco.libs.kotlin.sequences.Sequence;
import com.willfp.eco.libs.kotlin.sequences.SequencesKt;
import com.willfp.eco.libs.kotlin.text.StringsKt;
import com.willfp.libreforge.Plugins;
import com.willfp.libreforge.ViolationContext;
import com.willfp.libreforge.configs.LibreforgeConfigCategory;
import com.willfp.libreforge.configs.category.FoundConfig;
import com.willfp.libreforge.effects.Chain;
import com.willfp.libreforge.effects.Effects;
import com.willfp.talismans.libreforge.loader.configs.ConfigCategory;
import com.willfp.talismans.libreforge.loader.configs.LegacyLocation;
import com.willfp.talismans.libreforge.loader.internal.InvalidLibreforgePluginError;
import com.willfp.talismans.libreforge.loader.internal.LibreforgeLoaderKt;
import com.willfp.talismans.libreforge.loader.internal.LoadedLibreforgePluginImpl;
import com.willfp.talismans.libreforge.loader.internal.configs.RegistrableConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.jetbrains.annotations.NotNull;

// $VF: Compiled from LibreforgePlugin.kt
@Metadata(
   mv = {2, 2, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001:\u0001-B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001a\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00062\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0002J&\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00110\u0019H\u0002J\b\u0010\u001a\u001a\u00020\u0011H\u0002J\b\u0010\u001b\u001a\u00020\u0011H\u0002J\u0010\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0006H\u0002J\u0016\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00170\u001e2\u0006\u0010\u0012\u001a\u00020\u0006H\u0002J\u0016\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 2\u0006\u0010\u0012\u001a\u00020\u0006H\u0002J\u001e\u0010\"\u001a\b\u0012\u0004\u0012\u00020!0#2\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010$\u001a\u00020\u0017H\u0002J\b\u0010%\u001a\u00020&H\u0016J\u0010\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020(H\u0014J\b\u0010*\u001a\u00020\u0017H\u0017J\u000e\u0010+\u001a\b\u0012\u0004\u0012\u00020\u00060 H\u0016J\u000e\u0010,\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0006R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006.",
   d2 = {
         "Lcom/willfp/talismans/libreforge/loader/LibreforgePlugin;",
         "Lcom/willfp/eco/core/EcoPlugin;",
         "<init>",
         "()V",
         "loaderCategories",
         "",
         "Lcom/willfp/talismans/libreforge/loader/configs/ConfigCategory;",
         "categories",
         "Lcom/willfp/eco/core/registry/Registry;",
         "Lcom/willfp/libreforge/configs/LibreforgeConfigCategory;",
         "getCategories",
         "()Lcom/willfp/eco/core/registry/Registry;",
         "libreforgeVersion",
         "Lcom/willfp/eco/core/version/Version;",
         "getLibreforgeVersion",
         "()Lcom/willfp/eco/core/version/Version;",
         "loadCategory",
         "",
         "category",
         "preload",
         "",
         "withLogs",
         "position",
         "",
         "block",
         "Lcom/willfp/eco/libs/kotlin/Function0;",
         "competeForVersion",
         "loadCategories",
         "copyConfigs",
         "getDefaultConfigNames",
         "",
         "fetchConfigs",
         "",
         "Lcom/willfp/talismans/libreforge/loader/internal/configs/RegistrableConfig;",
         "doFetchConfigs",
         "",
         "directory",
         "getFile",
         "Ljava/io/File;",
         "mutateProps",
         "Lcom/willfp/eco/core/PluginProps;",
         "props",
         "getMinimumEcoVersion",
         "loadConfigCategories",
         "addCategory",
         "RegistryLock",
         "loader"
   }
)
@SourceDebugExtension(
   "SMAP\nLibreforgePlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 LibreforgePlugin.kt\ncom/willfp/libreforge/loader/LibreforgePlugin\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 _Sequences.kt\nkotlin/sequences/SequencesKt___SequencesKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,245:1\n777#2:246\n873#2,2:247\n777#2:252\n873#2,2:253\n1586#2:255\n1661#2,3:256\n1915#2,2:259\n1068#2:261\n1068#2:262\n777#2:263\n873#2,2:264\n1184#3,3:249\n1#4:266\n*S KotlinDebug\n*F\n+ 1 LibreforgePlugin.kt\ncom/willfp/libreforge/loader/LibreforgePlugin\n*L\n158#1:246\n158#1:247,2\n176#1:252\n176#1:253,2\n176#1:255\n176#1:256,3\n183#1:259,2\n188#1:261\n189#1:262\n52#1:263\n52#1:264,2\n170#1:249,3\n*E\n"
)
public abstract class LibreforgePlugin extends EcoPlugin {
   @NotNull
   private final List<ConfigCategory> loaderCategories = new ArrayList<>();
   @NotNull
   private final Registry<LibreforgeConfigCategory> categories = new Registry();
   @NotNull
   private final Version libreforgeVersion;

   public LibreforgePlugin() {
      Version var10001 = new Version;
      String var10003 = this.getProps().getEnvironmentVariable("libreforge version");
      if (var10003 == null) {
         throw new InvalidLibreforgePluginError("libreforge version environment variable not set!");
      }

      var10001./* $VF: Unable to resugar constructor */<init>(var10003);
      this.libreforgeVersion = var10001;
      this.categories.lock(LibreforgePlugin.RegistryLock.INSTANCE);
      File var10000 = this.getDataFolder().getParentFile();
      Intrinsics.checkNotNullExpressionValue(var10000, "getParentFile(...)");
      LibreforgeLoaderKt.tryLoadForceVersion(var10000);
      this.competeForVersion();
      this.onLoad(LifecyclePosition.START, LibreforgePlugin::_init_$lambda$0);
      this.onLoad(LifecyclePosition.END, LibreforgePlugin::_init_$lambda$1);
      this.onEnable(LifecyclePosition.START, LibreforgePlugin::_init_$lambda$2);
      this.onReload(LifecyclePosition.START, LibreforgePlugin::_init_$lambda$3);
      this.onReload(LifecyclePosition.START, LibreforgePlugin::_init_$lambda$4);
   }

   @NotNull
   public final Registry<LibreforgeConfigCategory> getCategories() {
      return this.categories;
   }

   @NotNull
   public final Version getLibreforgeVersion() {
      return this.libreforgeVersion;
   }

   private final void loadCategory(ConfigCategory category, boolean preload) {
      this.withLogs(category, "before reload", LibreforgePlugin::loadCategory$lambda$0);
      this.withLogs(category, "clear", LibreforgePlugin::loadCategory$lambda$1);

      for (RegistrableConfig config : this.fetchConfigs(category)) {
         this.withLogs(category, "loading config " + config.getId(), LibreforgePlugin::loadCategory$lambda$2);
      }

      LegacyLocation legacy = category.getLegacyLocation();
      if (legacy != null) {
         for (Config config : legacy.getConfig(this).getSubsections(legacy.getSection())) {
            String var10000 = config.getString("id");
            Intrinsics.checkNotNullExpressionValue(var10000, "getString(...)");
            String id = var10000;
            Intrinsics.checkNotNull(config);
            RegistrableConfig registrable = new RegistrableConfig(config, null, id, category);
            this.withLogs(category, "loading legacy config " + id, LibreforgePlugin::loadCategory$lambda$3);
         }
      }

      this.withLogs(category, "after reload", LibreforgePlugin::loadCategory$lambda$4);
   }

   private final void withLogs(ConfigCategory category, String position, Function0<Unit> block) {
      try {
         block.invoke();
      } catch (Exception e) {
         this.getLogger().warning("Exception loading " + category.getId() + " at " + position + "!");
         e.printStackTrace();
      }
   }

   private final void competeForVersion() {
      LibreforgeLoaderKt.checkHighestVersion(this);
   }

   private final void loadCategories() {
      for (ConfigCategory category : this.loadConfigCategories()) {
         this.addCategory(category);
      }
   }

   private final void copyConfigs(ConfigCategory category) {
      File var10000 = this.getDataFolder();
      Intrinsics.checkNotNullExpressionValue(var10000, "getDataFolder(...)");
      File folder = FilesKt.resolve(var10000, category.getDirectory());
      Collection configNames = this.getDefaultConfigNames(category);
      if (!folder.exists()) {
         for (String name : configNames) {
            new FoundConfig(name, category.getDirectory(), this).copy();
         }
      }

      Iterable $this$filter$iv = configNames;
      int $i$f$filter = 0;
      Iterable $this$filterTo$iv$iv = $this$filter$iv;
      var destination$iv$iv = new ArrayList();
      int $i$f$filterTo = 0;

      for (Object element$iv$iv : $this$filterTo$iv$iv) {
         String it = (String)element$iv$iv;
         int var13/* $VF was: $i$a$-filter-LibreforgePlugin$copyConfigs$1 */ = 0;
         if (StringsKt.startsWith$default(it, "_", false, 2, null)) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      for (String exampleConfigs : (List)destination$iv$iv) {
         new FoundConfig(exampleConfigs, category.getDirectory(), this).copy();
      }
   }

   private final Collection<String> getDefaultConfigNames(ConfigCategory category) {
      List files = new ArrayList();

      try {
         var $this$map$iv = new ZipFile(this.getFile());
         Throwable $i$f$map = null;

         try {
            ZipFile zipFile = $this$map$iv;
            int var6/* $VF was: $i$a$-use-LibreforgePlugin$getDefaultConfigNames$1 */ = 0;
            Enumeration var10000 = zipFile.entries();
            Intrinsics.checkNotNullExpressionValue(var10000, "entries(...)");
            Sequence $i$f$mapTo = SequencesKt.filter(
               SequencesKt.asSequence(CollectionsKt.iterator(var10000)), LibreforgePlugin::getDefaultConfigNames$lambda$0$0
            );
            var destination$iv = files;
            int $i$f$mapTox = 0;

            for (Object item$iv : $i$f$mapTo) {
               ZipEntry it = (ZipEntry)item$iv;
               Collection var13 = destination$iv;
               int var14/* $VF was: $i$a$-mapTo-LibreforgePlugin$getDefaultConfigNames$1$2 */ = 0;
               String var41 = it.getName();
               Intrinsics.checkNotNullExpressionValue(var41, "getName(...)");
               var13.add(StringsKt.removePrefix(var41, category.getDirectory() + "/"));
            }

            List var26 = (List)destination$iv;
         } catch (Throwable var19) {
            $i$f$map = var19;
            throw var19;
         } finally {
            CloseableKt.closeFinally($this$map$iv, $i$f$map);
         }
      } catch (Exception var21) {
      }

      Iterable $this$filter$iv = files;
      int $i$f$filter = 0;
      Iterable var27 = $this$filter$iv;
      var destination$iv$iv = new ArrayList();
      int $i$f$filterTo = 0;

      for (Object element$iv$iv : var27) {
         String it = (String)element$iv$iv;
         int var39/* $VF was: $i$a$-filter-LibreforgePlugin$getDefaultConfigNames$3 */ = 0;
         if (StringsKt.endsWith$default(it, ".yml", false, 2, null)) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      $this$filter$iv = (Iterable & List)destination$iv$iv;
      $i$f$filter = 0;
      var27 = $this$filter$iv;
      destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$filter$iv, 10));
      $i$f$filterTo = 0;

      for (Object item$iv$iv : var27) {
         String var38 = (String)item$iv$iv;
         Collection var15 = destination$iv$iv;
         int var40/* $VF was: $i$a$-map-LibreforgePlugin$getDefaultConfigNames$4 */ = 0;
         var15.add(StringsKt.removeSuffix(var38, ".yml"));
      }

      return destination$iv$iv;
   }

   private final List<RegistrableConfig> fetchConfigs(ConfigCategory category) {
      Set configs = new LinkedHashSet();
      CollectionsKt.addAll(configs, this.doFetchConfigs(category, category.getDirectory()));
      LegacyLocation var10000 = category.getLegacyLocation();
      if (var10000 != null) {
         List var13 = var10000.getAlternativeDirectories();
         if (var13 != null) {
            Iterable $this$forEach$iv = var13;
            int $i$f$forEach = 0;

            for (Object element$iv : $this$forEach$iv) {
               String directory = (String)element$iv;
               int var10/* $VF was: $i$a$-forEach-LibreforgePlugin$fetchConfigs$1 */ = 0;
               CollectionsKt.addAll(configs, this.doFetchConfigs(category, directory));
            }
         }
      }

      Iterable $this$sortedBy$iv = configs;
      int $i$f$sortedBy = 0;
      $this$sortedBy$iv = CollectionsKt.sortedWith($this$sortedBy$iv, new LibreforgePlugin$fetchConfigs$$inlined$sortedBy$1());
      $i$f$sortedBy = 0;
      return CollectionsKt.sortedWith($this$sortedBy$iv, new LibreforgePlugin$fetchConfigs$$inlined$sortedBy$2());
   }

   private final Set<RegistrableConfig> doFetchConfigs(ConfigCategory category, String directory) {
      File var10000 = this.getDataFolder();
      Intrinsics.checkNotNullExpressionValue(var10000, "getDataFolder(...)");
      return SequencesKt.toSet(
         SequencesKt.map(
            SequencesKt.filter((Sequence)FilesKt.walk$default(FilesKt.resolve(var10000, directory), null, 1, null), LibreforgePlugin::doFetchConfigs$lambda$0),
            LibreforgePlugin::doFetchConfigs$lambda$1
         )
      );
   }

   @NotNull
   public File getFile() {
      File var10000 = super.getFile();
      Intrinsics.checkNotNullExpressionValue(var10000, "getFile(...)");
      return var10000;
   }

   @NotNull
   protected PluginProps mutateProps(@NotNull PluginProps props) {
      Intrinsics.checkNotNullParameter(props, "props");
      PluginProps var2 = props;
      PluginProps $this$mutateProps_u24lambda_u240 = var2;
      int var4/* $VF was: $i$a$-apply-LibreforgePlugin$mutateProps$1 */ = 0;
      $this$mutateProps_u24lambda_u240.setSupportingExtensions(true);

      try {
         $this$mutateProps_u24lambda_u240.setEcoApiVersion(
            (Version)RangesKt.coerceAtLeast((Comparable)props.getEcoApiVersion(), (Comparable)(new Version("6.77.0")))
         );
      } catch (NoSuchMethodError var6) {
      }

      return var2;
   }

   /** @deprecated */
   @Deprecated(message = "Use eco-api-version in eco.yml instead")
   @NotNull
   public String getMinimumEcoVersion() {
      return "6.77.0";
   }

   @NotNull
   public List<ConfigCategory> loadConfigCategories() {
      return CollectionsKt.emptyList();
   }

   public final void addCategory(@NotNull ConfigCategory category) {
      Intrinsics.checkNotNullParameter(category, "category");
      this.categories.unlock(LibreforgePlugin.RegistryLock.INSTANCE);
      category.makeHandle$loader(this);
      this.copyConfigs(category);
      this.loaderCategories.add(category);
      this.categories.register((Registrable)category.getHandle$loader());
      this.categories.lock(LibreforgePlugin.RegistryLock.INSTANCE);
   }

   private static final void _init_$lambda$0(LibreforgePlugin this$0) {
      File var10000 = this$0.getDataFolder().getParentFile();
      Intrinsics.checkNotNullExpressionValue(var10000, "getParentFile(...)");
      LibreforgeLoaderKt.loadHighestLibreforgeVersion(var10000);
      var10000 = this$0.getDataFolder();
      Intrinsics.checkNotNullExpressionValue(var10000, "getDataFolder(...)");
      FilesKt.resolve(var10000, "lrcdb.yml").delete();
   }

   private static final void _init_$lambda$1(LibreforgePlugin this$0) {
      this$0.loadCategories();
      Iterable $this$filter$iv = this$0.loaderCategories;
      int $i$f$filter = 0;
      Iterable $this$filterTo$iv$iv = $this$filter$iv;
      var destination$iv$iv = new ArrayList();
      int $i$f$filterTo = 0;

      for (Object element$iv$iv : $this$filterTo$iv$iv) {
         ConfigCategory it = (ConfigCategory)element$iv$iv;
         int var10/* $VF was: $i$a$-filter-LibreforgePlugin$2$1 */ = 0;
         if (it.getShouldPreload()) {
            destination$iv$iv.add(element$iv$iv);
         }
      }

      for (ConfigCategory category : (List)destination$iv$iv) {
         this$0.loadCategory(category, true);
      }
   }

   private static final void _init_$lambda$2(LibreforgePlugin this$0) {
      Plugins.INSTANCE.register((Registrable)(new LoadedLibreforgePluginImpl(this$0)));
   }

   private static final void _init_$lambda$3(LibreforgePlugin this$0) {
      File var10000 = this$0.getDataFolder();
      Intrinsics.checkNotNullExpressionValue(var10000, "getDataFolder(...)");
      File it = FilesKt.resolve(var10000, "chains.yml");
      int var4/* $VF was: $i$a$-let-LibreforgePlugin$4$chainsYml$1 */ = 0;
      Config chainsYml = it.exists() ? ConfigExtensions.readConfig(it) : ConfigExtensions.emptyConfig();

      for (Config config : chainsYml.getSubsections("chains")) {
         Effects var6 = Effects.INSTANCE;
         String var10001 = config.getString("id");
         Intrinsics.checkNotNullExpressionValue(var10001, "getString(...)");
         Effects var10002 = Effects.INSTANCE;
         List var10003 = config.getSubsections("effects");
         Intrinsics.checkNotNullExpressionValue(var10003, "getSubsections(...)");
         Chain var7 = var10002.compileChain(var10003, new ViolationContext(this$0, "chains.yml"));
         if (var7 != null) {
            var6.register(var10001, var7);
         }
      }
   }

   private static final void _init_$lambda$4(LibreforgePlugin this$0) {
      for (ConfigCategory category : this$0.loaderCategories) {
         loadCategory$default(this$0, category, false, 2, null);
      }
   }

   private static final Unit loadCategory$lambda$0(ConfigCategory $category, LibreforgePlugin this$0) {
      $category.beforeReload(this$0);
      return Unit.INSTANCE;
   }

   private static final Unit loadCategory$lambda$1(ConfigCategory $category, LibreforgePlugin this$0) {
      $category.clear(this$0);
      $category.getHandle$loader().clear();
      return Unit.INSTANCE;
   }

   private static final Unit loadCategory$lambda$2(ConfigCategory $category, RegistrableConfig $config, boolean $preload, LibreforgePlugin this$0) {
      $category.getHandle$loader().register((Registrable)$config.getHandle());
      if ($preload) {
         $category.acceptPreloadConfig(this$0, $config.getId(), $config.getConfig());
      } else {
         $category.acceptConfig(this$0, $config.getId(), $config.getConfig());
      }

      return Unit.INSTANCE;
   }

   private static final Unit loadCategory$lambda$3(ConfigCategory $category, RegistrableConfig $registrable, LibreforgePlugin this$0, String $id) {
      $category.getHandle$loader().register((Registrable)$registrable.getHandle());
      $category.acceptConfig(this$0, $id, $registrable.getConfig());
      return Unit.INSTANCE;
   }

   private static final Unit loadCategory$lambda$4(ConfigCategory $category, LibreforgePlugin this$0) {
      $category.afterReload(this$0);
      return Unit.INSTANCE;
   }

   private static final boolean getDefaultConfigNames$lambda$0$0(ConfigCategory $category, ZipEntry it) {
      String var10000 = it.getName();
      Intrinsics.checkNotNullExpressionValue(var10000, "getName(...)");
      return StringsKt.startsWith$default(var10000, $category.getDirectory() + "/", false, 2, null);
   }

   private static final boolean doFetchConfigs$lambda$0(File it) {
      Intrinsics.checkNotNullParameter(it, "it");
      if (it.isFile()) {
         String var10000 = it.getName();
         Intrinsics.checkNotNullExpressionValue(var10000, "getName(...)");
         if (StringsKt.endsWith$default(var10000, ".yml", false, 2, null)
            && !StringsKt.startsWith$default(FilesKt.getNameWithoutExtension(it), "_", false, 2, null)) {
            return true;
         }
      }

      return false;
   }

   private static final RegistrableConfig doFetchConfigs$lambda$1(ConfigCategory $category, File file) {
      Intrinsics.checkNotNullParameter(file, "file");
      String id = FilesKt.getNameWithoutExtension(file);
      Config config = ConfigExtensions.readConfig(file);
      return new RegistrableConfig(config, file, id, $category);
   }

   // $VF: Compiled from LibreforgePlugin.kt
   @Metadata(
      mv = {2, 2, 0},
      k = 1,
      xi = 48,
      d1 = "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\bÂ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003¨\u0006\u0004",
      d2 = {"Lcom/willfp/talismans/libreforge/loader/LibreforgePlugin$RegistryLock;", "", "<init>", "()V", "loader"}
   )
   private static final class RegistryLock {
      @NotNull
      public static final LibreforgePlugin.RegistryLock INSTANCE = new LibreforgePlugin.RegistryLock();
   }
}
