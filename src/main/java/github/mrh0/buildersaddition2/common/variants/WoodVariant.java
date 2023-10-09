package github.mrh0.buildersaddition2.common.variants;

import github.mrh0.buildersaddition2.Utils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class WoodVariant extends ResourceVariant{
    public final Block planks;
    public final Block log;
    public final Block stripped;
    public final Block slab;

    public final String texturePlanks;
    public final String textureLog;
    public final String textureStripped;

    public WoodVariant(
            String name,
            String displayName,
            Block planks,
            Block log,
            Block stripped,
            Block slab,
            String texturePlanks,
            String textureLog,
            String textureStripped
    ) {
        super(name, displayName);
        this.planks = planks;
        this.log = log;
        this.stripped = stripped;
        this.slab = slab;

        this.texturePlanks = texturePlanks;
        this.textureLog = textureLog;
        this.textureStripped = textureStripped;
    }

    public WoodVariant(
            String name,
            Block planks,
            Block log,
            Block stripped,
            Block slab
    ) {
        super(name, Utils.capitalizeWords(name));
        this.planks = planks;
        this.log = log;
        this.stripped = stripped;
        this.slab = slab;

        this.texturePlanks = "minecraft:block/"+name+"_planks";
        this.textureLog = "minecraft:block/"+name+"_log";
        this.textureStripped = "minecraft:block/stripped_"+name+"_log";
    }

    public static WoodVariant OAK = new WoodVariant(
            "oak",
            Blocks.OAK_PLANKS,
            Blocks.OAK_LOG,
            Blocks.STRIPPED_OAK_LOG,
            Blocks.OAK_SLAB
    );

    public static WoodVariant SPRUCE = new WoodVariant(
            "spruce",
            Blocks.SPRUCE_PLANKS,
            Blocks.SPRUCE_LOG,
            Blocks.STRIPPED_SPRUCE_LOG,
            Blocks.SPRUCE_SLAB
    );

    public static WoodVariant BIRCH = new WoodVariant(
            "birch",
            Blocks.BIRCH_PLANKS,
            Blocks.BIRCH_LOG,
            Blocks.STRIPPED_BIRCH_LOG,
            Blocks.BIRCH_SLAB
    );

    public static WoodVariant JUNGLE = new WoodVariant(
            "jungle",
            Blocks.JUNGLE_PLANKS,
            Blocks.JUNGLE_LOG,
            Blocks.STRIPPED_JUNGLE_LOG,
            Blocks.JUNGLE_SLAB
    );

    public static WoodVariant ACACIA = new WoodVariant(
            "acacia",
            Blocks.ACACIA_PLANKS,
            Blocks.ACACIA_LOG,
            Blocks.STRIPPED_ACACIA_LOG,
            Blocks.ACACIA_SLAB
    );

    public static WoodVariant DARK_OAK = new WoodVariant(
            "dark_oak",
            Blocks.DARK_OAK_PLANKS,
            Blocks.DARK_OAK_LOG,
            Blocks.STRIPPED_DARK_OAK_LOG,
            Blocks.DARK_OAK_SLAB
    );

    public static WoodVariant MANGROVE = new WoodVariant(
            "mangrove",
            Blocks.MANGROVE_PLANKS,
            Blocks.MANGROVE_LOG,
            Blocks.STRIPPED_MANGROVE_LOG,
            Blocks.MANGROVE_SLAB
    );

    public static WoodVariant CHERRY = new WoodVariant(
            "cherry",
            Blocks.CHERRY_PLANKS,
            Blocks.CHERRY_LOG,
            Blocks.STRIPPED_CHERRY_LOG,
            Blocks.CHERRY_SLAB
    );

    public static WoodVariant BAMBOO = new WoodVariant(
            "bamboo",
            Blocks.BAMBOO_PLANKS,
            Blocks.BAMBOO_BLOCK,
            Blocks.STRIPPED_BAMBOO_BLOCK,
            Blocks.BAMBOO_SLAB
    );

    public static WoodVariant CRIMSON = new WoodVariant(
            "crimson",
            Blocks.CRIMSON_PLANKS,
            Blocks.CRIMSON_STEM,
            Blocks.STRIPPED_CRIMSON_STEM,
            Blocks.CRIMSON_SLAB
    );

    public static WoodVariant WARPED = new WoodVariant(
            "warped",
            Blocks.WARPED_PLANKS,
            Blocks.WARPED_STEM,
            Blocks.STRIPPED_WARPED_STEM,
            Blocks.WARPED_SLAB
    );

    public static List<WoodVariant> ALL = List.of(
            OAK,
            SPRUCE,
            BIRCH,
            JUNGLE,
            ACACIA,
            DARK_OAK,
            MANGROVE,
            CHERRY,
            BAMBOO,
            CRIMSON,
            WARPED
    );
}
