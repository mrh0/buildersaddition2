package github.mrh0.buildersaddition2.blocks.bedside_table;

import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlock;
import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlockEntity;
import github.mrh0.buildersaddition2.blocks.base.IComparatorOverride;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BedsideTableBlock extends AbstractStorageBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape SHAPE_BASE = Block.box(0d, 14d, 0d, 16d, 16d, 16d);
    protected static final VoxelShape SHAPE_NW = Block.box(1d, 0d, 1d, 3d, 14d, 3d);
    protected static final VoxelShape SHAPE_NE = Block.box(13d, 0d, 1d, 15d, 14d, 3d);
    protected static final VoxelShape SHAPE_SW = Block.box(1d, 0d, 13d, 3d, 14d, 15d);
    protected static final VoxelShape SHAPE_SE = Block.box(13d, 0d, 13d, 15d, 14d, 15d);

    protected static final VoxelShape SHAPE_BOX_X = Block.box(3d, 8d, 1d, 13d, 16d, 15d);
    protected static final VoxelShape SHAPE_BOX_Z = Block.box(1d, 8d, 3d, 15d, 16d, 13d);

    protected static final VoxelShape SHAPE_X = Shapes.or(SHAPE_BASE, SHAPE_NW, SHAPE_NE, SHAPE_SW, SHAPE_SE, SHAPE_BOX_X);
    protected static final VoxelShape SHAPE_Z = Shapes.or(SHAPE_BASE, SHAPE_NW, SHAPE_NE, SHAPE_SW, SHAPE_SE, SHAPE_BOX_Z);

    public BedsideTableBlock(Properties props) {
        super(props);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    private VoxelShape getShapeForDirection(Direction d) {
        switch (d) {
            case NORTH:
            case SOUTH:
                return SHAPE_Z;
            case EAST:
            case WEST:
                return SHAPE_X;
            default:
                break;
        }
        return SHAPE_Z;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return getShapeForDirection(state.getValue(FACING));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BedsideTableBlockEntity(pos, state);
    }
}
