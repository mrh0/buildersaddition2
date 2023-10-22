package github.mrh0.buildersaddition2.ui;

import github.mrh0.buildersaddition2.Index;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GenericStorageMenu extends AbstractContainerMenu {
    private final Container container;
    private final int containerRows;
    private final int containerColumns;

    public GenericStorageMenu(MenuType<?> type, int id, Inventory inv, Level level, BlockPos pos, int rows, int columns) {
        super(type, id);
        //checkContainerSize(container, rows * columns);
        this.container = (Container) level.getBlockEntity(pos);
        this.containerRows = rows;
        this.containerColumns = columns;
        container.startOpen(inv.player);
        int slotSize = 18;
        int i = (this.containerRows - 4) * slotSize;

        for(int j = 0; j < this.containerRows; ++j) {
            for(int k = 0; k < containerColumns; ++k) {
                this.addSlot(new Slot(
                        container,
                        k + j * containerColumns,
                        8 + k * slotSize + ((9-containerColumns)*slotSize/2),
                        slotSize + j * slotSize
                ));
            }
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inv, j1 + l * 9 + 9, 8 + j1 * slotSize, 103 + l * slotSize + i));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inv, i1, 8 + i1 * slotSize, 161 + i));
        }
    }

    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < this.containerRows * this.containerColumns) {
                if (!this.moveItemStackTo(itemstack1, this.containerRows * this.containerColumns, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.containerRows * this.containerColumns, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    public Container getContainer() {
        return this.container;
    }

    public int getRowCount() {
        return this.containerRows;
    }

    public int getColumnCount() {
        return this.containerColumns;
    }
}
