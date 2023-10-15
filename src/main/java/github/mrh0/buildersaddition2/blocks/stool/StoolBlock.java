package github.mrh0.buildersaddition2.blocks.stool;

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

public class StoolBlock extends Block implements ISeatBlock {

    public static final EnumProperty<PillowState> PILLOW = EnumProperty.create("pillow", PillowState.class);

    private static VoxelShape SHAPE_BASE = Block.box(2d, 6d, 2d, 14d, 8d, 14d);
    private static VoxelShape SHAPE_PILLOW = Shapes.or(SHAPE_BASE, Block.box(3d, 0d, 3d, 13d, 9d, 13d));
    private static VoxelShape SHAPE_NO_PILLOW = Shapes.or(SHAPE_BASE, Block.box(3d, 0d, 3d, 13d, 8d, 13d));

    public StoolBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState().setValue(PILLOW, PillowState.None));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext col) {
        if(state.getValue(PILLOW) == PillowState.None)
            return SHAPE_NO_PILLOW;
        return SHAPE_PILLOW;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PILLOW);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        boolean type = state.getValue(PILLOW) == PillowState.None;
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
