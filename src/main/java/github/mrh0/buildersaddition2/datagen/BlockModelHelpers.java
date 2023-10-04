package github.mrh0.buildersaddition2.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockModelHelpers {
    public static ModelFile parent(ResourceLocation rl, ExistingFileHelper efh, ResourceLocation baseModel) {
        return new BlockModelBuilder(rl, efh).parent(new ModelFile.ExistingModelFile(baseModel, efh));
    }
}
