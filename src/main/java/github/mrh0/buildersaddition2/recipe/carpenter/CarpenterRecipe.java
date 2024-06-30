package github.mrh0.buildersaddition2.recipe.carpenter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.bedside_table.BedsideTableBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class CarpenterRecipe implements Recipe<CraftingInput> {
    public static final String RECIPE_TYPE_NAME = "carpenter";
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;

    public CarpenterRecipe(String group, NonNullList<Ingredient> inputItems, ItemStack output) {
        this.inputItems = inputItems;
        this.output = output;
    }

    public CraftingBookCategory category() {
        return CraftingBookCategory.BUILDING;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return inputItems;
    }

    @Override
    public boolean matches(CraftingInput container, Level pLevel) {
        for (int i = 0; i < inputItems.size(); i++) {
            if(inputItems.get(i).isEmpty()) break;
            boolean match = false;
            for(int j = 0; j < 4; j++) {
                if(inputItems.get(i).test(container.getItem(j))) match = true;
            }
            if(!match) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(CraftingInput container, HolderLookup.Provider registry) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider p_331967_) {
        return output;
    }

    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return Index.CARPENTER_SERIALIZER.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return Index.CARPENTER_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<CarpenterRecipe> {
        private static final MapCodec<CarpenterRecipe> CODEC = RecordCodecBuilder.mapCodec(
                p_327212_ -> p_327212_.group(
                                Codec.STRING.optionalFieldOf("group", "").forGetter(Recipe::getGroup),
                                CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(CarpenterRecipe::category),
                                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(p_300770_ -> p_300770_.output),
                                Ingredient.CODEC_NONEMPTY
                                        .listOf()
                                        .fieldOf("ingredients")
                                        .flatXmap(
                                                p_297969_ -> {
                                                    Ingredient[] aingredient = p_297969_.stream().filter(p_298915_ -> !p_298915_.isEmpty()).toArray(Ingredient[]::new);
                                                    if (aingredient.length == 0) {
                                                        return DataResult.error(() -> "No ingredients for shapeless recipe");
                                                    } else {
                                                        return aingredient.length > 4
                                                                ? DataResult.error(() -> "Too many ingredients for shapeless recipe")
                                                                : DataResult.success(NonNullList.of(Ingredient.EMPTY, aingredient));
                                                    }
                                                },
                                                DataResult::success
                                        )
                                        .forGetter(recipe -> recipe.inputItems)
                        )
                        .apply(p_327212_, (group, category, result, ingredients) -> new CarpenterRecipe(group, ingredients, result))
        );
        public static final StreamCodec<RegistryFriendlyByteBuf, CarpenterRecipe> STREAM_CODEC = StreamCodec.of(
                CarpenterRecipe.Serializer::toNetwork, CarpenterRecipe.Serializer::fromNetwork
        );

        @Override
        public MapCodec<CarpenterRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CarpenterRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static CarpenterRecipe fromNetwork(RegistryFriendlyByteBuf p_335962_) {
            String s = p_335962_.readUtf();
            //CraftingBookCategory craftingbookcategory = p_335962_.readEnum(CraftingBookCategory.class);
            int i = p_335962_.readVarInt();
            NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);
            nonnulllist.replaceAll(p_327214_ -> Ingredient.CONTENTS_STREAM_CODEC.decode(p_335962_));
            ItemStack itemstack = ItemStack.STREAM_CODEC.decode(p_335962_);
            return new CarpenterRecipe(s, nonnulllist, itemstack);
        }

        private static void toNetwork(RegistryFriendlyByteBuf p_329239_, CarpenterRecipe p_44282_) {
            p_329239_.writeUtf(p_44282_.getGroup());
            p_329239_.writeEnum(p_44282_.category());
            p_329239_.writeVarInt(p_44282_.inputItems.size());

            for (Ingredient ingredient : p_44282_.inputItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(p_329239_, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(p_329239_, p_44282_.output);
        }
    }
}
