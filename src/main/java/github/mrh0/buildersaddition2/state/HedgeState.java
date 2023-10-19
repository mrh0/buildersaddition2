package github.mrh0.buildersaddition2.state;

import net.minecraft.util.StringRepresentable;

public enum HedgeState implements StringRepresentable {
    None("none"),
    Straight_X("straight_x"),
    Straight_Z("straight_z"),
    Corner_NW("corner_nw"),
    Corner_NE("corner_ne"),
    Corner_SE("corner_se"),
    Corner_SW("corner_sw"),
    TCross_N("tcross_n"),
    TCross_E("tcross_e"),
    TCross_S("tcross_s"),
    TCross_W("tcross_w"),
    Cross("cross");

    private String name;

    private HedgeState(String name) {
        this.name = name;
    }

	public String getModelName() {
        return switch(this) {
            case Straight_X, Straight_Z -> "straight";
            case Corner_NW, Corner_NE, Corner_SE, Corner_SW -> "corner";
            case TCross_N, TCross_E, TCross_S, TCross_W -> "tcross";
            case Cross -> "cross";
            default -> "none";
        };
    }

    public int getModelYRotation() {
        return switch(this) {
            case Straight_X, Corner_NE, TCross_E -> 90;
            case Corner_SE, TCross_S -> 180;
            case Corner_SW, TCross_W -> 270;
            default -> 0;
        };
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}