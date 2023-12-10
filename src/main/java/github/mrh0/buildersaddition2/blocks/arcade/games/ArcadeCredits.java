package github.mrh0.buildersaddition2.blocks.arcade.games;

import github.mrh0.buildersaddition2.blocks.arcade.ArcadeDisplay;

public class ArcadeCredits extends AbstractArcadeGame {

    public ArcadeCredits(ArcadeDisplay display) {
        super(display);
    }

    @Override
    public void start() {
        super.start();
        display.setColors(0x0, 0xf);
        display.clear();

        int ln = 1;
        display.print(1, ln++, "Builders Crafts & Additions");
        ln++;
        display.print(1, ln++, "Programming: MRH0");
        display.print(1, ln++, "Models: MRH0");
        display.print(1, ln++, "Textures: MRH0");
        display.print(1, ln++, "Translation: vanja-san, Mikeliro,");
        display.print(1, ln++, "Pancakes0228, Yupoman, alierenreis,");
        display.print(1, ln++, "spiderfromi, EdicionGamerYT, Lyaiya");
        ln++;
        display.print(1, ln++, "Source:");
        display.print(1, ln++, "github.com/mrh0/buildersaddition/");
    }
}
