package github.mrh0.buildersaddition2;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.blocks.chair.ChairBlock;
import github.mrh0.buildersaddition2.blocks.chair.ChairBlueprint;
import github.mrh0.buildersaddition2.blocks.pillow.PillowBlock;
import github.mrh0.buildersaddition2.blocks.pillow.PillowBlueprint;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.ResourceVariants;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;

import github.mrh0.buildersaddition2.common.variants.WoolVariant;
import github.mrh0.buildersaddition2.entity.SeatEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class Index {
    /*private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BA2.BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return BA2.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }*/

    public static RegistryObject<EntityType<?>> SEAT_ENTITY_TYPE = BA2.ENTITIES.register("seat", () -> EntityType.Builder.<SeatEntity>of(SeatEntity::new, MobCategory.MISC)
            .setCustomClientFactory((packet, world) -> new SeatEntity(Index.SEAT_ENTITY_TYPE.get(), world)).build(BA2.MODID+":seat"));

    public static BlockBlueprint<WoodVariant, ChairBlock> CHAIR =
            new ChairBlueprint(WoodVariant.ALL);

    public static BlockBlueprint<WoolVariant, PillowBlock> PILLOW =
            new PillowBlueprint(WoolVariant.ALL);

    public static void load() {}
}
