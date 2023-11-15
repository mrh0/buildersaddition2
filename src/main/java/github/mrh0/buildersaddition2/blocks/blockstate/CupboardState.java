package github.mrh0.buildersaddition2.blocks.blockstate;

import net.minecraft.util.StringRepresentable;

public enum CupboardState implements StringRepresentable {
    SINGLE("single"),
    TOP("top"),
    BOTTOM("bottom");

    private String name;

    private static CupboardState[] list = {
            SINGLE,
            TOP,
            BOTTOM
    };

    CupboardState(String name) {
        this.name = name;
    }

    public static CupboardState fromIndex(int i) {
        return list[i];
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public boolean isSingle() {
        return this == SINGLE;
    }

    public boolean isTop() {
        return this == TOP;
    }

    public boolean isBottom() {
        return this == BOTTOM;
    }
}
