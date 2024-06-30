package github.mrh0.buildersaddition2.recipe.carpenter;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.crafting.RecipeInput;

public class SimpleContainerInput extends SimpleContainer implements RecipeInput {

    public SimpleContainerInput(int size) {
        super(size);
    }

    @Override
    public int size() {
        return getContainerSize();
    }
}
