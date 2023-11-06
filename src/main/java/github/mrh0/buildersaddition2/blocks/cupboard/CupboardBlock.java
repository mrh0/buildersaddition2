package github.mrh0.buildersaddition2.blocks.cupboard;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.state.CupboardState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiPredicate;

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
        //return AbstractStorageBlockEntity.useOpen(state, level, pos, player);
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            MenuProvider menuprovider = this.getMenuProvider(state, level, pos);
            if (menuprovider != null) {
                player.openMenu(menuprovider);
                PiglinAi.angerNearbyPiglins(player, true);
            }

            return InteractionResult.CONSUME;
        }
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
            case SINGLE -> selectByDirection(state.getValue(FACING), SHAPE_NORTH_SINGLE, SHAPE_EAST_SINGLE, SHAPE_SOUTH_SINGLE, SHAPE_WEST_SINGLE);
            case TOP -> selectByDirection(state.getValue(FACING), SHAPE_NORTH_TOP, SHAPE_EAST_TOP, SHAPE_SOUTH_TOP, SHAPE_WEST_TOP);
            case BOTTOM -> selectByDirection(state.getValue(FACING), SHAPE_NORTH_BOTTOM, SHAPE_EAST_BOTTOM, SHAPE_SOUTH_BOTTOM, SHAPE_WEST_BOTTOM);
        };
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        boolean shift = c.getPlayer() != null && c.getPlayer().isSecondaryUseActive();
        BlockState aboveState = c.getLevel().getBlockState(c.getClickedPos().above());
        BlockState belowState = c.getLevel().getBlockState(c.getClickedPos().below());

        if (aboveState.getBlock() instanceof CupboardBlock) {
            if(!aboveState.getValue(VARIANT).isBottom() && aboveState.getValue(FACING) == c.getHorizontalDirection()) {
                return this.defaultBlockState()
                        .setValue(VARIANT, CupboardState.BOTTOM)
                        .setValue(MIRROR, aboveState.getValue(MIRROR))
                        .setValue(FACING, c.getHorizontalDirection());
            }
        }
        if (belowState.getBlock() instanceof CupboardBlock) {
            if(!belowState.getValue(VARIANT).isTop() && belowState.getValue(FACING) == c.getHorizontalDirection()) {
                return this.defaultBlockState()
                        .setValue(VARIANT, CupboardState.TOP)
                        .setValue(MIRROR, belowState.getValue(MIRROR))
                        .setValue(FACING, c.getHorizontalDirection());
            }
        }

        return this.defaultBlockState()
                .setValue(VARIANT, CupboardState.SINGLE)
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
                            .setValue(VARIANT, CupboardState.TOP)
                            .setValue(FACING, currentState.getValue(FACING))
                            .setValue(MIRROR, currentState.getValue(MIRROR));

            }
            if(direction == Direction.UP) {
                if(!newState.getValue(VARIANT).isBottom())
                    return defaultBlockState()
                            .setValue(VARIANT, CupboardState.BOTTOM)
                            .setValue(FACING, currentState.getValue(FACING))
                            .setValue(MIRROR, currentState.getValue(MIRROR));
            }
        }
        else {
            if((direction == Direction.DOWN && currentState.getValue(VARIANT).isTop())
            || (direction == Direction.UP && currentState.getValue(VARIANT).isBottom())) {
                return defaultBlockState()
                        .setValue(VARIANT, CupboardState.SINGLE)
                        .setValue(FACING, currentState.getValue(FACING))
                        .setValue(MIRROR, currentState.getValue(MIRROR));
            }
        }
        return super.updateShape(currentState, direction, newState, level, myPos, otherPos);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (level.isClientSide()) return;
        if (state.is(newState.getBlock())) return;
        if (level.getBlockEntity(pos) instanceof Container container) {
            Containers.dropContents(level, pos, container);
            level.updateNeighborsAt(pos, this);
            // level.updateNeighbourForOutputSignal(pos, this);
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CupboardBlockEntity(pos, state);
    }

    // Combiner

    public static DoubleBlockCombiner.BlockType getBlockType(BlockState state) {
        CupboardState type = state.getValue(VARIANT);
        if (type == CupboardState.SINGLE) return DoubleBlockCombiner.BlockType.SINGLE;
        else return type == CupboardState.TOP ? DoubleBlockCombiner.BlockType.FIRST : DoubleBlockCombiner.BlockType.SECOND;
    }

    public static Direction getConnectedDirection(BlockState state) {
        return state.getValue(VARIANT) == CupboardState.BOTTOM ? Direction.UP :  Direction.DOWN;
    }

    public static Container getContainer(CupboardBlock block, BlockState state, Level level, BlockPos pos, boolean flag) {
        return block.combine(state, level, pos, flag).apply(CUPBOARD_COMBINER).orElse((Container)null);
    }

    public DoubleBlockCombiner.NeighborCombineResult<? extends CupboardBlockEntity> combine(BlockState p_51544_, Level p_51545_, BlockPos p_51546_, boolean p_51547_) {
        BiPredicate<LevelAccessor, BlockPos> bipredicate = (level, pos) -> false;
        //if (p_51547_) bipredicate = (p_51578_, p_51579_) -> false;
        //else bipredicate = ChestBlock::isChestBlockedAt;

        return DoubleBlockCombiner.combineWithNeigbour(Index.CUPBOARD_ENTITY_TYPE.get(), CupboardBlock::getBlockType, CupboardBlock::getConnectedDirection, FACING, p_51544_, p_51545_, p_51546_, bipredicate);
    }

    public MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return this.combine(state, level, pos, false).apply(MENU_PROVIDER_COMBINER).orElse((MenuProvider)null);
    }

    private static final DoubleBlockCombiner.Combiner<CupboardBlockEntity, Optional<Container>> CUPBOARD_COMBINER = new DoubleBlockCombiner.Combiner<CupboardBlockEntity, Optional<Container>>() {
        public Optional<Container> acceptDouble(CupboardBlockEntity be1, CupboardBlockEntity be2) {
            return Optional.of(new CompoundContainer(be1, be2));
        }
        public Optional<Container> acceptSingle(CupboardBlockEntity be) {
            return Optional.of(be);
        }
        public Optional<Container> acceptNone() {
            return Optional.empty();
        }
    };
    private static final DoubleBlockCombiner.Combiner<CupboardBlockEntity, Optional<MenuProvider>> MENU_PROVIDER_COMBINER = new DoubleBlockCombiner.Combiner<CupboardBlockEntity, Optional<MenuProvider>>() {
        public Optional<MenuProvider> acceptDouble(final CupboardBlockEntity be1, final CupboardBlockEntity be2) {
            final Container container = new CompoundContainer(be1, be2);
            return Optional.of(new MenuProvider() {
                @javax.annotation.Nullable
                public AbstractContainerMenu createMenu(int id, Inventory inv, Player player) {
                    if (be1.canOpen(player) && be2.canOpen(player)) {
                        be1.unpackLootTable(inv.player);
                        be2.unpackLootTable(inv.player);
                        return ChestMenu.sixRows(id, inv, container);
                    } else return null;
                }

                public Component getDisplayName() {
                    if (be1.hasCustomName()) return be1.getDisplayName();
                    else return (Component)(be2.hasCustomName() ? be2.getDisplayName() : BA2.translatable("container", "cupboard"));
                }
            });
        }

        public Optional<MenuProvider> acceptSingle(CupboardBlockEntity be) {
            return Optional.of(be);
        }

        public Optional<MenuProvider> acceptNone() {
            return Optional.empty();
        }
    };
}
