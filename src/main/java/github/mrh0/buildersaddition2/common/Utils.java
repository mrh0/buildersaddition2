package github.mrh0.buildersaddition2.common;

import github.mrh0.buildersaddition2.Index;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static String capitalizeWords(String input) {
        // Split the input string into an array of words using underscores as the delimiter
        String[] words = input.split("_");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                // Capitalize the first letter of each word
                String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
                result.append(capitalizedWord).append(" ");
            }
        }

        // Remove the trailing space and return the result
        return result.toString().trim();
    }

    public static VoxelShape rotateBox(double x1, double y1, double z1, double x2, double y2, double z2, Direction direction) {
        // Rotation origin
        double originX = 8d, originY = 8d, originZ = 8d;

        // Calculate the new coordinates based on the specified direction
        double rotatedX1 = x1, rotatedY1 = y1, rotatedZ1 = z1;
        double rotatedX2 = x2, rotatedY2 = y2, rotatedZ2 = z2;

        switch (direction) {
            case NORTH:
                // No rotation needed for the "NORTH" direction (facing south initially)
                break;
            case EAST:
                // Rotate 90 degrees clockwise in the y-z plane (around the y-axis)
                rotatedX1 = originX - (z2 - originZ);
                rotatedZ1 = originZ + (x1 - originX);
                rotatedX2 = originX - (z1 - originZ);
                rotatedZ2 = originZ + (x2 - originX);
                break;
            case SOUTH:
                // Rotate 180 degrees (flip in both x and z directions)
                rotatedX1 = originX - (x2 - originX);
                rotatedZ1 = originZ - (z2 - originZ);
                rotatedX2 = originX - (x1 - originX);
                rotatedZ2 = originZ - (z1 - originZ);
                break;
            case WEST:
                // Rotate 90 degrees counterclockwise in the y-z plane (around the y-axis)
                rotatedX1 = originX + (z1 - originZ);
                rotatedZ1 = originZ - (x2 - originX);
                rotatedX2 = originX + (z2 - originZ);
                rotatedZ2 = originZ - (x1 - originX);
                break;
            case UP:
                // Rotate 90 degrees counterclockwise in the x-y plane (around the x-axis)
                rotatedY1 = originY + (z1 - originZ);
                rotatedZ1 = originZ - (y2 - originY);
                rotatedY2 = originY + (z2 - originZ);
                rotatedZ2 = originZ - (y1 - originY);
                break;
            case DOWN:
                // Rotate 90 degrees clockwise in the x-y plane (around the x-axis)
                rotatedY1 = originY - (z2 - originZ);
                rotatedZ1 = originZ + (y1 - originY);
                rotatedY2 = originY - (z1 - originZ);
                rotatedZ2 = originZ + (y2 - originY);
                break;
        }

        return Block.box(rotatedX1, rotatedY1, rotatedZ1, rotatedX2, rotatedY2, rotatedZ2);
    }

    public static VoxelShape fromSouthFacing(VoxelShape input, Direction dir) {
        List<VoxelShape> shapes = new ArrayList<>();
        VoxelShape output = Shapes.empty();
        input.forAllBoxes((x1, y1, z1, x2, y2, z2) -> {
            shapes.add(rotateBox(x1, y1, z1, x2, y2, z2, dir));
        });
        for(VoxelShape shape : shapes) {
            output = Shapes.or(output, shape);
        }
        return output;
    }

    public static boolean eq(BlockPos a, BlockPos b) {
        return a.getX() == b.getX() && a.getY() == b.getY() && a.getZ() == b.getZ();
    }

    public static final Direction[] HORIZONTAL_DIRECTIONS = { Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };

    public static ItemStack getIcon() {
        var list = Index.COUNTER.getAllBlocks();
        var selected = list[(int)(Math.random()*list.length)];
        return new ItemStack(selected);
    }

    //public static PaintingVariant createPainting(int w, int h) {
    //    return new PaintingVariant(16*w, 16*h);
    //}

    public static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos) {
        return false;
    }

    public static boolean isntSolid(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> ent) {
        return false;
    }

    public static boolean accessCheck(Level world, BlockPos pos, Direction facing) {
        // if(!Config.INVENTORY_ACCESS_BLOCK_CHECK.get()) return true;
        BlockState front = world.getBlockState(pos.relative(facing));
        return !(front.isFaceSturdy(world, pos.relative(facing), facing.getOpposite()) || front.isFaceSturdy(world, pos.relative(facing), Direction.UP));
    }

    @Nullable
    public static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> createTickerHelper(BlockEntityType<A> p_152133_, BlockEntityType<E> p_152134_, BlockEntityTicker<? super E> p_152135_) {
        return p_152134_ == p_152133_ ? (BlockEntityTicker<A>)p_152135_ : null;
    }
}
