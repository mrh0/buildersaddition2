package github.mrh0.buildersaddition2.blocks.symbol;

import github.mrh0.buildersaddition2.Index;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SymbolBlockEntity extends BlockEntity {
    public SymbolBlockEntity(BlockPos pos, BlockState state) {
        super(Index.SYMBOL_ENTITY_TYPE.get(), pos, state);
    }
}
