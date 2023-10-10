package github.mrh0.buildersaddition2.common.variants;

import github.mrh0.buildersaddition2.Utils;
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

    public static WoolVariant WHITE = new WoolVariant(
            "white",
            Blocks.WHITE_WOOL
    );

    public static WoolVariant ORANGE = new WoolVariant(
            "orange",
            Blocks.ORANGE_WOOL
    );

    public static List<WoolVariant> ALL = List.of(
            WHITE,
            ORANGE
    );
}
