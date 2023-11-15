package github.mrh0.buildersaddition2.blocks.bookshelf;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlockEntity;
import github.mrh0.buildersaddition2.blocks.cabinet.CabinetBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.BlockState;

public class BookshelfBlockEntity extends AbstractStorageBlockEntity {
    public BookshelfBlockEntity(BlockPos pos, BlockState state) {
        super(Index.CABINET_ENTITY_TYPE.get(), pos, state);
    }

    @Override
    public int getRows() {
        return 2;
    }

    @Override
    protected void playSound(BlockState state, SoundEvent evt) {
        Vec3i vector3i = state.getValue(CabinetBlock.FACING).getNormal();
        playDefaultSound(evt, vector3i);
    }

    @Override
    protected Component getDefaultName() {
        return BA2.translatable("container", "bookshelf");
    }
}
