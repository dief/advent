package com.leynmaster.advent.aoc2024.day15;

import com.leynmaster.advent.aoc2024.common.Direction;

import java.util.Arrays;
import java.util.List;

public class SimpleGrid {
    private final char[][] grid;
    private final int startX;
    private final int startY;

    public SimpleGrid(char[][] grid, int startX, int startY) {
        this.grid = new char[grid.length][];
        for (int i = 0; i < grid.length; i++) {
            this.grid[i] = Arrays.copyOf(grid[i], grid[i].length);
        }
        this.startX = startX;
        this.startY = startY;
    }

    int score() {
        int score = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 'O') {
                    score += x + y * 100;
                }
            }
        }
        return score;
    }

    void run(List<Direction> steps) {
        int x = startX;
        int y = startY;
        for (Direction step : steps) {
            if (move(x, y, step)) {
                x += step.getDeltaX();
                y += step.getDeltaY();
            }
        }
    }

    private boolean move(int x, int y, Direction step) {
        int deltaX = step.getDeltaX();
        int deltaY = step.getDeltaY();
        int nextX = x + deltaX;
        int nextY = y + deltaY;
        while (grid[nextY][nextX] == 'O') {
            nextX += deltaX;
            nextY += deltaY;
        }
        if (grid[nextY][nextX] == '#') {
            return false;
        }
        while(grid[nextY - deltaY][nextX - deltaX] == 'O') {
            grid[nextY - deltaY][nextX - deltaX] = '.';
            grid[nextY][nextX] = 'O';
            nextX -= deltaX;
            nextY -= deltaY;
        }
        return true;
    }
}
