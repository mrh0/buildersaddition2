package github.mrh0.buildersaddition2.blocks.panel;

import github.mrh0.buildersaddition2.blocks.blockstate.PanelState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
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

import javax.annotation.Nullable;

public class PanelBlock extends Block implements SimpleWaterloggedBlock {
    public static final EnumProperty<PanelState> SHAPE = EnumProperty.create("shape", PanelState.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    protected static final VoxelShape WEST_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);
    protected static final VoxelShape EAST_SHAPE = Block.box(8D, 0.0D, 0.0D, 16D, 16.0D, 16.0D);
    protected static final VoxelShape NORTH_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16D, 16D, 8D);
    protected static final VoxelShape SOUTH_SHAPE = Block.box(0.0D, 0.0D, 8D, 16D, 16D, 16D);

    public PanelBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.defaultBlockState().setValue(SHAPE, PanelState.NORTH).setValue(WATERLOGGED, Boolean.FALSE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, WATERLOGGED);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return rotate(state, mirror.getRotation(state.getValue(SHAPE).getFacing()));
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(SHAPE, PanelState.reverseFacing(rotation.rotate(state.getValue(SHAPE).getFacing()), state.getValue(SHAPE).isDouble()));
    }

    @Override
    public BlockState rotate(BlockState state, LevelAccessor level, BlockPos pos, Rotation direction) {
        return rotate(state, direction);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(SHAPE)) {
            case DOUBLE_X, DOUBLE_Z -> Shapes.block();
            case EAST -> EAST_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> NORTH_SHAPE;
        };
    }

    public boolean isTransparent(BlockState state) {
        return !state.getValue(SHAPE).isDouble();
    }

    private PanelState getDoubleState(PanelState s) {
        if (s == PanelState.EAST || s == PanelState.WEST) return PanelState.DOUBLE_X;
        return PanelState.DOUBLE_Z;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        BlockPos blockpos = c.getClickedPos();
        BlockState blockstate = c.getLevel().getBlockState(blockpos);
        if (blockstate.getBlock() == this) {
            return blockstate.setValue(SHAPE, getDoubleState(blockstate.getValue(SHAPE))).setValue(WATERLOGGED, Boolean.FALSE);
        }

        FluidState fluidState = c.getLevel().getFluidState(blockpos);
        BlockState blockState = this.defaultBlockState().setValue(SHAPE, PanelState.NORTH).setValue(WATERLOGGED,
                fluidState.getType() == Fluids.WATER);
        Direction face = c.getClickedFace();

        double flagX = c.getClickLocation().x - (double) c.getClickedPos().getX() - .5d;
        double flagZ = c.getClickLocation().z - (double) c.getClickedPos().getZ() - .5d;

        PanelState vss = PanelState.forFacings(face, c.getHorizontalDirection());

        if (flagZ > 0 && diffG(flagZ, flagX)) vss = PanelState.SOUTH;
        if (flagZ < 0 && diffG(flagZ, flagX)) vss = PanelState.NORTH;
        if (flagX > 0 && diffG(flagX, flagZ)) vss = PanelState.EAST;
        if (flagX < 0 && diffG(flagX, flagZ)) vss = PanelState.WEST;

        return blockState.setValue(SHAPE, vss);
    }

    private boolean diffG(double d1, double d2) {
        return Math.abs(d1) > Math.abs(d2);
    }

    @Override
    public boolean canBeReplaced(BlockState state, BlockPlaceContext c) {
        ItemStack itemstack = c.getItemInHand();
        PanelState panelShape = state.getValue(SHAPE);
        if (panelShape != PanelState.DOUBLE_X && panelShape != PanelState.DOUBLE_Z
                && itemstack.getItem() == this.asItem()) {
            if (!c.replacingClickedOnBlock()) return true;
            if (c.getClickedFace() == Direction.NORTH && panelShape == PanelState.SOUTH) return true;
            if (c.getClickedFace() == Direction.EAST && panelShape == PanelState.WEST) return true;
            if (c.getClickedFace() == Direction.SOUTH && panelShape == PanelState.NORTH) return true;
            return c.getClickedFace() == Direction.WEST && panelShape == PanelState.EAST;
        }
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        return state.getValue(SHAPE) != PanelState.DOUBLE_X && state.getValue(SHAPE) != PanelState.DOUBLE_Z && SimpleWaterloggedBlock.super.placeLiquid(level, pos, state, fluidStateIn);
    }

    @Override
    public boolean canPlaceLiquid(@org.jetbrains.annotations.Nullable Player p_298313_, BlockGetter p_56301_, BlockPos p_56302_, BlockState p_56303_, Fluid p_56304_) {
        return SimpleWaterloggedBlock.super.canPlaceLiquid(p_298313_, p_56301_, p_56302_, p_56303_, p_56304_);
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
