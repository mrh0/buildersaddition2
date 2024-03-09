package github.mrh0.buildersaddition2.blocks.carpenters_table;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.recipe.carpenter.CarpenterRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.List;

public class CarpenterTableScreen extends AbstractContainerScreen<CarpenterTableMenu> {
    private static final ResourceLocation BG_LOCATION = BA2.get("textures/gui/container/carpenter_table.png");
    private static final int SCROLLER_WIDTH = 12;
    private static final int SCROLLER_HEIGHT = 15;
    private static final int RECIPES_COLUMNS = 4;
    private static final int RECIPES_ROWS = 3;
    private static final int RECIPES_IMAGE_SIZE_WIDTH = 16;
    private static final int RECIPES_IMAGE_SIZE_HEIGHT = 18;
    private static final int SCROLLER_FULL_HEIGHT = 54;
    private static final int RECIPES_X = 52;
    private static final int RECIPES_Y = 14;
    private float scrollOffs;
    private boolean scrolling;
    private int startIndex;
    private boolean displayRecipes;

    public CarpenterTableScreen(CarpenterTableMenu menu, Inventory p_99311_, Component p_99312_) {
        super(menu, p_99311_, p_99312_);
        menu.registerUpdateListener(this::containerChanged);
        --this.titleLabelY;
    }

    public void render(GuiGraphics gg, int x, int y, float partial) {
        super.render(gg, x, y, partial);
        this.renderTooltip(gg, x, y);
    }

    protected void renderBg(GuiGraphics gg, float partial, int x, int y) {
        this.renderTransparentBackground(gg);
        int i = this.leftPos;
        int j = this.topPos;
        gg.blit(BG_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
        int k = (int)(41.0F * this.scrollOffs);
        gg.blit(BG_LOCATION, i + 119, j + 15 + k, 176 + (this.isScrollBarActive() ? 0 : 12), 0, 12, 15);
        int l = this.leftPos + 52;
        int i1 = this.topPos + 14;
        int j1 = this.startIndex + 12;
        this.renderButtons(gg, x, y, l, i1, j1);
        this.renderRecipes(gg, l, i1, j1);
    }

    protected void renderTooltip(GuiGraphics gg, int x, int y) {
        super.renderTooltip(gg, x, y);
        if (!this.displayRecipes) return;
        int i = this.leftPos + 52;
        int j = this.topPos + 14;
        int k = this.startIndex + 12;
        List<RecipeHolder<CarpenterRecipe>> list = this.menu.getRecipes();

        for(int l = this.startIndex; l < k && l < this.menu.getNumRecipes(); ++l) {
            int i1 = l - this.startIndex;
            int j1 = i + i1 % 4 * 16;
            int k1 = j + i1 / 4 * 18 + 2;
            if (x >= j1 && x < j1 + 16 && y >= k1 && y < k1 + 18) {
                gg.renderTooltip(this.font, list.get(l).value().getResultItem(this.minecraft.level.registryAccess()), x, y);
            }
        }
    }

    private void renderButtons(GuiGraphics gg, int p_282136_, int p_282147_, int p_281987_, int p_281276_, int p_282688_) {
        if (!this.displayRecipes) return;
        for(int i = this.startIndex; i < p_282688_ && i < this.menu.getNumRecipes(); ++i) {
            int j = i - this.startIndex;
            int k = p_281987_ + j % 4 * 16;
            int l = j / 4;
            int i1 = p_281276_ + l * 18 + 2;
            int j1 = this.imageHeight;
            if (i == this.menu.getSelectedRecipeIndex()) {
                j1 += 18;
            } else if (p_282136_ >= k && p_282147_ >= i1 && p_282136_ < k + 16 && p_282147_ < i1 + 18) {
                j1 += 36;
            }

            gg.blit(BG_LOCATION, k, i1 - 1, 0, j1, 16, 18);
        }
    }

    private void renderRecipes(GuiGraphics gg, int p_282658_, int p_282563_, int p_283352_) {
        if (!this.displayRecipes) return;
        List<RecipeHolder<CarpenterRecipe>> list = this.menu.getRecipes();
        for(int i = this.startIndex; i < p_283352_ && i < this.menu.getNumRecipes(); ++i) {
            int j = i - this.startIndex;
            int k = p_282658_ + j % 4 * 16;
            int l = j / 4;
            int i1 = p_282563_ + l * 18 + 2;
            gg.renderItem(list.get(i).value().getResultItem(this.minecraft.level.registryAccess()), k, i1);
        }

    }

    public boolean mouseClicked(double x, double y, int p_99320_) {
        this.scrolling = false;
        if (!this.displayRecipes) return super.mouseClicked(x, y, p_99320_);
        int i = this.leftPos + 52;
        int j = this.topPos + 14;
        int k = this.startIndex + 12;

        for(int l = this.startIndex; l < k; ++l) {
            int i1 = l - this.startIndex;
            double d0 = x - (double)(i + i1 % 4 * 16);
            double d1 = y - (double)(j + i1 / 4 * 18);
            if (d0 >= 0.0D && d1 >= 0.0D && d0 < 16.0D && d1 < 18.0D && this.menu.clickMenuButton(this.minecraft.player, l)) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0F));
                this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, l);
                return true;
            }
        }

        i = this.leftPos + 119;
        j = this.topPos + 9;
        if (x >= (double)i && x < (double)(i + 12) && y >= (double)j && y < (double)(j + 54)) {
            this.scrolling = true;
        }

        return super.mouseClicked(x, y, p_99320_);
    }

    public boolean mouseDragged(double x1, double y1, int p_99324_, double p_99325_, double p_99326_) {
        if (this.scrolling && this.isScrollBarActive()) {
            int i = this.topPos + 14;
            int j = i + 54;
            this.scrollOffs = ((float)y1 - (float)i - 7.5F) / ((float)(j - i) - 15.0F);
            this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)this.getOffscreenRows()) + 0.5D) * 4;
            return true;
        } else {
            return super.mouseDragged(x1, y1, p_99324_, p_99325_, p_99326_);
        }
    }

    public boolean mouseScrolled(double p_99314_, double p_99315_, double p_99316_) {
        if (this.isScrollBarActive()) {
            int i = this.getOffscreenRows();
            float f = (float)p_99316_ / (float)i;
            this.scrollOffs = Mth.clamp(this.scrollOffs - f, 0.0F, 1.0F);
            this.startIndex = (int)((double)(this.scrollOffs * (float)i) + 0.5D) * 4;
        }

        return true;
    }

    private boolean isScrollBarActive() {
        return this.displayRecipes && this.menu.getNumRecipes() > 12;
    }

    protected int getOffscreenRows() {
        return (this.menu.getNumRecipes() + 4 - 1) / 4 - 3;
    }

    private void containerChanged() {
        this.displayRecipes = this.menu.hasInputItem();
        if (!this.displayRecipes) {
            this.scrollOffs = 0.0F;
            this.startIndex = 0;
        }
    }
}