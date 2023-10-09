package github.mrh0.buildersaddition2.compat.jei;

import com.mojang.blaze3d.vertex.PoseStack;
import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.recipe.CarpenterRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

@JeiPlugin
public class BA2JEI implements IModPlugin {

    private static final ResourceLocation ID = new ResourceLocation(BA2.MODID, "jei_plugin");

    @Override
    @Nonnull
    public ResourceLocation getPluginUid() {
        return ID;
    }

    public IIngredientManager ingredientManager;

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        System.out.println("registerCategories!!");
        var guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new CarpenterRecipeCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        System.out.println("registerRecipes!!");
        ingredientManager = registration.getIngredientManager();
        var recipes = getLevel().getRecipeManager().getAllRecipesFor(CarpenterRecipe.Type.INSTANCE);
        recipes.forEach(recipe -> {
            System.out.println("recipe:" + recipe);
        });
        registration.addRecipes(CarpenterRecipeCategory.type, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Index.CARPENTER_TABLE.get().asItem()), CarpenterRecipeCategory.type);
    }

    private static ClientLevel getLevel() {
        return Minecraft.getInstance().level;
    }
}
