package com.leynmaster.advent.aoc2023.day22;

import java.util.List;

public record BrickCoordinate(int x, int y, int z) {
    public BrickCoordinate(List<Integer> components) {
        this(components.get(0), components.get(1), components.get(2));
    }

    public BrickCoordinate lowerLevel() {
        return new BrickCoordinate(x(), y(), z() - 1);
    }

    public BrickCoordinate upperLevel() {
        return new BrickCoordinate(x(), y(), z() + 1);
    }
}
