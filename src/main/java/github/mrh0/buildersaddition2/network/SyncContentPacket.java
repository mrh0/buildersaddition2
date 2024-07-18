package github.mrh0.buildersaddition2.network;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class SyncContentPacket {
    private BlockPos pos;
    private int count;
    private NonNullList<ItemStack> items;

    public SyncContentPacket(BlockPos pos, int count, NonNullList<ItemStack> items) {
        this.pos = pos;
        this.count = count;
        this.items = items;
    }

    public static void encode(SyncContentPacket pkt, FriendlyByteBuf tag) {
        tag.writeBlockPos(pkt.pos);
        tag.writeInt(pkt.count);
        for(int i = 0; i < pkt.count; i++) {
            //tag.writeItem(pkt.items.get(i)); // TODO: FIX
        }
    }

    public static SyncContentPacket decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        int count = buf.readInt();
        NonNullList<ItemStack> items = NonNullList.createWithCapacity(count);
        for(int i = 0; i < count; i++) {
            //items.set(i, buf.readItem()); // TODO: FIX
        }
        return new SyncContentPacket(pos, count, items);
    }

    public static void handle(SyncContentPacket pkt, CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            try {
                ServerPlayer player = ctx.getSender();
                if (player != null) sendUpdate(pkt.pos, player);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ctx.setPacketHandled(true);
    }

    private static void sendUpdate(BlockPos pos, ServerPlayer player) {
        try {
            BlockEntity be = player.level().getBlockEntity(pos);

            if(be instanceof IContentProvider provider) {
                Packet<ClientGamePacketListener> packet = be.getUpdatePacket();
                if (packet != null) player.connection.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void send(BlockPos pos, ServerPlayer player) {
        //BA2.Network.send(PacketDistributor.PLAYER.with(() -> player), new SyncContentPacket(pos, data));
    }
}