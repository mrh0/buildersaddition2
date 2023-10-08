package github.mrh0.buildersaddition2.common.variants;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class WoodVariant extends ResourceVariant{
    public final Block planks;
    public final Block log;
    public final Block stripped;

    public final String texturePlanks;
    public final String textureLog;
    public final String textureStripped;

    public WoodVariant(
            String name,
            String displayName,
            Block planks,
            Block log,
            Block stripped,
            String texturePlanks,
            String textureLog,
            String textureStripped
    ) {
        super(name, displayName);
        this.planks = planks;
        this.log = log;
        this.stripped = stripped;

        this.texturePlanks = texturePlanks;
        this.textureLog = textureLog;
        this.textureStripped = textureStripped;
    }

    public static WoodVariant OAK = new WoodVariant(
            "oak",
            "Oak",
            Blocks.OAK_PLANKS,
            Blocks.OAK_LOG,
            Blocks.STRIPPED_OAK_LOG,
            "minecraft:block/oak_planks",
            "minecraft:block/oak_log",
            "minecraft:block/stripped_oak_log"
    );
    public static List<WoodVariant> ALL = List.of(
            OAK
    );
}
