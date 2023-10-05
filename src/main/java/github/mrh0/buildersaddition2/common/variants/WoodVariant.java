package github.mrh0.buildersaddition2.common.variants;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

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
}
