package github.mrh0.buildersaddition2.state;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

public enum BenchState implements StringRepresentable {
    NORTH("north"),
    WEST("west"),
    SOUTH("south"),
    EAST("east"),
    NONE_X("none_x"),
    NONE_Z("none_z"),
    BOTH_X("both_x"),
    BOTH_Z("both_z");

    private String name;

    BenchState(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public Direction.Axis getAxis() {
        if(this == NORTH || this == SOUTH || this == NONE_Z || this == BOTH_Z)
            return Direction.Axis.Z;
        return Direction.Axis.X;
    }

    public boolean hasLegNorth() {
        return this == SOUTH || this == BOTH_Z;
    }

    public boolean hasLegWest() {
        return this == EAST || this == BOTH_X;
    }

    public boolean hasLegSouth() {
        return this == NORTH || this == BOTH_Z;
    }

    public boolean hasLegEast() {
        return this == WEST || this == BOTH_X;
    }
}