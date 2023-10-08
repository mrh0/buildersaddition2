package github.mrh0.buildersaddition2.common.variants;

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

    public static WoolVariant WHITE = new WoolVariant(
            "white",
            "White",
            Blocks.WHITE_WOOL,
            "minecraft:block/white_wool"
    );
    public static List<WoolVariant> ALL = List.of(
            WHITE
    );
}
