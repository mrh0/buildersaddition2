package github.mrh0.buildersaddition2.state;

import net.minecraft.util.StringRepresentable;

public enum PillowState implements StringRepresentable {
    None("none"),
    White("white"),
    Orange("orange"),
    Magenta("magenta"),
    LightBlue("light_blue"),
    Yellow("yellow"),
    Lime("lime"),
    Pink("pink"),
    Gray("gray"),
    LightGray("light_gray"),
    Cyan("cyan"),
    Purple("purple"),
    Blue("blue"),
    Brown("brown"),
    Green("green"),
    Red("red"),
    Black("black");

    private String name;

    private static PillowState[] list = {
            White,
            Orange,
            Magenta,
            LightBlue,
            Yellow,
            Lime,
            Pink,
            Gray,
            LightGray,
            Cyan,
            Purple,
            Blue,
            Brown,
            Green,
            Red,
            Black
    };

    PillowState(String name) {
        this.name = name;
    }

    public static PillowState fromIndex(int i) {
        return list[i];
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}