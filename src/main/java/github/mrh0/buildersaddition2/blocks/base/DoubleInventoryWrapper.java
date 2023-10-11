package github.mrh0.buildersaddition2.blocks.base;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class DoubleInventoryWrapper extends NonNullList<ItemStack> {
    NonNullList<ItemStack> a, b;
    public DoubleInventoryWrapper(NonNullList<ItemStack> a, NonNullList<ItemStack> b) {
        super(List.of(), ItemStack.EMPTY);
        this.a = a;
        this.b = b;
    }

    public void transferFrom(NonNullList<ItemStack> full) {
        for(int i = 0; i < full.size(); i++) {
            getListForIndex(i).set(getWrappedIndex(i), full.get(i));
        }
    }

    public NonNullList<ItemStack> getListForIndex(int index) {
        return index >= a.size() ? b : a;
    }

    public int getWrappedIndex(int index) {
        return index >= a.size() ? index - a.size() : index;
    }

    @Nonnull
    public ItemStack get(int index) {
        return getListForIndex(index).get(getWrappedIndex(index));
    }

    public ItemStack set(int index, ItemStack stack) {
        Validate.notNull(stack);
        return getListForIndex(index).set(getWrappedIndex(index), stack);
    }

    public void add(int index, ItemStack stack) {
        Validate.notNull(stack);
        getListForIndex(index).add(getWrappedIndex(index), stack);
    }

    public ItemStack remove(int index) {
        return getListForIndex(index).remove(getWrappedIndex(index));
    }

    public int size() {
        return a.size() + b.size();
    }

    public void clear() {
        for(int i = 0; i < this.size(); ++i) {
            this.set(i, ItemStack.EMPTY);
        }
    }
}
