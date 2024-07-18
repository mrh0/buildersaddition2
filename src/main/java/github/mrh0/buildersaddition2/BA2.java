package github.mrh0.buildersaddition2;

import com.mojang.logging.LogUtils;
import github.mrh0.buildersaddition2.blocks.arcade.ArcadeManager;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import github.mrh0.buildersaddition2.network.SyncContentPacket;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.neoforge.network.Channel;
import net.neoforged.neoforge.network.ChannelBuilder;
import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.SimpleChannel;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(BA2.MODID)
public class BA2 {
    public static final String MODID = "buildersaddition2";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    //public static final DeferredRegister<PaintingVariant> PAINTINGS = DeferredRegister.create(BuiltInRegistries.PAINTING_VARIANTS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPES, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("ba2", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> Index.COUNTER.getBlock(0).asItem().getDefaultInstance())
            .title(translatable("tab", "main"))
            .build());

    public BA2(IEventBus modEventBus, ModContainer container) {
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::postInit);

        Index.load();

        //PAINTINGS.register(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        MENUS.register(modEventBus);
        ENTITIES.register(modEventBus);
        RECIPE_TYPES.register(modEventBus);
        SERIALIZERS.register(modEventBus);

        ArcadeManager.init();

        //MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        //container.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    private static final int PROTOCOL = 1;

    public static final SimpleChannel Network = ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(MODID, "main"))
            .clientAcceptedVersions(Channel.VersionTest.exact(PROTOCOL))
            .serverAcceptedVersions(Channel.VersionTest.exact(PROTOCOL))
            .networkProtocolVersion(PROTOCOL)
            .simpleChannel();

    public void postInit(FMLLoadCompleteEvent evt) {
        int i = 0;
        Network.messageBuilder(SyncContentPacket.class, i++)
                .encoder(SyncContentPacket::encode)
                .decoder(SyncContentPacket::decode)
                .consumerMainThread(SyncContentPacket::handle);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        BlockBlueprint.ALL_BLUEPRINTS.forEach(blueprint -> {
            blueprint.addAllToCreativeTabs(event);
        });
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}

    //@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    //public static class ModEvents { }

    public static ResourceLocation get(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static Component translatable(String type, String path) {
        return Component.translatable(String.join(".", type, MODID, path));
    }
}
