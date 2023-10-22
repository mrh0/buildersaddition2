package github.mrh0.buildersaddition2;

import github.mrh0.buildersaddition2.blocks.bedside_table.BedsideTableBlock;
import github.mrh0.buildersaddition2.blocks.bedside_table.BedsideTableBlockEntity;
import github.mrh0.buildersaddition2.blocks.bedside_table.BedsideTableBlueprint;
import github.mrh0.buildersaddition2.blocks.bench.BenchBlock;
import github.mrh0.buildersaddition2.blocks.bench.BenchBlueprint;
import github.mrh0.buildersaddition2.blocks.cabinet.CabinetBlock;
import github.mrh0.buildersaddition2.blocks.cabinet.CabinetBlockEntity;
import github.mrh0.buildersaddition2.blocks.cabinet.CabinetBlueprint;
import github.mrh0.buildersaddition2.blocks.carpenters_table.CarpentersTableBlock;
import github.mrh0.buildersaddition2.blocks.chair.ChairBlock;
import github.mrh0.buildersaddition2.blocks.chair.ChairBlueprint;
import github.mrh0.buildersaddition2.blocks.counter.CounterBlock;
import github.mrh0.buildersaddition2.blocks.counter.CounterBlockEntity;
import github.mrh0.buildersaddition2.blocks.counter.CounterBlueprint;
import github.mrh0.buildersaddition2.blocks.cupboard.CupboardBlock;
import github.mrh0.buildersaddition2.blocks.cupboard.CupboardBlockEntity;
import github.mrh0.buildersaddition2.blocks.cupboard.CupboardBlueprint;
import github.mrh0.buildersaddition2.blocks.hedge.HedgeBlock;
import github.mrh0.buildersaddition2.blocks.hedge.HedgeBlueprint;
import github.mrh0.buildersaddition2.blocks.pillow.PillowBlock;
import github.mrh0.buildersaddition2.blocks.pillow.PillowBlueprint;
import github.mrh0.buildersaddition2.blocks.shelf.ShelfBlock;
import github.mrh0.buildersaddition2.blocks.shelf.ShelfBlockEntity;
import github.mrh0.buildersaddition2.blocks.shelf.ShelfBlueprint;
import github.mrh0.buildersaddition2.blocks.sofa.SofaBlock;
import github.mrh0.buildersaddition2.blocks.sofa.SofaBlueprint;
import github.mrh0.buildersaddition2.blocks.stool.StoolBlock;
import github.mrh0.buildersaddition2.blocks.stool.StoolBlueprint;
import github.mrh0.buildersaddition2.blocks.stripped_fence.StrippedFenceBlock;
import github.mrh0.buildersaddition2.blocks.stripped_fence.StrippedFenceBlueprint;
import github.mrh0.buildersaddition2.blocks.table.TableBlock;
import github.mrh0.buildersaddition2.blocks.table.TableBlueprint;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.CounterVariant;
import github.mrh0.buildersaddition2.common.variants.LeavesVariant;
import github.mrh0.buildersaddition2.common.variants.WoodVariant;

import github.mrh0.buildersaddition2.common.variants.WoolVariant;
import github.mrh0.buildersaddition2.entity.seat.SeatEntity;
import github.mrh0.buildersaddition2.recipe.carpenter.CarpenterRecipe;
import github.mrh0.buildersaddition2.blocks.carpenters_table.CarpenterTableMenu;
import github.mrh0.buildersaddition2.ui.GenericStorageMenu;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class Index {
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BA2.BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return BA2.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return BA2.MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    // Entity
    public static RegistryObject<EntityType<SeatEntity>> SEAT_ENTITY_TYPE = BA2.ENTITIES.register("seat", () -> EntityType.Builder.<SeatEntity>of(SeatEntity::new, MobCategory.MISC)
            .setCustomClientFactory((packet, world) -> new SeatEntity(Index.SEAT_ENTITY_TYPE.get(), world)).build(BA2.MODID+":seat"));

    // Menu
    public static final RegistryObject<MenuType<CarpenterTableMenu>> CARPENTER_TABLE_MENU =
            registerMenuType("carpenter_table_menu", CarpenterTableMenu::new);
    public static final RegistryObject<MenuType<GenericStorageMenu>> SHELF_MENU =
            registerMenuType("shelf_menu", ShelfBlockEntity::shelfMenu);

    // Recipe
    public static final RegistryObject<RecipeSerializer<CarpenterRecipe>> CARPENTER_SERIALIZER =
            BA2.SERIALIZERS.register(CarpenterRecipe.RECIPE_TYPE_NAME, () -> CarpenterRecipe.Serializer.INSTANCE);




    // Block
    public static RegistryObject<Block> CARPENTER_TABLE = registerBlock("carpenter_table", () -> new CarpentersTableBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));

    public static BlockBlueprint<WoodVariant, ChairBlock> CHAIR =
            new ChairBlueprint(WoodVariant.ALL);

    public static BlockBlueprint<WoodVariant, StoolBlock> STOOL =
            new StoolBlueprint(WoodVariant.ALL);

    public static BlockBlueprint<WoolVariant, PillowBlock> PILLOW =
            new PillowBlueprint(WoolVariant.ALL);

    public static BlockBlueprint<WoodVariant, CupboardBlock> CUPBOARD =
            new CupboardBlueprint(WoodVariant.ALL);

    public static BlockBlueprint<WoodVariant, BedsideTableBlock> BEDSIDE_TABLE =
            new BedsideTableBlueprint(WoodVariant.ALL);

    public static BlockBlueprint<WoodVariant, TableBlock> TABLE =
            new TableBlueprint(WoodVariant.ALL);

    public static BlockBlueprint<WoodVariant, CabinetBlock> CABINET =
            new CabinetBlueprint(WoodVariant.ALL);

    public static BlockBlueprint<WoolVariant, SofaBlock> SOFA =
            new SofaBlueprint(WoolVariant.ALL);

    public static BlockBlueprint<WoodVariant, StrippedFenceBlock> STRIPPED_FENCE =
            new StrippedFenceBlueprint(WoodVariant.ALL);

    public static BlockBlueprint<LeavesVariant, HedgeBlock> HEDGE =
            new HedgeBlueprint(LeavesVariant.ALL);

    public static BlockBlueprint<WoodVariant, BenchBlock> BENCH =
            new BenchBlueprint(WoodVariant.ALL);

    public static BlockBlueprint<CounterVariant, CounterBlock> COUNTER =
            new CounterBlueprint(CounterVariant.ALL);

    public static BlockBlueprint<WoodVariant, ShelfBlock> SHELF =
            new ShelfBlueprint(WoodVariant.ALL);

    // Block Entity
    public static RegistryObject<BlockEntityType<CupboardBlockEntity>> CUPBOARD_ENTITY_TYPE = BA2.BLOCK_ENTITIES.register("cupboard", () ->
            BlockEntityType.Builder.of(CupboardBlockEntity::new, CUPBOARD.getAllBlocks()).build(null));

    public static RegistryObject<BlockEntityType<BedsideTableBlockEntity>> BEDSIDE_TABLE_ENTITY_TYPE = BA2.BLOCK_ENTITIES.register("bedside_table", () ->
            BlockEntityType.Builder.of(BedsideTableBlockEntity::new, BEDSIDE_TABLE.getAllBlocks()).build(null));

    public static RegistryObject<BlockEntityType<CabinetBlockEntity>> CABINET_ENTITY_TYPE = BA2.BLOCK_ENTITIES.register("cabinet", () ->
            BlockEntityType.Builder.of(CabinetBlockEntity::new, CABINET.getAllBlocks()).build(null));

    public static RegistryObject<BlockEntityType<CounterBlockEntity>> COUNTER_ENTITY_TYPE = BA2.BLOCK_ENTITIES.register("counter", () ->
            BlockEntityType.Builder.of(CounterBlockEntity::new, COUNTER.getAllBlocks()).build(null));

    public static RegistryObject<BlockEntityType<ShelfBlockEntity>> SHELF_ENTITY_TYPE = BA2.BLOCK_ENTITIES.register("shelf", () ->
            BlockEntityType.Builder.of(ShelfBlockEntity::new, SHELF.getAllBlocks()).build(null));

    public static void load() {

    }
}
