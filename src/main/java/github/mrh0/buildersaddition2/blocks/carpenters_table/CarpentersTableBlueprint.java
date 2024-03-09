package github.mrh0.buildersaddition2.blocks.carpenters_table;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPRecipeProvider;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CarpentersTableBlueprint extends BlockBlueprint<WoodVariant, CarpentersTableBlock> {
    public CarpentersTableBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "carpenters_table";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return variant.getDisplayName() + " Carpenter's Table";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<CarpentersTableBlock> getBlock(WoodVariant variant) {
        return () -> new CarpentersTableBlock(BlockBehaviour.Properties.ofFullCopy(variant.planks));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<CarpentersTableBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant), resource("block/base_" + getBaseName()))
                .texture("planks", variant.texturePlanks)
                .texture("stripped", variant.textureStripped)
                .texture("top", variant.textureStrippedTop)
                .texture("particle", variant.texturePlanks);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<CarpentersTableBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant), resource(getBlockModelPath(variant)));
    }

    @Override
    public void buildBlockState(BPBlockStateProvider provider, RegistryObject<CarpentersTableBlock> block, WoodVariant variant) {
        provider.multipartHorizontalFacing(
                provider.getMultipartBuilder(block.get()),
                model(getBlockModelPath(variant)),
                180,
                false
        );
    }

    @Override
    public void buildRecipe(BPRecipeProvider provider, RecipeOutput out, RegistryObject<CarpentersTableBlock> block, WoodVariant variant) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, block.get().asItem())
                .define('A', Items.IRON_AXE)
                .define('S', variant.stripped)
                .pattern("A")
                .pattern("S")
                .unlockedBy(
                        BPRecipeProvider.getHasName(Index.CARPENTER_TABLE.getBlock(0)),
                        BPRecipeProvider.has(Index.CARPENTER_TABLE.getBlock(0)))
                .save(out, BA2.get(getRegistryName(variant)));
    }
}
