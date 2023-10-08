package github.mrh0.buildersaddition2.datagen;

import github.mrh0.buildersaddition2.BA2;
import github.mrh0.buildersaddition2.common.BlockBlueprint;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BA2.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    public static LootTableProvider createLootTableProvider(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(BA2LootTableProvider::new, LootContextParamSets.BLOCK)
        ));
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        //generator.addProvider(event.includeServer(), new BA2RecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), createLootTableProvider(packOutput));

        generator.addProvider(event.includeClient(), new BA2BlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new BA2ItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new BA2BlockModelProvider(packOutput, existingFileHelper));

        BA2BlockTagProvider blockTagProvider = generator.addProvider(event.includeServer(),
                new BA2BlockTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new BA2ItemTagProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper));

        System.out.println("==========================LANG===========================");
        BlockBlueprint.translationKeyPairs.forEach(pair -> {
            System.out.println("\"" + pair.getFirst() + "\": \"" + pair.getSecond() + "\",");
        });
        System.out.println("=========================================================");
    }
}