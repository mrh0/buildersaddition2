package github.mrh0.buildersaddition2.datagen;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.chair.ChairBlock;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

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

    public final int getAngleFromDir(Direction dir, int offset) {
        return (((int) dir.toYRot()) + offset) % 360;
    }

    public final int getAngleFromDir(Direction dir) {
        return getAngleFromDir(dir, 180);
    }

    public MultiPartBlockStateBuilder multipartHorizontalFacing(MultiPartBlockStateBuilder builder, ModelFile model, int offset, boolean uvLock) {
        return builder
                .part().modelFile(model).rotationY(getAngleFromDir(Direction.NORTH, offset)).uvLock(uvLock).addModel().condition(ChairBlock.FACING, Direction.NORTH).end()
                .part().modelFile(model).rotationY(getAngleFromDir(Direction.EAST, offset)).uvLock(uvLock).addModel().condition(ChairBlock.FACING, Direction.EAST).end()
                .part().modelFile(model).rotationY(getAngleFromDir(Direction.SOUTH, offset)).uvLock(uvLock).addModel().condition(ChairBlock.FACING, Direction.SOUTH).end()
                .part().modelFile(model).rotationY(getAngleFromDir(Direction.WEST, offset)).uvLock(uvLock).addModel().condition(ChairBlock.FACING, Direction.WEST).end();
    }
}
