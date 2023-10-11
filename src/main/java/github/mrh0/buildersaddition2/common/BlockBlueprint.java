package github.mrh0.buildersaddition2.common;

import com.mojang.datafixers.util.Pair;
import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.variants.ResourceVariant;
import github.mrh0.buildersaddition2.datagen.*;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class BlockBlueprint<V extends ResourceVariant, B extends Block> {

    public static List<Pair<String, String>> translationKeyPairs = new ArrayList<>();
    public static List<BlockBlueprint<? extends ResourceVariant, ? extends Block>> ALL_BLUEPRINTS = new ArrayList<>();

    public BlockBlueprint(Iterable<V> variants) {
        ALL_BLUEPRINTS.add(this);
        registerAll(variants);
    }

    private final List<Pair<RegistryObject<B>, V>> registryList = new ArrayList<>();

    public enum LootTableProviderType {
        NONE,
        SELF,
        CUSTOM
    }

    // Frontend
    protected abstract Supplier<B> getBlock(V variant);

    protected String getRegistryName(V variant) {
        return variant.toString() + "_" + getBaseName();
    }

    public ResourceLocation resource(V variant) {
        return BA2.get(getRegistryName(variant));
    }

    protected abstract void buildBlockState(BA2BlockStateProvider bsp, RegistryObject<B> block, V variant);

    public boolean hasItem(V variant) {
        return true;
    }

    public LootTableProviderType getLootTableType(V variant) {
        return LootTableProviderType.SELF;
    }

    protected void onCreativeTab(BuildCreativeModeTabContentsEvent event, RegistryObject<B> block, V variant) {
        if(event.getTabKey() == BA2.EXAMPLE_TAB.getKey())
            event.accept(block.get());
    }

    protected abstract void buildBlockModel(BA2BlockModelProvider provider, RegistryObject<B> block, V variant);

    protected abstract void buildItemModel(BA2ItemModelProvider provider, RegistryObject<B> block, V variant);

    public abstract String getBaseName();

    public String getBlockModelPath(V variant) {
        return  "block/" + getRegistryName(variant);
    }

    public String getBlockModelPath(V variant, String ext) {
        return  "block/" + getRegistryName(variant) + ext;
    }

    public List<TagKey<Block>> addBlockTags(V variant) {
        return List.of();
    }

    public String getLangKey(V variant) {
        return "block." + BA2.MODID + "." + getRegistryName(variant);
    }

    public abstract String getLangName(V variant);

    public void buildLootTable(BA2LootTableProvider provider, RegistryObject<B> block, V variant) {

    }

    public List<ItemLike> getRecipeRequired(V variant) {
        return List.of();
    }

    public int getRecipeResultCount(V variant) {
        return 1;
    }

    public void buildRecipe(BA2RecipeProvider provider, Consumer<FinishedRecipe> consumer, RegistryObject<B> block, V variant) {
        BA2RecipeProvider.carpenter(consumer, getRegistryName(variant), block.get().asItem(), getRecipeResultCount(variant), getRecipeRequired(variant));
    }

    // Backend
    private RegistryObject<B> registerOne(V variant) {
        var name = getRegistryName(variant);
        var block = BA2.BLOCKS.register(name, getBlock(variant));
        if(hasItem(variant))
            BA2.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        translationKeyPairs.add(Pair.of(getLangKey(variant), getLangName(variant)));
        return block;
    }

    public final void registerAll(Iterable<V> iterable) {
        iterable.forEach((variant) -> {
            registryList.add(Pair.of(registerOne(variant), variant));
        });
    }

    public final void generateAllBlockStates(BA2BlockStateProvider bsp) {
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

    public final void generateAllRecipes(BA2RecipeProvider provider, Consumer<FinishedRecipe> consumer) {
        registryList.forEach((pair) -> {
            buildRecipe(provider, consumer, pair.getFirst(), pair.getSecond());
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

    public final Map<TagKey<Block>, List<Block>> getTagPairs() {
        Map<TagKey<Block>, List<Block>> map = new HashMap<>();
        registryList.forEach((pair) -> {
            addBlockTags(pair.getSecond()).forEach(key -> {
                if(map.containsKey(key)) {
                    map.getOrDefault(key, new ArrayList<>()).add(pair.getFirst().get());
                }
                else {
                    var list = new ArrayList<Block>();
                    list.add(pair.getFirst().get());
                    map.putIfAbsent(key, list);
                }
            });
        });
        return map;
    }

    public final ModelFile model(String model) {
        return new ModelFile.UncheckedModelFile(BA2.get(model));
    }

    public final ModelFile blockModel(String model) {
        return new ModelFile.UncheckedModelFile(BA2.get("block/" + model));
    }

    public final Block getBlock(int index) {
        return registryList.get(index).getFirst().get();
    }

    public final int getBlockCount() {
        return registryList.size();
    }

    public final Block[] getAllBlocks() {
        Block[] blocks = new Block[registryList.size()];
        for (int i = 0; i < blocks.length; i++) {
            blocks[i] = getBlock(i);
        }
        return blocks;
    }
}
