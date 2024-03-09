package github.mrh0.buildersaddition2.recipe.carpenter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.blocks.bedside_table.BedsideTableBlock;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class CarpenterRecipe implements Recipe<SimpleContainer> {
    public static final String RECIPE_TYPE_NAME = "carpenter";

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;

    public CarpenterRecipe(String group, NonNullList<Ingredient> inputItems, ItemStack output) {
        this.inputItems = inputItems;
        this.output = output;
    }

    public CraftingBookCategory category() {
        return CraftingBookCategory.BUILDING;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public boolean matches(SimpleContainer container, Level pLevel) {
        for (int i = 0; i < inputItems.size(); i++) {
            if(inputItems.get(i).isEmpty()) break;
            boolean match = false;
            for(int j = 0; j < 4; j++) {
                if(inputItems.get(i).test(container.getItem(j))) match = true;
            }
            if(!match) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess registry) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registry) {
        return output.copy();
    }

    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<CarpenterRecipe> {
        public static final Type INSTANCE = new Type();
    }

    public static class Serializer implements RecipeSerializer<CarpenterRecipe> {
        public static final Codec<CarpenterRecipe> CODEC = RecordCodecBuilder.create((builder) -> {
            return builder.group(ExtraCodecs.strictOptionalField(Codec.STRING, "group", "").forGetter((recipe) -> {
                return ""; // group
            }), Ingredient.CODEC_NONEMPTY.listOf().fieldOf("ingredients").flatXmap((p_297969_) -> {
                Ingredient[] aingredient = p_297969_.stream().filter((p_298915_) -> {
                    return !p_298915_.isEmpty();
                }).toArray(Ingredient[]::new);
                if (aingredient.length == 0) {
                    return DataResult.error(() -> "No ingredients for shapeless recipe");
                } else {
                    return aingredient.length > 4 ? DataResult.error(() -> "Too many ingredients for shapeless recipe") : DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
                }
            }, DataResult::success).forGetter((recipe) -> {
                return recipe.inputItems;
            }), ItemStack.ITEM_WITH_COUNT_CODEC.fieldOf("result").forGetter((recipe) -> {
                return recipe.output;
            })).apply(builder, CarpenterRecipe::new);
        });
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(BA2.MODID, RECIPE_TYPE_NAME);

        /*
        @Override
        public CarpenterRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(4, Ingredient.EMPTY);

            for(int i = 0; i < ingredients.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new CarpenterRecipe(inputs, output, recipeId);
        }
        */

        /*
        @Override
        public @Nullable
        CarpenterRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buff) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buff.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buff));
            }

            ItemStack output = buff.readItem();
            return new CarpenterRecipe(inputs, output, recipeId);
        }
        */

        @Override
        public Codec<CarpenterRecipe> codec() {
            return CODEC;
        }

        @Override
        public @org.jetbrains.annotations.Nullable CarpenterRecipe fromNetwork(FriendlyByteBuf buff) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buff.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buff));
            }

            ItemStack output = buff.readItem();
            return new CarpenterRecipe("", inputs, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buff, CarpenterRecipe recipe) {
            buff.writeInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.toNetwork(buff);
            }

            buff.writeItemStack(recipe.getResultItem(null), false);
        }
    }
}
