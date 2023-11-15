package github.mrh0.buildersaddition2.blocks.bookshelf;

import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlock;
import github.mrh0.buildersaddition2.blocks.cabinet.CabinetBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BookshelfBlock extends AbstractStorageBlock {
    public static final BooleanProperty BOOK0 = BooleanProperty.create("book0");
    public static final BooleanProperty BOOK1 = BooleanProperty.create("book1");
    public static final BooleanProperty BOOK2 = BooleanProperty.create("book2");
    public static final BooleanProperty BOOK3 = BooleanProperty.create("book3");
    public static final BooleanProperty BOOK4 = BooleanProperty.create("book4");
    public static final BooleanProperty BOOK5 = BooleanProperty.create("book5");
    public static final BooleanProperty BOOK6 = BooleanProperty.create("book6");
    public static final BooleanProperty BOOK7 = BooleanProperty.create("book7");

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape SHAPE_NORTH = Block.box(0.0D, 0.0D, 0.0D, 16D, 16D, 8D);
    protected static final VoxelShape SHAPE_EAST = Block.box(8D, 0.0D, 0.0D, 16D, 16.0D, 16.0D);
    protected static final VoxelShape SHAPE_SOUTH = Block.box(0.0D, 0.0D, 8D, 16D, 16D, 16D);
    protected static final VoxelShape SHAPE_WEST = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 16.0D, 16.0D);

    public BookshelfBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH)
                .setValue(BOOK0, false).setValue(BOOK1, false).setValue(BOOK2, false).setValue(BOOK3, false)
                .setValue(BOOK4, false).setValue(BOOK5, false).setValue(BOOK6, false).setValue(BOOK7, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, BOOK0, BOOK1, BOOK2, BOOK3, BOOK4, BOOK5, BOOK6, BOOK7);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        return this.defaultBlockState().setValue(FACING, c.getHorizontalDirection());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CabinetBlockEntity(pos, state);
    }

    private VoxelShape getShapeForDirection(Direction direction) {
        return switch (direction) {
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_NORTH;
        };
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return getShapeForDirection(state.getValue(FACING));
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, LevelReader level, BlockPos pos) {
        return Math.min((float)getBookSum(state, level, pos)/3f, 6);
    }

    public static BlockState getState(BlockState state, boolean b1, boolean b2, boolean b3, boolean b4, boolean b5, boolean b6, boolean b7, boolean b8) {
        return state
                .setValue(BOOK0, b1).setValue(BOOK1, b2).setValue(BOOK2, b3).setValue(BOOK3, b4)
                .setValue(BOOK4, b5).setValue(BOOK5, b6).setValue(BOOK6, b7).setValue(BOOK7, b8);
    }

    public int getBookSum(BlockState state, LevelReader level, BlockPos pos) {
        int sum = 0;
        if(level.getBlockEntity(pos) instanceof BookshelfBlockEntity be) {
            for(int i = 0; i < be.getItems().size(); i++)
                if(be.getItems().get(i).getCount() > 0) sum++;
        }
        return sum;
    }
}
