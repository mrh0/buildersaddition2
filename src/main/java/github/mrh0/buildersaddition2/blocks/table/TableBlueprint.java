package github.mrh0.buildersaddition2.blocks.table;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import github.mrh0.buildersaddition2.datagen.BA2BlockModelProvider;
import github.mrh0.buildersaddition2.datagen.BA2BlockStateProvider;
import github.mrh0.buildersaddition2.datagen.BA2ItemModelProvider;
import github.mrh0.buildersaddition2.datagen.BA2LootTableProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class TableBlueprint extends BlockBlueprint<WoodVariant, TableBlock> {
    public TableBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "table";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return variant.displayName + " Table";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<TableBlock> getBlock(WoodVariant variant) {
        return () -> new TableBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    }

    @Override
    protected void buildBlockModel(BA2BlockModelProvider provider, RegistryObject<TableBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant), BA2.get("block/base_" + getBaseName()))
                .texture("tex", variant.texturePlanks)
                .texture("leg", variant.textureStripped)
                .texture("particle", variant.texturePlanks);
        provider.withParent(getBlockModelPath(variant) + "_leg", BA2.get("block/base_" + getBaseName() + "_leg"))
                .texture("tex", variant.texturePlanks)
                .texture("leg", variant.textureStripped)
                .texture("particle", variant.texturePlanks);
    }

    @Override
    protected void buildItemModel(BA2ItemModelProvider provider, RegistryObject<TableBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant), BA2.get("block/base_" + getBaseName() + "_full"))
                .texture("tex", variant.texturePlanks)
                .texture("leg", variant.textureStripped)
                .texture("particle", variant.texturePlanks);
    }

    @Override
    public void buildBlockState(BA2BlockStateProvider provider, RegistryObject<TableBlock> block, WoodVariant variant) {
        provider.getMultipartBuilder(block.get())
                .part().modelFile(model(getBlockModelPath(variant))).addModel().end()
                .part().modelFile(model(getBlockModelPath(variant) + "_leg")).addModel().condition(TableBlock.NE, true).end()
                .part().modelFile(model(getBlockModelPath(variant) + "_leg")).rotationY(270).addModel().condition(TableBlock.NW, true).end()
                .part().modelFile(model(getBlockModelPath(variant) + "_leg")).rotationY(90).addModel().condition(TableBlock.SE, true).end()
                .part().modelFile(model(getBlockModelPath(variant) + "_leg")).rotationY(180).addModel().condition(TableBlock.SW, true).end();
    }

    @Override
    public int getRecipeResultCount(WoodVariant variant) {
        return 2;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoodVariant variant) {
        return List.of(variant.slab, variant.stripped);
    }
}
