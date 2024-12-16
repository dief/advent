package com.leynmaster.advent.aoc2024.day12;

import com.leynmaster.advent.aoc2024.common.Coordinate;

import java.util.HashSet;
import java.util.Set;

public class Region {
    private final Set<Coordinate> coordinates = new HashSet<>();
    private int perimeter = 0;
    private int sides = 0;

    public int price() {
        return coordinates.size() * perimeter;
    }

    public int bulkPrice() {
        return coordinates.size() * sides;
    }

    public void addCoordinate(Coordinate coordinate, int corners, int boundaries) {
        coordinates.add(coordinate);
        perimeter += boundaries;
        sides += corners;
    }
}
