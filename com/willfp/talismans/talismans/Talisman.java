package com.willfp.talismans.talismans;

import com.willfp.eco.core.EcoPlugin;
import com.willfp.eco.core.config.interfaces.Config;
import com.willfp.eco.core.items.CustomItem;
import com.willfp.eco.core.items.Items;
import com.willfp.eco.core.items.TestableItem;
import com.willfp.eco.core.items.builder.ItemStackBuilder;
import com.willfp.eco.core.recipe.Recipes;
import com.willfp.eco.core.recipe.parts.EmptyTestableItem;
import com.willfp.eco.core.recipe.recipes.CraftingRecipe;
import com.willfp.eco.core.registry.Registrable;
import com.willfp.eco.libs.kotlin.Metadata;
import com.willfp.eco.libs.kotlin.collections.CollectionsKt;
import com.willfp.eco.libs.kotlin.jvm.internal.Intrinsics;
import com.willfp.eco.libs.kotlin.jvm.internal.SourceDebugExtension;
import com.willfp.eco.util.StringUtilsExtensions;
import com.willfp.libreforge.Holder;
import com.willfp.libreforge.ViolationContext;
import com.willfp.libreforge.conditions.ConditionList;
import com.willfp.libreforge.conditions.Conditions;
import com.willfp.libreforge.effects.EffectList;
import com.willfp.libreforge.effects.Effects;
import com.willfp.talismans.TalismansPluginKt;
import com.willfp.talismans.talismans.util.TalismanChecks;
import com.willfp.talismans.talismans.util.TalismanUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.Validate;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// $VF: Compiled from Talisman.kt
@Metadata(
   mv = {2, 3, 0},
   k = 1,
   xi = 48,
   d1 = "\u0000v\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0000\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0017\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0004\b\u0007\u0010\bJ\b\u00100\u001a\u00020\u0004H\u0016J\u0014\u00101\u001a\u0002022\b\u00103\u001a\u0004\u0018\u000104H\u0096\u0082\u0004J\n\u00105\u001a\u000206H\u0096\u0080\u0004J\n\u00107\u001a\u00020\u0004H\u0096\u0080\u0004R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0003\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\u00070\u0004¢\u0006\u0002\b\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R=\u0010\u0012\u001a.\u0012\f\u0012\n \u0014*\u0004\u0018\u00010\u00040\u0004 \u0014*\u0015\u0012\f\u0012\n \u0014*\u0004\u0018\u00010\u00040\u00040\u0015¢\u0006\u0002\b\u000f0\u0013¢\u0006\u0002\b\u000f¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u001a\u001a\u00020\u00198F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u001cR\u0013\u0010\u001d\u001a\u0004\u0018\u00010\u001e¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010!\u001a\u00020\"¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0013\u0010%\u001a\u0004\u0018\u00010\u00008F¢\u0006\u0006\u001a\u0004\b&\u0010'R\u0014\u0010(\u001a\u00020)X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b*\u0010+R\u0014\u0010,\u001a\u00020-X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b.\u0010/¨\u00068",
   d2 = {
         "Lcom/willfp/talismans/talismans/Talisman;",
         "Lcom/willfp/libreforge/Holder;",
         "Lcom/willfp/eco/core/registry/Registrable;",
         "id",
         "",
         "config",
         "Lcom/willfp/eco/core/config/interfaces/Config;",
         "<init>",
         "(Ljava/lang/String;Lcom/willfp/eco/core/config/interfaces/Config;)V",
         "getConfig",
         "()Lcom/willfp/eco/core/config/interfaces/Config;",
         "Lorg/bukkit/NamespacedKey;",
         "getId",
         "()Lorg/bukkit/NamespacedKey;",
         "name",
         "Lorg/jetbrains/annotations/NotNull;",
         "getName",
         "()Ljava/lang/String;",
         "description",
         "",
         "com.willfp.eco.libs.kotlin.jvm.PlatformType",
         "",
         "getDescription",
         "()Ljava/util/List;",
         "_itemStack",
         "Lorg/bukkit/inventory/ItemStack;",
         "itemStack",
         "getItemStack",
         "()Lorg/bukkit/inventory/ItemStack;",
         "recipe",
         "Lcom/willfp/eco/core/recipe/recipes/CraftingRecipe;",
         "getRecipe",
         "()Lcom/willfp/eco/core/recipe/recipes/CraftingRecipe;",
         "customItem",
         "Lcom/willfp/eco/core/items/CustomItem;",
         "getCustomItem",
         "()Lcom/willfp/eco/core/items/CustomItem;",
         "lowerLevel",
         "getLowerLevel",
         "()Lcom/willfp/talismans/talismans/Talisman;",
         "effects",
         "Lcom/willfp/libreforge/effects/EffectList;",
         "getEffects",
         "()Lcom/willfp/libreforge/effects/EffectList;",
         "conditions",
         "Lcom/willfp/libreforge/conditions/ConditionList;",
         "getConditions",
         "()Lcom/willfp/libreforge/conditions/ConditionList;",
         "getID",
         "equals",
         "",
         "other",
         "",
         "hashCode",
         "",
         "toString",
         "core-plugin"
   }
)
@SourceDebugExtension(
   "SMAP\nTalisman.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Talisman.kt\ncom/willfp/talismans/talismans/Talisman\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,116:1\n1586#2:117\n1661#2,3:118\n1#3:121\n*S KotlinDebug\n*F\n+ 1 Talisman.kt\ncom/willfp/talismans/talismans/Talisman\n*L\n44#1:117\n44#1:118,3\n*E\n"
)
public final class Talisman implements Holder, Registrable {
   @NotNull
   private final Config config;
   @NotNull
   private final NamespacedKey id;
   @NotNull
   private final String name;
   @NotNull
   private final List<String> description;
   @NotNull
   private final ItemStack _itemStack;
   @Nullable
   private final CraftingRecipe recipe;
   @NotNull
   private final CustomItem customItem;
   @NotNull
   private final EffectList effects;
   @NotNull
   private final ConditionList conditions;

   public Talisman(@NotNull String id, @NotNull Config config) {
      Intrinsics.checkNotNullParameter(id, "id");
      Intrinsics.checkNotNullParameter(config, "config");
      super();
      this.config = config;
      NamespacedKey var10001 = TalismansPluginKt.getPlugin().getNamespacedKeyFactory().create(id);
      Intrinsics.checkNotNullExpressionValue(var10001, "create(...)");
      this.id = var10001;
      String var36 = this.config.getString("name");
      Intrinsics.checkNotNullExpressionValue(var36, "getString(...)");
      this.name = var36;
      List var37 = this.config.getStrings("description");
      Intrinsics.checkNotNullExpressionValue(var37, "getStrings(...)");
      this.description = var37;
      Talisman var5 = this;
      Talisman var19 = this;
      int var6/* $VF was: $i$a$-run-Talisman$_itemStack$1 */ = 0;
      TestableItem var10000 = Items.lookup(var5.config.getString("item"));
      Intrinsics.checkNotNullExpressionValue(var10000, "lookup(...)");
      TestableItem item = var10000;
      Validate.isTrue(!(item instanceof EmptyTestableItem), "Item specified in " + id + " is invalid!", new Object[0]);
      TalismanUtils var32 = TalismanUtils.INSTANCE;
      Material var38 = item.getItem().getType();
      Intrinsics.checkNotNullExpressionValue(var38, "getType(...)");
      var32.registerTalismanMaterial(var38);
      ItemStackBuilder var33 = (ItemStackBuilder)((ItemStackBuilder)new ItemStackBuilder(item.getItem()).setAmount(1))
         .setDisplayName(StringUtilsExtensions.formatEco$default(var5.name, null, false, 3, null));
      Iterable $this$map$iv = var5.description;
      ItemStackBuilder var9 = var33;
      int $i$f$map = 0;
      Iterable $this$mapTo$iv$iv = $this$map$iv;
      var destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
      int $i$f$mapTo = 0;

      for (Object item$iv$iv : $this$mapTo$iv$iv) {
         String it = (String)item$iv$iv;
         Collection var17 = destination$iv$iv;
         int var18/* $VF was: $i$a$-map-Talisman$_itemStack$1$1 */ = 0;
         Intrinsics.checkNotNull(it);
         var17.add("§z" + StringUtilsExtensions.formatEco$default(it, null, false, 3, null));
      }

      ItemStack var39 = ((ItemStackBuilder)((ItemStackBuilder)var9.addLoreLines((List)destination$iv$iv))
            .writeMetaKey(TalismansPluginKt.getPlugin().getNamespacedKeyFactory().create("talisman"), PersistentDataType.STRING, id))
         .build();
      Intrinsics.checkNotNullExpressionValue(var39, "run(...)");
      var19._itemStack = var39;
      Boolean $this$customItem_u24lambda_u241 = this.config.getBool("craftable");
      boolean var23 = $this$customItem_u24lambda_u241;
      var19 = this;
      var6/* $VF was: $i$a$-run-Talisman$_itemStack$1 */ = 0;
      boolean var20 = var23;
      Talisman var34 = var19;
      Boolean var3 = var20 ? $this$customItem_u24lambda_u241 : null;
      CraftingRecipe var40;
      if (var3 != null) {
         boolean var24 = var3;
         var19 = var19;
         var6/* $VF was: $i$a$-run-Talisman$_itemStack$1 */ = 0;
         String craftingPermission = this.config.getStringOrNull("crafting-permission");
         if (craftingPermission == null) {
            TalismansPluginKt.getPlugin()
               .getLogger()
               .warning(
                  "Talisman '"
                     + id
                     + "' is using the deprecated talismans.fromtable permission system. This will be removed in a future version. Please set 'crafting-permission' explicitly in the config, or set it to [] for no permission."
               );
         }

         EcoPlugin var35 = TalismansPluginKt.getPlugin();
         ItemStack var10002 = this.getItemStack();
         List var10003 = this.config.getStrings("recipe");
         String var10004 = craftingPermission;
         if (craftingPermission == null) {
            var10004 = "talismans.fromtable." + id;
         }

         var40 = Recipes.createAndRegisterRecipe(var35, id, var10002, var10003, var10004, this.config.getBool("shapeless"));
         var34 = var19;
      } else {
         var40 = null;
      }

      var34.recipe = var40;
      CustomItem var21 = new CustomItem(this.getId(), Talisman::customItem$lambda$0, this.getItemStack());
      CustomItem var22 = var21;
      var19 = this;
      int var25/* $VF was: $i$a$-apply-Talisman$customItem$2 */ = 0;
      var22.register();
      var19.customItem = var21;
      Effects var41 = Effects.INSTANCE;
      List var43 = this.config.getSubsections("effects");
      Intrinsics.checkNotNullExpressionValue(var43, "getSubsections(...)");
      this.effects = var41.compile(var43, new ViolationContext(TalismansPluginKt.getPlugin(), "Talisman " + id));
      Conditions var42 = Conditions.INSTANCE;
      var43 = this.config.getSubsections("conditions");
      Intrinsics.checkNotNullExpressionValue(var43, "getSubsections(...)");
      this.conditions = var42.compile(var43, new ViolationContext(TalismansPluginKt.getPlugin(), "Talisman " + id));
   }

   @NotNull
   public final Config getConfig() {
      return this.config;
   }

   @NotNull
   public NamespacedKey getId() {
      return this.id;
   }

   @NotNull
   public final String getName() {
      return this.name;
   }

   @NotNull
   public final List<String> getDescription() {
      return this.description;
   }

   @NotNull
   public final ItemStack getItemStack() {
      ItemStack var10000 = this._itemStack.clone();
      Intrinsics.checkNotNullExpressionValue(var10000, "clone(...)");
      return var10000;
   }

   @Nullable
   public final CraftingRecipe getRecipe() {
      return this.recipe;
   }

   @NotNull
   public final CustomItem getCustomItem() {
      return this.customItem;
   }

   @Nullable
   public final Talisman getLowerLevel() {
      String var10000 = this.config.getString("higherLevelOf");
      Intrinsics.checkNotNullExpressionValue(var10000, "getString(...)");
      return Talismans.getByID(var10000);
   }

   @NotNull
   public EffectList getEffects() {
      return this.effects;
   }

   @NotNull
   public ConditionList getConditions() {
      return this.conditions;
   }

   @NotNull
   public String getID() {
      String var10000 = this.getId().getKey();
      Intrinsics.checkNotNullExpressionValue(var10000, "getKey(...)");
      return var10000;
   }

   @Override
   public boolean equals(@Nullable Object other) {
      if (this == other) {
         return true;
      } else {
         return !(other instanceof Talisman) ? false : Intrinsics.areEqual(this.getId(), ((Talisman)other).getId());
      }
   }

   @Override
   public int hashCode() {
      Object[] var1 = new Object[]{this.getId()};
      return Objects.hash(var1);
   }

   @NotNull
   @Override
   public String toString() {
      return "Talisman{" + this.getId() + "}";
   }

   private static final boolean customItem$lambda$0(Talisman this$0, ItemStack test) {
      Intrinsics.checkNotNullParameter(test, "test");
      return Intrinsics.areEqual(TalismanChecks.getTalismanOnItem(test), this$0);
   }
}
