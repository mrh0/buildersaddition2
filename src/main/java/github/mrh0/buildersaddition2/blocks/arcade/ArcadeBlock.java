package github.mrh0.buildersaddition2.blocks.arcade;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.common.Utils;
import github.mrh0.buildersaddition2.ui.GenericStorageMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class ArcadeBlock extends Block implements MenuProvider {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape SHAPE_NORTH_LOWER = Block.box(0d, 0d, 0d, 16d, 32d, 13d);
    private static final VoxelShape SHAPE_EAST_LOWER = Block.box(3d, 0d, 0d, 16d, 32d, 16d);
    private static final VoxelShape SHAPE_SOUTH_LOWER = Block.box(0d, 0d, 3d, 16d, 32d, 16d);
    private static final VoxelShape SHAPE_WEST_LOWER = Block.box(0d, 0d, 0d, 13d, 32d, 16d);

    private static final VoxelShape SHAPE_NORTH_UPPER = Block.box(0d, -16d, 0d, 16d, 16d, 13d);
    private static final VoxelShape SHAPE_EAST_UPPER = Block.box(3d, -16d, 0d, 16d, 16d, 16d);
    private static final VoxelShape SHAPE_SOUTH_UPPER = Block.box(0d, -16d, 3d, 16d, 16d, 16d);
    private static final VoxelShape SHAPE_WEST_UPPER = Block.box(0d, -16d, 0d, 13d, 16d, 16d);

    public ArcadeBlock(Properties props) {
        super(props);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.BLOCK;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        if (blockpos.getY() >= 255 || !context.getLevel().getBlockState(blockpos.above()).canBeReplaced(context)) return null;
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection())
                .setValue(HALF, DoubleBlockHalf.LOWER);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity ent,
                            ItemStack stack) {
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HALF);
    }

    private VoxelShape getShapeForDirection(Direction d, boolean b) {
        return switch (d) {
            case NORTH -> b ? SHAPE_NORTH_UPPER : SHAPE_NORTH_LOWER;
            case SOUTH -> b ? SHAPE_SOUTH_UPPER : SHAPE_SOUTH_LOWER;
            case EAST -> b ? SHAPE_EAST_UPPER : SHAPE_EAST_LOWER;
            case WEST -> b ? SHAPE_WEST_UPPER : SHAPE_WEST_LOWER;
            default -> SHAPE_NORTH_LOWER;
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShapeForDirection(state.getValue(FACING), state.getValue(HALF) == DoubleBlockHalf.UPPER);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (player.isSpectator()) return InteractionResult.PASS;
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        NetworkHooks.openScreen((ServerPlayer) player, this, extraData -> {
            extraData.writeBlockPos(pos);
        });
        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            if(state.getValue(HALF) == DoubleBlockHalf.LOWER)
                level.setBlockAndUpdate(pos.above(), Blocks.AIR.defaultBlockState());
            else
                level.setBlockAndUpdate(pos.below(), Blocks.AIR.defaultBlockState());
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean triggerEvent(BlockState state, Level level, BlockPos pos, int id, int param) {
        BlockEntity be = level.getBlockEntity(pos);
        return be != null && be.triggerEvent(id, param);
    }

    @Override
    public Component getDisplayName() {
        return BA2.translatable("container", "arcade");
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public ArcadeMenu createMenu(int id, Inventory inv, Player player) {
        return new ArcadeMenu(id);
    }

    public static ArcadeMenu createMenu(int id, Inventory inv, FriendlyByteBuf data) {
        return new ArcadeMenu(id);
    }
}
