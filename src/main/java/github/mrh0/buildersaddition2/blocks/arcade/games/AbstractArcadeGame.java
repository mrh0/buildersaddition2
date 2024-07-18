package github.mrh0.buildersaddition2.blocks.arcade.games;

import github.mrh0.buildersaddition2.blocks.arcade.ArcadeDisplay;
import github.mrh0.buildersaddition2.blocks.arcade.Notes;
import net.minecraft.sounds.SoundEvents;

public abstract class AbstractArcadeGame {
    public ArcadeDisplay display;

    public AbstractArcadeGame(ArcadeDisplay display) {
        this.display = display;
    }

    public void frame(long steps, float partial) {}

    public void start() {}

    public void onKeyPressed(int key) {}

    public void onKeyReleased(int key) {}

    public void onMousePressed(int key) {}

    public void onMouseReleased(int key) {}

    public void playSound(int note) {
        Notes.playClientNote(SoundEvents.NOTE_BLOCK_BIT.value(), note);
    }

    public static final int NOTE_BASS = 0;
    public static final int NOTE_HAT = 2;
    public static final int NOTE_BIT = 12;

    public void playSound(int sound, int note) {
        switch (sound) {
            case 0 -> Notes.playClientNote(SoundEvents.NOTE_BLOCK_BASS.value(), note);

            //case 1:
            //	Notes.playClientNote(SoundEvents.BLOCK_NOTE_BLOCK_SNARE, note);break;
            case 2 -> Notes.playClientNote(SoundEvents.NOTE_BLOCK_HAT.value(), note);

            //case 3:
            //	Notes.playClientNote(SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM, note);break;
            case 12 -> Notes.playClientNote(SoundEvents.NOTE_BLOCK_BIT.value(), note);
        }
    }

    public int getBestScore() {
        return 0;
    }

    public static boolean isAny(int key) {
        return key > 0;
    }

    public static boolean isSpace(int key) {
        return key == 32;
    }

    public static boolean isEnter(int key) {
        return key == 257;
    }

    public static boolean isHome(int key) {
        return key == 268;
    }

    public static boolean isEsc(int key) {
        return key == 256;
    }

    public static boolean isHelp(int key) {
        return key == 290;
    }

    public static boolean isLeft(int key) {
        return key == 263 || key == 65;
    }

    public static boolean isRight(int key) {
        return key == 262 || key == 68;
    }

    public static boolean isUp(int key) {
        return key == 265 || key == 87;
    }

    public static boolean isDown(int key) {
        return key == 264 || key == 83;
    }

    public static boolean isReset(int key) {
        return key == 269;
    }

    public static String getKeyName(int key) {
        return switch (key) {
            case 32 -> "space";
            case 257 -> "return";
            case 268 -> "home";
            case 256 -> "escape";
            case 290 -> "help";
            case 263, 65 -> "left";
            case 262, 68 -> "right";
            case 265, 87 -> "up";
            case 264, 83 -> "down";
            case 269 -> "reset";
            default -> "unknown";
        };
    }
}
