package com.leynmaster.advent.aoc2024.day4;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent 2024 Day 4.
 *
 * @author David Charles Pollack
 */
public class Day4 {
//    private static final String INPUT_FILE = "../../inputs/2024/day4/test.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day4/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final char[] xmasSearch = {'X', 'M', 'A', 'S'};
    private final char[] masSearch = {'M', 'A', 'S'};
    private final char[][] grid;
    private final int height;
    private final int width;


    public Day4(char[][] grid) {
        this.grid = grid;
        this.height = grid.length;
        this.width = grid[0].length;
    }

    public void run() {
        logger.info("Part 1: {}", part1());
        logger.info("Part 2: {}", part2());
    }

    private int part1() {
        int total = 0;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                total += scan(xmasSearch, x, y, 0, 1);
                total += scan(xmasSearch, x, y, 0, -1);
                total += scan(xmasSearch, x, y, 1, 0);
                total += scan(xmasSearch, x, y, -1, 0);
                total += scan(xmasSearch, x, y, 1, 1);
                total += scan(xmasSearch, x, y, -1, 1);
                total += scan(xmasSearch, x, y, 1, -1);
                total += scan(xmasSearch, x, y, -1, -1);
            }
        }
        return total;
    }

    private int part2() {
        int total = 0;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (scan(masSearch, x, y, 1, 1) > 0 ||
                        scan(masSearch, x + 2, y + 2, -1, -1) > 0) {
                    total += scan(masSearch, x + 2, y, -1, 1);
                    total += scan(masSearch, x, y + 2, 1, -1);
                }
            }
        }
        return total;
    }

    private int scan(char[] search, int startX, int startY, int dX, int dY) {
        int x = startX;
        int y = startY;
        for (char c : search) {
            if (x < 0 || x >= height || y < 0 || y >= width) {
                return 0;
            }
            if (grid[x][y] != c) {
                return 0;
            }
            x += dX;
            y += dY;
        }
        return 1;
    }

    public static void main() throws IOException {
        List<char[]> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.toCharArray());
            }
        }
        Day4 solution = new Day4(lines.toArray(new char[lines.size()][]));
        solution.run();
    }
}
