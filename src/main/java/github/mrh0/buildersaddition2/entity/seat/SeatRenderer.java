package github.mrh0.buildersaddition2.entity.seat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class SeatRenderer extends EntityRenderer<SeatEntity> {
    protected SeatRenderer(EntityRendererProvider.Context c) {
        super(c);
    }

    @Override
    public void render(SeatEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
                       MultiBufferSource bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(SeatEntity p_114482_) {
        return null;
    }
}