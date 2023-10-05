package github.mrh0.buildersaddition2.datagen;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class BA2BlockStateProvider extends BlockStateProvider {

    public BA2BlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BA2.MODID, exFileHelper);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    @Override
    protected void registerStatesAndModels() {
        BlockBlueprint.ALL_BLUEPRINTS.forEach(blueprint -> {
            blueprint.generateAllBlockStates(this);
        });
    }
}
