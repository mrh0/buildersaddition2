package github.mrh0.buildersaddition2.event;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.arcade.ArcadeScreen;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = BA2.MODID, dist = Dist.CLIENT)
public class ClientEventHandler {

    public ClientEventHandler(IEventBus modEventBus) {
        modEventBus.addListener(ClientEventHandler::registerColorBlock);
        modEventBus.addListener(ClientEventHandler::registerColorItem);
        modEventBus.addListener(ClientEventHandler::registerMenuScreen);
        modEventBus.addListener(ClientEventHandler::onClientSetup);
    }

    public static void registerColorBlock(RegisterColorHandlersEvent.Block evt){
        BlockColor bc = (a, b, c, d) ->
                b != null && c != null ? BiomeColors.getAverageFoliageColor(b, c) : FoliageColor.getDefaultColor();

        evt.getBlockColors().register(bc, Index.HEDGE.getAllBlocks());
    }

    public static void registerColorItem(RegisterColorHandlersEvent.Item evt){
        ItemColor ic = (a, b) -> {
            BlockState blockstate = ((BlockItem)a.getItem()).getBlock().defaultBlockState();
            return Minecraft.getInstance().getBlockColors().getColor(blockstate, null, (BlockPos)null, b);
        };

        evt.getItemColors().register(ic, Index.HEDGE.getAllBlocks());
    }

    public static void registerMenuScreen(RegisterMenuScreensEvent evt) {
        evt.register(Index.CARPENTER_TABLE_MENU.get(), CarpenterTableScreen::new);
        evt.register(Index.SHELF_MENU.get(), GenericStorageScreen::new);
        evt.register(Index.BOOKSHELF_MENU.get(), GenericStorageScreen::new);
        evt.register(Index.ARCADE_MENU.get(), ArcadeScreen::new);
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
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
