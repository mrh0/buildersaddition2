package github.mrh0.buildersaddition2.common.variants;

import github.mrh0.buildersaddition2.Utils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class LeavesVariant extends ResourceVariant{
    public final Block leaves;
    public final String textureLeaves;

    public LeavesVariant(
            String name,
            String displayName,
            Block leaves,
            String textureLeaves
    ) {
        super(name, displayName);
        this.leaves = leaves;
        this.textureLeaves = textureLeaves;
    }

    public LeavesVariant(String name, Block leaves) {
        this(name, Utils.capitalizeWords(name), leaves, "minecraft:block/"+name+"_leaves");
    }

    public static LeavesVariant OAK = new LeavesVariant("oak", Blocks.OAK_LEAVES);
    public static LeavesVariant SPRUCE = new LeavesVariant("spruce", Blocks.SPRUCE_LEAVES);
    public static LeavesVariant BIRCH = new LeavesVariant("birch", Blocks.BIRCH_LEAVES);
    public static LeavesVariant JUNGLE = new LeavesVariant("jungle", Blocks.JUNGLE_LEAVES);
    public static LeavesVariant ACACIA = new LeavesVariant("acacia", Blocks.ACACIA_LEAVES);
    public static LeavesVariant DARK_OAK = new LeavesVariant("dark_oak", Blocks.DARK_OAK_LEAVES);
    public static LeavesVariant MANGROVE = new LeavesVariant("mangrove", Blocks.MANGROVE_LEAVES);
    public static LeavesVariant CHERRY = new LeavesVariant("cherry", Blocks.CHERRY_LEAVES);

    public static List<LeavesVariant> ALL = List.of(
            OAK,
            SPRUCE,
            BIRCH,
            JUNGLE,
            ACACIA,
            DARK_OAK,
            MANGROVE,
            CHERRY
    );
}
