package github.mrh0.buildersaddition2.blocks.cupboard;

import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CupboardBlockEntity extends AbstractStorageBlockEntity {
    public CupboardBlockEntity(BlockPos pos, BlockState state) {
        super(Index.CUPBOARD_ENTITY_TYPE.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return Component.empty();
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return ChestMenu.threeRows(id, inv, this);
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    protected void playSound(BlockState state, SoundEvent evt) {
        if(state.getValue(CupboardBlock.VARIANT).isBottom()) return;
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

    private LazyOptional<IItemHandlerModifiable> storageHandler;
    @Override
    public void setBlockState(BlockState p_155251_) {
        super.setBlockState(p_155251_);
        if (this.storageHandler != null) {
            net.minecraftforge.common.util.LazyOptional<?> oldHandler = this.storageHandler;
            this.storageHandler = null;
            oldHandler.invalidate();
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> cap, Direction side) {
        if (!this.remove && cap == net.minecraftforge.common.capabilities.ForgeCapabilities.ITEM_HANDLER) {
            if (this.storageHandler == null)
                this.storageHandler = LazyOptional.of(this::createHandler);
            return this.storageHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    private IItemHandlerModifiable createHandler() {
        BlockState state = this.getBlockState();
        if (!(state.getBlock() instanceof CupboardBlock)) return new InvWrapper(this);
        Container inv = CupboardBlock.getContainer((CupboardBlock) state.getBlock(), state, getLevel(), getBlockPos(), true);
        return new InvWrapper(inv == null ? this : inv);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (storageHandler != null) {
            storageHandler.invalidate();
            storageHandler = null;
        }
    }
}
