package github.mrh0.buildersaddition2.blocks.sofa;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.blocks.chair.ChairBlock;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.WoolVariant;
import github.mrh0.buildersaddition2.datagen.BA2BlockModelProvider;
import github.mrh0.buildersaddition2.datagen.BA2BlockStateProvider;
import github.mrh0.buildersaddition2.datagen.BA2ItemModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.StairsShape;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class SofaBlueprint extends BlockBlueprint<WoolVariant, SofaBlock> {
    public SofaBlueprint(Iterable<WoolVariant> variants) {
        super(variants);
    }

    @Override
    protected Supplier<SofaBlock> getBlock(WoolVariant variant) {
        return () -> new SofaBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL));
    }

    @Override
    public String getBaseName() {
        return "sofa";
    }

    @Override
    public String getLangName(WoolVariant variant) {
        return variant.displayName + " Sofa";
    }

    @Override
    protected void buildBlockState(BA2BlockStateProvider bsp, RegistryObject<SofaBlock> block, WoolVariant variant) {
        boolean uvLock = true;
        int offset = 180;
        var straight = model(getBlockModelPath(variant, "_none"));
        var inner = model(getBlockModelPath(variant, "_inner_corner"));
        var outer = model(getBlockModelPath(variant, "_outer_corner"));

        bsp.getMultipartBuilder(block.get())
                .part().modelFile(straight).rotationY(bsp.getAngleFromDir(Direction.NORTH, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.NORTH).condition(SofaBlock.SHAPE, StairsShape.STRAIGHT).end()
                .part().modelFile(straight).rotationY(bsp.getAngleFromDir(Direction.EAST, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.EAST).condition(SofaBlock.SHAPE, StairsShape.STRAIGHT).end()
                .part().modelFile(straight).rotationY(bsp.getAngleFromDir(Direction.SOUTH, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.SOUTH).condition(SofaBlock.SHAPE, StairsShape.STRAIGHT).end()
                .part().modelFile(straight).rotationY(bsp.getAngleFromDir(Direction.WEST, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.WEST).condition(SofaBlock.SHAPE, StairsShape.STRAIGHT).end()

                .part().modelFile(inner).rotationY(bsp.getAngleFromDir(Direction.NORTH, offset-90)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.NORTH).condition(SofaBlock.SHAPE, StairsShape.INNER_LEFT).end()
                .part().modelFile(inner).rotationY(bsp.getAngleFromDir(Direction.EAST, offset-90)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.EAST).condition(SofaBlock.SHAPE, StairsShape.INNER_LEFT).end()
                .part().modelFile(inner).rotationY(bsp.getAngleFromDir(Direction.SOUTH, offset-90)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.SOUTH).condition(SofaBlock.SHAPE, StairsShape.INNER_LEFT).end()
                .part().modelFile(inner).rotationY(bsp.getAngleFromDir(Direction.WEST, offset-90)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.WEST).condition(SofaBlock.SHAPE, StairsShape.INNER_LEFT).end()

                .part().modelFile(inner).rotationY(bsp.getAngleFromDir(Direction.NORTH, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.NORTH).condition(SofaBlock.SHAPE, StairsShape.INNER_RIGHT).end()
                .part().modelFile(inner).rotationY(bsp.getAngleFromDir(Direction.EAST, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.EAST).condition(SofaBlock.SHAPE, StairsShape.INNER_RIGHT).end()
                .part().modelFile(inner).rotationY(bsp.getAngleFromDir(Direction.SOUTH, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.SOUTH).condition(SofaBlock.SHAPE, StairsShape.INNER_RIGHT).end()
                .part().modelFile(inner).rotationY(bsp.getAngleFromDir(Direction.WEST, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.WEST).condition(SofaBlock.SHAPE, StairsShape.INNER_RIGHT).end()

                .part().modelFile(outer).rotationY(bsp.getAngleFromDir(Direction.NORTH, offset-90)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.NORTH).condition(SofaBlock.SHAPE, StairsShape.OUTER_LEFT).end()
                .part().modelFile(outer).rotationY(bsp.getAngleFromDir(Direction.EAST, offset-90)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.EAST).condition(SofaBlock.SHAPE, StairsShape.OUTER_LEFT).end()
                .part().modelFile(outer).rotationY(bsp.getAngleFromDir(Direction.SOUTH, offset-90)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.SOUTH).condition(SofaBlock.SHAPE, StairsShape.OUTER_LEFT).end()
                .part().modelFile(outer).rotationY(bsp.getAngleFromDir(Direction.WEST, offset-90)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.WEST).condition(SofaBlock.SHAPE, StairsShape.OUTER_LEFT).end()

                .part().modelFile(outer).rotationY(bsp.getAngleFromDir(Direction.NORTH, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.NORTH).condition(SofaBlock.SHAPE, StairsShape.OUTER_RIGHT).end()
                .part().modelFile(outer).rotationY(bsp.getAngleFromDir(Direction.EAST, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.EAST).condition(SofaBlock.SHAPE, StairsShape.OUTER_RIGHT).end()
                .part().modelFile(outer).rotationY(bsp.getAngleFromDir(Direction.SOUTH, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.SOUTH).condition(SofaBlock.SHAPE, StairsShape.OUTER_RIGHT).end()
                .part().modelFile(outer).rotationY(bsp.getAngleFromDir(Direction.WEST, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.WEST).condition(SofaBlock.SHAPE, StairsShape.OUTER_RIGHT).end()
                ;
    }

    @Override
    protected void buildBlockModel(BA2BlockModelProvider provider, RegistryObject<SofaBlock> block, WoolVariant variant) {
        provider.withParent(getBlockModelPath(variant, "_both"), BA2.get("block/base_" + getBaseName() + "_both"))
                .texture("wool", variant.textureWool);
        provider.withParent(getBlockModelPath(variant, "_inner_corner"), BA2.get("block/base_" + getBaseName() + "_inner_corner"))
                .texture("wool", variant.textureWool);
        provider.withParent(getBlockModelPath(variant, "_left"), BA2.get("block/base_" + getBaseName() + "_left"))
                .texture("wool", variant.textureWool);
        provider.withParent(getBlockModelPath(variant, "_none"), BA2.get("block/base_" + getBaseName() + "_none"))
                .texture("wool", variant.textureWool);
        provider.withParent(getBlockModelPath(variant, "_outer_corner"), BA2.get("block/base_" + getBaseName() + "_outer_corner"))
                .texture("wool", variant.textureWool);
        provider.withParent(getBlockModelPath(variant, "_right"), BA2.get("block/base_" + getBaseName() + "_right"))
                .texture("wool", variant.textureWool);
    }

    @Override
    protected void buildItemModel(BA2ItemModelProvider provider, RegistryObject<SofaBlock> block, WoolVariant variant) {
        provider.withParent(getRegistryName(variant),  BA2.get("block/base_" + getBaseName() + "_item"));
    }

    @Override
    public int getRecipeResultCount(WoolVariant variant) {
        return 1;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoolVariant variant) {
        return List.of(variant.wool);
    }
}
