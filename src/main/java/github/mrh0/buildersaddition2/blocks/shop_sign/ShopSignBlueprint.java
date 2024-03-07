package github.mrh0.buildersaddition2.blocks.shop_sign;

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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ShopSignBlueprint extends BlockBlueprint<WoodVariant, ShopSignBlock> {
    public ShopSignBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "shop_sign";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return variant.getDisplayName() + " Shop Sign";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<ShopSignBlock> getBlock(WoodVariant variant) {
        return () -> new ShopSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<ShopSignBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant), resource("block/base_" + getBaseName()))
                .texture("texture", variant.textureStripped)
                .texture("particle", variant.textureStripped);

        provider.withParent(getBlockModelPath(variant, "_hanging"), resource("block/base_" + getBaseName() + "_hanging"))
                .texture("texture", variant.textureStripped)
                .texture("particle", variant.textureStripped);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<ShopSignBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant), resource(getBlockModelPath(variant)));
    }

    @Override
    public void buildBlockState(BPBlockStateProvider provider, RegistryObject<ShopSignBlock> block, WoodVariant variant) {
        provider.getVariantBuilder(block.get())
                .forAllStatesExcept(state -> ConfiguredModel.builder()
                                .modelFile(state.getValue(ShopSignBlock.TYPE).isHorizontal() ? blockModel(variant + "_shop_sign") : blockModel(variant + "_shop_sign_hanging"))
                                .rotationX(((int) state.getValue(ShopSignBlock.TYPE).toXRot()) % 360)
                                .rotationY(((int) state.getValue(ShopSignBlock.TYPE).toYRot()) % 360)
                                .uvLock(true)
                                .build(),
                        BlockStateProperties.WATERLOGGED
                );
    }

    @Override
    public int getRecipeResultCount(WoodVariant variant) {
        return 2;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoodVariant variant) {
        return List.of(variant.stripped, Items.IRON_INGOT);
    }
}
