package com.leynmaster.advent.aoc2023.day17;

import com.leynmaster.advent.aoc2023.common.matrix.Coordinate;
import com.leynmaster.advent.aoc2023.common.matrix.Direction;

public record CoordinateCall(Coordinate coordinate, Direction direction, int steps, int totalHeat) {

    public CoordinateCall(int x, int y, Direction direction, int steps, int totalHeat) {
        this(new Coordinate(x, y), direction, steps, totalHeat);
    }
}

