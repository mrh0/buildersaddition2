package github.mrh0.buildersaddition2.blocks.cupboard;

import github.mrh0.buildersaddition2.Utils;
import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlockEntity;
import github.mrh0.buildersaddition2.state.CupboardState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CupboardBlock extends Block implements EntityBlock {
    public static final EnumProperty<CupboardState> VARIANT = EnumProperty.create("variant", CupboardState.class);
    public static final BooleanProperty MIRROR = BooleanProperty.create("mirror");
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static VoxelShape SHAPE_NORTH_SINGLE = Block.box(0d, 0d, 0d, 16d, 16d, 15d);
    private static VoxelShape SHAPE_EAST_SINGLE = Block.box(1d, 0d, 0d, 16d, 16d, 16d);
    private static VoxelShape SHAPE_SOUTH_SINGLE = Block.box(0d, 0d, 1d, 16d, 16d, 16d);
    private static VoxelShape SHAPE_WEST_SINGLE = Block.box(0d, 0d, 0d, 15d, 16d, 16d);

    private static VoxelShape SHAPE_NORTH_TOP = Block.box(0d, -16d, 0d, 16d, 16d, 15d);
    private static VoxelShape SHAPE_EAST_TOP = Block.box(1d, -16d, 0d, 16d, 16d, 16d);
    private static VoxelShape SHAPE_SOUTH_TOP = Block.box(0d, -16d, 1d, 16d, 16d, 16d);
    private static VoxelShape SHAPE_WEST_TOP = Block.box(0d, -16d, 0d, 15d, 16d, 16d);

    private static VoxelShape SHAPE_NORTH_BOTTOM = Block.box(0d, 0d, 0d, 16d, 32d, 15d);
    private static VoxelShape SHAPE_EAST_BOTTOM = Block.box(1d, 0d, 0d, 16d, 32d, 16d);
    private static VoxelShape SHAPE_SOUTH_BOTTOM = Block.box(0d, 0d, 1d, 16d, 32d, 16d);
    private static VoxelShape SHAPE_WEST_BOTTOM = Block.box(0d, 0d, 0d, 15d, 32d, 16d);

    public CupboardBlock(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return AbstractStorageBlockEntity.useOpen(state, level, pos, player);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VARIANT, MIRROR, FACING);
    }

    private static VoxelShape selectByDirection(Direction dir, VoxelShape north, VoxelShape east, VoxelShape south, VoxelShape west) {
        return switch (dir) {
            case EAST -> east;
            case SOUTH -> south;
            case WEST -> west;
            default -> north;
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext cc) {
        return switch(state.getValue(VARIANT)) {
            case Single -> selectByDirection(state.getValue(FACING), SHAPE_NORTH_SINGLE, SHAPE_EAST_SINGLE, SHAPE_SOUTH_SINGLE, SHAPE_WEST_SINGLE);
            case Top -> selectByDirection(state.getValue(FACING), SHAPE_NORTH_TOP, SHAPE_EAST_TOP, SHAPE_SOUTH_TOP, SHAPE_WEST_TOP);
            case Bottom -> selectByDirection(state.getValue(FACING), SHAPE_NORTH_BOTTOM, SHAPE_EAST_BOTTOM, SHAPE_SOUTH_BOTTOM, SHAPE_WEST_BOTTOM);
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        boolean shift = c.getPlayer() != null && c.getPlayer().isCrouching();
        BlockState aboveState = c.getLevel().getBlockState(c.getClickedPos().above());
        BlockState belowState = c.getLevel().getBlockState(c.getClickedPos().below());

        if (aboveState.getBlock() instanceof CupboardBlock) {
            if(!aboveState.getValue(VARIANT).isBottom() && aboveState.getValue(FACING) == c.getHorizontalDirection()) {
                return this.defaultBlockState()
                        .setValue(VARIANT, CupboardState.Bottom)
                        .setValue(MIRROR, aboveState.getValue(MIRROR))
                        .setValue(FACING, c.getHorizontalDirection());
            }
        }
        if (belowState.getBlock() instanceof CupboardBlock) {
            if(!belowState.getValue(VARIANT).isTop() && belowState.getValue(FACING) == c.getHorizontalDirection()) {
                return this.defaultBlockState()
                        .setValue(VARIANT, CupboardState.Top)
                        .setValue(MIRROR, belowState.getValue(MIRROR))
                        .setValue(FACING, c.getHorizontalDirection());
            }
        }

        return this.defaultBlockState()
                .setValue(VARIANT, CupboardState.Single)
                .setValue(MIRROR, shift)
                .setValue(FACING, c.getHorizontalDirection());
    }

    @Override
    public BlockState updateShape(BlockState currentState, Direction direction, BlockState newState, LevelAccessor level, BlockPos myPos, BlockPos otherPos) {
        if(!(currentState.getBlock() instanceof CupboardBlock)) return currentState;
        if (newState.getBlock() instanceof CupboardBlock) {
            if(currentState.getValue(FACING) != newState.getValue(FACING)) return currentState;
            if(currentState.getValue(MIRROR) != newState.getValue(MIRROR)) return currentState;
            if(!currentState.getValue(VARIANT).isSingle()) return currentState;
            if(direction == Direction.DOWN) {
                if(!newState.getValue(VARIANT).isTop())
                    return defaultBlockState()
                            .setValue(VARIANT, CupboardState.Top)
                            .setValue(FACING, currentState.getValue(FACING))
                            .setValue(MIRROR, currentState.getValue(MIRROR));

            }
            if(direction == Direction.UP) {
                if(!newState.getValue(VARIANT).isBottom())
                    return defaultBlockState()
                            .setValue(VARIANT, CupboardState.Bottom)
                            .setValue(FACING, currentState.getValue(FACING))
                            .setValue(MIRROR, currentState.getValue(MIRROR));
            }
        }
        else {
            if((direction == Direction.DOWN && currentState.getValue(VARIANT).isTop())
            || (direction == Direction.UP && currentState.getValue(VARIANT).isBottom())) {
                return defaultBlockState()
                        .setValue(VARIANT, CupboardState.Single)
                        .setValue(FACING, currentState.getValue(FACING))
                        .setValue(MIRROR, currentState.getValue(MIRROR));
            }
        }

        return super.updateShape(currentState, direction, newState, level, myPos, otherPos);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CupboardBlockEntity(pos, state);
    }
}
