package github.mrh0.buildersaddition2.blocks.blockstate;

import net.minecraft.util.StringRepresentable;

public enum PillowState implements StringRepresentable {
    NONE("none"),
    WHITE("white"),
    ORANGE("orange"),
    MAGENTA("magenta"),
    LIGHT_BLUE("light_blue"),
    YELLOW("yellow"),
    LIME("lime"),
    PINK("pink"),
    Gray("gray"),
    LIGHT_GRAY("light_gray"),
    CYAN("cyan"),
    PURPLE("purple"),
    BLUE("blue"),
    BROWN("brown"),
    GREEN("green"),
    RED("red"),
    BLACK("black");

    private String name;

    private static PillowState[] list = {
            WHITE,
            ORANGE,
            MAGENTA,
            LIGHT_BLUE,
            YELLOW,
            LIME,
            PINK,
            Gray,
            LIGHT_GRAY,
            CYAN,
            PURPLE,
            BLUE,
            BROWN,
            GREEN,
            RED,
            BLACK
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