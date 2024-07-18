package github.mrh0.buildersaddition2.blocks.bookshelf;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.base.AbstractStorageBlockEntity;
import github.mrh0.buildersaddition2.ui.GenericStorageMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.state.BlockState;

public class BookshelfBlockEntity extends AbstractStorageBlockEntity {
    public BookshelfBlockEntity(BlockPos pos, BlockState state) {
        super(Index.BOOKSHELF_ENTITY_TYPE.get(), pos, state);
    }

    @Override
    public int getRows() {
        return 2;
    }

    @Override
    protected void playSound(BlockState state, SoundEvent evt) {}

    @Override
    protected Component getDefaultName() {
        return BA2.translatable("container", "bookshelf");
    }

    public static GenericStorageMenu bookshelfMenu(int id, Inventory inv, FriendlyByteBuf data) {
        return new GenericStorageMenu(Index.BOOKSHELF_MENU.get(), id, inv, inv.player.level(), data.readBlockPos(), 2, 9, 1, BookshelfBlockEntity::isBook);
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return new GenericStorageMenu(Index.BOOKSHELF_MENU.get(), id, inv, this.getLevel(), this.getBlockPos(), 2, 9, 1, BookshelfBlockEntity::isBook);
    }

    public static boolean isBook(ItemStack stack) {
        Item i = stack.getItem();
        String n = BuiltInRegistries.ITEM.getKey(i).getPath();

        TagKey<Item> FORGE_BOOKS_TAG = ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", "books"));

        return (i instanceof EnchantedBookItem) || (i instanceof KnowledgeBookItem) || i == Items.BOOK || i == Items.WRITABLE_BOOK || i == Items.WRITTEN_BOOK
                || n.endsWith("book") || n.endsWith("manual") || n.endsWith("journal") || n.endsWith("tome")  || n.startsWith("tome") || n.endsWith("lexicon")  || n.endsWith("codex")
                || n.endsWith("guide") || n.startsWith("guide") || n.startsWith("handbook") || n.endsWith("chronicle") || n.endsWith("companion") || n.endsWith("binder") || n.endsWith("nomicon")
                || n.endsWith("dictionary") || n.startsWith("dictionary") || n.endsWith("materials_and_you") || n.startsWith("binder")
                || stack.is(FORGE_BOOKS_TAG);
    }

    public void onChange() {
        if(level == null) return;
        if(level.isClientSide()) return;
        BlockState bs = level.getBlockState(this.getBlockPos());
        if(bs.getBlock() instanceof BookshelfBlock)
            level.setBlockAndUpdate(this.getBlockPos(), BookshelfBlock.getState(bs, hasIn(0), hasIn(1), hasIn(2), hasIn(3), hasIn(4), hasIn(5), hasIn(6), hasIn(7)));
    }

    private boolean hasIn(int i) {
        if(i == 0) return getItems().get(0).getCount() > 0 || getItems().get(1).getCount() > 0 || getItems().get(2).getCount() > 0;
        if(i == 7) return getItems().get(15).getCount() > 0 || getItems().get(16).getCount() > 0 || getItems().get(17).getCount() > 0;
        return getItems().get(i*2+1).getCount() > 0 || getItems().get(i*2+2).getCount() > 0;
    }
}
