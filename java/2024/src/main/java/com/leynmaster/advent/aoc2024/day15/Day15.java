package com.leynmaster.advent.aoc2024.day15;

import com.leynmaster.advent.aoc2024.common.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent 2024 Day 15.
 *
 * @author David Charles Pollack
 */
public class Day15 {
//    private static final String INPUT_FILE = "../../inputs/2024/day15/test-2.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day15/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Direction> steps = new ArrayList<>();
    private char[][] grid;
    private int startX;
    private int startY;

    void run() {
        SimpleGrid simple = new SimpleGrid(grid, startX, startY);
        simple.run(steps);
        logger.info("Part 1: {}", simple.score());
        DoubleGrid doubleGrid = new DoubleGrid(grid, startX, startY);
        doubleGrid.run(steps);
        logger.info("Part 2: {}", doubleGrid.score());
    }

    void main() throws IOException {
        List<char[]> gridLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                gridLines.add(line.toCharArray());
                line = reader.readLine();
            }
            grid = gridLines.toArray(new char[0][]);
            while ((line = reader.readLine()) != null) {
                steps.addAll(fromLine(line));
            }
        }
        findStart();
        run();
    }

    void findStart() {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[y][x] == '@') {
                    startX = x;
                    startY = y;
                    grid[y][x] = '.';
                    return;
                }
            }
        }
        throw new IllegalStateException("No start found");
    }

    private static List<Direction> fromLine(String line) {
        List<Direction> steps = new ArrayList<>();
        for (char c : line.toCharArray()) {
            steps.add(fromChar(c));
        }
        return steps;
    }

    private static Direction fromChar(char c) {
        return switch (c) {
            case '^' -> Direction.UP;
            case 'v' -> Direction.DOWN;
            case '<' -> Direction.LEFT;
            case '>' -> Direction.RIGHT;
            default -> throw new IllegalArgumentException("Invalid step character: " + c);
        };
    }
}
