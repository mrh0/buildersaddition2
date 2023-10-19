package github.mrh0.buildersaddition2.proxy;

import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.entity.seat.SeatRendererFactory;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ClientProxy extends CommonProxy {
    @Override
    public void init(final FMLCommonSetupEvent evt) {
        EntityRenderers.register(Index.SEAT_ENTITY_TYPE.get(), new SeatRendererFactory());

        //RenderType transl = RenderType.translucent();
        RenderType cutout = RenderType.cutoutMipped();

        Index.HEDGE.iterable().forEach(pair -> {
            ItemBlockRenderTypes.setRenderLayer(pair.getFirst().get(), cutout);
        });
    }
}
