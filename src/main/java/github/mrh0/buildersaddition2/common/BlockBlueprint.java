package github.mrh0.buildersaddition2.common;

import com.mojang.datafixers.util.Pair;
import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.variants.ResourceVariant;
import github.mrh0.buildersaddition2.datagen.BA2BlockModelProvider;
import github.mrh0.buildersaddition2.datagen.BA2ItemModelProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class BlockBlueprint<V extends ResourceVariant, B extends Block> {

    public static List<BlockBlueprint<?, ?>> ALL_BLUEPRINTS = new ArrayList<>();

    public BlockBlueprint(Iterable<V> variants) {
        ALL_BLUEPRINTS.add(this);
        registerAll(variants);
    }

    private final List<Pair<RegistryObject<B>, V>> registryList = new ArrayList<>();

    public enum LootTableType {
        NONE,
        SELF
    }

    // Frontend
    protected abstract Supplier<B> getBlock(V variant);

    protected abstract String getRegistryName(V variant);

    public ResourceLocation resource(V variant) {
        return BA2.get(getRegistryName(variant));
    }

    protected abstract void buildBlockState(BlockStateProvider bsp, RegistryObject<B> block, V variant);

    public boolean hasItem(V variant) {
        return true;
    }

    public LootTableType getLootTableType(V variant) {
        return LootTableType.SELF;
    }

    protected void onCreativeTab(BuildCreativeModeTabContentsEvent event, RegistryObject<B> block, V variant) {

    }

    protected abstract ModelFile buildBlockModel(BA2BlockModelProvider provider, RegistryObject<B> block, V variant);

    protected abstract ModelFile buildItemModel(BA2ItemModelProvider provider, RegistryObject<B> block, V variant);

    public abstract String getName();

    // Backend
    private RegistryObject<B> registerOne(V variant) {
        var name = getRegistryName(variant);
        var block = BA2.BLOCKS.register(name, getBlock(variant));
        if(hasItem(variant))
            BA2.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public final void registerAll(Iterable<V> iterable) {
        iterable.forEach((variant) -> {
            registryList.add(Pair.of(registerOne(variant), variant));
        });
    }

    public final void generateAllBlockStates(BlockStateProvider bsp) {
        registryList.forEach((pair) -> {
            buildBlockState(bsp, pair.getFirst(), pair.getSecond());
        });
    }

    public final void generateAllBlockModels(BA2BlockModelProvider provider) {
        registryList.forEach((pair) -> {
            buildBlockModel(provider, pair.getFirst(), pair.getSecond());
        });
    }

    public final void generateAllItemModels(BA2ItemModelProvider provider) {
        registryList.forEach((pair) -> {
            buildItemModel(provider, pair.getFirst(), pair.getSecond());
        });
    }

    public final void addAllToCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        registryList.forEach((pair) -> {
            onCreativeTab(event, pair.getFirst(), pair.getSecond());
        });
    }

    public Iterable<Pair<RegistryObject<B>, V>> iterable() {
        return registryList;
    }
}
