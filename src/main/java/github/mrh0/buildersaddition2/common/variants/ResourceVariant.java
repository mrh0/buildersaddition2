package github.mrh0.buildersaddition2.common.variants;

public abstract class ResourceVariant {
    public final String name;
    public final String displayName;
    public ResourceVariant(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return name;
    }
}
