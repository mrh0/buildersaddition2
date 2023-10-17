package github.mrh0.buildersaddition2.blocks.pillow;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import github.mrh0.buildersaddition2.common.variants.WoolVariant;
import github.mrh0.buildersaddition2.datagen.BA2BlockModelProvider;
import github.mrh0.buildersaddition2.datagen.BA2BlockStateProvider;
import github.mrh0.buildersaddition2.datagen.BA2ItemModelProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class PillowBlueprint extends BlockBlueprint<WoolVariant, PillowBlock> {
    public PillowBlueprint(Iterable<WoolVariant> variants) {
        super(variants);
    }

    @Override
    protected Supplier<PillowBlock> getBlock(WoolVariant variant) {
        return () -> new PillowBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL));
    }

    @Override
    public String getBaseName() {
        return "pillow";
    }

    @Override
    public String getLangName(WoolVariant variant) {
        return variant.displayName + " Pillow";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoolVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected void buildBlockState(BA2BlockStateProvider bsp, RegistryObject<PillowBlock> block, WoolVariant variant) {
        bsp.simpleBlock(block.get(), model(getBlockModelPath(variant)));
    }

    @Override
    protected void buildBlockModel(BA2BlockModelProvider provider, RegistryObject<PillowBlock> block, WoolVariant variant) {
        provider.withParent(getBlockModelPath(variant), BA2.get("block/base_" + getBaseName()))
                .texture("wool", variant.textureWool);
        provider.withParent("block/" + variant + "_stool_pillow", BA2.get("block/base_stool_" + getBaseName()))
                .texture("wool", variant.textureWool);
    }

    @Override
    protected void buildItemModel(BA2ItemModelProvider provider, RegistryObject<PillowBlock> block, WoolVariant variant) {
        provider.withParent(getRegistryName(variant), BA2.get(getBlockModelPath(variant)));
    }

    @Override
    public int getRecipeResultCount(WoolVariant variant) {
        return 3;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoolVariant variant) {
        return List.of(variant.wool);
    }
}
