package github.mrh0.buildersaddition2.event;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.carpenters_table.CarpenterTableScreen;
import github.mrh0.buildersaddition2.blocks.shelf.ShelfRenderer;
import github.mrh0.buildersaddition2.blocks.shop_sign.ShopSignRenderer;
import github.mrh0.buildersaddition2.entity.seat.SeatRendererFactory;
import github.mrh0.buildersaddition2.ui.GenericStorageScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BA2.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void registerColor(RegisterColorHandlersEvent.Block evt){
        BlockColor bc = (a, b, c, d) ->
                b != null && c != null ? BiomeColors.getAverageFoliageColor(b, c) : FoliageColor.getDefaultColor();

        evt.getBlockColors().register(bc, Index.HEDGE.getAllBlocks());
    }

    @SubscribeEvent
    public static void registerColor(RegisterColorHandlersEvent.Item evt){
        ItemColor ic = (a, b) -> {
            BlockState blockstate = ((BlockItem)a.getItem()).getBlock().defaultBlockState();
            return Minecraft.getInstance().getBlockColors().getColor(blockstate, null, (BlockPos)null, b);
        };

        evt.getItemColors().register(ic, Index.HEDGE.getAllBlocks());
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(Index.CARPENTER_TABLE_MENU.get(), CarpenterTableScreen::new);
        MenuScreens.register(Index.SHELF_MENU.get(), GenericStorageScreen::new);
        MenuScreens.register(Index.BOOKSHELF_MENU.get(), GenericStorageScreen::new);

        BlockEntityRenderers.register(Index.SHELF_ENTITY_TYPE.get(), c -> new ShelfRenderer(c.getBlockEntityRenderDispatcher()));
        BlockEntityRenderers.register(Index.SHOP_SIGN_ENTITY_TYPE.get(), c -> new ShopSignRenderer(c.getBlockEntityRenderDispatcher()));

        EntityRenderers.register(Index.SEAT_ENTITY_TYPE.get(), new SeatRendererFactory());

        //RenderType transl = RenderType.translucent();
        RenderType cutout = RenderType.cutoutMipped();

        Index.HEDGE.iterable().forEach(pair -> {
            ItemBlockRenderTypes.setRenderLayer(pair.getFirst().get(), cutout);
        });
    }
}
