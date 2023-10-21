package github.mrh0.buildersaddition2.common.variants;

import github.mrh0.buildersaddition2.common.Utils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class WoolVariant extends ResourceVariant{
    public static List<String> woolColors = List.of(
            "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "light_gray", "cyan", "purple", "blue", "brown", "green", "red", "black"
    );
    public final Block wool;

    public final String textureWool;

    public WoolVariant(
            String name,
            String displayName,
            Block wool,
            String textureWool
    ) {
        super(name, displayName);
        this.wool = wool;

        this.textureWool = textureWool;
    }

    public WoolVariant(String name, Block wool) {
        this(name, Utils.capitalizeWords(name), wool, "minecraft:block/" + name + "_wool");
    }

    public static WoolVariant WHITE = new WoolVariant("white", Blocks.WHITE_WOOL);
    public static WoolVariant ORANGE = new WoolVariant("orange", Blocks.ORANGE_WOOL);
    public static WoolVariant MAGENTA = new WoolVariant("magenta", Blocks.MAGENTA_WOOL);
    public static WoolVariant LIGHT_BLUE = new WoolVariant("light_blue", Blocks.LIGHT_BLUE_WOOL);
    public static WoolVariant YELLOW = new WoolVariant("yellow", Blocks.YELLOW_WOOL);
    public static WoolVariant LIME = new WoolVariant("lime", Blocks.LIME_WOOL);
    public static WoolVariant PINK = new WoolVariant("pink", Blocks.PINK_WOOL);
    public static WoolVariant GRAY = new WoolVariant("gray", Blocks.GRAY_WOOL);
    public static WoolVariant LIGHT_GRAY = new WoolVariant("light_gray", Blocks.LIGHT_GRAY_WOOL);
    public static WoolVariant CYAN = new WoolVariant("cyan", Blocks.CYAN_WOOL);
    public static WoolVariant PURPLE = new WoolVariant("purple", Blocks.PURPLE_WOOL);
    public static WoolVariant BLUE = new WoolVariant("blue", Blocks.BLUE_WOOL);
    public static WoolVariant BROWN = new WoolVariant("brown", Blocks.BROWN_WOOL);
    public static WoolVariant GREEN = new WoolVariant("green", Blocks.GREEN_WOOL);
    public static WoolVariant RED = new WoolVariant("red", Blocks.RED_WOOL);
    public static WoolVariant BLACK = new WoolVariant("black", Blocks.BLACK_WOOL);

    public static List<WoolVariant> ALL = List.of(
            WHITE,
            ORANGE,
            MAGENTA,
            LIGHT_BLUE,
            YELLOW,
            LIME,
            PINK,
            GRAY,
            LIGHT_GRAY,
            CYAN,
            PURPLE,
            BLUE,
            BROWN,
            GREEN,
            RED,
            BLACK
    );
}
