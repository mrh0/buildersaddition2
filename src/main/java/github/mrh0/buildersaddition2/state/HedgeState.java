package github.mrh0.buildersaddition2.state;

import net.minecraft.util.StringRepresentable;

public enum HedgeState implements StringRepresentable {
    NONE("none"),
    S_X("straight_x"),
    S_Z("straight_z"),
    C_NW("corner_nw"),
    C_NE("corner_ne"),
    C_SE("corner_se"),
    C_SW("corner_sw"),
    T_N("tcross_n"),
    T_E("tcross_e"),
    T_S("tcross_s"),
    T_W("tcross_w"),
    C("cross");

    private String name;

    private HedgeState(String name) {
        this.name = name;
    }

	public String getModelName() {
        return switch(this) {
            case S_X, S_Z -> "straight";
            case C_NW, C_NE, C_SE, C_SW -> "corner";
            case T_N, T_E, T_S, T_W -> "tcross";
            case C -> "cross";
            default -> "none";
        };
    }

    public int getModelYRotation() {
        return switch(this) {
            case S_X, C_NE, T_E -> 90;
            case C_SE, T_S -> 180;
            case C_SW, T_W -> 270;
            default -> 0;
        };
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}