package com.leynmaster.advent.aoc2023.day18;

import com.leynmaster.advent.aoc2023.common.matrix.Coordinate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Lagoon {
    private final List<Coordinate> coordinates = new ArrayList<>();
    private long perimeter;

    public long area() {
        Iterator<Coordinate> iterator = coordinates.iterator();
        Coordinate prev = iterator.next();
        Coordinate current;
        long shoelace = 0;
        while (iterator.hasNext()) {
            current = iterator.next();
            shoelace += (long)prev.x() * current.y() - (long)prev.y() * current.x();
            prev = current;
        }
        return (Math.abs(shoelace) + perimeter) / 2 + 1;
    }

    public void build(List<BuildStep> steps) {
        Coordinate initial = new Coordinate(0, 0);
        Coordinate prev = initial;
        coordinates.add(initial);
        for (BuildStep step : steps) {
            int distance = step.distance();
            perimeter += distance;
            Coordinate next = switch (step.direction()) {
                case UP -> new Coordinate(prev.x() - distance, prev.y());
                case DOWN -> new Coordinate(prev.x() + distance, prev.y());
                case LEFT -> new Coordinate(prev.x(), prev.y() - distance);
                case RIGHT -> new Coordinate(prev.x(), prev.y() + distance);
            };
            coordinates.add(next);
            prev = next;
        }
    }
}
