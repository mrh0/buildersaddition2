package github.mrh0.buildersaddition2.blocks.counter;

import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.BlockState;

public class CounterBlockEntity extends AbstractStorageBlockEntity {
    public CounterBlockEntity(BlockPos pos, BlockState state) {
        super(Index.COUNTER_ENTITY_TYPE.get(), pos, state);
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    protected void playSound(BlockState state, SoundEvent evt) {
        Vec3i vector3i = state.getValue(CounterBlock.FACING).getNormal();
        playDefaultSound(evt, vector3i);
    }

    @Override
    protected Component getDefaultName() {
        return Component.empty();
    }
}
