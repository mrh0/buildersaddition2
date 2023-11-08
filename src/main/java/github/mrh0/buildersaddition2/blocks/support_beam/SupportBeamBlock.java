package github.mrh0.buildersaddition2.blocks.support_beam;

import github.mrh0.buildersaddition2.blocks.base.AbstractBeamBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SupportBeamBlock extends AbstractBeamBlock {
    protected static final VoxelShape SHAPE_X = Block.box(0D, 6D, 3D, 16D, 16.0D, 13D);
    protected static final VoxelShape SHAPE_Y = Block.box(3D, 0D, 3D, 13D, 16.0D, 13.0D);
    protected static final VoxelShape SHAPE_Z = Block.box(3D, 6D, 0D, 13D, 16.0D, 16D);

    public SupportBeamBlock(Properties props) {
        super(props);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext cc) {
        boolean x = state.getValue(AXIS_X);
        boolean y = state.getValue(AXIS_Y);
        boolean z = state.getValue(AXIS_Z);
        if(!x && !y && !z) return Shapes.block();
        return Shapes.or(
                x ? SHAPE_X : Shapes.empty(),
                y ? SHAPE_Y : Shapes.empty(),
                z ? SHAPE_Z : Shapes.empty()
        );
    }
}
