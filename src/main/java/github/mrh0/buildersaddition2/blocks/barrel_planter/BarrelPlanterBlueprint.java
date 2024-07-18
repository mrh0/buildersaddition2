package github.mrh0.buildersaddition2.blocks.barrel_planter;

import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import github.mrh0.buildersaddition2.common.variants.SingleVariant;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class BarrelPlanterBlueprint extends BlockBlueprint<SingleVariant, BarrelPlanterBlock> {
    public BarrelPlanterBlueprint(Iterable<SingleVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "barrel_planter";
    }

    @Override
    public String getLangName(SingleVariant variant) {
        return variant.getDisplayName() + " Barrel Planter";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(SingleVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<BarrelPlanterBlock> getBlock(SingleVariant variant) {
        return () -> new BarrelPlanterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, DeferredHolder<Block, BarrelPlanterBlock> block, SingleVariant variant) {}

    @Override
    protected void buildItemModel(BPItemModelProvider provider, DeferredHolder<Block, BarrelPlanterBlock> block, SingleVariant variant) {
        provider.withParent(getRegistryName(variant), resource(getBlockModelPath(variant, "_dirt")));
    }

    @Override
    public void buildBlockState(BPBlockStateProvider provider, DeferredHolder<Block, BarrelPlanterBlock> block, SingleVariant variant) {
        Function<BlockState, ModelFile> modelFunc = (state) -> switch(state.getValue(BarrelPlanterBlock.STATE)) {
                case DIRT -> blockModel("barrel_planter_dirt");
                case FARMLAND -> blockModel("barrel_planter_farmland");
            };
        provider.getVariantBuilder(block.get())
                .forAllStates(state -> ConfiguredModel.builder().modelFile(modelFunc.apply(state)).build());
    }

    @Override
    public int getRecipeResultCount(SingleVariant variant) {
        return 1;
    }

    @Override
    public List<ItemLike> getRecipeRequired(SingleVariant variant) {
        return List.of(Blocks.BARREL, Blocks.DIRT);
    }
}
