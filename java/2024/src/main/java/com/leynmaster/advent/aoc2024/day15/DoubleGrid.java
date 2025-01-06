package com.leynmaster.advent.aoc2024.day15;

import com.leynmaster.advent.aoc2024.common.Coordinate;

import java.util.LinkedList;
import java.util.List;

public class DoubleGrid {
    private final char[][] grid;
    private final int startX;
    private final int startY;

    public DoubleGrid(char[][] grid, int startX, int startY) {
        this.grid = new char[grid.length][];
        for (int y = 0; y < grid.length; y++) {
            this.grid[y] = new char[grid[y].length * 2];
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == 'O') {
                    this.grid[y][x * 2] = '[';
                    this.grid[y][x * 2 + 1] = ']';
                } else {
                    this.grid[y][x * 2] = grid[y][x];
                    this.grid[y][x * 2 + 1] = grid[y][x];
                }

            }
        }
        this.startX = startX * 2;
        this.startY = startY;
    }

    int score() {
        int score = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == '[') {
                    score += x + y * 100;
                }
            }
        }
        return score;
    }

    void run(List<Step> steps) {
        int x = startX;
        int y = startY;
        for (Step step : steps) {
            if (move(x, y, step)) {
                x += step.getDeltaX();
                y += step.getDeltaY();
            }
        }
    }

    private boolean move(int x, int y, Step step) {
        return switch (step) {
            case UP -> moveUp(x, y);
            case DOWN -> moveDown(x, y);
            case LEFT -> moveLeft(x, y);
            case RIGHT -> moveRight(x, y);
        };
    }

    private boolean moveLeft(int x, int y) {
        int nextX = x - 1;
        LinkedList<Box> boxes = new LinkedList<>();
        while (grid[y][nextX] == ']') {
            boxes.addFirst(new Box(new Coordinate(nextX - 1, y), new Coordinate(nextX, y)));
            nextX -= 2;
        }
        if (grid[y][nextX] == '#') {
            return false;
        }
        for (Box box : boxes) {
            grid[y][box.c1().x() - 1] = '[';
            grid[y][box.c2().x() - 1] = ']';
            grid[y][box.c2().x()] = '.';
        }
        return true;
    }

    private boolean moveRight(int x, int y) {
        int nextX = x + 1;
        LinkedList<Box> boxes = new LinkedList<>();
        while (grid[y][nextX] == '[') {
            boxes.addFirst(new Box(new Coordinate(nextX, y), new Coordinate(nextX + 1, y)));
            nextX += 2;
        }
        if (grid[y][nextX] == '#') {
            return false;
        }
        for (Box box : boxes) {
            grid[y][box.c1().x() + 1] = '[';
            grid[y][box.c2().x() + 1] = ']';
            grid[y][box.c1().x()] = '.';
        }
        return true;
    }

    private boolean moveUp(int x, int y) {
        LinkedList<Box> moveQueue = new LinkedList<>();
        LinkedList<Box> searchQueue = new LinkedList<>();
        int nextY = y - 1;
        if (grid[nextY][x] == '#') {
            return false;
        }
        if (grid[nextY][x] == '[') {
            searchQueue.add(new Box(new Coordinate(x, nextY), new Coordinate(x + 1, nextY)));
        }
        if (grid[nextY][x] == ']') {
            searchQueue.add(new Box(new Coordinate(x - 1, nextY), new Coordinate(x, nextY)));
        }
        while (!searchQueue.isEmpty()) {
            Box box = searchQueue.removeFirst();
            moveQueue.addFirst(box);
            if (grid[box.c1().y() - 1][box.c1().x()] == '#' || grid[box.c2().y() - 1][box.c2().x()] == '#') {
                return false;
            }
            Coordinate topCorner = new Coordinate(box.c1().x(), box.c1().y() - 1);
            if (grid[box.c1().y() - 1][box.c1().x()] == '[') {
                searchQueue.add(new Box(topCorner, new Coordinate(box.c1().x() + 1, box.c1().y() - 1)));
            }
            if (grid[box.c1().y() - 1][box.c1().x()] == ']') {
                searchQueue.add(new Box(new Coordinate(box.c1().x() - 1, box.c1().y() - 1), topCorner));
            }
            if (grid[box.c2().y() - 1][box.c2().x()] == '[') {
                searchQueue.add(new Box(
                        new Coordinate(box.c2().x(), box.c2().y() - 1),
                        new Coordinate(box.c2().x() + 1, box.c2().y() - 1)));
            }
        }
        for (Box box : moveQueue) {
            grid[box.c1().y() - 1][box.c1().x()] = '[';
            grid[box.c2().y() - 1][box.c2().x()] = ']';
            grid[box.c1().y()][box.c1().x()] = '.';
            grid[box.c2().y()][box.c2().x()] = '.';
        }
        return true;
    }

    private boolean moveDown(int x, int y) {
        LinkedList<Box> moveQueue = new LinkedList<>();
        LinkedList<Box> searchQueue = new LinkedList<>();
        int nextY = y + 1;
        if (grid[nextY][x] == '#') {
            return false;
        }
        if (grid[nextY][x] == '[') {
            searchQueue.add(new Box(new Coordinate(x, nextY), new Coordinate(x + 1, nextY)));
        }
        if (grid[nextY][x] == ']') {
            searchQueue.add(new Box(new Coordinate(x - 1, nextY), new Coordinate(x, nextY)));
        }
        while (!searchQueue.isEmpty()) {
            Box box = searchQueue.removeFirst();
            moveQueue.addFirst(box);
            if (grid[box.c1().y() + 1][box.c1().x()] == '#' || grid[box.c2().y() + 1][box.c2().x()] == '#') {
                return false;
            }
            Coordinate bottomCorner = new Coordinate(box.c1().x(), box.c1().y() + 1);
            if (grid[box.c1().y() + 1][box.c1().x()] == '[') {
                searchQueue.add(new Box(bottomCorner, new Coordinate(box.c1().x() + 1, box.c1().y() + 1)));
            }
            if (grid[box.c1().y() + 1][box.c1().x()] == ']') {
                searchQueue.add(new Box(new Coordinate(box.c1().x() - 1, box.c1().y() + 1), bottomCorner));
            }
            if (grid[box.c2().y() + 1][box.c2().x()] == '[') {
                searchQueue.add(new Box(
                        new Coordinate(box.c2().x(), box.c2().y() + 1),
                        new Coordinate(box.c2().x() + 1, box.c2().y() + 1)));
            }
        }
        for (Box box : moveQueue) {
            grid[box.c1().y() + 1][box.c1().x()] = '[';
            grid[box.c2().y() + 1][box.c2().x()] = ']';
            grid[box.c1().y()][box.c1().x()] = '.';
            grid[box.c2().y()][box.c2().x()] = '.';
        }
        return true;
    }

    private record Box(Coordinate c1, Coordinate c2) { }
}
