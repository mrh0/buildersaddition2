package github.mrh0.buildersaddition2.event;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
}
