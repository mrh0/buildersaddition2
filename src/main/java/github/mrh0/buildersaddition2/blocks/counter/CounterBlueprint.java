package github.mrh0.buildersaddition2.blocks.counter;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.blocks.cabinet.CabinetBlock;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import github.mrh0.buildersaddition2.common.variants.CounterVariant;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class CounterBlueprint extends BlockBlueprint<CounterVariant, CounterBlock> {
    public CounterBlueprint(Iterable<CounterVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "counter";
    }

    @Override
    public String getLangName(CounterVariant variant) {
        return variant.getDisplayName() + " Counter";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(CounterVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<CounterBlock> getBlock(CounterVariant variant) {
        return () -> new CounterBlock(BlockBehaviour.Properties.copy(variant.wood.planks));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<CounterBlock> block, CounterVariant variant) {
        provider.withParent(getBlockModelPath(variant), BA2.get("block/base_" + getBaseName()))
                .texture("top", variant.textureTop)
                .texture("planks", variant.wood.texturePlanks)
                .texture("stripped", variant.wood.textureStripped)
                .texture("particle", variant.wood.texturePlanks);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<CounterBlock> block, CounterVariant variant) {
        provider.withParent(getRegistryName(variant), BA2.get(getBlockModelPath(variant)));
    }

    @Override
    public void buildBlockState(BPBlockStateProvider provider, RegistryObject<CounterBlock> block, CounterVariant variant) {
        provider.horizontalBlock(block.get(), model(getBlockModelPath(variant)));
    }

    @Override
    public int getRecipeResultCount(CounterVariant variant) {
        return 1;
    }

    @Override
    public List<ItemLike> getRecipeRequired(CounterVariant variant) {
        return List.of(variant.wood.planks, Blocks.CHEST, variant.top);
    }
}
