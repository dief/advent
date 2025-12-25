package com.leynmaster.advent.aoc2022.day14;

import com.leynmaster.advent.utils.input.NumberUtils;
import com.leynmaster.advent.utils.map.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class RockLine {
    private final Coordinate start;
    private final Coordinate end;
    private final boolean horizontal;

    private RockLine(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
        this.horizontal = start.x() < end.x();
    }

    Coordinate getStart() {
        return start;
    }

    Coordinate getEnd() {
        return end;
    }

    RockLine translate(int xShift) {
        Coordinate shift = new Coordinate(-1 * xShift, 0);
        return new RockLine(getStart().move(shift), getEnd().move(shift));
    }

    void draw(char[][] matrix) {
        if (horizontal) {
            for (int i = start.x(); i <= end.x(); i++) {
                matrix[start.y()][i] = '#';
            }
        } else {
            for (int i = start.y(); i <= end.y(); i++) {
                matrix[i][start.x()] = '#';
            }
        }
    }

    static Stream<RockLine> parse(String line) {
        List<RockLine> lines = new ArrayList<>();
        String[] endings = line.split("\\s*->\\s*");
        for (int i = 1; i < endings.length; i++) {
            Coordinate c1 = parseCoordinate(endings[i - 1]);
            Coordinate c2 = parseCoordinate(endings[i]);
            lines.add(c1.x() < c2.x() || c1.y() < c2.y() ? new RockLine(c1, c2) : new RockLine(c2, c1));
        }
        return lines.stream();
    }

    private static Coordinate parseCoordinate(String coordinateStr) {
        int[] split = NumberUtils.parseInts(coordinateStr);
        return new Coordinate(split[0], split[1]);
    }
}
