package github.mrh0.buildersaddition2.datagen;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.ResourceVariant;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.function.Function;

public class BA2LootTableProvider extends BlockLootSubProvider {
    public BA2LootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        BlockBlueprint.ALL_BLUEPRINTS.forEach(this::generateBlueprint);
    }

    private <V extends ResourceVariant, B extends Block> void generateBlueprint(BlockBlueprint<V, B> blueprint) {
        blueprint.iterable().forEach((pair) -> {
            switch (blueprint.getLootTableType(pair.getSecond())) {
                case NONE: break;
                case SELF: this.dropSelf(pair.getFirst().get()); break;
                case CUSTOM: blueprint.buildLootTable(this, pair.getFirst(), pair.getSecond());
            }
        });
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BA2.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    public void add(Block block, Function<Block, LootTable.Builder> builder) {
        this.add(block, builder.apply(block));
    }

    public void add(Block block, LootTable.Builder builder) {
        this.map.put(block.getLootTable(), builder);
    }
}
