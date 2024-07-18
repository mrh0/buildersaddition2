package github.mrh0.buildersaddition2.common.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BPItemTagProvider extends ItemTagsProvider {
    public BPItemTagProvider(String modid, PackOutput out, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> block, ExistingFileHelper existingFileHelper) {
        super(out, provider, block);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }
}