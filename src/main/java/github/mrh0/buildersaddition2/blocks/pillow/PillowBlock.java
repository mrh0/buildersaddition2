package github.mrh0.buildersaddition2.blocks.pillow;

import github.mrh0.buildersaddition2.blocks.base.ISeatBlock;
import github.mrh0.buildersaddition2.entity.seat.SeatEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PillowBlock extends Block implements ISeatBlock {

    private static VoxelShape SHAPE_PILLOW = Block.box(3d, 0d, 3d, 13d, 2d, 13d);

    public PillowBlock(Properties props) {
        super(props);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_PILLOW;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        return SeatEntity.createSeat(world, pos, player, .02, SoundEvents.WOOL_HIT);
    }
}
