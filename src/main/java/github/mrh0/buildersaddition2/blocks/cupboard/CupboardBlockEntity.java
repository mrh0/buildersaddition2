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
        super.playSound(state, evt);
    }

    public boolean isDouble() {
        return !this.getBlockState().getValue(CupboardBlock.VARIANT).isSingle();
    }

    public boolean isTop() {
        return !this.getBlockState().getValue(CupboardBlock.VARIANT).isTop();
    }

    public IItemHandlerModifiable createHandler() {
        BlockState state = this.getBlockState();
        if (!(state.getBlock() instanceof CupboardBlock)) return new InvWrapper(this);
        Container inv = CupboardBlock.getContainer((CupboardBlock) state.getBlock(), state, getLevel(), getBlockPos(), true);
        return new InvWrapper(inv == null ? this : inv);
    }

    @Override
    public int getComparatorOverride() {
        return AbstractContainerMenu.getRedstoneSignalFromContainer(this);
    }
}
