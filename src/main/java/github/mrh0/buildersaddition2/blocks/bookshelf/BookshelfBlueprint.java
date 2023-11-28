package github.mrh0.buildersaddition2.blocks.bookshelf;

import github.mrh0.buildersaddition2.blocks.chair.ChairBlock;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.datagen.BPBlockModelProvider;
import github.mrh0.buildersaddition2.common.datagen.BPBlockStateProvider;
import github.mrh0.buildersaddition2.common.datagen.BPItemModelProvider;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class BookshelfBlueprint extends BlockBlueprint<WoodVariant, BookshelfBlock> {
    public BookshelfBlueprint(Iterable<WoodVariant> variants) {
        super(variants);
    }

    @Override
    public String getBaseName() {
        return "bookshelf";
    }

    @Override
    public String getLangName(WoodVariant variant) {
        return variant.getDisplayName() + " Bookshelf";
    }

    @Override
    public List<TagKey<Block>> addBlockTags(WoodVariant variant) {
        return List.of(BlockTags.MINEABLE_WITH_AXE);
    }

    @Override
    protected Supplier<BookshelfBlock> getBlock(WoodVariant variant) {
        return () -> new BookshelfBlock(BlockBehaviour.Properties.copy(variant.planks));
    }

    @Override
    protected void buildBlockModel(BPBlockModelProvider provider, RegistryObject<BookshelfBlock> block, WoodVariant variant) {
        provider.withParent(getBlockModelPath(variant), resource("block/base_" + getBaseName()))
                .texture("planks", variant.texturePlanks)
                .texture("stripped", variant.textureStripped)
                .texture("particle", variant.texturePlanks);
    }

    @Override
    protected void buildItemModel(BPItemModelProvider provider, RegistryObject<BookshelfBlock> block, WoodVariant variant) {
        provider.withParent(getRegistryName(variant), resource(getBlockModelPath(variant)));
    }

    @Override
    public void buildBlockState(BPBlockStateProvider provider, RegistryObject<BookshelfBlock> block, WoodVariant variant) {
        var bs = provider.multipartHorizontalFacing(
                provider.getMultipartBuilder(block.get()),
                model(getBlockModelPath(variant)),
                180,
                false
        );

        for (int i = 0; i < BookshelfBlock.BOOKS.length; i++) {
            bs.part().modelFile(model("block/books_" + i)).rotationY(provider.getAngleFromDir(Direction.NORTH, 180)).uvLock(false).addModel()
                    .condition(ChairBlock.FACING, Direction.NORTH).condition(BookshelfBlock.BOOKS[i], true).end()
                .part().modelFile(model("block/books_" + i)).rotationY(provider.getAngleFromDir(Direction.EAST, 180)).uvLock(false).addModel()
                    .condition(ChairBlock.FACING, Direction.EAST).condition(BookshelfBlock.BOOKS[i], true).end()
                .part().modelFile(model("block/books_" + i)).rotationY(provider.getAngleFromDir(Direction.SOUTH, 180)).uvLock(false).addModel()
                    .condition(ChairBlock.FACING, Direction.SOUTH).condition(BookshelfBlock.BOOKS[i], true).end()
                .part().modelFile(model("block/books_" + i)).rotationY(provider.getAngleFromDir(Direction.WEST, 180)).uvLock(false).addModel()
                    .condition(ChairBlock.FACING, Direction.WEST).condition(BookshelfBlock.BOOKS[i], true).end();
        }
    }

    @Override
    public int getRecipeResultCount(WoodVariant variant) {
        return 2;
    }

    @Override
    public List<ItemLike> getRecipeRequired(WoodVariant variant) {
        return List.of(variant.planks, variant.slab);
    }
}
