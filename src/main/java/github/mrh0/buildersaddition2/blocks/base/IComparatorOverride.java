package github.mrh0.buildersaddition2.blocks.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IComparatorOverride {
    int getComparatorOverride();

    static int getComparatorOverride(Level worldIn, BlockPos pos) {
        if(worldIn.getBlockEntity(pos) instanceof IComparatorOverride be)
            return be.getComparatorOverride();
        return 0;
    }
}
