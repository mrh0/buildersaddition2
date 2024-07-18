package github.mrh0.buildersaddition2.blocks.chair;

import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import github.mrh0.buildersaddition2.common.variants.WoolVariant;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPLootTableProvider;
import github.mrh0.buildersaddition2.blocks.blockstate.PillowState;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.function.Supplier;

public class ChairBlueprint extends BlockBlueprint<WoodVariant, ChairBlock> {
    public ChairBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "chair";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return variant.getDisplayName() + " Chair";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<ChairBlock> getBlock(WoodVariant variant) {
        return () -> new ChairBlock(BlockBehaviour.Properties.ofFullCopy(variant.planks));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, DeferredHolder<Block, ChairBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant), resource("block/base_" + getBaseName()))
                .texture("planks", variant.texturePlanks)
                .texture("stripped", variant.textureStripped)
                .texture("particle", variant.texturePlanks);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, DeferredHolder<Block, ChairBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant), resource(getBlockModelPath(variant)));
    }

    @Override
    public void buildBlockState(BPBlockStateProvider provider, DeferredHolder<Block, ChairBlock> block, WoodVariant variant) {
        var bs = provider.multipartHorizontalFacing(
                provider.getMultipartBuilder(block.get()),
                model(getBlockModelPath(variant)),
                180,
                false
        );

        for (int i = 0; i < WoolVariant.ALL.size(); i++) {
            var wool = WoolVariant.ALL.get(i);
            bs.part().modelFile(model("block/" + wool.getName() + "_stool_pillow")).addModel().condition(ChairBlock.PILLOW, PillowState.fromIndex(i)).end();
        }
    }

    @Override
    public void buildLootTable(BPLootTableProvider provider, DeferredHolder<Block, ChairBlock> block, WoodVariant variant) {
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
                                    .hasProperty(ChairBlock.PILLOW, PillowState.fromIndex(i)))));
        }
        provider.add(block.get(), builder);
    }

    @Override
    public int getRecipeResultCount(WoodVariant variant) {
        return 1;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoodVariant variant) {
        return List.of(variant.stripped);
    }
}
