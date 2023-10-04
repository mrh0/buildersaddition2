package github.mrh0.buildersaddition2.common;

import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class ResourceVariants {
    public static WoodVariant OAK = new WoodVariant("oak", "Oak", Blocks.OAK_PLANKS, Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG);
    public static WoodVariant[] ALL_WOOD = {
            OAK
    };
}
