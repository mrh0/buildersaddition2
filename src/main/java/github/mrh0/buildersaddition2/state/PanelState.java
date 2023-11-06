package github.mrh0.buildersaddition2.state;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

public enum PanelState implements StringRepresentable {
    NORTH("north", Direction.NORTH),
    WEST("west", Direction.WEST),
    EAST("east", Direction.EAST),
    SOUTH("south", Direction.SOUTH),
    DOUBLE_X("double_x", Direction.EAST),
    DOUBLE_Z("double_z", Direction.NORTH);

    private final String name;
    private final Direction facing;

    private PanelState(String name, Direction facing) {
        this.name = name;
        this.facing = facing;
    }

    public Direction getFacing() {
        return this.facing;
    }

    public String toString() {
        return this.name;
    }

    public boolean isDouble() {
        return this == PanelState.DOUBLE_X || this == PanelState.DOUBLE_Z;
    }

    public PanelState getStateFromDirection(Direction dir) {
        return switch (dir) {
            case NORTH -> NORTH;
            case EAST -> EAST;
            case WEST -> WEST;
            default -> SOUTH;
        };
    }

    public static PanelState forFacings(Direction clickedSide, Direction entityFacing) {
        if(clickedSide == Direction.UP || clickedSide == Direction.DOWN) {
            if(entityFacing == Direction.NORTH)
                return PanelState.NORTH;
            if(entityFacing == Direction.EAST)
                return PanelState.EAST;
            if(entityFacing == Direction.SOUTH)
                return PanelState.SOUTH;
            if(entityFacing == Direction.WEST)
                return PanelState.WEST;
        }
        if(clickedSide == Direction.NORTH) return PanelState.SOUTH;
        if(clickedSide == Direction.EAST) return PanelState.WEST;
        if(clickedSide == Direction.SOUTH) return PanelState.NORTH;
        if(clickedSide == Direction.WEST) return PanelState.EAST;
        return PanelState.NORTH;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static PanelState reverseFacing(Direction facing, boolean doub) {
        if(doub) return facing.getAxis() == Direction.Axis.X ? DOUBLE_X : DOUBLE_Z;
        return switch(facing) {
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            default -> NORTH;
        };
    }

    public float toYRot() {
        return switch(this) {
            case EAST -> Direction.EAST.toYRot();
            case SOUTH -> Direction.SOUTH.toYRot();
            case WEST -> Direction.WEST.toYRot();
            default -> Direction.NORTH.toYRot();
        };
    }
}
