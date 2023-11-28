package github.mrh0.buildersaddition2.blocks.shelf;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlockEntity;
import github.mrh0.buildersaddition2.ui.GenericStorageMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class ShelfBlockEntity extends AbstractStorageBlockEntity {
    public ShelfBlockEntity(BlockPos pos, BlockState state) {
        super(Index.SHELF_ENTITY_TYPE.get(), pos, state);
    }

    // Less than 9 slots
    @Override
    public int getRows() {
        return 0;
    }

    @Override
    public int getContainerSize() {
        return 6;
    }

    @Override
    protected void playSound(BlockState state, SoundEvent evt) {}

    @Override
    protected Component getDefaultName() {
        return BA2.translatable("container", "shelf");
    }

    public static GenericStorageMenu shelfMenu(int id, Inventory inv, FriendlyByteBuf data) {
        return new GenericStorageMenu(Index.SHELF_MENU.get(), id, inv, inv.player.level(), data.readBlockPos(), 2, 3, 0);
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return new GenericStorageMenu(Index.SHELF_MENU.get(), id, inv, this.getLevel(), this.getBlockPos(), 2, 3, 0);
    }
}
