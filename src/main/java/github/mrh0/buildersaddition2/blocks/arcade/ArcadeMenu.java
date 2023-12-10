package github.mrh0.buildersaddition2.blocks.arcade;

import github.mrh0.buildersaddition2.Index;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ArcadeMenu extends AbstractContainerMenu {

    protected ArcadeMenu(int id) {
        super(Index.ARCADE_MENU.get(), id);
    }

    @Override
    public boolean stillValid(Player player) {
        return !player.isSpectator();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        return ItemStack.EMPTY;
    }
}
