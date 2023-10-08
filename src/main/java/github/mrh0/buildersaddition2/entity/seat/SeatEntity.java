package github.mrh0.buildersaddition2.entity.seat;

import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.blocks.base.AbstractSeatBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class SeatEntity extends Entity {

    public SeatEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(Index.SEAT_ENTITY_TYPE.get(), worldIn);
        this.noPhysics = true;
    }

    public SeatEntity(EntityType<?> entityTypeIn, Level worldIn, BlockPos pos) {
        super(Index.SEAT_ENTITY_TYPE.get(), worldIn);
        this.setPos(pos.getX(), pos.getY(), pos.getZ());
    }

    private SeatEntity(Level world, BlockPos pos, double y)
    {
        this(Index.SEAT_ENTITY_TYPE.get(), world);
        this.setPos(pos.getX() + 0.5, pos.getY() + y, pos.getZ() + 0.5);
    }

    private BlockPos getCheckPos() {
        int x = (int)this.getX();
        int y = (int)this.getY();
        int z = (int)this.getZ();
        return new BlockPos(x < 0 ? x-1 : x, y < 0 ? y-1 : y, z < 0 ? z-1 : z);
    }

    @Override
    public void tick() {
        super.tick();
        if(level().isClientSide()) return;
        if(this.getPassengers().isEmpty() || !(level().getBlockState(getCheckPos()).getBlock() instanceof AbstractSeatBlock)) {
            this.setRemoved(RemovalReason.KILLED);
        }
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected boolean canAddPassenger(@NotNull Entity entity) {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    public boolean canRiderInteract() {
        return false;
    }

    @Override
    public double getPassengersRidingOffset() {
        return -.1d;
    }

    public static InteractionResult createSeat(Level world, BlockPos pos, LivingEntity e, double y, SoundEvent sound) {
        if(e instanceof Player)
            world.playSound((Player)e, pos, sound, SoundSource.BLOCKS, 1f, 1f);
        if(world.isClientSide())
            return InteractionResult.SUCCESS;
        if(world.getEntitiesOfClass(SeatEntity.class, new AABB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)).isEmpty()) {
            SeatEntity seat = new SeatEntity(world, pos, y);//.35d
            world.addFreshEntity(seat);
            e.startRiding(seat);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    public static InteractionResult createSeat(Level world, BlockPos pos, LivingEntity e, SoundEvent sound) {
        return createSeat(world, pos, e, .45d, sound);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag tag) {}
}
