package github.mrh0.buildersaddition2.blocks.stool;

import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.base.ISeatBlock;
import github.mrh0.buildersaddition2.blocks.blockstate.PillowState;
import github.mrh0.buildersaddition2.entity.seat.SeatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class StoolBlock extends Block implements ISeatBlock {

    public static final EnumProperty<PillowState> PILLOW = EnumProperty.create("pillow", PillowState.class);

    private static VoxelShape SHAPE_BASE = Block.box(2d, 6d, 2d, 14d, 8d, 14d);
    private static VoxelShape SHAPE_PILLOW = Shapes.or(SHAPE_BASE, Block.box(3d, 0d, 3d, 13d, 9d, 13d));
    private static VoxelShape SHAPE_NO_PILLOW = Shapes.or(SHAPE_BASE, Block.box(3d, 0d, 3d, 13d, 8d, 13d));

    public StoolBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState().setValue(PILLOW, PillowState.NONE));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext col) {
        if(state.getValue(PILLOW) == PillowState.NONE)
            return SHAPE_NO_PILLOW;
        return SHAPE_PILLOW;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PILLOW);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        boolean type = state.getValue(PILLOW) == PillowState.NONE;
        if(type) {
            Item item = player.getItemInHand(hand).getItem();
            for(int i = 0; i < Index.PILLOW.getBlockCount(); i++) {
                if(item == Index.PILLOW.getBlock(i).asItem()) {
                    if(!player.isCreative())
                        player.getItemInHand(hand).shrink(1);
                    level.setBlockAndUpdate(pos, state.setValue(PILLOW, PillowState.fromIndex(i)));
                    level.playSound(player, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1f, 1f);
                    return ItemInteractionResult.CONSUME;
                }
            }
        }
        return SeatEntity.createSeat(level, pos, player, type ? .45 - 1d/16d : .45d, type ? SoundEvents.WOOD_HIT : SoundEvents.WOOL_HIT);
    }
}
