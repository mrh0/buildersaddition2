package github.mrh0.buildersaddition2.blocks.shelf;

import com.mojang.serialization.MapCodec;
import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlock;
import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ShelfBlock extends AbstractStorageBlock {
    public static final MapCodec<ShelfBlock> CODEC = simpleCodec(ShelfBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    //protected static final VoxelShape NORTH_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16D, 16D, 8D);
    //protected static final VoxelShape EAST_SHAPE = Block.box(8D, 0.0D, 0.0D, 16D, 16.0D, 16.0D);
    //protected static final VoxelShape SOUTH_SHAPE = Block.box(0.0D, 0.0D, 8D, 16D, 16D, 16D);
    //protected static final VoxelShape WEST_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);

    protected static final VoxelShape NORTH_SHAPE_BOTTOM = Block.box(0d, 0d, 0d, 16d, 2d, 8d);
    protected static final VoxelShape EAST_SHAPE_BOTTOM = Block.box(8D, 0d, 0d, 16d, 2d, 16d);
    protected static final VoxelShape SOUTH_SHAPE_BOTTOM = Block.box(0d, 0d, 8d, 16d, 2d, 16d);
    protected static final VoxelShape WEST_SHAPE_BOTTOM = Block.box(0d, 0d, 0d, 8d, 2d, 16d);

    protected static final VoxelShape NORTH_SHAPE_TOP = Block.box(0d, 8d, 0d, 16d, 10d, 8d);
    protected static final VoxelShape EAST_SHAPE_TOP = Block.box(8D, 8d, 0d, 16d, 10d, 16d);
    protected static final VoxelShape SOUTH_SHAPE_TOP = Block.box(0d, 8d, 8d, 16d, 10d, 16d);
    protected static final VoxelShape WEST_SHAPE_TOP = Block.box(0d, 8d, 0d, 8d, 10d, 16d);

    public ShelfBlock(Properties props) {
        super(props);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        return this.defaultBlockState().setValue(FACING, c.getHorizontalDirection());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext c) {
        return switch (state.getValue(FACING)) {
            case EAST -> Shapes.or(EAST_SHAPE_BOTTOM, EAST_SHAPE_TOP);
            case SOUTH -> Shapes.or(SOUTH_SHAPE_BOTTOM, SOUTH_SHAPE_TOP);
            case WEST -> Shapes.or(WEST_SHAPE_BOTTOM, WEST_SHAPE_TOP);
            default -> Shapes.or(NORTH_SHAPE_BOTTOM, NORTH_SHAPE_TOP);
        };
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;
        //if(!Util.accessCheck(world, pos, state.getValue(FACING).getOpposite()))
        //    return InteractionResult.CONSUME;
        if (level.getBlockEntity(pos) instanceof AbstractStorageBlockEntity be) {
            if(!(player instanceof ServerPlayer fsp)) return InteractionResult.SUCCESS;
            fsp.openMenu( be, extraData -> {
                extraData.writeBlockPos(pos);
            });
            PiglinAi.angerNearbyPiglins(player, true);
        }
        return InteractionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ShelfBlockEntity(pos, state);
    }
}
