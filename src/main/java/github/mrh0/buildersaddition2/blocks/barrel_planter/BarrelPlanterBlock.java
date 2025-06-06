package github.mrh0.buildersaddition2.blocks.barrel_planter;

import github.mrh0.buildersaddition2.blocks.blockstate.PlanterState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

public class BarrelPlanterBlock extends Block {
    public static final EnumProperty<PlanterState> STATE = EnumProperty.<PlanterState>create("soil", PlanterState.class);

    public BarrelPlanterBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState().setValue(STATE, PlanterState.DIRT));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE);
    }

    /*
    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, IPlantable plantable) {
        if(state.getValue(STATE) == PlanterState.FARMLAND)
            return plantable.getPlantType(level, pos) == PlantType.CROP || super.canSustainPlant(state, level, pos, facing, plantable);
        else
            return plantable.getPlantType(level, pos) != PlantType.CROP && !super.canSustainPlant(state, level, pos, facing, plantable);
    }
    */

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
                                              InteractionHand hand, BlockHitResult hit) {
        if(state.getValue(STATE) == PlanterState.FARMLAND)
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (stack.getItem() instanceof HoeItem) {
            stack.hurtAndBreak(1, player, EquipmentSlot.byName(hand.name())); // Not sure this is correct
            level.setBlockAndUpdate(pos, defaultBlockState().setValue(STATE, PlanterState.FARMLAND));
            level.playSound(player, pos, SoundEvents.GRAVEL_PLACE, SoundSource.BLOCKS, 1f, 1f);
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
