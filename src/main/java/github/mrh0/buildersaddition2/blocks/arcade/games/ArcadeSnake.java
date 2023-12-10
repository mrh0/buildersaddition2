package github.mrh0.buildersaddition2.blocks.arcade.games;

import github.mrh0.buildersaddition2.blocks.arcade.ArcadeDisplay;

public class ArcadeSnake extends AbstractArcadeGame {
    public ArcadeSnake(ArcadeDisplay s) {
        super(s);
        pw = s.width - 10;
        ph = s.height;
    }

    int px = 0;
    int py = 0;
    int lpx = 0;
    int lpy = 0;
    int dir = -1;
    int ldir = -1;
    int pw;
    int ph;

    long deadTimer = Long.MAX_VALUE;
    boolean isDead = false;

    int snakeColor = 0xa;
    int score;
    boolean initScreen = true;

    Tail last = null;
    Tail first = null;
    int tailExt = 0;

    @Override
    public void start() {
        super.start();
        display.clear();
        display.testScreen();
        display.setColors(0x0, 0xf);
        display.clear(0, 1, display.width, display.height-2);
        display.print(1, 1, "Arcade Snake v1.0");
        String msg = " Press any key to continue. ";
        display.print(display.width/2 - msg.length()/2, display.height/2, msg);

        initScreen = false; // skip
        begin(); // skip

        dir = -1;
    }

    private void begin() {
        display.setColors(0x0, 0xf);
        display.clear();
        px = pw/2;
        py = ph/2;
        lpx = px;
        lpy = py;
        dir = -1;
        ldir = -1;
        score = 0;
        isDead = false;
        deadTimer = Long.MAX_VALUE;
        last = null;
        first = null;
        tailExt = 0;
        display.setColors(0x8, 0xa);
        display.clear(pw, 0, display.width-pw, display.height);
        display.print(pw+1, 1, "Score:");
        display.print(pw+1, 2, score+"");
        display.print(pw+1, display.height-5, "Arrows");
        display.print(pw+1, display.height-4, "or WASD");
        spawnFood();
        spawnFood();
    }

    public int randomRange(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void spawnFood() {
        int x = 0;
        int y = 0;
        do {
            x = randomRange(0, pw);
            y = randomRange(0, ph);
        } while(display.getBackground(x, y) != 0x0 && display.getChar(x, y) != 'o');
        display.setColors(0x0, 0xe);
        display.print(x, y, "o");
        display.setColors(0x8, 0xa);
        display.print(pw+1, display.height-2, repeat(">", 7-tickRate(score)));
    }

    private int tickRate(int t) {
        if(t > 20) return 2;
        else if(t > 10) return 3;
        else if(t > 7) return 4;
        else if(t > 3) return 5;
        return 6;
    }

    private String repeat(String t, int n) {
        return String.valueOf(t).repeat(Math.max(0, n));
    }

    private void updateMovement(long time) {
        lpx = px;
        lpy = py;
        if(dir >= 0) {
            switch (dir) {
                case 0 -> {
                    py--;
                    if (py < 0) py = ph - 1;
                }
                case 1 -> {
                    px++;
                    if (px > pw - 1) px = 0;
                }
                case 2 -> {
                    py++;
                    if (py > ph - 1) py = 0;
                }
                case 3 -> {
                    px--;
                    if (px < 0) px = pw - 1;
                }
            }
            if(display.getBackground(px, py) == snakeColor) {
                deadTimer = time + 60;
                isDead = true;
                display.setColors(0x4, 0xf);
                display.print(px, py,"x");
                playSound(0);
                return;
            }
            else if(display.getChar(px, py) == 'o') {
                score += 1;
                display.setColors(0x8, 0xa);
                display.print(pw+1, 1, "Score:");
                display.print(pw+1, 2, score+"");
                spawnFood();
                tailExt = 3;
                playSound(30);
            }
            if(last != null && tailExt <= 0) {
                display.setColors(0x0, 0xf);
                display.print(last.x,  last.y, " ");
                last = last.next;
            }
            display.setColors(snakeColor, 0xf);
            display.print(lpx, lpy, getToken(dir, ldir));
            ldir = dir;
            Tail t = new Tail(lpx, lpy, null);
            if(first != null) first.next = t;
            first = t;
            if(last == null) last = t;
            tailExt--;
            if(tailExt < 0) tailExt = 0;
        }
        display.setColors(snakeColor, 0xf);
        display.print(px, py, "@");
    }

    private String getToken(int d, int ld) {
        if(d == ld) return dir == 0 || dir == 2 ? "-" : "|";
        else if((d == 0 && ld == 1) || (d == 1 && ld == 0)) return "\\";
        else if((d == 0 && ld == 3) || (d == 3 && ld == 0)) return "/";
        else if((d == 2 && ld == 1) || (d == 1 && ld == 2)) return "/";
        else if((d == 2 && ld == 3) || (d == 3 && ld == 2)) return "\\";
        return "-";
    }

    long lastStep = 0;
    @Override
    public void frame(long steps, float partial) {
        super.frame(steps, partial);
        if(steps == lastStep) return;
        if(steps > deadTimer) begin();
        if(steps%tickRate(score) == 0 && !isDead) updateMovement(steps);
        lastStep = steps;
    }

    @Override
    public void onKeyPressed(int key) {
        super.onKeyPressed(key);
        if(initScreen && isAny(key)) {
            initScreen = false;
            begin();
        }
        if(isUp(key) && ldir != 2) dir = 0;
        else if(isRight(key) && ldir != 3) dir = 1;
        else if(isDown(key) && ldir != 0) dir = 2;
        else if(isLeft(key) && ldir != 1) dir = 3;
        else if(isReset(key)) {
            display.setColors(0x4, 0x0);
            String msg = " Restarting. ";
            display.print(pw/2 - msg.length()/2, display.height/2, msg);
        }
    }

    @Override
    public void onKeyReleased(int key) {
        super.onKeyReleased(key);
        if(isReset(key)) begin();
    }

    private static class Tail {
        public int x;
        public int y;
        public Tail next;

        public Tail(int x, int y, Tail next) {
            this.x = x;
            this.y = y;
            this.next = next;
        }
    }
}