package github.mrh0.buildersaddition2.blocks.bench;

import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.base.ISeatBlock;
import github.mrh0.buildersaddition2.entity.seat.SeatEntity;
import github.mrh0.buildersaddition2.blocks.blockstate.BenchState;
import github.mrh0.buildersaddition2.blocks.blockstate.PillowState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BenchBlock extends Block implements ISeatBlock {

    public static final EnumProperty<PillowState> PILLOW = EnumProperty.create("pillow", PillowState.class);
    public static final EnumProperty<BenchState> SHAPE = EnumProperty.<BenchState>create("shape", BenchState.class);

    protected static final VoxelShape SHAPE_X = Block.box(0d, 6d, 2d, 16d, 8d, 14d);
    protected static final VoxelShape SHAPE_Z = Block.box(2d, 6d, 0d, 14d, 8d, 16d);

    protected static final VoxelShape SHAPE_NORTH = Block.box(3d, 0d, 1d, 13d, 7d, 3d);
    protected static final VoxelShape SHAPE_SOUTH = Block.box(3d, 0d, 13d, 13d, 7d, 15d);

    protected static final VoxelShape SHAPE_WEST = Block.box(1d, 0d, 3d, 3d, 7d, 13d);
    protected static final VoxelShape SHAPE_EAST = Block.box(13d, 0d, 3d, 15d, 7d, 13d);

    public BenchBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState().setValue(PILLOW, PillowState.NONE));
    }

    public VoxelShape getShape(BlockState state) {
        BenchState bs = state.getValue(SHAPE);
        VoxelShape shape = bs.getAxis() == Direction.Axis.Z ? SHAPE_Z : SHAPE_X;
        if(bs.hasLegNorth())
            shape = Shapes.or(shape, SHAPE_NORTH);
        if(bs.hasLegWest())
            shape = Shapes.or(shape, SHAPE_WEST);
        if(bs.hasLegSouth())
            shape = Shapes.or(shape, SHAPE_SOUTH);
        if(bs.hasLegEast())
            shape = Shapes.or(shape, SHAPE_EAST);
        return shape;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return getShape(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SHAPE, PILLOW);
    }

    public boolean isBench(BlockState state) {
        return state.getBlock() instanceof BenchBlock;
    }

    public boolean connects(Direction.Axis current, BlockGetter worldIn, BlockPos pos, Direction dir) {
        BlockState state = worldIn.getBlockState(pos.relative(dir));
        if(isBench(state)) return state.getValue(SHAPE).getAxis() == current;
        return false;
    }

    public BlockState getState(Direction.Axis current, BlockGetter worldIn, BlockPos pos, PillowState pillow) {
        BlockState out = this.defaultBlockState().setValue(PILLOW, pillow);
        if(current == Direction.Axis.Z) {
            boolean n = connects(current, worldIn, pos, Direction.NORTH);
            boolean s = connects(current, worldIn, pos, Direction.SOUTH);
            if(n && !s) return out.setValue(SHAPE, BenchState.NORTH);
            if(!n && s) return out.setValue(SHAPE, BenchState.SOUTH);
            if(n) return out.setValue(SHAPE, BenchState.NONE_Z);
            return out.setValue(SHAPE, BenchState.BOTH_Z);
        }
        boolean w = connects(current, worldIn, pos, Direction.WEST);
        boolean e = connects(current, worldIn, pos, Direction.EAST);
        if(e && !w) return out.setValue(SHAPE, BenchState.EAST);
        if(!e && w) return out.setValue(SHAPE, BenchState.WEST);
        if(e) return out.setValue(SHAPE, BenchState.NONE_X);
        return out.setValue(SHAPE, BenchState.BOTH_X);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        return getState(c.getHorizontalDirection().getClockWise().getAxis(), c.getLevel(), c.getClickedPos(), PillowState.NONE);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction p_60542_, BlockState p_60543_, LevelAccessor worldIn, BlockPos pos, BlockPos otherPos) {
        return getState(stateIn.getValue(SHAPE).getAxis(), worldIn, pos, stateIn.getValue(PILLOW));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        boolean type = state.getValue(PILLOW) == PillowState.NONE;
        if(type) {
            Item item = player.getItemInHand(hand).getItem();
            for(int i = 0; i < Index.PILLOW.getBlockCount(); i++) {
                if(item == Index.PILLOW.getBlock(i).asItem()) {
                    if(!player.isCreative()) player.getItemInHand(hand).shrink(1);
                    world.setBlockAndUpdate(pos, state.setValue(PILLOW, PillowState.fromIndex(i)));
                    world.playSound(player, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1f, 1f);
                    return InteractionResult.CONSUME;
                }
            }
        }
        return SeatEntity.createSeat(world, pos, player, type ? .45 - 1d/16d : .45d, type ? SoundEvents.WOOD_HIT : SoundEvents.WOOL_HIT);
    }
}
