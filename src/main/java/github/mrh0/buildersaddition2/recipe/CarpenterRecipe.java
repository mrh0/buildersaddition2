package github.mrh0.buildersaddition2.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import github.mrh0.buildersaddition2.BA2;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

public class CarpenterRecipe implements Recipe<SimpleContainer> {

    public static final String RECIPE_NAME = "carpenter";

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;

    public CarpenterRecipe(NonNullList<Ingredient> inputItems, ItemStack output, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer container, Level pLevel) {
        boolean match = true;
        for (int i = 0; i < inputItems.size(); i++) {
            if(inputItems.get(i).isEmpty()) continue;
            if(container.getItem(i).isEmpty()) continue;
            if(!inputItems.get(i).test(container.getItem(i))) match = false;
        }
        return match;
    }

    @Override
    public ItemStack assemble(SimpleContainer container, RegistryAccess registry) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registry) {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
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
        public static final String ID = RECIPE_NAME;
    }

    public static class Serializer implements RecipeSerializer<CarpenterRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(BA2.MODID, RECIPE_NAME);

        @Override
        public CarpenterRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new CarpenterRecipe(inputs, output, recipeId);
        }

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
