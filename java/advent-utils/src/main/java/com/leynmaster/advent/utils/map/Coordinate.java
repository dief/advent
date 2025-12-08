package com.leynmaster.advent.utils.map;

import java.util.Objects;

public record Coordinate(int x, int y) {

    public Coordinate move(Direction direction) {
        return new Coordinate(x + direction.getDeltaX(), y + direction.getDeltaY());
    }

    public Coordinate move(Coordinate delta) {
        return new Coordinate(x + delta.x(), y + delta.y());
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
