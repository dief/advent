package com.leynmaster.advent.aoc2023.day3;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Advent 2023 Day 3.
 *
 * @author David Charles Pollack
 */
public class Day3 {
//    private static final String INPUT_FILE = "../../inputs/2023/day3/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2023/day3/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<String> schematic = new ArrayList<>();
    private int height;
    private int width;
    private int[][] ratios;
    private int[][] counter;

    void main() throws IOException {
        for (String line : FileUtils.readLines(new File(INPUT_FILE), StandardCharsets.UTF_8)) {
            if (!line.isBlank()) {
                schematic.add(line);
            }
        }
        init();
        logger.info("Starting");
        logger.info("Part 1: {}", partOne());
        logger.info("Part 2: {}", partTwo());
    }

    private void init() {
        height = schematic.size();
        width = schematic.getFirst().length();
        ratios = new int[height][width];
        counter = new int[height][width];
        for (int[] row : ratios) {
            Arrays.fill(row, 1);
        }
        for (int[] row : counter) {
            Arrays.fill(row, 0);
        }
    }

    private int partOne() {
        int sum = 0;
        for (int i = 0; i < height; i++) {
            List<Integer> parts = findParts(schematic.get(i), i);
            for (int part : parts) {
                sum += part;
            }
        }
        return sum;
    }

    private int partTwo() {
        int sum = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (counter[i][j] == 2) {
                    sum += ratios[i][j];
                }
            }
        }
        return sum;
    }

    private List<Integer> findParts(String row, int rowNum) {
        List<Integer> parts = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            if (Character.isDigit(row.charAt(i))) {
                int end = findEnd(row, i);
                if (isPart(rowNum, i, end)) {
                    int num = Integer.parseInt(row.substring(i, end));
                    parts.add(num);
                    checkGears(num, rowNum, i, end);
                }
                i = end - 1;
            }
        }
        return parts;
    }

    private void checkGears(int num, int row, int start, int end) {
        if (get(row, start - 1) == '*') {
            addToRatio(num, row, start - 1);
        }
        if (get(row, end) == '*') {
            addToRatio(num, row, end);
        }
        checkAdjacent(num, row, start, end);
    }

    private void checkAdjacent(int num, int row, int start, int end) {
        for (int i = start - 1; i <= end; i++) {
            if (get(row - 1, i) == '*') {
                addToRatio(num, row - 1, i);
            }
            if (get(row + 1, i) == '*') {
                addToRatio(num, row + 1, i);
            }
        }
    }

    private void addToRatio(int num, int row, int col) {
        ratios[row][col] *= num;
        counter[row][col]++;
    }

    private boolean isPart(int rowNum, int start, int end) {
        if (isSymbol(rowNum, start - 1) || isSymbol(rowNum, end)) {
            return true;
        }
        for (int i = start - 1; i <= end; i++) {
            if (isSymbol(rowNum - 1, i) || isSymbol(rowNum + 1, i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSymbol(int row, int col) {
        char c = get(row, col);
        return !(Character.isDigit(c) || c == '.');
    }

    private char get(int row, int col) {
        if (row < 0 || row >= height || col < 0 || col >= width) {
            return '.';
        }
        return schematic.get(row).charAt(col);
    }

    private static int findEnd(String row, int start) {
        for (int i = start + 1; i < row.length(); i++) {
            if (!Character.isDigit(row.charAt(i))) {
                return i;
            }
        }
        return row.length();
    }
}
