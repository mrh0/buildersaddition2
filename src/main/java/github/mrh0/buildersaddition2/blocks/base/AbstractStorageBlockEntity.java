package github.mrh0.buildersaddition2.blocks.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;

public abstract class AbstractStorageBlockEntity extends RandomizableContainerBlockEntity implements IComparatorOverride {
    protected NonNullList<ItemStack> inventory = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);

    public AbstractStorageBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public abstract int getRows();

    protected abstract void playSound(BlockState state, SoundEvent evt);

    protected final void playDefaultSound(SoundEvent evt, Vec3i vector3i) {
        double d0 = (double) this.getBlockPos().getX() + 0.5D + (double) vector3i.getX() / 2.0D;
        double d1 = (double) this.getBlockPos().getY() + 0.5D + (double) vector3i.getY() / 2.0D;
        double d2 = (double) this.getBlockPos().getZ() + 0.5D + (double) vector3i.getZ() / 2.0D;
        this.level.playSound((Player) null, d0, d1, d2, evt, SoundSource.BLOCKS, 0.5F,
                this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return inventory;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        inventory = items;
    }

    @Override
    public int getContainerSize() {
        return getRows() * 9;
    }

    protected IItemHandlerModifiable createHandler() {
        return new InvWrapper(this);
    }

    private static MenuType getMenuType(int rows) {
        return switch(rows) {
            case 1 -> MenuType.GENERIC_9x1;
            case 2 -> MenuType.GENERIC_9x2;
            case 4 -> MenuType.GENERIC_9x4;
            case 5 -> MenuType.GENERIC_9x5;
            case 6 -> MenuType.GENERIC_9x6;
            default -> MenuType.GENERIC_9x3;
        };
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return new ChestMenu(getMenuType(getRows()), id, inv, this, getRows());
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.inventory = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(nbt))
            ContainerHelper.loadAllItems(nbt, this.inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        if (!this.trySaveLootTable(nbt))
            ContainerHelper.saveAllItems(nbt, this.inventory);
        super.saveAdditional(nbt);
    }

    private LazyOptional<IItemHandlerModifiable> storageHandler;
    @Override
    public void setBlockState(BlockState state) {
        super.setBlockState(state);
        if (this.storageHandler != null) {
            LazyOptional<?> oldHandler = this.storageHandler;
            this.storageHandler = null;
            oldHandler.invalidate();
        }
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (!this.remove && cap == ForgeCapabilities.ITEM_HANDLER) {
            if (this.storageHandler == null)
                this.storageHandler = LazyOptional.of(this::createHandler);
            return this.storageHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        if (storageHandler != null) {
            storageHandler.invalidate();
            storageHandler = null;
        }
    }

    @Override
    public int getComparatorOverride() {
        return AbstractContainerMenu.getRedstoneSignalFromContainer(this);
    }

    @Override
    public void startOpen(Player player) {
        System.out.println("startOpen");
        if (!this.remove && !player.isSpectator())
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
    }

    @Override
    public void stopOpen(Player player) {
        System.out.println("stopOpen");
        if (!this.remove && !player.isSpectator())
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
    }

    public void recheckOpen() {
        if (!this.remove)
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
    }

    private ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        protected void onOpen(Level level, BlockPos pos, BlockState state) {
            System.out.println("onOpen");
            playSound(state, SoundEvents.BARREL_OPEN);
            //updateBlockState(state, true);
        }

        protected void onClose(Level level, BlockPos pos, BlockState state) {
            System.out.println("onClose");
            playSound(state, SoundEvents.BARREL_CLOSE);
            //updateBlockState(state, false);
        }

        protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int a, int b) {

        }

        protected boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof ChestMenu menu)
                return menu.getContainer() == AbstractStorageBlockEntity.this;
            return false;
        }
    };
}
