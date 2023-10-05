package github.mrh0.buildersaddition2.blocks.chair;

import github.mrh0.buildersaddition2.blocks.base.AbstractSeatBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class ChairBlock extends AbstractSeatBlock {

    public ChairBlock(Properties props) {
        super(props);
    }

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
