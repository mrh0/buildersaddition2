package github.mrh0.buildersaddition2.blocks.arcade;

import github.mrh0.buildersaddition2.blocks.cupboard.CupboardBlock;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ArcadeBlueprint extends BlockBlueprint<WoodVariant, ArcadeBlock> {
    public ArcadeBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "arcade";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return variant.getDisplayName() + " Arcade";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<ArcadeBlock> getBlock(WoodVariant variant) {
        return () -> new ArcadeBlock(BlockBehaviour.Properties.copy(variant.planks));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<ArcadeBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant, "_top"), resource("block/base_arcade_top"))
                .texture("sides", variant.textureStripped)
                .texture("particle", variant.texturePlanks);
        provider.withParent(getBlockModelPath(variant, "_bottom"), resource("block/base_arcade_bottom"))
                .texture("sides", variant.textureStripped)
                .texture("particle", variant.texturePlanks);
        provider.withParent(getBlockModelPath(variant, "_inventory"), resource("block/base_arcade_inventory"))
                .texture("sides", variant.textureStripped)
                .texture("particle", variant.texturePlanks);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<ArcadeBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant), resource(getBlockModelPath(variant, "_inventory")));
    }

    @Override
    public void buildBlockState(BPBlockStateProvider provider, RegistryObject<ArcadeBlock> block, WoodVariant variant) {
        Function<BlockState, ModelFile> modelFunc = (state) ->  switch(state.getValue(ArcadeBlock.HALF)) {
                case UPPER -> blockModel(variant + "_arcade_top");
                case LOWER -> blockModel(variant + "_arcade_bottom");
        };
        provider.getVariantBuilder(block.get())
                .forAllStates(state -> ConfiguredModel.builder()
                        .modelFile(modelFunc.apply(state))
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                        .build()
                );
    }

    @Override
    public int getRecipeResultCount(WoodVariant variant) {
        return 1;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoodVariant variant) {
        return List.of(variant.stripped, Items.IRON_INGOT, Items.COMPARATOR);
    }
}