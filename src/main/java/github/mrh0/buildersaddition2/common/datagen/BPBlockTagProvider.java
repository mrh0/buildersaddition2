package github.mrh0.buildersaddition2.common.datagen;

import github.mrh0.buildersaddition2.common.BlockBlueprint;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BPBlockTagProvider extends BlockTagsProvider {
    public BPBlockTagProvider(String modid, PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modid, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        BlockBlueprint.ALL_BLUEPRINTS.forEach(blueprint -> {
            var map = blueprint.getTagPairs();
            map.keySet().forEach(key -> {
                this.tag(key).add(map.getOrDefault(key, List.of()).toArray(new Block[0]));
            });
        });
    }
}