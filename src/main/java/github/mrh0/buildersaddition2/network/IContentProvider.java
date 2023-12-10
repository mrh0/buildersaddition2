package github.mrh0.buildersaddition2.network;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface IContentProvider {
    void getContentData(int count, NonNullList<ItemStack> stacks);
}
