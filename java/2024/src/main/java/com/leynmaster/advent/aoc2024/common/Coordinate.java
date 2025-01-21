package com.leynmaster.advent.aoc2024.common;

public record Coordinate(int x, int y) {

    public Coordinate move(Direction direction) {
        return new Coordinate(x + direction.getDeltaX(), y + direction.getDeltaY());
    }

    public Coordinate move(Coordinate delta) {
        return new Coordinate(x + delta.x(), y + delta.y());
    }
}
