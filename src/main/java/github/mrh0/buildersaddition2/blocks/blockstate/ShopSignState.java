package github.mrh0.buildersaddition2.blocks.blockstate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public enum ShopSignState implements StringRepresentable {
    UP_X("up_x"),
    UP_Z("up_z"),
    DOWN_X("down_x"),
    DOWN_Z("down_z"),
    NORTH("north"),
    EAST("east"),
    SOUTH("south"),
    WEST("west");

    private final String name;

    ShopSignState(String name) {
        this.name = name;
    }

    public static ShopSignState getFor(Direction face, Direction facing, BlockPos pos, Level world) {
        Block b = world.getBlockState(pos.relative(face.getOpposite())).getBlock();
        return switch (face) {
            case NORTH, EAST, SOUTH, WEST -> getForBlock(face, b);
            case UP -> facing.getAxis() == Direction.Axis.X ? UP_X : UP_Z;
            case DOWN -> facing.getAxis() == Direction.Axis.X ? DOWN_X : DOWN_Z;
        };
    }

    private static ShopSignState getForBlock(Direction d, Block b) {
        return switch (d) {
            case EAST -> EAST;
            case SOUTH -> SOUTH;
            case WEST -> WEST;
            default -> NORTH;
        };
    }

    public Direction.Axis getAxis() {
        if(this == EAST || this == WEST || this == UP_X || this == DOWN_X )
            return Direction.Axis.X;
        return Direction.Axis.Z;
    }

    public boolean isHorizontal() {
        return this != UP_X && this != UP_Z && this != DOWN_X && this != DOWN_Z;
    }

    public int toYRot() {
        return switch (this) {
            case UP_X, DOWN_X -> 90;
            case UP_Z, DOWN_Z -> 0;
            case NORTH -> (int) Direction.NORTH.toYRot();
            case EAST -> (int) Direction.EAST.toYRot();
            case SOUTH -> (int) Direction.SOUTH.toYRot();
            case WEST -> (int) Direction.WEST.toYRot();
        };
    }

    public int toXRot() {
        return switch (this) {
            case UP_X, UP_Z -> 180;
            default -> 0;
        };
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}