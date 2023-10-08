package github.mrh0.buildersaddition2.blocks.base;

import github.mrh0.buildersaddition2.Index;
import github.mrh0.buildersaddition2.entity.SeatEntity;
import github.mrh0.buildersaddition2.state.PillowState;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class AbstractSeatBlock extends Block {
    public AbstractSeatBlock(Properties props) {
        super(props);
    }
}
