package github.mrh0.buildersaddition2.blocks.support_beam;

import github.mrh0.buildersaddition2.blocks.panel.PanelBlock;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPLootTableProvider;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import github.mrh0.buildersaddition2.state.PanelState;
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

public class SupportBeamBlueprint extends BlockBlueprint<WoodVariant, SupportBeamBlock> {
    public SupportBeamBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "support_beam";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return variant.getDisplayName() + " Support Beam";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<SupportBeamBlock> getBlock(WoodVariant variant) {
        return () -> new SupportBeamBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<SupportBeamBlock> block, WoodVariant variant) {
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
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<SupportBeamBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant), resource(getBlockModelPath(variant)));
    }

    @Override
    public void buildBlockState(BPBlockStateProvider provider, RegistryObject<SupportBeamBlock> block, WoodVariant variant) {
        Function<BlockState, ModelFile> modelFunc = (state) -> {
            boolean x = state.getValue(SupportBeamBlock.AXIS_X);
            boolean y = state.getValue(SupportBeamBlock.AXIS_Y);
            boolean z = state.getValue(SupportBeamBlock.AXIS_Z);
            if (x && y && z) return blockModel(variant + "_xyz");
            else if (y && (z || x)) return blockModel(variant + "_yz");
            else if (z && x) return blockModel(variant + "_xz");
            else if (z || x) return blockModel(variant + "_z");
            else return blockModel(variant + "_y");
        };
        Function<BlockState, Integer> rotFunc = (state) -> {
            boolean x = state.getValue(SupportBeamBlock.AXIS_X);
            boolean y = state.getValue(SupportBeamBlock.AXIS_Y);
            boolean z = state.getValue(SupportBeamBlock.AXIS_Z);
            if (x && y && z) return 0;
            else if (x) return 90;
            return 0;
        };
        provider.getVariantBuilder(block.get())
                .forAllStatesExcept(state -> ConfiguredModel.builder()
                                .modelFile(modelFunc.apply(state))
                                .rotationY(state.getValue(SupportBeamBlock.AXIS_X) && ! state.getValue(SupportBeamBlock.AXIS_Z) ? 90 : 0)
                                .uvLock(false)
                                .build(),
                        BlockStateProperties.WATERLOGGED
                );
    }

    @Override
    public void buildLootTable(BPLootTableProvider provider, RegistryObject<SupportBeamBlock> block, WoodVariant variant) {
        var builder = LootTable.lootTable()
                .withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block.get().asItem()))
                .when(ExplosionCondition.survivesExplosion())
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(SupportBeamBlock.AXIS_X, true))));

        builder.withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block.get().asItem()))
                .when(ExplosionCondition.survivesExplosion())
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(SupportBeamBlock.AXIS_Y, true))));

        builder.withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block.get().asItem()))
                .when(ExplosionCondition.survivesExplosion())
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(SupportBeamBlock.AXIS_Z, true))));

        provider.add(block.get(), builder);
    }

    @Override
    public int getRecipeResultCount(WoodVariant variant) {
        return 2;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoodVariant variant) {
        return List.of(variant.stripped);
    }
}
