package github.mrh0.buildersaddition2.compat.jei;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.recipe.carpenter.CarpenterRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class CarpenterRecipeCategory implements IRecipeCategory<CarpenterRecipe> {

    private final IDrawable background;
    private final IDrawable icon;
    public static final RecipeType<CarpenterRecipe> type = new RecipeType<>(CarpenterRecipe.Serializer.ID, CarpenterRecipe.class);

    public CarpenterRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.drawableBuilder(BA2.get("textures/gui/container/jei_carpenter.png"), 0, 0, 125, 32)
                .addPadding(0, 0, 0, 0)
                .build();
        icon = guiHelper.createDrawableItemStack(new ItemStack(Index.CARPENTER_TABLE.get().asItem()));
    }

    @Override
    public RecipeType<CarpenterRecipe> getRecipeType() {
        return type;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("recipe." + BA2.MODID + ".carpenter.title");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CarpenterRecipe recipe, IFocusGroup focuses) {
        var ing = recipe.getIngredients();
        for (int i = 0; i < ing.size(); i++) {
            builder
                    .addSlot(RecipeIngredientRole.INPUT, 7 + i*18, 8)
                    .addIngredients(ing.get(i));
        }

        builder
                .addSlot(RecipeIngredientRole.OUTPUT, 101, 8)
                .addItemStack(recipe.getResultItem());
    }
}
