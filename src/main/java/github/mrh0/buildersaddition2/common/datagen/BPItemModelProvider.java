package github.mrh0.buildersaddition2.common.datagen;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class BPItemModelProvider extends ItemModelProvider {
    public BPItemModelProvider(String modid, PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        BlockBlueprint.ALL_BLUEPRINTS.forEach(blueprint -> {
            blueprint.generateAllItemModels(this);
        });
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                BA2.get("item/generated")).texture("layer0",
                BA2.get("item/" + item.getId().getPath()));
    }

    public ItemModelBuilder withParent(String name, ResourceLocation parent) {
        return getBuilder(name).parent(new ModelFile.UncheckedModelFile(parent));
    }
}
