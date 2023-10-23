package github.mrh0.buildersaddition2.blocks.shelf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class ShelfRenderer implements BlockEntityRenderer<ShelfBlockEntity> {
    public ShelfRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {
        super();
    }

    private static final float u1 = 1f/16f;

    private void translateSignText(PoseStack pose, float rot, Vec3 pos) {
        pose.mulPose(Axis.YP.rotationDegrees(rot));

        float f = u1 * 1f;
        pose.translate(pos.x, pos.y, pos.z);
        pose.scale(f, -f, f);
    }

    @Override
    public void render(ShelfBlockEntity be, float partialTicks, PoseStack matrixStackIn,
                       MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Direction dir = be.getBlockState().getValue(ShelfBlock.FACING);


        //Minecraft.getInstance().font.drawInBatch("Test", 0f, 0f, 0, false, matrixStackIn.last().pose(), bufferIn, Font.DisplayMode.POLYGON_OFFSET, 0, 15728880);

        float scale = .30f;
        float xoffset = 5.2f;
        float yoffset = 3.5f;
        float yoffset2 = 4.5f;
        float zoffset = .15f;

        //Stack 1
        if(be.getItem(0) != ItemStack.EMPTY) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(.5f, .5f, .5f);
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(-dir.toYRot())); // rotate | getHorizontalAngle
            matrixStackIn.translate(u1*xoffset, u1*yoffset, zoffset);
            matrixStackIn.scale(scale, scale, scale);
            Minecraft.getInstance().getItemRenderer().renderStatic(be.getItem(0), ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, be.getLevel(), 1); // 1 ?
            matrixStackIn.popPose();
        }

        //Stack 2
        if(be.getItem(1) != ItemStack.EMPTY) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(.5f, .5f, .5f);
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(-dir.toYRot()));
            matrixStackIn.translate(0, u1*yoffset, zoffset);
            matrixStackIn.scale(scale, scale, scale);
            Minecraft.getInstance().getItemRenderer().renderStatic(be.getItem(1), ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, be.getLevel(), 1);
            matrixStackIn.popPose();
        }

        //Stack 3
        if(be.getItem(2) != ItemStack.EMPTY) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(.5f, .5f, .5f);
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(-dir.toYRot()));
            matrixStackIn.translate(-u1*xoffset, u1*yoffset, zoffset);
            matrixStackIn.scale(scale, scale, scale);
            Minecraft.getInstance().getItemRenderer().renderStatic(be.getItem(2), ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, be.getLevel(), 1);
            matrixStackIn.popPose();
        }

        //Stack 4
        if(be.getItem(3) != ItemStack.EMPTY) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(.5f, .5f, .5f);
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(-dir.toYRot()));
            matrixStackIn.translate(u1*xoffset, -u1*yoffset2, zoffset);
            matrixStackIn.scale(scale, scale, scale);
            Minecraft.getInstance().getItemRenderer().renderStatic(be.getItem(3), ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, be.getLevel(), 1);
            matrixStackIn.popPose();
        }

        //Stack 5
        if(be.getItem(4) != ItemStack.EMPTY) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(.5f, .5f, .5f);
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(-dir.toYRot()));
            matrixStackIn.translate(0, -u1*yoffset2, zoffset);
            matrixStackIn.scale(scale, scale, scale);
            Minecraft.getInstance().getItemRenderer().renderStatic(be.getItem(4), ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, be.getLevel(), 1);
            matrixStackIn.popPose();
        }

        //Stack 6
        if(be.getItem(5) != ItemStack.EMPTY) {
            matrixStackIn.pushPose();
            matrixStackIn.translate(.5f, .5f, .5f);
            matrixStackIn.mulPose(Axis.YP.rotationDegrees(-dir.toYRot()));
            matrixStackIn.translate(-u1*xoffset, -u1*yoffset2, zoffset);
            matrixStackIn.scale(scale, scale, scale);
            Minecraft.getInstance().getItemRenderer().renderStatic(be.getItem(5), ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, be.getLevel(), 1);
            matrixStackIn.popPose();
        }
    }
}
