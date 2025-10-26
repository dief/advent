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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinate that = (Coordinate)o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
