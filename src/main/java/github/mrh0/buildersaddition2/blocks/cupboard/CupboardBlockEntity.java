package github.mrh0.buildersaddition2.blocks.cupboard;

import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlockEntity;
import github.mrh0.buildersaddition2.blocks.base.DoubleInventoryWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CupboardBlockEntity extends AbstractStorageBlockEntity {
    public CupboardBlockEntity(BlockPos pos, BlockState state) {
        super(Index.CUPBOARD_ENTITY_TYPE.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.empty();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        if(isDouble()) {
            BlockEntity be = getLevel().getBlockEntity(isTop() ? getBlockPos().below() : getBlockPos().above());
            if(!(be instanceof CupboardBlockEntity cbe)) return super.getItems();
            return new DoubleInventoryWrapper(getItems(), cbe.getItems());
        }
        return super.getItems();
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        if(isDouble()) {
            BlockEntity be = getLevel().getBlockEntity(isTop() ? getBlockPos().below() : getBlockPos().above());
            if(!(be instanceof CupboardBlockEntity cbe)) return;
            new DoubleInventoryWrapper(getItems(), cbe.getItems());
        }
        super.setItems(items);
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return isDouble() ? ChestMenu.sixRows(id, inv, this) : ChestMenu.threeRows(id, inv, this);
    }

    @Override
    public int getRows() {
        return isDouble() ? 6 : 3;
    }

    @Override
    protected void playSound(BlockState state, SoundEvent evt) {
        Vec3i vector3i = state.getValue(CupboardBlock.FACING).getNormal();
        double d0 = (double) this.getBlockPos().getX() + 0.5D + (double) vector3i.getX() / 2.0D;
        double d1 = (double) this.getBlockPos().getY() + 0.5D + (double) vector3i.getY() / 2.0D;
        double d2 = (double) this.getBlockPos().getZ() + 0.5D + (double) vector3i.getZ() / 2.0D;
        this.level.playSound((Player) null, d0, d1, d2, evt, SoundSource.BLOCKS, 0.5F,
                this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    public boolean isDouble() {
        return !this.getBlockState().getValue(CupboardBlock.VARIANT).isSingle();
    }

    public boolean isTop() {
        return !this.getBlockState().getValue(CupboardBlock.VARIANT).isTop();
    }
}
