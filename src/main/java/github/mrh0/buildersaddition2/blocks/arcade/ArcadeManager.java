package github.mrh0.buildersaddition2.blocks.arcade;

import github.mrh0.buildersaddition2.blocks.arcade.games.AbstractArcadeGame;
import github.mrh0.buildersaddition2.blocks.arcade.games.ArcadeSnake;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class ArcadeManager {
    public interface GameConstructor {
        AbstractArcadeGame construct(ArcadeDisplay s);
    }

    public static AbstractArcadeGame activeGame = null;

    public static ArcadeManager instance = null;
    private final ArrayList<GameConstructor> games;
    private final ArrayList<Component> names;

	public ArcadeManager() {
        if(instance == null) instance = this;
        games = new ArrayList<GameConstructor>();
        names = new ArrayList<Component>();
    }

    public void add(GameConstructor game, String key) {
        games.add(game);
        names.add(Component.translatable("arcade.buildersaddition2.game." + key));
    }

    public List<GameConstructor> getGames() {
        return this.games;
    }

    public GameConstructor getGame(int index) {
        return this.games.get(index);
    }

    public String getGameName(int index) {
        return this.names.get(index).getString();
    }

    public static void init() {
        new ArcadeManager();
        instance.add(ArcadeSnake::new, "snake");
        //instance.add(ArcadeBreakout::new, "breakout");
        //instance.add(ArcadeCredits::new, "credits");
    }

    static long time = 0;
    public void clientTick() {
        if(ArcadeManager.activeGame == null) return;
        ArcadeManager.activeGame.frame(time++);
    }
}