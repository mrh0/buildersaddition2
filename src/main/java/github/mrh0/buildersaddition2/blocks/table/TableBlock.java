package github.mrh0.buildersaddition2.blocks.table;

import github.mrh0.buildersaddition2.blocks.chair.ChairBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class TableBlock extends Block {
    public static final BooleanProperty NW = BooleanProperty.create("nw");
    public static final BooleanProperty NE = BooleanProperty.create("ne");
    public static final BooleanProperty SW = BooleanProperty.create("sw");
    public static final BooleanProperty SE = BooleanProperty.create("se");

    protected static final VoxelShape BASE_SHAPE = Block.box(0d, 14d, 0d, 16d, 16d, 16d);
    protected static final VoxelShape NW_SHAPE = Block.box(1d, 0d, 1d, 3d, 14d, 3d);
    protected static final VoxelShape NE_SHAPE = Block.box(13d, 0d, 1d, 15d, 14d, 3d);
    protected static final VoxelShape SW_SHAPE = Block.box(1d, 0d, 13d, 3d, 14d, 15d);
    protected static final VoxelShape SE_SHAPE = Block.box(13d, 0d, 13d, 15d, 14d, 15d);

    private final Map<BlockState, VoxelShape> shapesCache;

    public TableBlock(Properties props) {
        super(props);
        this.shapesCache = getShapeForEachState(TableBlock::buildShape);
    }

    private static VoxelShape buildShape(BlockState state) {
        return Shapes.or(BASE_SHAPE,
                state.getValue(NE) ? NE_SHAPE : Shapes.empty(),
                state.getValue(NW) ? NW_SHAPE : Shapes.empty(),
                state.getValue(SE) ? SE_SHAPE : Shapes.empty(),
                state.getValue(SW) ? SW_SHAPE : Shapes.empty()
        );
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return this.shapesCache.get(state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NE, NW, SE, SW);
    }

    public BlockState getState(BlockState state, BlockGetter level, BlockPos pos) {
        boolean n = isTable(level.getBlockState(pos.north()));
        boolean w = isTable(level.getBlockState(pos.west()));
        boolean s = isTable(level.getBlockState(pos.south()));
        boolean e = isTable(level.getBlockState(pos.east()));
        boolean nw = isTable(level.getBlockState(pos.north().west()));
        boolean ne = isTable(level.getBlockState(pos.north().east()));
        boolean sw = isTable(level.getBlockState(pos.south().west()));
        boolean se = isTable(level.getBlockState(pos.south().east()));

        return this.defaultBlockState()
                .setValue(NE, (!n && !e) || (!ne && n && e))
                .setValue(NW, (!n && !w) || (!nw && n && w))
                .setValue(SE, (!s && !e) || (!se && s && e))
                .setValue(SW, (!s && !w) || (!sw && s && w));
    }

    private static boolean isTable(BlockState state) {
        return state.getBlock() instanceof TableBlock;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        return getState(this.defaultBlockState(), c.getLevel(), c.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction dir, BlockState newState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return getState(state, level, currentPos);
    }
}
