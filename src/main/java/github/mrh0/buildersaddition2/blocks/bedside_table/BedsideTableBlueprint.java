package github.mrh0.buildersaddition2.blocks.bedside_table;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.chair.ChairBlock;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import github.mrh0.buildersaddition2.common.variants.WoolVariant;
import github.mrh0.buildersaddition2.datagen.BA2BlockModelProvider;
import github.mrh0.buildersaddition2.datagen.BA2BlockStateProvider;
import github.mrh0.buildersaddition2.datagen.BA2ItemModelProvider;
import github.mrh0.buildersaddition2.datagen.BA2LootTableProvider;
import github.mrh0.buildersaddition2.state.PillowState;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
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
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class BedsideTableBlueprint extends BlockBlueprint<WoodVariant, BedsideTableBlock> {
    public BedsideTableBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "bedside_table";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return variant.displayName + " Bedside Table";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<BedsideTableBlock> getBlock(WoodVariant variant) {
        return () -> new BedsideTableBlock(BlockBehaviour.Properties.copy(variant.planks));
    }

    @Override
    protected void buildBlockModel(BA2BlockModelProvider provider, RegistryObject<BedsideTableBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant), BA2.get("block/base_" + getBaseName()))
                .texture("planks", variant.texturePlanks)
                .texture("stripped", variant.textureStripped)
                .texture("particle", variant.texturePlanks);
    }

    @Override
    protected void buildItemModel(BA2ItemModelProvider provider, RegistryObject<BedsideTableBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant), BA2.get(getBlockModelPath(variant)));
    }

    @Override
    public void buildBlockState(BA2BlockStateProvider provider, RegistryObject<BedsideTableBlock> block, WoodVariant variant) {
        provider.multipartHorizontalFacing(
                provider.getMultipartBuilder(block.get()),
                model(getBlockModelPath(variant)),
                180,
                false
        );
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
