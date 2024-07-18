package github.mrh0.buildersaddition2.blocks.shop_sign;

import github.mrh0.buildersaddition2.blocks.blockstate.ShopSignState;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class ShopSignBlock extends Block implements EntityBlock {
    public static final EnumProperty<ShopSignState> TYPE = EnumProperty.create("type", ShopSignState.class);

    private static final VoxelShape SHAPE_DOWN_X = Shapes.or(Block.box(1d, 0d, 7d, 15d, 14d, 9d),
            Block.box(2d, 14d, 6d, 4d, 16d, 10d), Block.box(12d, 14d, 6d, 14d, 16d, 10d));
    private static final VoxelShape SHAPE_DOWN_Z = Shapes.or(Block.box(7d, 0d, 1d, 9d, 14d, 15d),
            Block.box(6d, 14d, 2d, 10d, 16d, 4d), Block.box(6d, 14d, 12d, 10d, 16d, 14d));

    private static final VoxelShape SHAPE_UP_X = Shapes.or(Block.box(1d, 2d, 7d, 15d, 16d, 9d),
            Block.box(2d, 0d, 6d, 4d, 2d, 10d), Block.box(12d, 0d, 6d, 14d, 2d, 10d));
    private static final VoxelShape SHAPE_UP_Z = Shapes.or(Block.box(7d, 2d, 1d, 9d, 16d, 15d),
            Block.box(6d, 0d, 2d, 10d, 2d, 4d), Block.box(6d, 0d, 12d, 10d, 2d, 14d));

    private static final VoxelShape SHAPE_NORTH = Shapes.or(Block.box(7d, 1d, 0d, 9d, 15d, 14d),
            Block.box(6d, 2d, 14d, 10d, 4d, 16d), Block.box(6d, 12d, 14d, 10d, 14d, 16d));
    private static final VoxelShape SHAPE_EAST = Shapes.or(Block.box(2d, 1d, 7d, 16d, 15d, 9d),
            Block.box(0d, 2d, 6d, 2d, 4d, 10d), Block.box(0d, 12d, 6d, 2d, 14d, 10d));
    private static final VoxelShape SHAPE_SOUTH = Shapes.or(Block.box(7d, 1d, 2d, 9d, 15d, 16d),
            Block.box(6d, 2d, 0d, 10d, 4d, 2d), Block.box(6d, 12d, 0d, 10d, 14d, 2d));
    private static final VoxelShape SHAPE_WEST = Shapes.or(Block.box(0d, 1d, 7d, 14d, 15d, 9d),
            Block.box(14d, 2d, 6d, 16d, 4d, 10d), Block.box(14d, 12d, 6d, 16d, 14d, 10d));


    public ShopSignBlock(Properties props) {
        super(props);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        return defaultBlockState().setValue(TYPE, ShopSignState.getFor(c.getClickedFace(), c.getHorizontalDirection().getClockWise(), c.getClickedPos(), c.getLevel()));
    }

    @Override
    public @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShape(state.getValue(TYPE));
    }

    public VoxelShape getShape(ShopSignState state) {
        return switch (state) {
            case UP_X -> SHAPE_UP_X;
            case UP_Z -> SHAPE_UP_Z;
            case DOWN_X -> SHAPE_DOWN_X;
            case DOWN_Z -> SHAPE_DOWN_Z;
            case NORTH -> SHAPE_NORTH;
            case EAST -> SHAPE_EAST;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
        };
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if(level.isClientSide()) return InteractionResult.SUCCESS;
        if(level.getBlockEntity(pos) instanceof ShopSignBlockEntity be) {
            if (player.isCrouching()) {
                if (!player.isCreative())
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), be.getDisplayItem());
                be.setDisplayItem(ItemStack.EMPTY);
                level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(level.isClientSide()) return ItemInteractionResult.SUCCESS;
        if(level.getBlockEntity(pos) instanceof ShopSignBlockEntity be) {
            if(player.isCrouching()) {
                if(!player.isCreative())
                    Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), be.getDisplayItem());
                be.setDisplayItem(ItemStack.EMPTY);
                level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                return ItemInteractionResult.CONSUME;
            }
            else if(stack != ItemStack.EMPTY) {
                if(be.hasDisplayItem()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                else {
                    be.setDisplayItem(stack);
                    if(!player.isCreative()) stack.shrink(1);
                    level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                    return ItemInteractionResult.CONSUME;
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        BlockEntity be = level.getBlockEntity(pos);
        if(be == null) return;
        if(be instanceof ShopSignBlockEntity ssbe)
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), ssbe.getDisplayItem());
        be.setRemoved();
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShopSignBlockEntity(pos, state);
    }
}