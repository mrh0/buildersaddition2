package github.mrh0.buildersaddition2.state;

import net.minecraft.util.StringRepresentable;

public enum PlanterState implements StringRepresentable {
    DIRT("dirt"),
    FARMLAND("farmland");

    private final String name;

    PlanterState(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}


