package github.mrh0.buildersaddition2.blocks.stripped_fence;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
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
        return () -> new StrippedFenceBlock(BlockBehaviour.Properties.ofFullCopy(variant.planks));
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
        return "Stripped " + variant.getDisplayName() + " Fence";
    }

    @Override
    protected void buildBlockState(BPBlockStateProvider bsp, RegistryObject<StrippedFenceBlock> block, WoodVariant variant) {
        bsp.fenceBlock(block.get(), ResourceLocation.parse(variant.textureStripped));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<StrippedFenceBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant, "_post"), resource("block/base_" + getBaseName() + "_post"))
                .texture("texture", variant.textureStripped)
                .texture("particle", variant.textureStripped);
        provider.withParent(getBlockModelPath(variant, "_side"), resource("block/base_" + getBaseName() + "_side"))
                .texture("texture", variant.texturePlanks)
                .texture("particle", variant.textureStripped);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<StrippedFenceBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant),  resource("block/base_" + getBaseName() + "_inventory"))
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
