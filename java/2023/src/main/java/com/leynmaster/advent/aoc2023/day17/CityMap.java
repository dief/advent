package com.leynmaster.advent.aoc2023.day17;

import com.leynmaster.advent.utils.map.Coordinate;
import com.leynmaster.advent.utils.map.Direction;

public class CityMap {
    private final HeatTraversalQueue queue = new HeatTraversalQueue();
    private final int[][] map;
    private final int rows;
    private final int cols;
    private final int minThreshold;
    private final int maxThreshold;

    public CityMap(int[][] map, int minThreshold, int maxThreshold) {
        this.map = map;
        this.rows = map.length;
        this.cols = map[0].length;
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
    }

    public int heat() {
        queue.offer(new CoordinateCall(0, 1, Direction.RIGHT, 0, map[0][1]));
        queue.offer(new CoordinateCall(1, 0, Direction.DOWN, 0, map[1][0]));
        int lowestHeat = Integer.MAX_VALUE;
        CoordinateCall call;
        while ((call = queue.poll()) != null) {
            if (call.coordinate().x() == rows - 1 && call.coordinate().y() == cols - 1 &&
                    call.steps() > minThreshold && call.totalHeat() < lowestHeat) {
                lowestHeat = call.totalHeat();
            }
            updateNeighbors(call);
        }
        return lowestHeat;
    }

    private void nextCall(CoordinateCall call, int x, int y, Direction direction, int steps) {
        if (x < 0 || x >= rows || y < 0 || y >= cols) {
            return;
        }
        queue.offer(new CoordinateCall(x, y, direction, steps, call.totalHeat() + map[x][y]));
    }

    private void updateNeighbors(CoordinateCall call) {
        switch (call.direction()) {
            case UP -> neighborsUp(call);
            case DOWN -> neighborsDown(call);
            case LEFT -> neighborsLeft(call);
            case RIGHT -> neighborsRight(call);
        }
    }

    private void neighborsUp(CoordinateCall call) {
        int steps = call.steps();
        Coordinate coordinate = call.coordinate();
        int x = coordinate.x();
        int y = coordinate.y();
        if (steps < maxThreshold) {
            nextCall(call, x - 1, y, Direction.UP, steps + 1);
        }
        if (steps > minThreshold) {
            nextCall(call, x, y - 1, Direction.LEFT, 0);
            nextCall(call, x, y + 1, Direction.RIGHT, 0);
        }
    }

    private void neighborsDown(CoordinateCall call) {
        int steps = call.steps();
        Coordinate coordinate = call.coordinate();
        int x = coordinate.x();
        int y = coordinate.y();
        if (steps < maxThreshold) {
            nextCall(call, x + 1, y, Direction.DOWN, steps + 1);
        }
        if (steps > minThreshold) {
            nextCall(call, x, y - 1, Direction.LEFT, 0);
            nextCall(call, x, y + 1, Direction.RIGHT, 0);
        }
    }

    private void neighborsLeft(CoordinateCall call) {
        int steps = call.steps();
        Coordinate coordinate = call.coordinate();
        int x = coordinate.x();
        int y = coordinate.y();
        if (steps < maxThreshold) {
            nextCall(call, x, y - 1, Direction.LEFT, steps + 1);
        }
        if (steps > minThreshold) {
            nextCall(call, x - 1, y, Direction.UP, 0);
            nextCall(call, x + 1, y, Direction.DOWN, 0);
        }
    }

    private void neighborsRight(CoordinateCall call) {
        int steps = call.steps();
        Coordinate coordinate = call.coordinate();
        int x = coordinate.x();
        int y = coordinate.y();
        if (steps < maxThreshold) {
            nextCall(call, x, y + 1, Direction.RIGHT, steps + 1);
        }
        if (steps > minThreshold) {
            nextCall(call, x - 1, y, Direction.UP, 0);
            nextCall(call, x + 1, y, Direction.DOWN, 0);
        }
    }
}
