package com.leynmaster.advent.aoc2024.day14;

public class Robot {
    public static final int HEIGHT = 103;
    public static final int WIDTH = 101;
    private final int vX;
    private final int vY;
    private final int initialX;
    private final int initialY;
    private int x;
    private int y;

    public Robot(int vX, int vY, int initialX, int initialY) {
        this.vX = vX;
        this.vY = vY;
        this.initialX = initialX;
        this.initialY = initialY;
        this.x = initialX;
        this.y = initialY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move() {
        x = wrap(x + vX, WIDTH);
        y = wrap(y + vY, HEIGHT);
    }

    public int quadrant() {
        int midX = WIDTH / 2;
        int midY = HEIGHT / 2;
        if (x == midX || y == midY) {
            return 0;
        }
        if (x < midX) {
            return y < midY ? 1 : 3;
        }
        return y < midY ? 2 : 4;
    }

    public void reset() {
        x = initialX;
        y = initialY;
    }

    private int wrap(int value, int max) {
        return (value + max) % max;
    }
}
