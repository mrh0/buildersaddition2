package github.mrh0.buildersaddition2.blocks.cupboard;

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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class CupboardBlueprint extends BlockBlueprint<WoodVariant, CupboardBlock> {
    public CupboardBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "cupboard";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return variant.displayName + " Cupboard";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<CupboardBlock> getBlock(WoodVariant variant) {
        return () -> new CupboardBlock(BlockBehaviour.Properties.copy(variant.planks));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<CupboardBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant, "_single"), BA2.get("block/base_cupboard_single"))
                .texture("planks", variant.texturePlanks)
                .texture("stripped", variant.textureStripped)
                //.texture("detail", "minecraft:polished_andesite")
                .texture("particle", variant.texturePlanks);
        provider.withParent(getBlockModelPath(variant, "_single_mirror"), BA2.get("block/base_cupboard_single_mirror"))
                .texture("planks", variant.texturePlanks)
                .texture("stripped", variant.textureStripped)
                //.texture("detail", "minecraft:polished_andesite")
                .texture("particle", variant.texturePlanks);
        provider.withParent(getBlockModelPath(variant, "_top"), BA2.get("block/base_cupboard_top"))
                .texture("planks", variant.texturePlanks)
                .texture("stripped", variant.textureStripped)
                //.texture("detail", "minecraft:polished_andesite")
                .texture("particle", variant.texturePlanks);
        provider.withParent(getBlockModelPath(variant, "_top_mirror"), BA2.get("block/base_cupboard_top_mirror"))
                .texture("planks", variant.texturePlanks)
                .texture("stripped", variant.textureStripped)
                //.texture("detail", "minecraft:polished_andesite")
                .texture("particle", variant.texturePlanks);
        provider.withParent(getBlockModelPath(variant, "_bottom"), BA2.get("block/base_cupboard_bottom"))
                .texture("planks", variant.texturePlanks)
                .texture("stripped", variant.textureStripped)
                .texture("particle", variant.texturePlanks);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<CupboardBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant), BA2.get(getBlockModelPath(variant, "_single")));
    }

    @Override
    public void buildBlockState(BPBlockStateProvider provider, RegistryObject<CupboardBlock> block, WoodVariant variant) {
        Function<BlockState, ModelFile> modelFunc = (state) -> {
            boolean mirror = state.getValue(CupboardBlock.MIRROR);
             return switch(state.getValue(CupboardBlock.VARIANT)) {
                 case Single -> mirror ? blockModel(variant + "_cupboard_single_mirror") : blockModel(variant + "_cupboard_single");
                 case Top -> mirror ? blockModel(variant + "_cupboard_top_mirror") : blockModel(variant + "_cupboard_top");
                 case Bottom -> blockModel(variant + "_cupboard_bottom");
             };
        };
        provider.getVariantBuilder(block.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(modelFunc.apply(state))
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .build()
                );
    }

    @Override
    public LootTableProviderType getLootTableType(WoodVariant variant) {
        return LootTableProviderType.SELF;
    }

    @Override
    public int getRecipeResultCount(WoodVariant variant) {
        return 1;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoodVariant variant) {
        return List.of(variant.stripped, Blocks.CHEST.asItem());
    }
}