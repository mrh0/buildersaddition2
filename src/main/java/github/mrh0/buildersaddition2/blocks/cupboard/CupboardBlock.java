package github.mrh0.buildersaddition2.blocks.cupboard;

import github.mrh0.buildersaddition2.state.CupboardState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CupboardBlock extends Block {
    public static final EnumProperty<CupboardState> VARIANT = EnumProperty.create("variant", CupboardState.class);
    public static final BooleanProperty MIRROR = BooleanProperty.create("mirror");
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    private static VoxelShape SHAPE_NORTH_LOWER = Block.box(0d, 0d, 1d, 16d, 32d, 16d);
    private static VoxelShape SHAPE_EAST_LOWER = Block.box(0d, 0d, 0d, 15d, 32d, 16d);
    private static VoxelShape SHAPE_SOUTH_LOWER = Block.box(0d, 0d, 0d, 16d, 32d, 15d);
    private static VoxelShape SHAPE_WEST_LOWER = Block.box(1d, 0d, 0d, 16d, 32d, 16d);

    private static VoxelShape SHAPE_NORTH_UPPER = Block.box(0d, -16d, 1d, 16d, 16d, 16d);
    private static VoxelShape SHAPE_EAST_UPPER = Block.box(0d, -16d, 0d, 15d, 16d, 16d);
    private static VoxelShape SHAPE_SOUTH_UPPER = Block.box(0d, -16d, 0d, 16d, 16d, 15d);
    private static VoxelShape SHAPE_WEST_UPPER = Block.box(1d, -16d, 0d, 16d, 16d, 16d);

    public CupboardBlock(Properties props) {
        super(props);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VARIANT, MIRROR, FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        BlockPos above = c.getClickedPos().above();
        BlockPos below = c.getClickedPos().below();
        return super.getStateForPlacement(c);
    }
}
