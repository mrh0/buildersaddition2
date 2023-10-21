package github.mrh0.buildersaddition2.common.variants;

import github.mrh0.buildersaddition2.common.Utils;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class CounterVariant extends ResourceVariant {
    public final WoodVariant wood;

    public final Block top;
    public final String textureTop;

    public CounterVariant(WoodVariant wood, Block top, String textureTop) {
        super(wood.getName(), wood.getDisplayName());

        this.wood = wood;
        this.top = top;
        this.textureTop = textureTop;
    }

    public CounterVariant(WoodVariant wood, PolishedVariant polished) {
        super(wood.getName() + "_" + polished.getName(), wood.getDisplayName() + " " + polished.getDisplayName());

        this.wood = wood;
        this.top = polished.polished;
        this.textureTop = polished.texturePolished;
    }

    public static final List<CounterVariant> ALL = new ArrayList<>();

    static {
        WoodVariant.ALL.forEach(wood -> {
            ALL.add(new CounterVariant(wood, wood.stripped, wood.textureStripped));
            PolishedVariant.ALL.forEach(polished -> {
                ALL.add(new CounterVariant(wood, polished));
            });
        });
    }
}
