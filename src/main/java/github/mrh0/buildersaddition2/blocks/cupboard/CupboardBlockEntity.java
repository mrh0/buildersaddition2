package github.mrh0.buildersaddition2.blocks.cupboard;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlockEntity;
import github.mrh0.buildersaddition2.blocks.bedside_table.BedsideTableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CupboardBlockEntity extends AbstractStorageBlockEntity {
    public CupboardBlockEntity(BlockPos pos, BlockState state) {
        super(Index.CUPBOARD_ENTITY_TYPE.get(), pos, state);
    }

    @Override
    protected Component getDefaultName() {
        return BA2.translatable("container", "cupboard");
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
        Vec3i vector3i = state.getValue(BedsideTableBlock.FACING).getNormal();
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
