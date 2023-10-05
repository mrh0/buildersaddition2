package github.mrh0.buildersaddition2.datagen;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BA2BlockModelProvider extends BlockModelProvider {
    public BA2BlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BA2.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        BlockBlueprint.ALL_BLUEPRINTS.forEach(blueprint -> {
            blueprint.generateAllBlockModels(this);
        });
    }
}
