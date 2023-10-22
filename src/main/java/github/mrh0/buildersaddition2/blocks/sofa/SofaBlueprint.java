package github.mrh0.buildersaddition2.blocks.sofa;

import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.WoolVariant;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
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
        return variant.getDisplayName() + " Sofa";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoolVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected void buildBlockState(BPBlockStateProvider bsp, RegistryObject<SofaBlock> block, WoolVariant variant) {
        boolean uvLock = true;
        int offset = 180;
        var straight = model(getBlockModelPath(variant, "_none"));
        var inner = model(getBlockModelPath(variant, "_inner_corner"));
        var outer = model(getBlockModelPath(variant, "_outer_corner"));
        var armrest = model(getBlockModelPath(variant, "_side"));

        bsp.getMultipartBuilder(block.get())
                .part().modelFile(straight).rotationY(bsp.getAngleFromDir(Direction.NORTH, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.NORTH).condition(SofaBlock.SHAPE, StairsShape.STRAIGHT).end()
                .part().modelFile(straight).rotationY(bsp.getAngleFromDir(Direction.EAST, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.EAST).condition(SofaBlock.SHAPE, StairsShape.STRAIGHT).end()
                .part().modelFile(straight).rotationY(bsp.getAngleFromDir(Direction.SOUTH, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.SOUTH).condition(SofaBlock.SHAPE, StairsShape.STRAIGHT).end()
                .part().modelFile(straight).rotationY(bsp.getAngleFromDir(Direction.WEST, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.WEST).condition(SofaBlock.SHAPE, StairsShape.STRAIGHT).end()

                .part().modelFile(armrest).rotationY(bsp.getAngleFromDir(Direction.NORTH, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.NORTH).condition(SofaBlock.ARMREST_LEFT, true).end()
                .part().modelFile(armrest).rotationY(bsp.getAngleFromDir(Direction.EAST, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.EAST).condition(SofaBlock.ARMREST_LEFT, true).end()
                .part().modelFile(armrest).rotationY(bsp.getAngleFromDir(Direction.SOUTH, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.SOUTH).condition(SofaBlock.ARMREST_LEFT, true).end()
                .part().modelFile(armrest).rotationY(bsp.getAngleFromDir(Direction.WEST, offset)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.WEST).condition(SofaBlock.ARMREST_LEFT, true).end()

                .part().modelFile(armrest).rotationY(bsp.getAngleFromDir(Direction.NORTH, offset-180)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.NORTH).condition(SofaBlock.ARMREST_RIGHT, true).end()
                .part().modelFile(armrest).rotationY(bsp.getAngleFromDir(Direction.EAST, offset-180)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.EAST).condition(SofaBlock.ARMREST_RIGHT, true).end()
                .part().modelFile(armrest).rotationY(bsp.getAngleFromDir(Direction.SOUTH, offset-180)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.SOUTH).condition(SofaBlock.ARMREST_RIGHT, true).end()
                .part().modelFile(armrest).rotationY(bsp.getAngleFromDir(Direction.WEST, offset-180)).uvLock(uvLock).addModel().condition(SofaBlock.FACING, Direction.WEST).condition(SofaBlock.ARMREST_RIGHT, true).end()

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
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<SofaBlock> block, WoolVariant variant) {
        provider.withParent(getBlockModelPath(variant, "_inner_corner"), resource("block/base_" + getBaseName() + "_inner_corner"))
                .texture("wool", variant.textureWool)
                .texture("particle", variant.textureWool);
        provider.withParent(getBlockModelPath(variant, "_none"), resource("block/base_" + getBaseName() + "_none"))
                .texture("wool", variant.textureWool)
                .texture("particle", variant.textureWool);
        provider.withParent(getBlockModelPath(variant, "_outer_corner"), resource("block/base_" + getBaseName() + "_outer_corner"))
                .texture("wool", variant.textureWool)
                .texture("particle", variant.textureWool);
        provider.withParent(getBlockModelPath(variant, "_side"), resource("block/base_" + getBaseName() + "_side"))
                .texture("wool", variant.textureWool)
                .texture("particle", variant.textureWool);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<SofaBlock> block, WoolVariant variant) {
        provider.withParent(getRegistryName(variant),  resource("block/base_" + getBaseName() + "_inventory"))
                .texture("wool", variant.textureWool);
    }

    @Override
    public int getRecipeResultCount(WoolVariant variant) {
        return 1;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoolVariant variant) {
        return List.of(variant.wool, Items.IRON_INGOT);
    }
}
