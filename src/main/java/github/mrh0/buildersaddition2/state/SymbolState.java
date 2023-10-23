package github.mrh0.buildersaddition2.state;

import net.minecraft.util.StringRepresentable;

public enum SymbolState implements StringRepresentable {
    Roof("roof"),
    Wall("wall"),
    Floor("floor");

    private String name;

    private static SymbolState[] list = {
            Roof,
            Wall,
            Floor
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
        return this == Roof;
    }

    public boolean isWall() {
        return this == Wall;
    }

    public boolean isFloor() {
        return this == Floor;
    }
}
