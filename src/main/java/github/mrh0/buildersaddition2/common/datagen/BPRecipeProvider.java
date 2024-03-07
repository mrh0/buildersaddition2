package github.mrh0.buildersaddition2.common.datagen;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import github.mrh0.buildersaddition2.recipe.carpenter.CarpenterRecipeBuilder;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class BPRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public BPRecipeProvider(String modid, PackOutput output) {
        super(output);
    }

    @Override
    protected final void buildRecipes(RecipeOutput out) {
        BlockBlueprint.ALL_BLUEPRINTS.forEach(blueprint -> {
            blueprint.generateAllRecipes(this, out);
        });

        // TODO: Move
        WoodVariant.ALL.forEach(wood -> {
            carpenter(out, wood.getName() + "_stairs_carpentry", wood.stairs, 1, wood.planks);
            carpenter(out, wood.getName() + "_slab_carpentry", wood.slab, 2, wood.planks);
            carpenter(out, wood.getName() + "_fence_carpentry", wood.fence, 1, wood.planks, Items.STICK);
            carpenter(out, wood.getName() + "_door_carpentry", wood.door, 1, wood.planks);
            carpenter(out, wood.getName() + "_trap_door_carpentry", wood.trapDoor, 1, wood.planks);
            //if(wood != WoodVariant.BAMBOO)
            //    carpenter(consumer, wood.name + "_stripped_variant_carpentry", wood.stripped2, 1, wood.stripped);
        });
    }

    public static void carpenter(RecipeOutput out, String name, ItemLike result, int count, ItemLike...required) {
        var builder = CarpenterRecipeBuilder.carpenter(RecipeCategory.DECORATIONS, result, count);
        for(int i = 0; i < Math.min(required.length, 4); i++) builder.requires(required[i]);
        builder.unlockedBy(getHasName(Index.CARPENTER_TABLE.getBlock(0)), has(Index.CARPENTER_TABLE.getBlock(0))).save(out, BA2.get(name));
    }

    public static void carpenter(RecipeOutput out, String name, ItemLike result, int count, List<ItemLike> required) {
        var builder = CarpenterRecipeBuilder.carpenter(RecipeCategory.DECORATIONS, result, count);
        for(int i = 0; i < Math.min(required.size(), 4); i++) builder.requires(required.get(i));
        builder.unlockedBy(getHasName(Index.CARPENTER_TABLE.getBlock(0)), has(Index.CARPENTER_TABLE.getBlock(0))).save(out, BA2.get(name));
    }

    public static InventoryChangeTrigger.TriggerInstance has(MinMaxBounds.Ints bound, ItemLike itemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(itemLike).withCount(bound).build());
    }

    public static InventoryChangeTrigger.TriggerInstance has(ItemLike itemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(itemLike).build());
    }

    public static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> tagKey) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(tagKey).build());
    }

    public static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... predicate) {
        return new InventoryChangeTrigger.TriggerInstance(ContextAwarePredicate.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, predicate);
    }

    public static String getHasName(ItemLike itemLike) {
        return "has_" + getItemName(itemLike);
    }

    public static String getItemName(ItemLike itemLike) {
        return BuiltInRegistries.ITEM.getKey(itemLike.asItem()).getPath();
    }
}
