package github.mrh0.buildersaddition2.common;

import com.mojang.datafixers.util.Pair;
import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.variants.ResourceVariant;
import github.mrh0.buildersaddition2.index.BA2Blocks;
import github.mrh0.buildersaddition2.index.BA2Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class BlockBlueprint<V extends ResourceVariant, B extends Block> {
    private final List<Pair<RegistryObject<B>, V>> registryList = new ArrayList<>();

    public enum LootTableType {
        NONE,
        SELF
    }

    // Frontend
    protected abstract Supplier<B> getBlock(V variant);

    public abstract String getRegistryName(V variant);

    public ResourceLocation resource(V variant) {
        return BA2.get(getRegistryName(variant));
    }

    public abstract void buildBlockState(BlockStateProvider bsp, RegistryObject<B> block, V variant);

    public boolean hasItem(V variant) {
        return true;
    }

    public LootTableType getLootTableType(V variant) {
        return LootTableType.SELF;
    }

    // Backend
    private RegistryObject<B> register(V variant) {
        var name = getRegistryName(variant);
        var block = BA2Blocks.BLOCKS.register(name, getBlock(variant));
        if(hasItem(variant))
            BA2Items.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public final void register(Iterable<V> iterable) {
        iterable.forEach((variant) -> {
            registryList.add(Pair.of(register(variant), variant));
        });
    }

    public final void generateBlockStates(BlockStateProvider bsp) {
        registryList.forEach((pair) -> {
            buildBlockState(bsp, pair.getFirst(), pair.getSecond());
        });
    }

    public Iterable<Pair<RegistryObject<B>, V>> iterable() {
        return registryList;
    }
}
