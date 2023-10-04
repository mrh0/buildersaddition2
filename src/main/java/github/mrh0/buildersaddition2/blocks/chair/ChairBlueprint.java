package github.mrh0.buildersaddition2.blocks.chair;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import github.mrh0.buildersaddition2.datagen.BlockModelHelpers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ChairBlueprint extends BlockBlueprint<WoodVariant, ChairBlock> {
    @Override
    protected Supplier<ChairBlock> getBlock(WoodVariant variant) {
        return () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    }

    @Override
    public String getRegistryName(WoodVariant variant) {
        return "chair_" + variant.toString();
    }

    @Override
    public void buildBlockState(BlockStateProvider bsp, RegistryObject<ChairBlock> block, WoodVariant variant) {
        var model = BlockModelHelpers.parent(resource(variant), bsp.models().existingFileHelper, BA2.get("chair/base"));
        bsp.horizontalBlock(block.get(), model);
    }
}
