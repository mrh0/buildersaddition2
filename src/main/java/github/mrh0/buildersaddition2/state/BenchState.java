package github.mrh0.buildersaddition2.state;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

public enum BenchState implements StringRepresentable {
    North("north"),
    West("west"),
    South("south"),
    East("east"),
    None_X("none_x"),
    None_Z("none_z"),
    Both_X("both_x"),
    Both_Z("both_z");

    private String name;

    BenchState(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public Direction.Axis getAxis() {
        if(this == North || this == South || this == None_Z || this == Both_Z)
            return Direction.Axis.Z;
        return Direction.Axis.X;
    }

    public boolean hasLegNorth() {
        return this == South || this == Both_Z;
    }

    public boolean hasLegWest() {
        return this == East || this == Both_X;
    }

    public boolean hasLegSouth() {
        return this == North || this == Both_Z;
    }

    public boolean hasLegEast() {
        return this == West || this == Both_X;
    }
}