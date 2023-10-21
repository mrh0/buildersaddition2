package github.mrh0.buildersaddition2.common.variants;

import com.mojang.datafixers.util.Pair;

public abstract class CompoundVariant<A extends ResourceVariant, B extends ResourceVariant> extends ResourceVariant {
    private final Pair<A, B> pair;
    public CompoundVariant(A first, B second) {
        super(first.getName() + " " + second.getName(), first.getDisplayName() + " " + second.getDisplayName());
        pair = Pair.of(first, second);
    }
}
