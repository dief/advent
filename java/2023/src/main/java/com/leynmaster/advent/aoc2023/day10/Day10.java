package com.leynmaster.advent.aoc2023.day10;

import com.leynmaster.advent.utils.map.Coordinate;
import com.leynmaster.advent.utils.map.Direction;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Advent 2023 Day 10.
 *
 * @author David Charles Pollack
 */
public class Day10 {
//    private static final String INPUT_FILE = "../../inputs/2023/day10/test-1.txt";
//    private static final String INPUT_FILE = "../../inputs/2023/day10/test-2.txt";
//    private static final String INPUT_FILE = "../../inputs/2023/day10/test-3.txt";
//    private static final String INPUT_FILE = "../../inputs/2023/day10/test-4.txt";
//    private static final String INPUT_FILE = "../../inputs/2023/day10/test-5.txt";
//    private static final String INPUT_FILE = "../../inputs/2023/day10/test-6.txt";
    private static final String INPUT_FILE = "../../inputs/2023/day10/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private char[][] grid;
    private int height;
    private int width;
    private Coordinate start;
    private int perimeter;
    private int shoelace;

    void main() throws IOException {
        grid = FileUtils.readLines(new File(INPUT_FILE), StandardCharsets.UTF_8).stream()
                .filter(line -> !line.isBlank())
                .map(String::toCharArray)
                .toArray(char[][]::new);
        height = grid.length;
        width = grid[0].length;
        start = findStart();
        grid[start.y()][start.x()] = determineStartType();
        traversePath();
        logger.info("Starting");
        logger.info("Part 1: {}", perimeter / 2);
        logger.info("Part 2: {}", (Math.abs(shoelace) - perimeter) / 2 + 1);
    }

    private void traversePath() {
        perimeter++;
        Coordinate prev = start;
        Coordinate current = nextCoordinates(start)[0];
        while (!current.equals(start)) {
            perimeter++;
            shoelace += prev.x() * current.y() - prev.y() * current.x();
            Coordinate[] search = nextCoordinates(current);
            Coordinate next = prev.equals(search[0]) ? search[1] : search[0];
            prev = current;
            current = next;
        }
        shoelace += prev.x() * start.y() - prev.y() * start.x();
    }

    private Coordinate[] nextCoordinates(Coordinate coordinate) {
        return switch(grid[coordinate.y()][coordinate.x()]) {
            case 'L' -> new Coordinate[] { coordinate.move(Direction.UP), coordinate.move(Direction.RIGHT) };
            case '|' -> new Coordinate[] { coordinate.move(Direction.UP), coordinate.move(Direction.DOWN) };
            case 'J' -> new Coordinate[] { coordinate.move(Direction.UP), coordinate.move(Direction.LEFT) };
            case 'F' -> new Coordinate[] { coordinate.move(Direction.RIGHT), coordinate.move(Direction.DOWN) };
            case '-' -> new Coordinate[] { coordinate.move(Direction.RIGHT), coordinate.move(Direction.LEFT) };
            case '7' -> new Coordinate[] { coordinate.move(Direction.DOWN), coordinate.move(Direction.LEFT) };
            default -> throw new RuntimeException("Not part of the pipe: " + coordinate + " " + grid[coordinate.y()][coordinate.x()]);
        };
    }

    private Coordinate findStart() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == 'S') {
                    return new Coordinate(j, i);
                }
            }
        }
        throw new RuntimeException("No start coordinate");
    }

    private char determineStartType() {
        boolean north = checkCoordinate(start.x(), start.y() - 1, '|', '7', 'F');
        boolean south = checkCoordinate(start.x(), start.y() + 1, '|', 'L', 'J');
        boolean east = checkCoordinate(start.x() + 1, start.y(), '-', 'J', '7');
        if (north) {
            return south ? '|' : east ? 'L' : 'J';
        }
        if (south) {
            return east ? 'F' : '7';
        }
        return '-';
    }

    private boolean checkCoordinate(int x, int y, char... checks) {
        char spot = x < 0 || x >= width || y < 0 || y >= height ? '.' : grid[y][x];
        for (char check : checks) {
            if (spot == check) {
                return true;
            }
        }
        return false;
    }
}
