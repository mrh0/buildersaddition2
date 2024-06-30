package github.mrh0.buildersaddition2.blocks.shop_sign;

import github.mrh0.buildersaddition2.Index;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ShopSignBlockEntity extends BlockEntity {

    private ItemStack item;

    public ShopSignBlockEntity(BlockPos pos, BlockState state) {
        super(Index.SHOP_SIGN_ENTITY_TYPE.get(), pos, state);
        item = ItemStack.EMPTY;
    }

    public ItemStack getDisplayItem() {
        return item.copy();
    }

    public void setDisplayItem(ItemStack item) {
        if(level == null) return;
        if(item == null) item = ItemStack.EMPTY;
        this.item = item.copy();
        this.item.setCount(1);
        level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 0);
        this.setChanged();

    }

    public boolean hasDisplayItem() {
        return item != ItemStack.EMPTY && item.getItem() != Items.AIR;
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.saveAdditional(nbt, provider);
        if (!item.isEmpty()) nbt.put("item", item.save(provider, new CompoundTag()));
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider provider) {
        super.loadAdditional(nbt, provider);
        item = ItemStack.parse(provider, nbt.getCompound("item")).orElseGet(() -> ItemStack.EMPTY);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider provider) {
        super.onDataPacket(connection, pkt, provider);
        CompoundTag update = pkt.getTag();
        handleUpdateTag(update, provider);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag nbt = new CompoundTag();
        saveAdditional(nbt, provider);
        return nbt;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider provider) {
        loadAdditional(tag, provider);
    }
}
