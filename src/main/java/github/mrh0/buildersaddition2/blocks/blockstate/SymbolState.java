package github.mrh0.buildersaddition2.blocks.blockstate;

import net.minecraft.util.StringRepresentable;

public enum SymbolState implements StringRepresentable {
    ROOF("roof"),
    WALL("wall"),
    FLOOR("floor");

    private String name;

    private static SymbolState[] list = {
            ROOF,
            WALL,
            FLOOR
    };

    SymbolState(String name) {
        this.name = name;
    }

    public static SymbolState fromIndex(int i) {
        return list[i];
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public boolean isRoof() {
        return this == ROOF;
    }

    public boolean isWall() {
        return this == WALL;
    }

    public boolean isFloor() {
        return this == FLOOR;
    }
}
