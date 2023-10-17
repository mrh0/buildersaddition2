package github.mrh0.buildersaddition2.blocks.sofa;

import github.mrh0.buildersaddition2.Utils;
import github.mrh0.buildersaddition2.blocks.base.ISeatBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class SofaBlock extends Block implements ISeatBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<StairsShape> SHAPE = BlockStateProperties.STAIRS_SHAPE;
    public static final BooleanProperty ARMREST_LEFT = BooleanProperty.create("armrest_left");
    public static final BooleanProperty ARMREST_RIGHT = BooleanProperty.create("armrest_right");

    private static VoxelShape BASE_SHAPE = Block.box(0d, 2d, 0d, 16d, 9d, 16d);
    private static VoxelShape BACK_SHAPE = Block.box(0d, 9d, 0d, 16d, 16d, 4d);

    private final Map<BlockState, VoxelShape> shapesCache;

    public SofaBlock(Properties props) {
        super(props);
        this.shapesCache = getShapeForEachState(SofaBlock::buildShape);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, SHAPE, ARMREST_LEFT, ARMREST_RIGHT);
    }

    private static boolean isSofa(BlockState state) {
        return state.getBlock() instanceof SofaBlock;
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
        blockstate = blockstate.setValue(SHAPE, getStairsShape(blockstate, context.getLevel(), blockpos));
        return blockstate.setValue(ARMREST_LEFT, hasArmrestLeft(blockstate, context.getLevel(), blockpos))
                .setValue(ARMREST_RIGHT, hasArmrestRight(blockstate, context.getLevel(), blockpos));
    }

    public BlockState updateShape(BlockState myState, Direction direction, BlockState otherState, LevelAccessor level, BlockPos myPos, BlockPos otherPos) {
        if(!direction.getAxis().isHorizontal()) return super.updateShape(myState, direction, otherState, level, myPos, otherPos);
        BlockState newState = myState.setValue(SHAPE, getStairsShape(myState, level, myPos));
        return newState.setValue(ARMREST_LEFT, hasArmrestLeft(newState, level, myPos))
                .setValue(ARMREST_RIGHT, hasArmrestRight(newState, level, myPos));
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

    public static boolean hasArmrestLeft(BlockState myState, BlockGetter getter, BlockPos pos) {
        if(myState.getValue(SHAPE) != StairsShape.STRAIGHT) return false;
        BlockState state = getter.getBlockState(pos.relative(myState.getValue(FACING).getCounterClockWise()));
        return !isSofa(state);
    }

    public static boolean hasArmrestRight(BlockState myState, BlockGetter getter, BlockPos pos) {
        if(myState.getValue(SHAPE) != StairsShape.STRAIGHT) return false;
        BlockState state = getter.getBlockState(pos.relative(myState.getValue(FACING).getClockWise()));
        return !isSofa(state);
    }

    private static boolean canTakeShape(BlockState state, BlockGetter getter, BlockPos pos, Direction direction) {
        BlockState blockstate = getter.getBlockState(pos.relative(direction));
        return !isSofa(blockstate) || blockstate.getValue(FACING) != state.getValue(FACING);
    }

    private static VoxelShape buildShape(BlockState state) {
        return BASE_SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext col) {
        return BASE_SHAPE;//this.shapesCache.get(state);
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter level, Entity entityIn) {
        if (entityIn.isSuppressingBounce())
            super.updateEntityAfterFallOn(level, entityIn);
        else
            this.bounceUp(entityIn);
    }

    private void bounceUp(Entity entity) {
        Vec3 vector3d = entity.getDeltaMovement();
        if (vector3d.y < 0.0D) {
            double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setDeltaMovement(vector3d.x, -vector3d.y * (double)0.66F * d0, vector3d.z);
        }
    }
}
