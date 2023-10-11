package github.mrh0.buildersaddition2.state;

import net.minecraft.util.StringRepresentable;

public enum CupboardState implements StringRepresentable {
    Single("single"),
    Top("top"),
    Bottom("bottom");

    private String name;

    private static CupboardState[] list = {
            Single,
            Top,
            Bottom
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
        return this == Single;
    }

    public boolean isTop() {
        return this == Top;
    }

    public boolean isBottom() {
        return this == Bottom;
    }
}
