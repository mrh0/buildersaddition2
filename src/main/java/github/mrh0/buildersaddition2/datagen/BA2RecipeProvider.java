package github.mrh0.buildersaddition2.datagen;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import github.mrh0.buildersaddition2.datagen.builder.CarpenterRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class BA2RecipeProvider extends RecipeProvider implements IConditionBuilder {
    public BA2RecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        BlockBlueprint.ALL_BLUEPRINTS.forEach(blueprint -> {
            blueprint.generateAllRecipes(this, consumer);
        });
        //carpenter(consumer, "test_recipe", Index.STOOL.getBlock(0).asItem(), 1, Items.OAK_PLANKS);

        WoodVariant.ALL.forEach(wood -> {
            carpenter(consumer, wood.name + "_stairs_carpentry", wood.stairs, 1, wood.planks);
            carpenter(consumer, wood.name + "_slab_carpentry", wood.slab, 2, wood.planks);
            //if(wood != WoodVariant.BAMBOO)
            //    carpenter(consumer, wood.name + "_stripped_variant_carpentry", wood.stripped2, 1, wood.stripped);
        });
    }

    public static void carpenter(Consumer<FinishedRecipe> consumer, String name, ItemLike result, int count, ItemLike...required) {
        var builder = CarpenterRecipeBuilder.carpenter(RecipeCategory.DECORATIONS, result, count);
        for(int i = 0; i < Math.min(required.length, 4); i++) {
            builder.requires(required[i]);
        }
        builder.unlockedBy(getHasName(Index.CARPENTER_TABLE.get()), has(Index.CARPENTER_TABLE.get())).save(consumer, BA2.get(name));
    }

    public static void carpenter(Consumer<FinishedRecipe> consumer, String name, ItemLike result, int count, List<ItemLike> required) {
        var builder = CarpenterRecipeBuilder.carpenter(RecipeCategory.DECORATIONS, result, count);
        for(int i = 0; i < Math.min(required.size(), 4); i++) {
            builder.requires(required.get(i));
        }
        builder.unlockedBy(getHasName(Index.CARPENTER_TABLE.get()), has(Index.CARPENTER_TABLE.get())).save(consumer, BA2.get(name));
    }
}
