package github.mrh0.buildersaddition2.blocks.chair;

import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.base.ISeatBlock;
import github.mrh0.buildersaddition2.entity.seat.SeatEntity;
import github.mrh0.buildersaddition2.state.PillowState;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ChairBlock extends Block implements ISeatBlock {

    public static final EnumProperty<PillowState> PILLOW = EnumProperty.create("pillow", PillowState.class);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape SHAPE_PILLOW = Block.box(3d, 8d, 3d, 13d, 9d, 13d);
    private static final VoxelShape SHAPE_BASE = Block.box(2d, 6d, 2d, 14d, 8d, 14d);
    private final Map<BlockState, VoxelShape> shapesCache;

    public ChairBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(PILLOW, PillowState.NONE));
        this.shapesCache = getShapeForEachState(ChairBlock::buildShape);
    }

    private static VoxelShape getLegShape(int x, int z, boolean tall) {
        return Block.box(x, 0d, z, x+2d, tall?16d:6d, z+2d);
    }

    private static VoxelShape getBackShape(Direction dir) {
        return switch (dir) {
            case NORTH -> Block.box(4d, 12d, 12d, 12d, 18d, 14d);
            case WEST -> Block.box(12d, 12d, 4d, 14d, 18d, 12d);
            case EAST -> Block.box(2d, 12d, 4d, 4d, 18d, 12d);
            default -> Block.box(4d, 12d, 2d, 12d, 18d, 4d);
        };
    }

    private static VoxelShape getLegsShape(Direction dir) {
        return switch (dir) {
            case NORTH -> Shapes.or(getLegShape(2, 2, false), getLegShape(12, 2, false), getLegShape(2, 12, true), getLegShape(12, 12, true));
            case WEST -> Shapes.or(getLegShape(2, 2, false), getLegShape(12, 2, true), getLegShape(2, 12, false), getLegShape(12, 12, true));
            case EAST -> Shapes.or(getLegShape(2, 2, true), getLegShape(12, 2, false), getLegShape(2, 12, true), getLegShape(12, 12, false));
            default -> Shapes.or(getLegShape(2, 2, true), getLegShape(12, 2, true), getLegShape(2, 12, false), getLegShape(12, 12, false));
        };
    }

    private static VoxelShape buildShape(BlockState state) {
        Direction dir = state.getValue(FACING);
        if(state.getValue(PILLOW) == PillowState.NONE) return Shapes.or(SHAPE_BASE, getBackShape(dir), getLegsShape(dir));
        return Shapes.or(SHAPE_PILLOW, SHAPE_BASE, getBackShape(dir), getLegsShape(dir));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext col) {
        return this.shapesCache.get(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PILLOW);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        return defaultBlockState().setValue(FACING, c.isSecondaryUseActive() ? c.getHorizontalDirection() : c.getHorizontalDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        boolean type = state.getValue(PILLOW) == PillowState.NONE;
        if(type) {
            Item item = player.getItemInHand(hand).getItem();
            for(int i = 0; i < Index.PILLOW.getBlockCount(); i++) {
                if(item == Index.PILLOW.getBlock(i).asItem()) {
                    if(!player.isCreative())
                        player.getItemInHand(hand).shrink(1);
                    world.setBlockAndUpdate(pos, state.setValue(PILLOW, PillowState.fromIndex(i)));
                    world.playSound(player, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1f, 1f);
                    return InteractionResult.CONSUME;
                }
            }
        }
        return SeatEntity.createSeat(world, pos, player, type ? .45 - 1d/16d : .45d, type ? SoundEvents.WOOD_HIT : SoundEvents.WOOL_HIT);
    }
}
