package github.mrh0.buildersaddition2.blocks.post;

import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPLootTableProvider;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class PostBlueprint extends BlockBlueprint<WoodVariant, PostBlock> {
    public PostBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "post";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return variant.getDisplayName() + " Post";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<PostBlock> getBlock(WoodVariant variant) {
        return () -> new PostBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<PostBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant) + "_xyz", resource("block/base_" + getBaseName() + "_xyz"))
                .texture("0", variant.textureStripped)
                .texture("1", variant.textureStrippedTop)
                .texture("particle", variant.textureStripped);

        provider.withParent(getBlockModelPath(variant) + "_y", resource("block/base_" + getBaseName() + "_y"))
                .texture("0", variant.textureStripped)
                .texture("1", variant.textureStrippedTop)
                .texture("particle", variant.textureStripped);

        provider.withParent(getBlockModelPath(variant) + "_yz", resource("block/base_" + getBaseName() + "_yz"))
                .texture("0", variant.textureStripped)
                .texture("1", variant.textureStrippedTop)
                .texture("particle", variant.textureStripped);

        provider.withParent(getBlockModelPath(variant) + "_xz", resource("block/base_" + getBaseName() + "_xz"))
                .texture("0", variant.textureStripped)
                .texture("1", variant.textureStrippedTop)
                .texture("particle", variant.textureStripped);

        provider.withParent(getBlockModelPath(variant) + "_z", resource("block/base_" + getBaseName() + "_z"))
                .texture("0", variant.textureStripped)
                .texture("1", variant.textureStrippedTop)
                .texture("particle", variant.textureStripped);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<PostBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant), resource(getBlockModelPath(variant, "_y")));
    }

    @Override
    public void buildBlockState(BPBlockStateProvider provider, RegistryObject<PostBlock> block, WoodVariant variant) {
        Function<BlockState, ModelFile> modelFunc = (state) -> {
            boolean x = state.getValue(PostBlock.AXIS_X);
            boolean y = state.getValue(PostBlock.AXIS_Y);
            boolean z = state.getValue(PostBlock.AXIS_Z);
            if (x && y && z) return blockModel(variant + "_" + getBaseName() + "_xyz");
            else if (y && (z || x)) return blockModel(variant + "_" + getBaseName() + "_yz");
            else if (z && x) return blockModel(variant + "_" + getBaseName() + "_xz");
            else if (z || x) return blockModel(variant + "_" + getBaseName() + "_z");
            else return blockModel(variant + "_" + getBaseName() + "_y");
        };
        provider.getVariantBuilder(block.get())
            .forAllStatesExcept(state -> ConfiguredModel.builder()
                    .modelFile(modelFunc.apply(state))
                    .rotationY(state.getValue(PostBlock.AXIS_X) && ! state.getValue(PostBlock.AXIS_Z) ? 90 : 0)
                    .uvLock(false)
                    .build(),
                BlockStateProperties.WATERLOGGED
            );
    }

    @Override
    public void buildLootTable(BPLootTableProvider provider, RegistryObject<PostBlock> block, WoodVariant variant) {
        var builder = LootTable.lootTable()
                .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block.get().asItem()))
                .when(ExplosionCondition.survivesExplosion())
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(PostBlock.AXIS_X, true))));

        builder.withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block.get().asItem()))
                .when(ExplosionCondition.survivesExplosion())
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(PostBlock.AXIS_Y, true))));

        builder.withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block.get().asItem()))
                .when(ExplosionCondition.survivesExplosion())
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(PostBlock.AXIS_Z, true))));

        provider.add(block.get(), builder);
    }

    @Override
    public int getRecipeResultCount(WoodVariant variant) {
        return 8;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoodVariant variant) {
        return List.of(variant.stripped);
    }
}
