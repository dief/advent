package com.leynmaster.advent.aoc2022.day8;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Day8 {
//    private static final String INPUT = "../../inputs/2022/day8/test-1.txt";
    private static final String INPUT = "../../inputs/2022/day8/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private int[][] grid;
    int height;
    int width;

    void main() throws IOException {
        logger.info("Starting");
        parseInput(FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8));
        logger.info("Part 1: {}", countVisible());
        logger.info("Part 2: {}", maxScore());
    }

    private int countVisible() {
        int total = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (checkVisible(i, j)) {
                    total++;
                }
            }
        }
        return total;
    }

    private int maxScore() {
        int max = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int score = viewUp(i, j) * viewDown(i, j) * viewLeft(i, j) * viewRight(i, j);
                if (score > max) {
                    max = score;
                }
            }
        }
        return max;
    }

    private boolean checkVisible(int row, int col) {
        boolean visible = visibleFromTop(row, col);
        visible = visible || visibleFromBottom(row, col);
        visible = visible || visibleFromLeft(row, col);
        visible = visible || visibleFromRight(row, col);
        return visible;
    }

    private boolean visibleFromTop(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (grid[i][col] >= grid[row][col]) {
                return false;
            }
        }
        return true;
    }

    private boolean visibleFromBottom(int row, int col) {
        for (int i = row + 1; i < height; i++) {
            if (grid[i][col] >= grid[row][col]) {
                return false;
            }
        }
        return true;
    }

    private boolean visibleFromLeft(int row, int col) {
        for (int j = 0; j < col; j++) {
            if (grid[row][j] >= grid[row][col]) {
                return false;
            }
        }
        return true;
    }

    private boolean visibleFromRight(int row, int col) {
        for (int j = col + 1; j < width; j++) {
            if (grid[row][j] >= grid[row][col]) {
                return false;
            }
        }
        return true;
    }

    private int viewUp(int row, int col) {
        int count = 0;
        for (int i = row - 1; i >= 0; i--) {
            if (grid[i][col] >= grid[row][col]) {
                return ++count;
            }
            count++;
        }
        return count;
    }

    private int viewDown(int row, int col) {
        int count = 0;
        for (int i = row + 1; i < height; i++) {
            if (grid[i][col] >= grid[row][col]) {
                return ++count;
            }
            count++;
        }
        return count;
    }

    private int viewLeft(int row, int col) {
        int count = 0;
        for (int j = col - 1; j >= 0; j--) {
            if (grid[row][j] >= grid[row][col]) {
                return ++count;
            }
            count++;
        }
        return count;
    }

    private int viewRight(int row, int col) {
        int count = 0;
        for (int j = col + 1; j < width; j++) {
            if (grid[row][j] >= grid[row][col]) {
                return ++count;
            }
            count++;
        }
        return count;
    }

    private void parseInput(List<String> lines) {
        height = lines.size();
        width = lines.getFirst().length();
        grid = new int[height][width];
        for (int i = 0; i < height; i++) {
            String line = lines.get(i);
            for (int j = 0; j < width; j++) {
                grid[i][j] = Integer.parseInt(line.charAt(j) + "");
            }
        }
    }
}
