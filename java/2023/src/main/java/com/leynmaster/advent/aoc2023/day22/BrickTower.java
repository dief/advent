package com.leynmaster.advent.aoc2023.day22;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BrickTower {
    private final Map<Integer, Brick> brickMap = new TreeMap<>();
    private final List<int[][]> levels;
    private final int depth;
    private final int width;

    public BrickTower(List<int[][]> levels, List<Brick> bricks) {
        this.levels = levels;
        int[][] base = levels.getFirst();
        depth = base.length;
        width = base[0].length;
        bricks.forEach(brick -> brickMap.put(brick.getLabel(), brick));
    }

    public void settleBricks() {
        for (int z = 1; z < levels.size(); z++) {
            for (int i = 0; i < depth; i++) {
                for (int j = 0; j < width; j++) {
                    settleCoordinate(i, j, z);
                }
            }
        }
        wireSupports();
    }

    public long bricksToDisintegrate() {
        return brickMap.values().stream().filter(Brick::canDisintegrate).count();
    }

    private void wireSupports() {
        for (Brick brick : brickMap.values()) {
            if (brick.isVertical()) {
                wireVerticalSupport(brick);
            } else {
                wireHorizontalSupport(brick);
            }
        }
    }

    private void wireVerticalSupport(Brick brick) {
        Brick below = brickMap.get(getLabel(brick.getCoordinates().getFirst().lowerLevel()));
        Brick above = brickMap.get(getLabel(brick.getCoordinates().getLast().upperLevel()));
        if (below != null) {
            wireBricks(below, brick);
        }
        if (above != null) {
            wireBricks(brick, above);
        }
    }

    private void settleCoordinate(int x, int y, int z) {
        int label = levels.get(z)[x][y];
        if (label > 0) {
            Brick brick = brickMap.get(label);
            if (!brick.isSettled()) {
                settleBrick(brick);
            }
        }
    }

    private void settleBrick(Brick brick) {
        boolean blocked = false;
        int level = brick.getLevel();
        List<BrickCoordinate> coordinates = brick.getCoordinates();
        while (!blocked) {
            blocked = level < 2 || (brick.isVertical() ?
                    checkBlocked(coordinates.getFirst()) :
                    checkBlocked(coordinates));
            if (blocked) {
                brick.setCoordinates(coordinates);
                setCoordinates(coordinates, brick.getLabel());
            } else {
                setCoordinates(coordinates, 0);
                coordinates = coordinates.stream().map(BrickCoordinate::lowerLevel).toList();
            }
            level--;
        }
        brick.setSettled(true);
    }

    private void setCoordinates(List<BrickCoordinate> coordinates, int label) {
        for (BrickCoordinate coordinate : coordinates) {
            levels.get(coordinate.z())[coordinate.x()][coordinate.y()] = label;
        }
    }

    private boolean checkBlocked(List<BrickCoordinate> coordinates) {
        for (BrickCoordinate coordinate : coordinates) {
            if (checkBlocked(coordinate)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkBlocked(BrickCoordinate coordinate) {
        return getLabel(coordinate.lowerLevel()) > 0;
    }

    private void wireHorizontalSupport(Brick brick) {
        for (BrickCoordinate coordinate : brick.getCoordinates()) {
            Brick below = brickMap.get(getLabel(coordinate.lowerLevel()));
            Brick above = brickMap.get(getLabel(coordinate.upperLevel()));
            if (below != null) {
                wireBricks(below, brick);
            }
            if (above != null) {
                wireBricks(brick, above);
            }
        }
    }

    private void wireBricks(Brick lower, Brick upper) {
        lower.addSupported(upper);
        upper.addSupportedBy(lower);
    }

    private int getLabel(BrickCoordinate coordinate) {
        return levels.get(coordinate.z())[coordinate.x()][coordinate.y()];
    }
}
