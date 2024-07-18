package github.mrh0.buildersaddition2.common.datagen;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.ResourceVariant;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;
import java.util.function.Function;

public class BPLootTableProvider extends BlockLootSubProvider {
    public BPLootTableProvider(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        BlockBlueprint.ALL_BLUEPRINTS.forEach(this::generateBlueprint);
    }

    private <V extends ResourceVariant, B extends Block> void generateBlueprint(BlockBlueprint<V, B> blueprint) {
        blueprint.iterable().forEach((pair) -> {
            blueprint.buildLootTable(this, pair.getFirst(), pair.getSecond());
        });
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BA2.BLOCKS.getEntries().stream().map(e -> (Block)e.get())::iterator;//BlockBlueprint.getAllBlueprintBlocks();
    }

    public void add(Block block, Function<Block, LootTable.Builder> builder) {
        this.add(block, builder.apply(block));
    }

    public void add(Block block, LootTable.Builder builder) {
        this.map.put(block.getLootTable(), builder);
    }

    public void dropOther(Block block, ItemLike itemLike) {
        this.add(block, this.createSingleItemTable(itemLike));
    }

    protected void dropWhenSilkTouch(Block block) {
        this.otherWhenSilkTouch(block, block);
    }

    public void dropSelf(Block block) {
        this.dropOther(block, block);
    }
}
