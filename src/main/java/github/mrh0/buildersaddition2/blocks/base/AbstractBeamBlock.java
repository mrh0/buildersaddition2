package github.mrh0.buildersaddition2.blocks.base;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractBeamBlock extends Block {
    public static final BooleanProperty AXIS_X = BooleanProperty.create("axis_x");
    public static final BooleanProperty AXIS_Y = BooleanProperty.create("axis_y");
    public static final BooleanProperty AXIS_Z = BooleanProperty.create("axis_z");

    public AbstractBeamBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState()
                .setValue(AXIS_X, false)
                .setValue(AXIS_Y, false)
                .setValue(AXIS_Z, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS_X, AXIS_Y, AXIS_Z);
    }

    protected BlockState getState(boolean x, boolean y, boolean z) {
        return defaultBlockState()
                .setValue(AXIS_X, x)
                .setValue(AXIS_Y, y)
                .setValue(AXIS_Z, z);
    }

    protected BlockState getState(Direction.Axis axis) {
        return defaultBlockState()
                .setValue(AXIS_X, axis == Direction.Axis.X)
                .setValue(AXIS_Y, axis == Direction.Axis.Y)
                .setValue(AXIS_Z, axis == Direction.Axis.Z);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext c) {
        return getState(c.getClickedFace().getAxis());
    }
}
