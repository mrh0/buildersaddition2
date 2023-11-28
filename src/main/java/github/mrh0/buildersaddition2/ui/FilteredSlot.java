package github.mrh0.buildersaddition2.ui;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Function;

public class FilteredSlot extends Slot {
    private final Function<ItemStack, Boolean> filter;

    public FilteredSlot(Container container, int index, int x, int y, Function<ItemStack, Boolean> filter) {
        super(container, index, x, y);
        this.filter = filter;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return filter.apply(stack);
    }

    public static final Function<ItemStack, Boolean> ALLOW_ALL = (stack) -> true;
}
