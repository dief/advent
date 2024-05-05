package com.leynmaster.advent.aoc2023.common.matrix;

public enum Direction {
    UP(-1, 0), DOWN(1, 0), LEFT(0, -1), RIGHT(0, 1);

    private final int xShift;
    private final int yShift;

    Direction(int xShift, int yShift) {
        this.xShift = xShift;
        this.yShift = yShift;
    }

    public Coordinate shift(Coordinate coordinate) {
        return new Coordinate(coordinate.x() + xShift, coordinate.y() + yShift);
    }
}
