package github.mrh0.buildersaddition2.blocks.arcade;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.blocks.arcade.games.AbstractArcadeGame;
import github.mrh0.buildersaddition2.blocks.arcade.games.ArcadeHomeMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ArcadeScreen extends AbstractContainerScreen<ArcadeMenu> {
    private ArcadeDisplay display = new ArcadeDisplay();
    private AbstractArcadeGame game;

    private static final ResourceLocation GUI = ResourceLocation.fromNamespaceAndPath(BA2.MODID,
            "textures/gui/container/arcade.png");

    public ArcadeScreen(ArcadeMenu containerMenu, Inventory inv, Component tc) {
        super(containerMenu, inv, tc);

        this.imageWidth = 336;
        this.imageHeight = 226;
        setGame(ArcadeHomeMenu::new);
    }

    @Override
    protected void renderBg(GuiGraphics gg, float partial, int x, int y) {
        renderTransparentBackground(gg);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        gg.blit(GUI, i, j - 5, 0, 0, this.imageWidth, this.imageHeight, 512, 256);
    }

    public void setGame(ArcadeManager.GameConstructor g) {
        display = new ArcadeDisplay();
        game = g.construct(display);

        display.setBgRenderer(null);
        display.setFgRenderer(null);
        if(game instanceof ArcadeHomeMenu homeMenu) homeMenu.gui = this;
        game.start();
    }

    @Override
    public void resize(Minecraft minecraft, int w, int h) {
        super.resize(minecraft, w, h);
        //this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void onClose() {
        super.onClose();
        game = null;
        System.out.println("Closed Arcade");
    }

    @Override
    public boolean mouseClicked(double x, double y, int b) {
        return super.mouseClicked(x, y, b);
    }

    long steps = 0;
    float partialAccumulator = 0f;
    private long lastTime = System.nanoTime();
    @Override
    public void render(GuiGraphics gg, int x, int y, float partial) {
        super.render(gg, x, y, partial);
        long currentTime = System.nanoTime();
        float deltaTime = (currentTime - lastTime) / 1_000_000_000f;
        if((partialAccumulator += deltaTime) > 1f/20f) {
            steps += 1;
            partialAccumulator = 0f;
            clientTick(steps, deltaTime);
        }
        lastTime = currentTime;

        display.renderBackground(gg, this.width, this.height);
        display.renderForeground(gg, this.font, this.width, this.height);

        GlStateManager._disableBlend();
        this.renderTooltip(gg, x, y);
    }

    public void clientTick(long steps, float deltaTime) {
        if(game == null) return;
        game.frame(steps, deltaTime);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        game.onKeyPressed(keyCode);
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        game.onKeyReleased(keyCode);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    protected void renderLabels(GuiGraphics gg, int x, int y) {
        gg.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY - 5, 4210752, false);
    }
}
