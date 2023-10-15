package github.mrh0.buildersaddition2.blocks.bedside_table;

import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
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
    protected void playSound(BlockState state, SoundEvent evt) {
        Vec3i vector3i = state.getValue(BedsideTableBlock.FACING).getNormal();
        playDefaultSound(evt, vector3i);
    }

    @Override
    protected Component getDefaultName() {
        return Component.empty();
    }
}
