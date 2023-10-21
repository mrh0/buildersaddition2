package github.mrh0.buildersaddition2.common.variants;

public abstract class ResourceVariant {
    private final String name;
    private final String displayName;
    public ResourceVariant(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
