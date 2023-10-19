package github.mrh0.buildersaddition2.blocks.bedside_table;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class BedsideTableBlueprint extends BlockBlueprint<WoodVariant, BedsideTableBlock> {
    public BedsideTableBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "bedside_table";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return variant.displayName + " Bedside Table";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<BedsideTableBlock> getBlock(WoodVariant variant) {
        return () -> new BedsideTableBlock(BlockBehaviour.Properties.copy(variant.planks));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<BedsideTableBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant), BA2.get("block/base_" + getBaseName()))
                .texture("planks", variant.texturePlanks)
                .texture("stripped", variant.textureStripped)
                .texture("particle", variant.texturePlanks);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<BedsideTableBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant), BA2.get(getBlockModelPath(variant)));
    }

    @Override
    public void buildBlockState(BPBlockStateProvider provider, RegistryObject<BedsideTableBlock> block, WoodVariant variant) {
        provider.multipartHorizontalFacing(
                provider.getMultipartBuilder(block.get()),
                model(getBlockModelPath(variant)),
                180,
                false
        );
    }

    @Override
    public int getRecipeResultCount(WoodVariant variant) {
        return 1;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoodVariant variant) {
        return List.of(variant.stripped);
    }
}
