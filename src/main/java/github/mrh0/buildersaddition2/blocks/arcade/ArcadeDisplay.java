package github.mrh0.buildersaddition2.blocks.arcade;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.Arrays;

public class ArcadeDisplay {
    public final int width = 40;
    public final int height = 25;
    public final int cellWidth = 8;
    public final int cellHeight = 8;
    public final short[] screen;
    public static final int[] fgColors = {
            0x000000,
            0x0000AA,
            0x00AA00,
            0x00AAAA,
            0xAA0000,
            0xAA00AA,
            0xFFAA00,
            0xAAAAAA,
            0x555555,
            0x5555FF,
            0x55FF55,
            0x55FFFF,
            0xFF5555,
            0xFF55FF,
            0xFFFF55,
            0xFFFFFF
    };

    private int bg = 0;
    private int fg = 0;

    private ScreenRender fgRenderer = null;
    private ScreenRender bgRenderer = null;

    public interface ScreenRender {
        void render(GuiGraphics gg, int startx, int starty, int width, int height);
    }

    public ArcadeDisplay() {
        screen = new short[width * height];
        clear();
    }

    public void setBgRenderer(ScreenRender bgr) {
        this.bgRenderer = bgr;
    }

    public void setFgRenderer(ScreenRender fgr) {
        this.fgRenderer = fgr;
    }

    public void clear() {
        Arrays.fill(screen, encode('\0', bg, fg));
    }

    public void clear(int x, int y, int w, int h) {
        for(int ix = x; ix < screen.length && ix < x+w; ix++) {
            for(int iy = y; iy < screen.length && iy < y+h; iy++) {
                screen[getIndex(ix, iy)] = encode('\0', bg, fg);
            }
        }
    }

    public void testScreen() {
        for(int i = 0; i < screen.length; i++) {
            screen[i] = encode('\0', i%16, fg);
        }
    }

    public int getIndex(int x, int y) {
        int i = y * width + x;
        if(i < 0)
            return 0;
        if(i >= width*height)
            return 0;
        return i;
    }

    public int getX(int i) {
        return i % width;
    }

    public int getY(int i) {
        return i / width;
    }

    public static short encode(char c, int bg, int fg) {
        return (short)((c << 8) + ((fg & 0xf) << 4) + (bg & 0xf));
    }

    private static char getChar(short s) {
        return (char)((s >> 8) & 0xff);
    }

    private static int getBg(short s) {
        return (s & 0xf);
    }

    private static int getFg(short s) {
        return ((s >> 4) & 0xf);
    }

    public static int getHexColor(int i) {
        return fgColors[i];
    }

    public static int getRenderColor(int i) {
        return getHexColor(i) + 0xFF000000;
    }

    public int print(int pos, String text) {
        int i;
        for(i = 0; i + pos < screen.length && i < text.length(); i++) {
            screen[i + pos] = encode(text.charAt(i), bg, fg);
        }
        return i;
    }

    public int print(int x, int y, String text) {
        return print(getIndex(x, y), text);
    }

    public int print(int x, int y, Component text) {
        int i = getIndex(x, y);
        return print(i, text.getString(width*height-i));
    }

    public void setBg(int col) {
        this.bg = col;
    }

    public void setFg(int col) {
        this.fg = col;
    }

    public void setColors(int bg, int fg) {
        this.setBg(bg);
        this.setFg(fg);
    }

    public short getDataAt(int x, int y) {
        return screen[getIndex(x, y)];
    }

    public int getBackground(int x, int y) {
        return getBg(getDataAt(x, y));
    }

    public int getForeground(int x, int y) {
        return getFg(getDataAt(x, y));
    }

    public char getChar(int x, int y) {
        return getChar(getDataAt(x, y));
    }

    public static void main(String[] args) {
        short e = encode('z', 3, 14);
        System.out.println(Integer.toBinaryString(e));
        System.out.println(getChar(e) + ":" + getBg(e) + ":" + getFg(e));
    }

    public void renderBackground(GuiGraphics gg, int swidth, int sheight) {
        int startx = swidth/2 - width*cellWidth/2;
        int starty = sheight/2 - height*cellHeight/2;
        for(int i = 0; i < screen.length; i++) {
            int x = startx + getX(i) * cellWidth;
            int y = starty + getY(i) * cellHeight;
            gg.fill(x, y, x + cellWidth, y + cellHeight, getRenderColor(getBg(screen[i])));
        }

        if(bgRenderer != null)
            bgRenderer.render(gg, startx, starty, width * cellWidth, height * cellHeight);
    }

    public void renderForeground(GuiGraphics gg, Font fr, int swidth, int sheight) {
        int startx = swidth/2 - width*cellWidth/2;
        int starty = sheight/2 - height*cellHeight/2;
        for(int i = 0; i < screen.length; i++) {
            int x = startx + getX(i) * cellWidth;
            int y = starty + getY(i) * cellHeight;
            if(getChar(screen[i]) != '\0')
                gg.drawString(fr, Component.translatable(getChar(screen[i])+""), x+1, y, getHexColor(getFg(screen[i])));
        }

        if(fgRenderer != null)
            fgRenderer.render(gg, startx, starty, width * cellWidth, height * cellHeight);
    }
}
