package github.mrh0.buildersaddition2.common.variants;

public class CompoundVariant<A extends ResourceVariant, B extends ResourceVariant> extends ResourceVariant {
    public CompoundVariant(String name, String displayName) {
        super(name, displayName);
    }
}
