package github.mrh0.buildersaddition2.common.variants;

import github.mrh0.buildersaddition2.common.Utils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.List;

public class PolishedVariant extends ResourceVariant {
    public final Block polished;
    public final String texturePolished;
    public PolishedVariant(String name, Block polished) {
        super(name, Utils.capitalizeWords(name));

        this.polished = polished;
        this.texturePolished = "minecraft:block/polished_"+name;
    }

    public static PolishedVariant GRANITE = new PolishedVariant("granite", Blocks.POLISHED_GRANITE);
    public static PolishedVariant DIORITE = new PolishedVariant("diorite", Blocks.POLISHED_DIORITE);
    public static PolishedVariant ANDESITE = new PolishedVariant("andesite", Blocks.POLISHED_ANDESITE);
    public static PolishedVariant DEEPSLATE = new PolishedVariant("deepslate", Blocks.POLISHED_DEEPSLATE);
    public static PolishedVariant BLACKSTONE = new PolishedVariant("blackstone", Blocks.POLISHED_BLACKSTONE);

    public static List<PolishedVariant> ALL = List.of(
            GRANITE,
            DIORITE,
            ANDESITE,
            DEEPSLATE,
            BLACKSTONE
    );
}
