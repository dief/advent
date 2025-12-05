package com.leynmaster.advent.aoc2025.day4;

import com.leynmaster.advent.utils.map.Coordinate;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class Day4 {
//    private static final String INPUT = "../../inputs/2025/day4/test-1.txt";
    private static final String INPUT = "../../inputs/2025/day4/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private char[][] grid;
    private int height;
    private int width;

    void main() throws IOException {
        grid = FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8).stream().map(String::toCharArray)
                .toArray(char[][]::new);
        height = grid.length;
        width = grid[0].length;
        logger.info("Starting");
        LinkedList<Coordinate> lift = getLiftList();
        logger.info("Part 1: {}", lift.size());
        int removeCount = 0;
        while (!lift.isEmpty()) {
            removeCount += lift.size();
            for (Coordinate coordinate : lift) {
                grid[coordinate.y()][coordinate.x()] = '.';
            }
            lift = getLiftList();
        }
        logger.info("Part 2: {}", removeCount);
    }

    private LinkedList<Coordinate> getLiftList() {
        LinkedList<Coordinate> lift = new LinkedList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == '@' && canLift(j, i)) {
                    lift.add(new Coordinate(j, i));
                }
            }
        }
        return lift;
    }

    private boolean canLift(int x, int y) {
        int adjacentRolls = 0;
        adjacentRolls += addRoll(x - 1, y);
        adjacentRolls += addRoll(x + 1, y);
        for (int i = x - 1; i <= x + 1; i++) {
            adjacentRolls += addRoll(i, y - 1);
            adjacentRolls += addRoll(i, y + 1);
        }
        return adjacentRolls < 4;
    }

    private int addRoll(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height && grid[y][x] == '@' ? 1 : 0;
    }
}
