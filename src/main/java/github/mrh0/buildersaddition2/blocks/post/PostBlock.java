package github.mrh0.buildersaddition2.blocks.post;

import github.mrh0.buildersaddition2.blocks.base.AbstractBeamBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PostBlock extends AbstractBeamBlock {
    protected static final VoxelShape SHAPE_X = Block.box(0D, 6D, 6D, 16D, 10D, 10D);
    protected static final VoxelShape SHAPE_Y = Block.box(6D, 0D, 6D, 10D, 16D, 10D);
    protected static final VoxelShape SHAPE_Z = Block.box(6D, 6D, 0D, 10D, 10D, 16D);

    public PostBlock(Properties props) {
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
