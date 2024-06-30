package github.mrh0.buildersaddition2.compat.jei;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.recipe.carpenter.CarpenterRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;

@JeiPlugin
public class BA2JEI implements IModPlugin {

    private static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(BA2.MODID, "jei_plugin");

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return ID;
    }

    public IIngredientManager ingredientManager;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new CarpenterRecipeCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ingredientManager = registration.getIngredientManager();
        var recipes = getLevel().getRecipeManager().getAllRecipesFor(Index.CARPENTER_TYPE.get());
        registration.addRecipes(CarpenterRecipeCategory.type, recipes.stream().map(RecipeHolder::value).toList());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        for (Block block : Index.CARPENTER_TABLE.getAllBlocks())
            registration.addRecipeCatalyst(new ItemStack(block.asItem()), CarpenterRecipeCategory.type);
    }

    private static ClientLevel getLevel() {
        return Minecraft.getInstance().level;
    }
}
