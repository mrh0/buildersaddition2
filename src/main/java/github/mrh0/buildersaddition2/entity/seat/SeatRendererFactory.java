package github.mrh0.buildersaddition2.entity.seat;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class SeatRendererFactory implements EntityRendererProvider<SeatEntity> {
    @Override
    public EntityRenderer<SeatEntity> create(Context c) {
        return new SeatRenderer(c);
    }
}
