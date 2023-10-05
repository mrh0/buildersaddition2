package github.mrh0.buildersaddition2.blocks.chair;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import github.mrh0.buildersaddition2.datagen.BA2BlockModelProvider;
import github.mrh0.buildersaddition2.datagen.BA2ItemModelProvider;
import github.mrh0.buildersaddition2.datagen.BlockModelHelpers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ChairBlueprint extends BlockBlueprint<WoodVariant, ChairBlock> {
    public ChairBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    public String getName() {
        return "chair";
    }

    @Override
    protected Supplier<ChairBlock> getBlock(WoodVariant variant) {
        return () -> new ChairBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    }

    @Override
    public String getRegistryName(WoodVariant variant) {
        return "chair_" + variant.toString();
    }

    @Override
    protected ModelFile buildBlockModel(BA2BlockModelProvider provider, RegistryObject<ChairBlock> block, WoodVariant variant) {
        return provider.withExistingParent(getRegistryName(variant), BA2.get("block/chair/chair_base"))
                .texture("planks", variant.texturePlanks)
                .texture("stripped", variant.textureStripped)
                .texture("particle", variant.texturePlanks);
    }

    @Override
    protected ModelFile buildItemModel(BA2ItemModelProvider provider, RegistryObject<ChairBlock> block, WoodVariant variant) {
        return provider.withExistingParent(getRegistryName(variant), BA2.get("block/chair/" + getRegistryName(variant)));
    }

    @Override
    public void buildBlockState(BlockStateProvider bsp, RegistryObject<ChairBlock> block, WoodVariant variant) {
        bsp.horizontalBlock(block.get(), new ModelFile.UncheckedModelFile(BA2.get("block/" + getName() + "/" + getRegistryName(variant))));
    }
}
