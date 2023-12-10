package github.mrh0.buildersaddition2.blocks.arcade.games;

import github.mrh0.buildersaddition2.blocks.arcade.ArcadeDisplay;
import github.mrh0.buildersaddition2.blocks.arcade.ArcadeManager;
import github.mrh0.buildersaddition2.blocks.arcade.ArcadeScreen;
import net.minecraft.network.chat.Component;

public class ArcadeHomeMenu extends AbstractArcadeGame {
    public ArcadeScreen gui = null;
    public ArcadeHomeMenu(ArcadeDisplay s) {
        super(s);
    }

    int selected;
    int size = ArcadeManager.instance.getGames().size();

    @Override
    public void start() {
        super.start();
        selected = 0;
        render();
    }

    private void logo() {
        String l1 = "  ___                    _      ";
        String l2 = " / _ \\                  | |     ";
        String l3 = "/ /_\\ \\_ __ ___ __ _  __| | ___ ";
        String l4 = "|  _  | '__/ __/ _` |/ _` |/ _ \\";
        String l5 = "| | | | | | (_| (_| | (_| |  __/";
        String l6 = "\\_| |_/_|  \\___\\__,_|\\__,_|\\___|";

        int x = display.width / 2 - (l4.length() / 2);

        display.print(x, 0, l1);
        display.print(x, 1, l2);
        display.print(x, 2, l3);
        display.print(x, 3, l4);
        display.print(x, 4, l5);
        display.print(x, 5, l6);

        display.print(1, display.height-3, Component.translatable("arcade.buildersaddition2.menu.select")); //"Select a game with [UP] and [DOWN]"
        display.print(1, display.height-2, Component.translatable("arcade.buildersaddition2.menu.start")); //"Start the game with [ENTER] or [SPACE]"
    }

    private void render() {
        display.setColors(0x0, 0xf);
        display.clear();
        logo();
        for(int i = 0; i < size; i++) {
            display.setColors(0x0, 0xf);
            if(selected == i) display.setColors(0x0, 0x6);
            display.print(1, i + 8, (selected == i ? ">":" ") + ArcadeManager.instance.getGameName(i));
        }
    }

    @Override
    public void onKeyPressed(int key) {
        super.onKeyPressed(key);
        if(isUp(key) && selected > 0) selected--;
        if(isDown(key) && selected < size-1) selected++;
        render();
        if(isEnter(key) || isSpace(key)) {
            if(gui != null) gui.setGame(ArcadeManager.instance.getGame(selected));
        }
    }
}