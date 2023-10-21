package github.mrh0.buildersaddition2.blocks.bench;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.sofa.SofaBlock;
import github.mrh0.buildersaddition2.blocks.stool.StoolBlock;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPLootTableProvider;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import github.mrh0.buildersaddition2.common.variants.WoolVariant;
import github.mrh0.buildersaddition2.state.BenchState;
import github.mrh0.buildersaddition2.state.PillowState;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class BenchBlueprint extends BlockBlueprint<WoodVariant, BenchBlock> {
    public BenchBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    protected Supplier<BenchBlock> getBlock(WoodVariant variant) {
        return () -> new BenchBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    }

    @Override
    public String getBaseName() {
        return "bench";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return variant.displayName + " Bench";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    public void buildLootTable(BPLootTableProvider provider, RegistryObject<BenchBlock> block, WoodVariant variant) {
        var builder = LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(block.get().asItem()))
                        .when(ExplosionCondition.survivesExplosion()));
        for (int i = 0; i < Index.PILLOW.getBlockCount(); i++) {
            var wool = Index.PILLOW.getBlock(i);
            builder.withPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .add(LootItem.lootTableItem(wool.asItem()))
                    .when(ExplosionCondition.survivesExplosion())
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block.get())
                            .setProperties(StatePropertiesPredicate.Builder.properties()
                                    .hasProperty(StoolBlock.PILLOW, PillowState.fromIndex(i)))));
        }
        provider.add(block.get(), builder);
    }

    @Override
    protected void buildBlockState(BPBlockStateProvider bsp, RegistryObject<BenchBlock> block, WoodVariant variant) {
        boolean uvLock = true;
        int offset = 90;
        var base = model(getBlockModelPath(variant));
        var legs = model(getBlockModelPath(variant, "_legs"));

        var bs = bsp.getMultipartBuilder(block.get())
                .part().modelFile(base).rotationY(0).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.Both_X).end()
                .part().modelFile(base).rotationY(0).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.None_X).end()
                .part().modelFile(base).rotationY(90).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.Both_Z).end()
                .part().modelFile(base).rotationY(90).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.None_Z).end()

                .part().modelFile(base).rotationY(90).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.North).end()
                .part().modelFile(base).rotationY(90).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.South).end()
                .part().modelFile(base).rotationY(0).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.East).end()
                .part().modelFile(base).rotationY(0).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.West).end()

                .part().modelFile(legs).rotationY(bsp.getAngleFromDir(Direction.NORTH, offset)).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.North).end()
                .part().modelFile(legs).rotationY(bsp.getAngleFromDir(Direction.EAST, offset)).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.East).end()
                .part().modelFile(legs).rotationY(bsp.getAngleFromDir(Direction.SOUTH, offset)).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.South).end()
                .part().modelFile(legs).rotationY(bsp.getAngleFromDir(Direction.WEST, offset)).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.West).end()

                .part().modelFile(legs).rotationY(bsp.getAngleFromDir(Direction.NORTH, offset)).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.Both_Z).end()
                .part().modelFile(legs).rotationY(bsp.getAngleFromDir(Direction.SOUTH, offset)).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.Both_Z).end()
                .part().modelFile(legs).rotationY(bsp.getAngleFromDir(Direction.EAST, offset)).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.Both_X).end()
                .part().modelFile(legs).rotationY(bsp.getAngleFromDir(Direction.WEST, offset)).uvLock(uvLock).addModel().condition(BenchBlock.SHAPE, BenchState.Both_X).end()
        ;

        for (int i = 0; i < WoolVariant.ALL.size(); i++) {
            var wool = WoolVariant.ALL.get(i);
            bs.part().modelFile(model("block/" + wool.name + "_stool_pillow")).addModel().condition(BenchBlock.PILLOW, PillowState.fromIndex(i)).end();
        }
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<BenchBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant), BA2.get("block/base_" + getBaseName()))
                .texture("planks", variant.texturePlanks)
                .texture("stripped", variant.textureStripped)
                .texture("particle", variant.textureStripped);
        provider.withParent(getBlockModelPath(variant, "_legs"), BA2.get("block/base_" + getBaseName() + "_legs"))
                .texture("planks", variant.texturePlanks)
                .texture("stripped", variant.textureStripped)
                .texture("particle", variant.textureStripped);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<BenchBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant),  BA2.get("block/base_" + getBaseName() + "_inventory"))
                .texture("planks", variant.texturePlanks)
                .texture("stripped", variant.textureStripped);
    }

    @Override
    public int getRecipeResultCount(WoodVariant variant) {
        return 3;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoodVariant variant) {
        return List.of(variant.stripped, variant.planks);
    }
}