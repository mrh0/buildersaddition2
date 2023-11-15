package github.mrh0.buildersaddition2.blocks.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBeamBlock extends Block implements SimpleWaterloggedBlock {
    public static final BooleanProperty AXIS_X = BooleanProperty.create("axis_x");
    public static final BooleanProperty AXIS_Y = BooleanProperty.create("axis_y");
    public static final BooleanProperty AXIS_Z = BooleanProperty.create("axis_z");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public AbstractBeamBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState()
                .setValue(AXIS_X, false)
                .setValue(AXIS_Y, false)
                .setValue(AXIS_Z, false)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS_X, AXIS_Y, AXIS_Z, WATERLOGGED);
    }

    protected BlockState getState(boolean x, boolean y, boolean z, boolean wl) {
        return defaultBlockState()
                .setValue(AXIS_X, x)
                .setValue(AXIS_Y, y)
                .setValue(AXIS_Z, z)
                .setValue(WATERLOGGED, wl);
    }

    protected BlockState getState(Direction.Axis axis, boolean wl) {
        return getState(
                axis == Direction.Axis.X,
                axis == Direction.Axis.Y,
                axis == Direction.Axis.Z,
                wl
        );
    }

    protected BlockState getState(Direction.Axis axis, BlockState prev) {
        return getState(
                axis == Direction.Axis.X || prev.getValue(AXIS_X),
                axis == Direction.Axis.Y || prev.getValue(AXIS_Y),
                axis == Direction.Axis.Z || prev.getValue(AXIS_Z),
                prev.getValue(WATERLOGGED)
        );
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        BlockPos blockpos = c.getClickedPos();
        BlockState blockstate = c.getLevel().getBlockState(blockpos);
        if (blockstate.getBlock() == this) return getState(c.getClickedFace().getAxis(), blockstate);
        FluidState fluidState = c.getLevel().getFluidState(blockpos);
        return getState(c.getClickedFace().getAxis(), fluidState.getType() == Fluids.WATER);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext c) {
        ItemStack itemstack = c.getItemInHand();
        boolean x = state.getValue(AXIS_X);
        boolean y = state.getValue(AXIS_Y);
        boolean z = state.getValue(AXIS_Z);
        if (c.isSecondaryUseActive()) return false;
        if (itemstack.getItem() == this.asItem()) {
            if (!c.replacingClickedOnBlock()) return true;
            if (c.getClickedFace().getAxis() == Direction.Axis.X) return !x;
            if (c.getClickedFace().getAxis() == Direction.Axis.Y) return !y;
            if (c.getClickedFace().getAxis() == Direction.Axis.Z) return !z;
        }
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return stateIn;
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        if (type == PathComputationType.WATER) return level.getFluidState(pos).is(FluidTags.WATER);
        return false;
    }
}
