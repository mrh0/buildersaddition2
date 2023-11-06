package github.mrh0.buildersaddition2.blocks.hedge;

import github.mrh0.buildersaddition2.state.HedgeState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.IForgeShearable;

public class HedgeBlock extends Block implements SimpleWaterloggedBlock, IForgeShearable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<HedgeState> STATE = EnumProperty.<HedgeState>create("state", HedgeState.class);

    private static VoxelShape SHAPE_NONE = Block.box(4d, 0d, 4d, 12d, 16d, 12d);
    private static VoxelShape SHAPE_STRAIGHT_Z = Block.box(0d, 0d, 4d, 16d, 16d, 12d);
    private static VoxelShape SHAPE_STRAIGHT_X = Block.box(4d, 0d, 0d, 12d, 16d, 16d);

    private static VoxelShape SHAPE_SHORT_N = Block.box(4d, 0d, 0d, 12d, 16d, 4d);
    private static VoxelShape SHAPE_SHORT_E = Block.box(12d, 0d, 4d, 16d, 16d, 12d);
    private static VoxelShape SHAPE_SHORT_S = Block.box(4d, 0d, 12d, 12d, 16d, 16d);
    private static VoxelShape SHAPE_SHORT_W = Block.box(0d, 0d, 4d, 4d, 16d, 12d);

    private static VoxelShape SHAPE_CORNER_NE = Shapes.or(SHAPE_NONE, SHAPE_SHORT_N, SHAPE_SHORT_E);
    private static VoxelShape SHAPE_CORNER_NW = Shapes.or(SHAPE_NONE, SHAPE_SHORT_N, SHAPE_SHORT_W);
    private static VoxelShape SHAPE_CORNER_SE = Shapes.or(SHAPE_NONE, SHAPE_SHORT_S, SHAPE_SHORT_E);
    private static VoxelShape SHAPE_CORNER_SW = Shapes.or(SHAPE_NONE, SHAPE_SHORT_S, SHAPE_SHORT_W);

    private static VoxelShape SHAPE_T_N = Shapes.or(SHAPE_STRAIGHT_Z, SHAPE_SHORT_N);
    private static VoxelShape SHAPE_T_E = Shapes.or(SHAPE_STRAIGHT_X, SHAPE_SHORT_E);
    private static VoxelShape SHAPE_T_S = Shapes.or(SHAPE_STRAIGHT_Z, SHAPE_SHORT_S);
    private static VoxelShape SHAPE_T_W = Shapes.or(SHAPE_STRAIGHT_X, SHAPE_SHORT_W);

    private static VoxelShape SHAPE_CROSS = Shapes.or(SHAPE_STRAIGHT_X, SHAPE_STRAIGHT_Z);

    private static VoxelShape COL_NONE = Block.box(4d, 0d, 4d, 12d, 24d, 12d);
    private static VoxelShape COL_STRAIGHT_Z = Block.box(0d, 0d, 4d, 16d, 24d, 12d);
    private static VoxelShape COL_STRAIGHT_X = Block.box(4d, 0d, 0d, 12d, 24d, 16d);

    private static VoxelShape COL_SHORT_N = Block.box(4d, 0d, 0d, 12d, 24d, 4d);
    private static VoxelShape COL_SHORT_E = Block.box(12d, 0d, 4d, 16d, 24d, 12d);
    private static VoxelShape COL_SHORT_S = Block.box(4d, 0d, 12d, 12d, 24d, 16d);
    private static VoxelShape COL_SHORT_W = Block.box(0d, 0d, 4d, 4d, 24d, 12d);

    private static VoxelShape COL_CORNER_NE = Shapes.or(COL_NONE, COL_SHORT_N, COL_SHORT_E);
    private static VoxelShape COL_CORNER_NW = Shapes.or(COL_NONE, COL_SHORT_N, COL_SHORT_W);
    private static VoxelShape COL_CORNER_SE = Shapes.or(COL_NONE, COL_SHORT_S, COL_SHORT_E);
    private static VoxelShape COL_CORNER_SW = Shapes.or(COL_NONE, COL_SHORT_S, COL_SHORT_W);

    private static VoxelShape COL_T_N = Shapes.or(COL_STRAIGHT_Z, COL_SHORT_N);
    private static VoxelShape COL_T_E = Shapes.or(COL_STRAIGHT_X, COL_SHORT_E);
    private static VoxelShape COL_T_S = Shapes.or(COL_STRAIGHT_Z, COL_SHORT_S);
    private static VoxelShape COL_T_W = Shapes.or(COL_STRAIGHT_X, COL_SHORT_W);

    private static VoxelShape COL_CROSS = Shapes.or(COL_STRAIGHT_X, COL_STRAIGHT_Z);

    public HedgeBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState().setValue(WATERLOGGED, false).setValue(STATE, HedgeState.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE, WATERLOGGED);
    }

    @Override
    public boolean isShearable(ItemStack item, Level level, BlockPos pos) {
        return true;
    }

    public VoxelShape getShape(BlockState state) {
        return switch (state.getValue(STATE)) {
            case NONE -> SHAPE_NONE;
            case S_X -> SHAPE_STRAIGHT_X;
            case S_Z -> SHAPE_STRAIGHT_Z;
            case C_NE -> SHAPE_CORNER_NE;
            case C_NW -> SHAPE_CORNER_NW;
            case C_SE -> SHAPE_CORNER_SE;
            case C_SW -> SHAPE_CORNER_SW;
            case T_N -> SHAPE_T_N;
            case T_E -> SHAPE_T_E;
            case T_S -> SHAPE_T_S;
            case T_W -> SHAPE_T_W;
            case C -> SHAPE_CROSS;
        };
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos,
                                        CollisionContext context) {
        return switch (state.getValue(STATE)) {
            case NONE -> COL_NONE;
            case S_X -> COL_STRAIGHT_X;
            case S_Z -> COL_STRAIGHT_Z;
            case C_NE -> COL_CORNER_NE;
            case C_NW -> COL_CORNER_NW;
            case C_SE -> COL_CORNER_SE;
            case C_SW -> COL_CORNER_SW;
            case T_N -> COL_T_N;
            case T_E -> COL_T_E;
            case T_S -> COL_T_S;
            case T_W -> COL_T_W;
            case C -> COL_CROSS;
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state);
    }

    public BlockState getState(BlockState state, BlockGetter level, BlockPos pos) {
        BlockState bn = level.getBlockState(pos.north());
        BlockState be = level.getBlockState(pos.east());
        BlockState bs = level.getBlockState(pos.south());
        BlockState bw = level.getBlockState(pos.west());

        boolean n = bn.getBlock() instanceof HedgeBlock;
        boolean e = be.getBlock() instanceof HedgeBlock;
        boolean s = bs.getBlock() instanceof HedgeBlock;
        boolean w = bw.getBlock() instanceof HedgeBlock;

        if(n && e && s && w)
            return getNextState(state, HedgeState.C);

        if(!n && !e && !s && !w)
            return getNextState(state, HedgeState.NONE);

        else if(n && e && !s && w)
            return getNextState(state, HedgeState.T_N);
        else if(n && e && s)
            return getNextState(state, HedgeState.T_E);
        else if(!n && e && s && w)
            return getNextState(state, HedgeState.T_S);
        else if(n && !e && s && w)
            return getNextState(state, HedgeState.T_W);

        else if(!e && !w)
            return getNextState(state, HedgeState.S_X);
        else if(!n && !s)
            return getNextState(state, HedgeState.S_Z);

        else if(n && e)
            return getNextState(state, HedgeState.C_NE);
        else if(n)
            return getNextState(state, HedgeState.C_NW);
        else if(e)
            return getNextState(state, HedgeState.C_SE);
        return getNextState(state, HedgeState.C_SW);
    }

    private BlockState getNextState(BlockState state, HedgeState shape) {
        return state.setValue(STATE, shape);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        return getState(this.defaultBlockState(), c.getLevel(), c.getClickedPos()).setValue(WATERLOGGED,
                c.getLevel().getFluidState(c.getClickedPos()).getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        return SimpleWaterloggedBlock.super.placeLiquid(level, pos, state, fluidStateIn);
    }

    @Override
    public boolean canPlaceLiquid(BlockGetter level, BlockPos pos, BlockState state, Fluid fluidIn) {
        return SimpleWaterloggedBlock.super.canPlaceLiquid(level, pos, state, fluidIn);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        if(type == PathComputationType.WATER) return level.getFluidState(pos).is(FluidTags.WATER);
        return false;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor level,
                                  BlockPos currentPos, BlockPos facingPos) {
        if(stateIn.getValue(WATERLOGGED)) level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        return getState(stateIn, level, currentPos);
    }
}
