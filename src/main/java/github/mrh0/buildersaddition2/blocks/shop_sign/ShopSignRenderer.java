package github.mrh0.buildersaddition2.blocks.shop_sign;

import com.mojang.blaze3d.vertex.PoseStack;
import github.mrh0.buildersaddition2.state.ShopSignState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ShopSignRenderer implements BlockEntityRenderer<ShopSignBlockEntity> {
    private final Minecraft mc = Minecraft.getInstance();
    public ShopSignRenderer(BlockEntityRenderDispatcher rendererDispatcherIn) {
        super();
    }

    private static final float u1 = 1f/16f;

    @Override
    public void render(ShopSignBlockEntity be, float partialTicks, @NotNull PoseStack matrixStackIn,
                       @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ShopSignState state = be.getBlockState().getValue(ShopSignBlock.TYPE);

        ItemStack item = be.getDisplayItem();

        if(item == ItemStack.EMPTY) return;

        //First side
        matrixStackIn.pushPose();
        matrixStackIn.translate(.5f, .5f, .5f);
        matrixStackIn.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180f));
        if(state.getAxis() == Direction.Axis.Z) {
            matrixStackIn.mulPose(com.mojang.math.Axis.YP.rotationDegrees(90f));
        }
        matrixStackIn.translate(-offsetSide(state), offsetY(state), u1);
        matrixStackIn.scale(0.8f, 0.8f, .8f);
        matrixStackIn.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180f));
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, be.getLevel(), 1);
        matrixStackIn.popPose();

        //Other side
        matrixStackIn.pushPose();
        matrixStackIn.translate(.5f, .5f, .5f);
        if(state.getAxis() == Direction.Axis.Z) {
            matrixStackIn.mulPose(com.mojang.math.Axis.YP.rotationDegrees(90f));
        }
        matrixStackIn.translate(offsetSide(state), offsetY(state), u1);
        matrixStackIn.scale(0.8f, 0.8f, .8f);
        matrixStackIn.mulPose(com.mojang.math.Axis.YP.rotationDegrees(180f));
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, be.getLevel(), 1);
        matrixStackIn.popPose();
    }

    private float offsetY(ShopSignState state) {
        return switch (state) {
            case DOWN_X, DOWN_Z -> -u1;
            case UP_X, UP_Z -> u1;
            default -> 0f;
        };
    }

    private float offsetSide(ShopSignState state) {
        return switch (state) {
            case NORTH, EAST -> u1;
            case SOUTH, WEST -> -u1;
            default -> 0f;
        };
    }
}
