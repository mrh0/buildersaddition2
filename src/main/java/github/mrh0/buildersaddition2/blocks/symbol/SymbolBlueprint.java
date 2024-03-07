package github.mrh0.buildersaddition2.blocks.symbol;
/*
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPLootTableProvider;
import github.mrh0.buildersaddition2.common.variants.SingleVariant;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class SymbolBlueprint extends BlockBlueprint<SingleVariant, SymbolBlock> {
    public SymbolBlueprint(Iterable<SingleVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "symbol";
    }

    @Override
    public String getLangName(SingleVariant variant) {
        return variant.getDisplayName() + " Symbol";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(SingleVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<SymbolBlock> getBlock(SingleVariant variant) {
        return () -> new SymbolBlock(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<SymbolBlock> block, SingleVariant variant) {

    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<SymbolBlock> block, SingleVariant variant) {
        provider.withParent(getRegistryName(variant), resource(getBlockModelPath(variant)));
    }

    @Override
    public void buildBlockState(BPBlockStateProvider provider, RegistryObject<SymbolBlock> block, SingleVariant variant) {

    }

    @Override
    public void buildLootTable(BPLootTableProvider provider, RegistryObject<SymbolBlock> block, SingleVariant variant) {
        provider.dropSelf(block.get());
    }

    @Override
    public int getRecipeResultCount(SingleVariant variant) {
        return 1;
    }

    @Override
    public List<ItemLike> getRecipeRequired(SingleVariant variant) {
        return List.of(Items.IRON_INGOT);
    }

    @Override
    protected boolean addToCreativeTab(RegistryObject<SymbolBlock> block, SingleVariant variant) {
        return false; // Dev
    }
}
*/
