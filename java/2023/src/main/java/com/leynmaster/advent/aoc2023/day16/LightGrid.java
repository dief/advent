package com.leynmaster.advent.aoc2023.day16;

import com.leynmaster.advent.aoc2023.common.matrix.Coordinate;
import com.leynmaster.advent.aoc2023.common.matrix.Direction;

import java.util.*;
import java.util.function.BinaryOperator;

public class LightGrid {
    private final Set<Coordinate> energizedSet = new HashSet<>();
    private final Set<CoordinateCall> callTracker = new HashSet<>();
    private final LinkedList<CoordinateCall> queue = new LinkedList<>();
    private final char[][] grid;
    private final int rows;
    private final int columns;

    public LightGrid(char[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.columns = grid[0].length;
    }

    public int part1() {
        return energize(new CoordinateCall(0, 0, Direction.RIGHT));
    }

    public int part2() {
        List<CoordinateCall> calls = new ArrayList<>();
        calls.add(new CoordinateCall(0, 0, Direction.RIGHT));
        calls.add(new CoordinateCall(0, columns - 1, Direction.LEFT));
        calls.add(new CoordinateCall(rows - 1, 0, Direction.RIGHT));
        calls.add(new CoordinateCall(rows - 1, columns - 1, Direction.LEFT));
        for (int i = 0; i < columns; i++) {
            calls.add(new CoordinateCall(0, i, Direction.DOWN));
            calls.add(new CoordinateCall(rows - 1, i, Direction.UP));
        }
        for (int i = 0; i < rows; i++) {
            calls.add(new CoordinateCall(i, 0, Direction.RIGHT));
            calls.add(new CoordinateCall(i, columns - 1, Direction.LEFT));
        }
        return calls.stream().map(this::energize).reduce(0, BinaryOperator.maxBy(
                Comparator.comparingInt(Integer::intValue)));
    }

    private int energize(CoordinateCall start) {
        energizedSet.clear();
        callTracker.clear();
        queue.addLast(start);
        while (!queue.isEmpty()) {
            CoordinateCall call = queue.removeFirst();
            int x = call.x();
            int y = call.y();
            if (x < 0 || x >= rows || y < 0 || y >= columns) {
                continue;
            }
            if (callTracker.contains(call)) {
                continue;
            }
            callTracker.add(call);
            energizedSet.add(new Coordinate(x, y));
            switch (grid[x][y]) {
                case '/' -> handleForwardReflect(x, y, call.direction());
                case '\\' -> handleBackReflect(x, y, call.direction());
                case '|' -> handleVerticalSplit(x, y, call.direction());
                case '-' -> handleHorizontalSplit(x, y, call.direction());
                default -> handleSpace(x, y, call.direction());
            }
        }
        return energizedSet.size();
    }
    
    private void handleSpace(int x, int y, Direction direction) {
        switch (direction) {
            case UP -> queue.addLast(new CoordinateCall(x - 1, y, direction));
            case DOWN -> queue.addLast(new CoordinateCall(x + 1, y, direction));
            case LEFT -> queue.addLast(new CoordinateCall(x, y - 1, direction));
            case RIGHT -> queue.addLast(new CoordinateCall(x, y + 1, direction));
        }
    }

    private void handleForwardReflect(int x, int y, Direction direction) {
        switch (direction) {
            case UP -> queue.addLast(new CoordinateCall(x, y + 1, Direction.RIGHT));
            case DOWN -> queue.addLast(new CoordinateCall(x, y - 1, Direction.LEFT));
            case LEFT -> queue.addLast(new CoordinateCall(x + 1, y, Direction.DOWN));
            case RIGHT -> queue.addLast(new CoordinateCall(x - 1, y, Direction.UP));
        }
    }

    private void handleBackReflect(int x, int y, Direction direction) {
        switch (direction) {
            case UP -> queue.addLast(new CoordinateCall(x, y - 1, Direction.LEFT));
            case DOWN -> queue.addLast(new CoordinateCall(x, y + 1, Direction.RIGHT));
            case LEFT -> queue.addLast(new CoordinateCall(x - 1, y, Direction.UP));
            case RIGHT -> queue.addLast(new CoordinateCall(x + 1, y, Direction.DOWN));
        }
    }

    private void handleVerticalSplit(int x, int y, Direction direction) {
        switch (direction) {
            case UP -> queue.addLast(new CoordinateCall(x - 1, y, direction));
            case DOWN -> queue.addLast(new CoordinateCall(x + 1, y, direction));
            default -> {
                queue.addLast(new CoordinateCall(x - 1, y, Direction.UP));
                queue.addLast(new CoordinateCall(x + 1, y, Direction.DOWN));
            }
        }
    }

    private void handleHorizontalSplit(int x, int y, Direction direction) {
        switch (direction) {
            case LEFT -> queue.addLast(new CoordinateCall(x, y - 1, direction));
            case RIGHT -> queue.addLast(new CoordinateCall(x, y + 1, direction));
            default -> {
                queue.addLast(new CoordinateCall(x, y - 1, Direction.LEFT));
                queue.addLast(new CoordinateCall(x, y + 1, Direction.RIGHT));
            }
        }
    }

    private record CoordinateCall(int x, int y, Direction direction) { }
}
