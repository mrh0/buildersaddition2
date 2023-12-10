package github.mrh0.buildersaddition2.blocks.arcade;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;

public class Notes {
    public static int getNoteFromKey(int key) {
        return switch (key) {
            case 65 -> 1;
            case 90 -> 3;
            case 83 -> 5;
            case 88 -> 6;
            case 68 -> 8;
            case 67 -> 10;
            case 70 -> 11;
            case 86 -> 13;
            case 71 -> 15;
            case 66 -> 17;
            case 72 -> 18;
            case 78 -> 20;
            case 74 -> 22;
            case 77 -> 23;//
            case 81 -> 0;
            case 87 -> 2;
            case 69 -> 4;
            case 82 -> 7;
            case 84 -> 9;
            case 89 -> 12;
            case 85 -> 14;
            case 73 -> 16;
            case 79 -> 19;
            case 80 -> 21;
            case 75 -> 24;
            default -> -1;
        };
    }

    public static void playClientNote(SoundEvent evt, int note) {
        if (note < 0 && note >= 48) return;
        float f = (float) Math.pow(2.0D, (double) (note - 12) / 12.0D);
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(evt, f));
    }
}