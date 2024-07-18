package github.mrh0.buildersaddition2.common.datagen;

import github.mrh0.buildersaddition2.common.BlockBlueprint;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class BPDataGenerator {
    public static LootTableProvider createLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(BPLootTableProvider::new, LootContextParamSets.BLOCK)
        ), lookupProvider);
    }
    public static void gatherData(String modid, GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new BPRecipeProvider(modid, packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), createLootTableProvider(packOutput, lookupProvider));

        generator.addProvider(event.includeClient(), new BPBlockStateProvider(modid, packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new BPItemModelProvider(modid, packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new BPBlockModelProvider(modid, packOutput, existingFileHelper));

        BPBlockTagProvider blockTagProvider = generator.addProvider(event.includeServer(),
                new BPBlockTagProvider(modid, packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new BPItemTagProvider(modid, packOutput, lookupProvider, blockTagProvider.contentsGetter(), existingFileHelper));

        System.out.println("==========================LANG===========================");
        BlockBlueprint.translationKeyPairs.forEach(pair -> {
            System.out.println("\"" + pair.getFirst() + "\": \"" + pair.getSecond() + "\",");
        });
        System.out.println("=========================================================");
    }
}
