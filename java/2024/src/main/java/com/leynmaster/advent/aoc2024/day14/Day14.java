package com.leynmaster.advent.aoc2024.day14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent 2024 Day 14.
 *
 * @author David Charles Pollack
 */
public class Day14 {
//    private static final String INPUT_FILE = "../../inputs/2024/day14/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day14/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Robot> robots = new ArrayList<>();

    void main() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("[,= ]");
                robots.add(new Robot(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]),
                        Integer.parseInt(parts[1]), Integer.parseInt(parts[2])));
            }
        }
        run();
    }

    void run() {
        for (int i = 0; i < 100; i++) {
            robots.forEach(Robot::move);
        }
        logger.info("Part 1: {}", safetyFactor());
        robots.forEach(Robot::reset);
        for (int i = 0; i < 100_000; i++) {
            robots.forEach(Robot::move);
            int[][] grid = createGrid();
            if (detectOutline(grid)) {
                logger.info("Part 2: {}", i + 1);
                printGrid(grid);
                break;
            }
        }
    }

    private int safetyFactor() {
        int[] counts = new int[5];
        for (Robot robot : robots) {
            counts[robot.quadrant()]++;
        }
        return counts[1] * counts[2] * counts[3] * counts[4];
    }

    private int[][] createGrid() {
        int[][] grid = new int[Robot.HEIGHT][Robot.WIDTH];
        for (Robot robot : robots) {
            grid[robot.getY()][robot.getX()]++;
        }
        return grid;
    }

    private void printGrid(int[][] count) {
        StringBuilder buf = new StringBuilder();
        for (int y = 0; y < Robot.HEIGHT; y++) {
            for (int x = 0; x < Robot.WIDTH; x++) {
                buf.append(count[y][x] > 0 ? String.valueOf(count[y][x]) : '.');
            }
            buf.append('\n');
        }
        logger.info("\n{}", buf);
    }

    private boolean detectOutline(int[][] grid) {
        for (int y = 0; y < Robot.HEIGHT; y++) {
            int count = 0;
            for (int x = 0; x < Robot.WIDTH; x++) {
                if (grid[y][x] > 0) {
                    count++;
                } else {
                    count = 0;
                }
                if (count > 30) {
                    return true;
                }
            }
        }
        return false;
    }
}
