package github.mrh0.buildersaddition2.blocks.sofa;

import github.mrh0.buildersaddition2.Utils;
import github.mrh0.buildersaddition2.blocks.chair.ChairBlock;
import github.mrh0.buildersaddition2.blocks.table.TableBlock;
import github.mrh0.buildersaddition2.state.PillowState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class SofaBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;

    private static VoxelShape BASE_SHAPE = Block.box(0d, 2d, 0d, 16d, 9d, 16d);

    private final Map<BlockState, VoxelShape> shapesCache;

    public SofaBlock(Properties props) {
        super(props);
        this.shapesCache = getShapeForEachState(SofaBlock::buildShape);
    }

    private static boolean isSofa(BlockState state) {
        return state.getBlock() instanceof SofaBlock;
    }

    public BlockState getStateForPlacement(BlockPlaceContext p_56872_) {
        BlockPos blockpos = p_56872_.getClickedPos();
        BlockState blockstate = this.defaultBlockState().setValue(FACING, p_56872_.getHorizontalDirection());
        return blockstate.setValue(SHAPE, getStairsShape(blockstate, p_56872_.getLevel(), blockpos));
    }

    public BlockState updateShape(BlockState myState, Direction direction, BlockState otherState, LevelAccessor level, BlockPos myPos, BlockPos otherPos) {
        return direction.getAxis().isHorizontal()
                ? myState.setValue(SHAPE, getStairsShape(myState, level, myPos))
                : super.updateShape(myState, direction, otherState, level, myPos, otherPos);
    }

    private static StairsShape getStairsShape(BlockState state, BlockGetter getter, BlockPos pos) {
        Direction forward = state.getValue(FACING);
        BlockState blockstate = getter.getBlockState(pos.relative(forward));
        if (isSofa(blockstate)) {
            Direction otherDirection = blockstate.getValue(FACING);
            if (otherDirection.getAxis() != state.getValue(FACING).getAxis() && canTakeShape(state, getter, pos, otherDirection.getOpposite())) {
                if (otherDirection == forward.getCounterClockWise()) return StairsShape.OUTER_LEFT;
                return StairsShape.OUTER_RIGHT;
            }
        }

        BlockState backward = getter.getBlockState(pos.relative(forward.getOpposite()));
        if (isSofa(backward)) {
            Direction direction2 = backward.getValue(FACING);
            if (direction2.getAxis() != state.getValue(FACING).getAxis() && canTakeShape(state, getter, pos, direction2)) {
                if (direction2 == forward.getCounterClockWise()) return StairsShape.INNER_LEFT;

                return StairsShape.INNER_RIGHT;
            }
        }
        return StairsShape.STRAIGHT;
    }

    private static boolean canTakeShape(BlockState state, BlockGetter getter, BlockPos pos, Direction direction) {
        BlockState blockstate = getter.getBlockState(pos.relative(direction));
        return !isSofa(blockstate) || blockstate.getValue(FACING) != state.getValue(FACING);
    }

    private static VoxelShape buildShape(BlockState state) {
        Direction dir = state.getValue(FACING);
        if(state.getValue(PILLOW) == PillowState.None)
            return Shapes.or(SHAPE_BASE, getBackShape(dir), getLegsShape(dir));
        return Shapes.or(SHAPE_PILLOW, SHAPE_BASE, getBackShape(dir), getLegsShape(dir));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SHAPE);
    }
}
