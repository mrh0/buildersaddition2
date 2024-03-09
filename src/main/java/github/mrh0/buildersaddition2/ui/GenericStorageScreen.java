package github.mrh0.buildersaddition2.ui;

import github.mrh0.buildersaddition2.BA2;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class GenericStorageScreen extends AbstractContainerScreen<GenericStorageMenu> implements MenuAccess<GenericStorageMenu> {
    private static final ResourceLocation CONTAINER_BACKGROUND = BA2.get("textures/gui/container/generic.png");
    private final int containerRows;
    private final int containerColumns;

    public GenericStorageScreen(GenericStorageMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        int i = 222;
        int j = 114;
        this.containerRows = menu.getRowCount();
        this.containerColumns = menu.getColumnCount();
        this.imageHeight = 114 + this.containerRows * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    public void render(GuiGraphics gg, int x, int y, float partial) {
        //this.renderBackground(gg, x, y, partial);
        super.render(gg, x, y, partial);
        this.renderTooltip(gg, x, y);
    }

    protected void renderBg(GuiGraphics gg, float partial, int mx, int my) {
        int iconIndex = menu.getSlotIconIndex();
        int slotSize = 18;
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        gg.blit(CONTAINER_BACKGROUND, i, j, 0, 0, this.imageWidth, this.containerRows * slotSize + 17);
        gg.blit(CONTAINER_BACKGROUND, i, j + this.containerRows * slotSize + 17, 0, 126, this.imageWidth, 96);

        for(int y = 0; y < this.containerRows; ++y) {
            for(int x = 0; x < this.containerColumns; ++x) {
                gg.blit(
                        CONTAINER_BACKGROUND,
                        x * slotSize + (this.width - slotSize*containerColumns)/2,
                        slotSize + y * slotSize + j - 1,
                        176 + slotSize*iconIndex,
                        0,
                        slotSize,
                        slotSize
                );
            }
        }
    }
}