package github.mrh0.buildersaddition2.common.variants;

import github.mrh0.buildersaddition2.common.Utils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class WoodVariant extends ResourceVariant{
    public final Block planks;
    public final Block log;
    public final Block stripped;
    public final Block stripped2;
    public final Block slab;
    public final Block stairs;
    public final Block fence;
    public final Block door;
    public final Block trapDoor;

    public final String texturePlanks;
    public final String textureLog;
    public final String textureStripped;
    public final String textureStrippedTop;

    public WoodVariant(
            String name,
            String displayName,
            Block planks,
            Block log,
            Block stripped,
            Block stripped2,
            Block slab,
            Block stairs,
            Block fence,
            Block door,
            Block trapDoor,
            String texturePlanks,
            String textureLog,
            String textureStripped,
            String textureStrippedTop
    ) {
        super(name, displayName);
        this.planks = planks;
        this.log = log;
        this.stripped = stripped;
        this.stripped2 = stripped2;
        this.slab = slab;
        this.stairs = stairs;
        this.fence = fence;
        this.door = door;
        this.trapDoor = trapDoor;

        this.texturePlanks = texturePlanks;
        this.textureLog = textureLog;
        this.textureStripped = textureStripped;
        this.textureStrippedTop = textureStrippedTop;
    }

    public WoodVariant(
            String name,
            Block planks,
            Block log,
            Block stripped,
            Block stripped2,
            Block slab,
            Block stairs,
            Block fence,
            Block door,
            Block trapDoor
    ) {
        super(name, Utils.capitalizeWords(name));
        this.planks = planks;
        this.log = log;
        this.stripped = stripped;
        this.stripped2 = stripped2;
        this.slab = slab;
        this.stairs = stairs;
        this.fence = fence;
        this.door = door;
        this.trapDoor = trapDoor;

        this.texturePlanks = "minecraft:block/"+name+"_planks";
        this.textureLog = "minecraft:block/"+name+"_log";
        this.textureStripped = "minecraft:block/stripped_"+name+"_log";
        this.textureStrippedTop = "minecraft:block/stripped_"+name+"_log_top";
    }

    public static WoodVariant OAK = new WoodVariant(
            "oak",
            Blocks.OAK_PLANKS,
            Blocks.OAK_LOG,
            Blocks.STRIPPED_OAK_LOG,
            Blocks.STRIPPED_OAK_WOOD,
            Blocks.OAK_SLAB,
            Blocks.OAK_STAIRS,
            Blocks.OAK_FENCE,
            Blocks.OAK_DOOR,
            Blocks.OAK_TRAPDOOR
    );

    public static WoodVariant SPRUCE = new WoodVariant(
            "spruce",
            Blocks.SPRUCE_PLANKS,
            Blocks.SPRUCE_LOG,
            Blocks.STRIPPED_SPRUCE_LOG,
            Blocks.STRIPPED_SPRUCE_WOOD,
            Blocks.SPRUCE_SLAB,
            Blocks.SPRUCE_STAIRS,
            Blocks.SPRUCE_FENCE,
            Blocks.SPRUCE_DOOR,
            Blocks.SPRUCE_TRAPDOOR
    );

    public static WoodVariant BIRCH = new WoodVariant(
            "birch",
            Blocks.BIRCH_PLANKS,
            Blocks.BIRCH_LOG,
            Blocks.STRIPPED_BIRCH_LOG,
            Blocks.STRIPPED_BIRCH_WOOD,
            Blocks.BIRCH_SLAB,
            Blocks.BIRCH_STAIRS,
            Blocks.BIRCH_FENCE,
            Blocks.BIRCH_DOOR,
            Blocks.BIRCH_TRAPDOOR
    );

    public static WoodVariant JUNGLE = new WoodVariant(
            "jungle",
            Blocks.JUNGLE_PLANKS,
            Blocks.JUNGLE_LOG,
            Blocks.STRIPPED_JUNGLE_LOG,
            Blocks.STRIPPED_JUNGLE_WOOD,
            Blocks.JUNGLE_SLAB,
            Blocks.JUNGLE_STAIRS,
            Blocks.JUNGLE_FENCE,
            Blocks.JUNGLE_DOOR,
            Blocks.JUNGLE_TRAPDOOR
    );

    public static WoodVariant ACACIA = new WoodVariant(
            "acacia",
            Blocks.ACACIA_PLANKS,
            Blocks.ACACIA_LOG,
            Blocks.STRIPPED_ACACIA_LOG,
            Blocks.STRIPPED_ACACIA_WOOD,
            Blocks.ACACIA_SLAB,
            Blocks.ACACIA_STAIRS,
            Blocks.ACACIA_FENCE,
            Blocks.JUNGLE_DOOR,
            Blocks.JUNGLE_TRAPDOOR
    );

    public static WoodVariant DARK_OAK = new WoodVariant(
            "dark_oak",
            Blocks.DARK_OAK_PLANKS,
            Blocks.DARK_OAK_LOG,
            Blocks.STRIPPED_DARK_OAK_LOG,
            Blocks.STRIPPED_DARK_OAK_WOOD,
            Blocks.DARK_OAK_SLAB,
            Blocks.DARK_OAK_STAIRS,
            Blocks.DARK_OAK_FENCE,
            Blocks.DARK_OAK_DOOR,
            Blocks.DARK_OAK_TRAPDOOR
    );

    public static WoodVariant MANGROVE = new WoodVariant(
            "mangrove",
            Blocks.MANGROVE_PLANKS,
            Blocks.MANGROVE_LOG,
            Blocks.STRIPPED_MANGROVE_LOG,
            Blocks.STRIPPED_MANGROVE_WOOD,
            Blocks.MANGROVE_SLAB,
            Blocks.MANGROVE_STAIRS,
            Blocks.MANGROVE_FENCE,
            Blocks.MANGROVE_DOOR,
            Blocks.MANGROVE_TRAPDOOR
    );

    public static WoodVariant CHERRY = new WoodVariant(
            "cherry",
            Blocks.CHERRY_PLANKS,
            Blocks.CHERRY_LOG,
            Blocks.STRIPPED_CHERRY_LOG,
            Blocks.STRIPPED_CHERRY_WOOD,
            Blocks.CHERRY_SLAB,
            Blocks.CHERRY_STAIRS,
            Blocks.CHERRY_FENCE,
            Blocks.CHERRY_DOOR,
            Blocks.CHERRY_TRAPDOOR
    );

    public static WoodVariant BAMBOO = new WoodVariant(
            "bamboo",
            "Bamboo",
            Blocks.BAMBOO_PLANKS,
            Blocks.BAMBOO_BLOCK,
            Blocks.STRIPPED_BAMBOO_BLOCK,
            Blocks.STRIPPED_BAMBOO_BLOCK, // Bamboo has no stripped variant 2
            Blocks.BAMBOO_SLAB,
            Blocks.BAMBOO_STAIRS,
            Blocks.BAMBOO_FENCE,
            Blocks.BAMBOO_DOOR,
            Blocks.BAMBOO_TRAPDOOR,
            "minecraft:block/bamboo_planks",
            "minecraft:block/bamboo_block",
            "minecraft:block/stripped_bamboo_block",
            "minecraft:block/stripped_bamboo_block_top"
    );

    public static WoodVariant CRIMSON = new WoodVariant(
            "crimson",
            "Crimson",
            Blocks.CRIMSON_PLANKS,
            Blocks.CRIMSON_STEM,
            Blocks.STRIPPED_CRIMSON_STEM,
            Blocks.STRIPPED_CRIMSON_HYPHAE,
            Blocks.CRIMSON_SLAB,
            Blocks.CRIMSON_STAIRS,
            Blocks.CRIMSON_FENCE,
            Blocks.CRIMSON_DOOR,
            Blocks.CRIMSON_TRAPDOOR,
            "minecraft:block/crimson_planks",
            "minecraft:block/crimson_stem",
            "minecraft:block/stripped_crimson_stem",
            "minecraft:block/stripped_crimson_stem_top"
    );

    public static WoodVariant WARPED = new WoodVariant(
            "warped",
            "Warped",
            Blocks.WARPED_PLANKS,
            Blocks.WARPED_STEM,
            Blocks.STRIPPED_WARPED_STEM,
            Blocks.STRIPPED_WARPED_HYPHAE,
            Blocks.WARPED_SLAB,
            Blocks.WARPED_STAIRS,
            Blocks.WARPED_FENCE,
            Blocks.WARPED_DOOR,
            Blocks.WARPED_TRAPDOOR,
            "minecraft:block/warped_planks",
            "minecraft:block/warped_stem",
            "minecraft:block/stripped_warped_stem",
            "minecraft:block/stripped_warped_stem_top"
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
