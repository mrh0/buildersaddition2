package github.mrh0.buildersaddition2.blocks.stripped_fence;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import github.mrh0.buildersaddition2.datagen.BA2BlockModelProvider;
import github.mrh0.buildersaddition2.datagen.BA2BlockStateProvider;
import github.mrh0.buildersaddition2.datagen.BA2ItemModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class StrippedFenceBlueprint extends BlockBlueprint<WoodVariant, StrippedFenceBlock> {
    public StrippedFenceBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    protected Supplier<StrippedFenceBlock> getBlock(WoodVariant variant) {
        return () -> new StrippedFenceBlock(BlockBehaviour.Properties.copy(variant.planks));
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(
                BlockTags.MINEABLE_WITH_AXE,
                BlockTags.FENCES,
                BlockTags.WOODEN_FENCES
        );
    }

    @Override
    public String getBaseName() {
        return "stripped_fence";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return "Stripped " + variant.displayName + " Fence";
    }

    @Override
    protected void buildBlockState(BA2BlockStateProvider bsp, RegistryObject<StrippedFenceBlock> block, WoodVariant variant) {
        bsp.fenceBlock(block.get(), new ResourceLocation(variant.textureStripped));
    }

    @Override
    protected void buildBlockModel(BA2BlockModelProvider provider, RegistryObject<StrippedFenceBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant, "_post"), BA2.get("block/base_" + getBaseName() + "_post"))
                .texture("texture", variant.textureStripped)
                .texture("particle", variant.textureStripped);
        provider.withParent(getBlockModelPath(variant, "_side"), BA2.get("block/base_" + getBaseName() + "_side"))
                .texture("texture", variant.texturePlanks)
                .texture("particle", variant.textureStripped);
    }

    @Override
    protected void buildItemModel(BA2ItemModelProvider provider, RegistryObject<StrippedFenceBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant),  BA2.get("block/base_" + getBaseName() + "_inventory"))
                .texture("texture", variant.textureStripped);
    }

    @Override
    public int getRecipeResultCount(WoodVariant variant) {
        return 4;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoodVariant variant) {
        return List.of(variant.stripped, variant.planks);
    }
}
