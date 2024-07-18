package github.mrh0.buildersaddition2;

import github.mrh0.buildersaddition2.blocks.arcade.ArcadeBlock;
import github.mrh0.buildersaddition2.blocks.arcade.ArcadeBlueprint;
import github.mrh0.buildersaddition2.blocks.arcade.ArcadeMenu;
import github.mrh0.buildersaddition2.blocks.bedside_table.BedsideTableBlock;
import github.mrh0.buildersaddition2.blocks.bedside_table.BedsideTableBlockEntity;
import github.mrh0.buildersaddition2.blocks.bedside_table.BedsideTableBlueprint;
import github.mrh0.buildersaddition2.blocks.bench.BenchBlock;
import github.mrh0.buildersaddition2.blocks.bench.BenchBlueprint;
import github.mrh0.buildersaddition2.blocks.bookshelf.BookshelfBlock;
import github.mrh0.buildersaddition2.blocks.bookshelf.BookshelfBlockEntity;
import github.mrh0.buildersaddition2.blocks.bookshelf.BookshelfBlueprint;
import github.mrh0.buildersaddition2.blocks.cabinet.CabinetBlock;
import github.mrh0.buildersaddition2.blocks.cabinet.CabinetBlockEntity;
import github.mrh0.buildersaddition2.blocks.cabinet.CabinetBlueprint;
import github.mrh0.buildersaddition2.blocks.carpenters_table.CarpentersTableBlock;
import github.mrh0.buildersaddition2.blocks.carpenters_table.CarpentersTableBlueprint;
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
import github.mrh0.buildersaddition2.blocks.panel.PanelBlock;
import github.mrh0.buildersaddition2.blocks.panel.PanelBlueprint;
import github.mrh0.buildersaddition2.blocks.pillow.PillowBlock;
import github.mrh0.buildersaddition2.blocks.pillow.PillowBlueprint;
import github.mrh0.buildersaddition2.blocks.post.PostBlock;
import github.mrh0.buildersaddition2.blocks.post.PostBlueprint;
import github.mrh0.buildersaddition2.blocks.shelf.ShelfBlock;
import github.mrh0.buildersaddition2.blocks.shelf.ShelfBlockEntity;
import github.mrh0.buildersaddition2.blocks.shelf.ShelfBlueprint;
import github.mrh0.buildersaddition2.blocks.shop_sign.ShopSignBlock;
import github.mrh0.buildersaddition2.blocks.shop_sign.ShopSignBlockEntity;
import github.mrh0.buildersaddition2.blocks.shop_sign.ShopSignBlueprint;
import github.mrh0.buildersaddition2.blocks.sofa.SofaBlock;
import github.mrh0.buildersaddition2.blocks.sofa.SofaBlueprint;
import github.mrh0.buildersaddition2.blocks.stool.StoolBlock;
import github.mrh0.buildersaddition2.blocks.stool.StoolBlueprint;
import github.mrh0.buildersaddition2.blocks.stripped_fence.StrippedFenceBlock;
import github.mrh0.buildersaddition2.blocks.stripped_fence.StrippedFenceBlueprint;
import github.mrh0.buildersaddition2.blocks.support_beam.SupportBeamBlock;
import github.mrh0.buildersaddition2.blocks.support_beam.SupportBeamBlueprint;
import github.mrh0.buildersaddition2.blocks.table.TableBlock;
import github.mrh0.buildersaddition2.blocks.table.TableBlueprint;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.common.variants.*;
import github.mrh0.buildersaddition2.entity.seat.SeatEntity;
import github.mrh0.buildersaddition2.recipe.carpenter.CarpenterRecipe;
import github.mrh0.buildersaddition2.blocks.carpenters_table.CarpenterTableMenu;
import github.mrh0.buildersaddition2.ui.GenericStorageMenu;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class Index {
    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return BA2.MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    // Entity
    public static DeferredHolder<EntityType<?>, EntityType<SeatEntity>> SEAT_ENTITY_TYPE = BA2.ENTITIES.register("seat", () ->
            EntityType.Builder.<SeatEntity>of(SeatEntity::new, MobCategory.MISC)
                    .passengerAttachments(new Vec3(0d, 0.4d, 0d))
                    .build(BA2.MODID + ":seat"));


    private static <T extends Recipe<?>> Supplier<RecipeType<T>> registerRecipeType(String id) {
        return BA2.RECIPE_TYPES.register(id, () -> new RecipeType<>() {
            public String toString() {
                return id;
            }
        });
    }

    // Menu
    public static final DeferredHolder<MenuType<?>, MenuType<CarpenterTableMenu>> CARPENTER_TABLE_MENU =
            registerMenuType("carpenter_table_menu", CarpenterTableMenu::new);
    public static final DeferredHolder<MenuType<?>, MenuType<GenericStorageMenu>> SHELF_MENU =
            registerMenuType("shelf_menu", ShelfBlockEntity::shelfMenu);
    public static final DeferredHolder<MenuType<?>, MenuType<GenericStorageMenu>> BOOKSHELF_MENU =
            registerMenuType("bookshelf_menu", BookshelfBlockEntity::bookshelfMenu);
    public static final DeferredHolder<MenuType<?>, MenuType<ArcadeMenu>> ARCADE_MENU =
            registerMenuType("arcade_menu", ArcadeBlock::createMenu);

    // Recipe
    public static final Supplier<RecipeType<CarpenterRecipe>> CARPENTER_TYPE = registerRecipeType(CarpenterRecipe.RECIPE_TYPE_NAME);
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<CarpenterRecipe>> CARPENTER_SERIALIZER =
            BA2.SERIALIZERS.register(CarpenterRecipe.RECIPE_TYPE_NAME, CarpenterRecipe.Serializer::new);

    // Block
    public static BlockBlueprint<WoodVariant, CarpentersTableBlock> CARPENTER_TABLE =
            new CarpentersTableBlueprint(WoodVariant.ALL);

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

    public static BlockBlueprint<WoodVariant, PanelBlock> PANEL =
            new PanelBlueprint(WoodVariant.ALL);

    public static BlockBlueprint<WoodVariant, SupportBeamBlock> SUPPORT_BEAM =
            new SupportBeamBlueprint(WoodVariant.ALL);

    public static BlockBlueprint<WoodVariant, PostBlock> POST =
            new PostBlueprint(WoodVariant.ALL);

    //public static BlockBlueprint<SingleVariant, SymbolBlock> SYMBOL =
    //        new SymbolBlueprint(SingleVariant.SINGLE);

    //public static BlockBlueprint<SingleVariant, BarrelPlanterBlock> BARREL_PLANTER =
    //        new BarrelPlanterBlueprint(SingleVariant.SINGLE);

    public static BlockBlueprint<WoodVariant, ShopSignBlock> SHOP_SIGN =
            new ShopSignBlueprint(WoodVariant.ALL);

    public static BlockBlueprint<WoodVariant, BookshelfBlock> BOOKSHELF =
            new BookshelfBlueprint(WoodVariant.ALL);

    public static BlockBlueprint<WoodVariant, ArcadeBlock> ARCADE =
            new ArcadeBlueprint(WoodVariant.ALL);

    // Block Entity
    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<CupboardBlockEntity>> CUPBOARD_ENTITY_TYPE = BA2.BLOCK_ENTITIES.register("cupboard", () ->
            BlockEntityType.Builder.of(CupboardBlockEntity::new, CUPBOARD.getAllBlocks()).build(null));

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BedsideTableBlockEntity>> BEDSIDE_TABLE_ENTITY_TYPE = BA2.BLOCK_ENTITIES.register("bedside_table", () ->
            BlockEntityType.Builder.of(BedsideTableBlockEntity::new, BEDSIDE_TABLE.getAllBlocks()).build(null));

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<CabinetBlockEntity>> CABINET_ENTITY_TYPE = BA2.BLOCK_ENTITIES.register("cabinet", () ->
            BlockEntityType.Builder.of(CabinetBlockEntity::new, CABINET.getAllBlocks()).build(null));

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<CounterBlockEntity>> COUNTER_ENTITY_TYPE = BA2.BLOCK_ENTITIES.register("counter", () ->
            BlockEntityType.Builder.of(CounterBlockEntity::new, COUNTER.getAllBlocks()).build(null));

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ShelfBlockEntity>> SHELF_ENTITY_TYPE = BA2.BLOCK_ENTITIES.register("shelf", () ->
            BlockEntityType.Builder.of(ShelfBlockEntity::new, SHELF.getAllBlocks()).build(null));

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ShopSignBlockEntity>> SHOP_SIGN_ENTITY_TYPE = BA2.BLOCK_ENTITIES.register("shop_sign", () ->
            BlockEntityType.Builder.of(ShopSignBlockEntity::new, SHOP_SIGN.getAllBlocks()).build(null));

    public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BookshelfBlockEntity>> BOOKSHELF_ENTITY_TYPE = BA2.BLOCK_ENTITIES.register("bookshelf", () ->
            BlockEntityType.Builder.of(BookshelfBlockEntity::new, BOOKSHELF.getAllBlocks()).build(null));

    //public static RegistryObject<BlockEntityType<SymbolBlockEntity>> SYMBOL_ENTITY_TYPE = BA2.BLOCK_ENTITIES.register("symbol", () ->
    //        BlockEntityType.Builder.of(SymbolBlockEntity::new, SYMBOL.getAllBlocks()).build(null));

    public static void load() {

    }
}
