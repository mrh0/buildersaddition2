package github.mrh0.buildersaddition2.common.datagen;

import github.mrh0.buildersaddition2.common.BlockBlueprint;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BPBlockModelProvider extends BlockModelProvider {
    public BPBlockModelProvider(String modid, PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        BlockBlueprint.ALL_BLUEPRINTS.forEach(blueprint -> {
            blueprint.generateAllBlockModels(this);
        });
    }

    public BlockModelBuilder withParent(String name, ResourceLocation parent) {
        return getBuilder(name).parent(new ModelFile.UncheckedModelFile(parent));
    }
}
