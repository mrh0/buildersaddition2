package github.mrh0.buildersaddition2.blocks.base;

import net.minecraft.core.BlockPos;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class AbstractStorageBlock extends Block implements EntityBlock  {
    public AbstractStorageBlock(Properties props) {
        super(props);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide()) return InteractionResult.SUCCESS;

        //if(!Util.accessCheck(world, pos, state.getValue(FACING).getOpposite()))
        //    return InteractionResult.CONSUME;
        if (level.getBlockEntity(pos) instanceof AbstractStorageBlockEntity be) {
            player.openMenu(be);
            PiglinAi.angerNearbyPiglins(player, true);
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (level.isClientSide()) return;
        if (state.is(newState.getBlock())) return;
        if (level.getBlockEntity(pos) instanceof Container be) {
            Containers.dropContents(level, pos, (Container) be);
            level.updateNeighborsAt(pos, this);
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity ent, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            if (level.getBlockEntity(pos) instanceof AbstractStorageBlockEntity be) {
                be.setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        return world.getBlockEntity(pos) instanceof MenuProvider be ? be : null;
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return IComparatorOverride.getComparatorOverride(world, pos);
    }
}
