package github.mrh0.buildersaddition2.blocks.shelf;

import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.function.Supplier;

public class ShelfBlueprint extends BlockBlueprint<WoodVariant, ShelfBlock> {
    public ShelfBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "shelf";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return variant.getDisplayName() + " Shelf";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<ShelfBlock> getBlock(WoodVariant variant) {
        return () -> new ShelfBlock(BlockBehaviour.Properties.ofFullCopy(variant.planks));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, DeferredHolder<Block, ShelfBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant), resource("block/base_" + getBaseName()))
                .texture("planks", variant.texturePlanks)
                .texture("particle", variant.texturePlanks);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, DeferredHolder<Block, ShelfBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant), resource(getBlockModelPath(variant)));
    }

    @Override
    public void buildBlockState(BPBlockStateProvider provider, DeferredHolder<Block, ShelfBlock> block, WoodVariant variant) {
        provider.horizontalBlock(block.get(), model(getBlockModelPath(variant)));
    }

    @Override
    public int getRecipeResultCount(WoodVariant variant) {
        return 2;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoodVariant variant) {
        return List.of(variant.planks, variant.slab);
    }
}
