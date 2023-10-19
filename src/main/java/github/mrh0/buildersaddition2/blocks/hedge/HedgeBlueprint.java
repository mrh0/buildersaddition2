package github.mrh0.buildersaddition2.blocks.hedge;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.LeavesVariant;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class HedgeBlueprint extends BlockBlueprint<LeavesVariant, HedgeBlock> {
    public HedgeBlueprint(Iterable<LeavesVariant> variants) {
        super(variants);
    }

    @Override
    protected Supplier<HedgeBlock> getBlock(LeavesVariant variant) {
        return () -> new HedgeBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL));
    }

    @Override
    public String getBaseName() {
        return "hedge";
    }

    @Override
    public String getLangName(LeavesVariant variant) {
        return variant.displayName + " Hedge";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(LeavesVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected void buildBlockState(BPBlockStateProvider bsp, RegistryObject<HedgeBlock> block, LeavesVariant variant) {
        bsp.getVariantBuilder(block.get()).forAllStatesExcept((BlockState state) -> ConfiguredModel.builder()
                .modelFile(model(getBlockModelPath(variant, "_" + state.getValue(HedgeBlock.STATE).getModelName())))
                .rotationY(state.getValue(HedgeBlock.STATE).getModelYRotation())
                .uvLock(true)
                .build(), HedgeBlock.WATERLOGGED);
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<HedgeBlock> block, LeavesVariant variant) {
        provider.withParent(getBlockModelPath(variant, "_corner"), BA2.get("block/base_" + getBaseName() + "_corner"))
                .texture("leaves", variant.textureLeaves)
                .texture("particles", variant.textureLeaves);
        provider.withParent(getBlockModelPath(variant, "_cross"), BA2.get("block/base_" + getBaseName() + "_cross"))
                .texture("leaves", variant.textureLeaves)
                .texture("particles", variant.textureLeaves);
        provider.withParent(getBlockModelPath(variant, "_none"), BA2.get("block/base_" + getBaseName() + "_none"))
                .texture("leaves", variant.textureLeaves)
                .texture("particles", variant.textureLeaves);
        provider.withParent(getBlockModelPath(variant, "_straight"), BA2.get("block/base_" + getBaseName() + "_straight"))
                .texture("leaves", variant.textureLeaves)
                .texture("particles", variant.textureLeaves);
        provider.withParent(getBlockModelPath(variant, "_tcross"), BA2.get("block/base_" + getBaseName() + "_tcross"))
                .texture("leaves", variant.textureLeaves)
                .texture("particles", variant.textureLeaves);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<HedgeBlock> block, LeavesVariant variant) {
        provider.withParent(getRegistryName(variant), BA2.get("block/base_" + getBaseName() + "_inventory"))
                .texture("leaves", variant.textureLeaves);
    }

    @Override
    public int getRecipeResultCount(LeavesVariant variant) {
        return 2;
    }

    @Override
    public List<ItemLike> getRecipeRequired(LeavesVariant variant) {
        return List.of(variant.leaves);
    }
}
