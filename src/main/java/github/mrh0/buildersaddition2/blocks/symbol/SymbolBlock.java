package github.mrh0.buildersaddition2.blocks.symbol;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class SymbolBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape NORTH_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16D, 16D, 8D);
    protected static final VoxelShape EAST_SHAPE = Block.box(8D, 0.0D, 0.0D, 16D, 16.0D, 16.0D);
    protected static final VoxelShape SOUTH_SHAPE = Block.box(0.0D, 0.0D, 8D, 16D, 16D, 16D);
    protected static final VoxelShape WEST_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);

    public SymbolBlock(Properties props) {
        super(props);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
        return null;
    }
}
