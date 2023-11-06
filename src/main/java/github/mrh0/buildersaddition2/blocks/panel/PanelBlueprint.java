package github.mrh0.buildersaddition2.blocks.panel;

import github.mrh0.buildersaddition2.blocks.cupboard.CupboardBlock;
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

public class PanelBlueprint extends BlockBlueprint<WoodVariant, PanelBlock> {
    public PanelBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "panel";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return variant.getDisplayName() + " Panel";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<PanelBlock> getBlock(WoodVariant variant) {
        return () -> new PanelBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<PanelBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant), resource("block/base_" + getBaseName()))
                .texture("texture", variant.texturePlanks)
                .texture("particle", variant.texturePlanks);

        provider.withParent(getBlockModelPath(variant) + "_double", resource("block/base_" + getBaseName() + "_double"))
                .texture("all", variant.texturePlanks)
                .texture("particle", variant.texturePlanks);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<PanelBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant), resource(getBlockModelPath(variant)));
    }

    @Override
    public void buildBlockState(BPBlockStateProvider provider, RegistryObject<PanelBlock> block, WoodVariant variant) {
        Function<BlockState, ModelFile> modelFunc = (state) -> switch(state.getValue(PanelBlock.SHAPE)) {
                case DOUBLE_X, DOUBLE_Z -> blockModel(variant + "_panel_double");
                default -> blockModel(variant + "_panel");
        };
        provider.getVariantBuilder(block.get())
                .forAllStatesExcept(state -> ConfiguredModel.builder()
                        .modelFile(modelFunc.apply(state))
                                .rotationY(((int) state.getValue(PanelBlock.SHAPE).toYRot() + 180) % 360)
                                .uvLock(true)
                                .build(),
                        BlockStateProperties.WATERLOGGED
                );
    }

    @Override
    public void buildLootTable(BPLootTableProvider provider, RegistryObject<PanelBlock> block, WoodVariant variant) {
        var builder = LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(block.get().asItem()))
                        .when(ExplosionCondition.survivesExplosion()));

        builder.withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block.get().asItem()))
                .when(ExplosionCondition.survivesExplosion())
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(PanelBlock.SHAPE, PanelState.DOUBLE_X))));

        builder.withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(block.get().asItem()))
                .when(ExplosionCondition.survivesExplosion())
                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                .hasProperty(PanelBlock.SHAPE, PanelState.DOUBLE_Z))));

        provider.add(block.get(), builder);
    }

    @Override
    public int getRecipeResultCount(WoodVariant variant) {
        return 2;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoodVariant variant) {
        return List.of(variant.planks);
    }
}