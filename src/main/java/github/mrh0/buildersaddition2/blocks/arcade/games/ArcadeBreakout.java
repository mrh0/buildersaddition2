package github.mrh0.buildersaddition2.blocks.arcade.games;

import github.mrh0.buildersaddition2.blocks.arcade.ArcadeDisplay;
import net.minecraft.client.gui.GuiGraphics;

import java.util.ArrayList;

public class ArcadeBreakout extends AbstractArcadeGame {

    public ArcadeBreakout(ArcadeDisplay display) {
        super(display);
        balls = new ArrayList<Ball>();
    }

    final int padWidth = 6;
    final float padSpeed = 1;
    final float ballSpeed = 1f;

    final ArrayList<Ball> balls;

    @Override
    public void start() {
        super.start();
        display.clear();
        balls.add(new Ball(display.width / 2, display.height / 2));

        display.setFgRenderer(this::fgRender);
        display.setColors(0x0, 0xf);
        display.print(0, "Work in progress.");
    }

    private void fgRender(GuiGraphics gg, int x, int y, int width, int height) {
        for (Ball b : balls) {
            b.render(gg, x, y, width, height);
        }
    }

    private class Ball {
        public float x;
        public float y;

        public float vx = 0f;
        public float vy = 0f;

        public Ball(int x, int y) {
            this.x = x;
            this.y = y;

            this.setVelocityAngle((float) Math.random() * 360f, ballSpeed);
        }

        public void update(float partial) {
            //s.setColors(0x0, 0x0);
            //s.print(getX(), getY(), " ");
            x += vx * partial;
            y += vy * partial;

            if (x + vx <= 0) {
                x = 0;
                bounceVertical();
            }
            if (y + vy <= 0) {
                y = 0;
                bounceHorizontal();
            }
            if (x + vx >= display.width - 1) {
                x = display.width - 1;
                bounceVertical();
            }
            if (y + vy >= display.height - 1) {
                y = display.height - 1;
                bounceHorizontal();
            }

            //s.setColors(0xa, 0xa);
            //s.print(getX(), getY(), " ");
        }

        public void render(GuiGraphics gg, int sx, int sy, int swidth, int sheight) {
            gg.fill(
                    sx + (int) (x * display.cellWidth),
                    sy + (int) (y * display.cellHeight),
                    sx + display.cellWidth + (int) (x * display.cellWidth),
                    sy + display.cellHeight + (int) (y * display.cellHeight),
                    ArcadeDisplay.getRenderColor(0xa)
            );
        }

        public int getX() {
            return (int) x;
        }

        public int getY() {
            return (int) y;
        }

        public void bounceVertical() {
            vx = -vx;
            playSound(NOTE_BIT, 0);
        }

        public void bounceHorizontal() {
            vy = -vy;
            playSound(NOTE_BIT, 0);
        }

        public void setVelocity(float x, float y) {
            this.vx = x;
            this.vy = y;
        }

        public void setVelocityAngle(float angle, float v) {
            this.setVelocity((float) Math.cos(Math.toRadians(angle)) * v, (float) Math.sin(Math.toRadians(angle)) * v);
        }
    }

    @Override
    public void frame(long step, float partial) {
        super.frame(step, partial);
        for (Ball b : balls) {
            b.update(partial);
        }
    }
}