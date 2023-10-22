package github.mrh0.buildersaddition2.common.variants;

public class SingleVariant extends ResourceVariant {
    public SingleVariant() {
        super("ERROR", "ERROR");
    }

    @Override
    public String getRegistryName(String blockName) {
        return blockName;
    }

    public static final SingleVariant INSTANCE = new SingleVariant();
}
