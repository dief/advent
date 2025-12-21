package com.leynmaster.advent.aoc2023.day23;

import com.leynmaster.advent.utils.map.Coordinate;

public class AbstractGraphBuilder {
    private final char[][] matrix;
    private final int height;
    private final int width;
    private final Coordinate start;
    private final Coordinate end;

    AbstractGraphBuilder(char[][] matrix) {
        this.matrix = matrix;
        this.height = matrix.length;
        this.width = matrix[0].length;
        start = new Coordinate(findOpening(matrix[0]), 0);
        end = new Coordinate(findOpening(matrix[height - 1]), height - 1);
    }

    Coordinate getStart() {
        return start;
    }

    Coordinate getEnd() {
        return end;
    }

    char location(Coordinate coordinate) {
        return matrix[coordinate.y()][coordinate.x()];
    }

    boolean inbounds(Coordinate coordinate) {
        int x = coordinate.x();
        int y = coordinate.y();
        return x >= 0 && x < height && y >= 0 && y < width && matrix[y][x] != '#';
    }

    static int findOpening(char[] row) {
        for (int i = 0; i < row.length; i++) {
            if (row[i] == '.') {
                return i;
            }
        }
        return -1;
    }
}
