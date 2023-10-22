package github.mrh0.buildersaddition2.blocks.counter;

import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CounterBlock extends AbstractStorageBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape SHAPE_COUNTERTOP = Block.box(0d, 15d, 0d, 16d, 16d, 16d);
    private static final VoxelShape SHAPE_NORTH = Block.box(0d, 0d, 0d, 16d, 16d, 15d);
    private static final VoxelShape SHAPE_EAST = Block.box(1d, 0d, 0d, 16d, 16d, 16d);
    private static final VoxelShape SHAPE_SOUTH =  Block.box(0d, 0d, 1d, 16d, 16d, 16d);
    private static final VoxelShape SHAPE_WEST = Block.box(0d, 0d, 0d, 15d, 16d, 16d);

    public CounterBlock(Properties props) {
        super(props);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        return this.defaultBlockState().setValue(FACING, c.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    private VoxelShape getShapeForDirection(Direction direction) {
        return Shapes.or(SHAPE_COUNTERTOP, switch (direction) {
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_NORTH;
        });
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShapeForDirection(state.getValue(FACING));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CounterBlockEntity(pos, state);
    }
}
