package github.mrh0.buildersaddition2.recipe.carpenter;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public class CarpenterRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final Item result;
    private final int count;
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String group;

    public CarpenterRecipeBuilder(RecipeCategory category, ItemLike item, int count) {
        this.category = category;
        this.result = item.asItem();
        this.count = count;
    }

    public static CarpenterRecipeBuilder carpenter(RecipeCategory category, ItemLike item) {
        return new CarpenterRecipeBuilder(category, item, 1);
    }

    public static CarpenterRecipeBuilder carpenter(RecipeCategory category, ItemLike item, int count) {
        return new CarpenterRecipeBuilder(category, item, count);
    }

    public CarpenterRecipeBuilder requires(TagKey<Item> ingredient) {
        return this.requires(Ingredient.of(ingredient));
    }

    public CarpenterRecipeBuilder requires(ItemLike ingredient) {
        return this.requires(ingredient, 1);
    }

    public CarpenterRecipeBuilder requires(ItemLike item, int count) {
        for(int i = 0; i < count; ++i) {
            this.requires(Ingredient.of(item));
        }

        return this;
    }

    public CarpenterRecipeBuilder requires(Ingredient ingredient) {
        return this.requires(ingredient, 1);
    }

    public CarpenterRecipeBuilder requires(Ingredient ingredient, int count) {
        for(int i = 0; i < count; ++i) {
            this.ingredients.add(ingredient);
        }

        return this;
    }

    @Override
    public RecipeBuilder unlockedBy(String p_176496_, Criterion<?> p_297505_) {
        this.criteria.put(p_176496_, p_297505_);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        return this;
    }

    public Item getResult() {
        return this.result;
    }

    public void save(RecipeOutput out, ResourceLocation rl) {
        this.ensureValid(rl);
        //this.advancement.parent(ROOT_RECIPE_ADVANCEMENT).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(rl)).rewards(AdvancementRewards.Builder.recipe(rl)).requirements(AdvancementRequirements.Strategy.OR);
        //out.accept(new CarpenterRecipeBuilder.Result(rl, this.result, this.count, RecipeBuilder.determineBookCategory(this.category), this.ingredients, this.advancement, rl.withPrefix("recipes/" + this.category.getFolderName() + "/")));

        Advancement.Builder advancement$builder = out.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(rl)).rewards(AdvancementRewards.Builder.recipe(rl)).requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement$builder::addCriterion);
        CarpenterRecipe recipe = new CarpenterRecipe(Objects.requireNonNullElse(this.group, ""), this.ingredients, new ItemStack(this.result, this.count));
        out.accept(rl, recipe, advancement$builder.build(rl.withPrefix("recipes/" + this.category.getFolderName() + "/")));
    }

    private void ensureValid(ResourceLocation rl) {
        if (this.criteria.isEmpty()) {
            //throw new IllegalStateException("No way of obtaining recipe " + rl);
        }
    }

    /*
    public static class Result extends CarpenterRecipeBuilder.CraftingResult {
        private final ResourceLocation id;
        private final Item result;
        private final int count;
        private final List<Ingredient> ingredients;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, Item item, int count, CraftingBookCategory category, List<Ingredient> ingredients, Advancement.Builder advancement, ResourceLocation advancementId) {
            super(category);
            this.id = id;
            this.result = item;
            this.count = count;
            this.ingredients = ingredients;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        public void serializeRecipeData(JsonObject json) {
            super.serializeRecipeData(json);

            JsonArray jsonarray = new JsonArray();

            for(Ingredient ingredient : this.ingredients) {
                jsonarray.add(ingredient.toJson());
            }

            json.add("ingredients", jsonarray);
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(this.result)).toString());
            if (this.count > 1) {
                jsonobject.addProperty("count", this.count);
            }

            json.add("result", jsonobject);
        }

        public RecipeSerializer<?> getType() {
            return CarpenterRecipe.Serializer.INSTANCE;
        }

        public ResourceLocation getId() {
            return this.id;
        }

        @Nullable
        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        @Nullable
        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
    */
}
