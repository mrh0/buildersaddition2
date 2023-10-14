package github.mrh0.buildersaddition2.blocks.bedside_table;

import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BedsideTableBlockEntity extends AbstractStorageBlockEntity {
    public BedsideTableBlockEntity(BlockPos pos, BlockState state) {
        super(Index.BEDSIDE_TABLE_ENTITY_TYPE.get(), pos, state);
    }

    @Override
    public int getRows() {
        return 1;
    }

    @Override
    protected Component getDefaultName() {
        return Component.empty();
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return ChestMenu.oneRow(id, inv);
    }
}
