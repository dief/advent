package com.leynmaster.advent.aoc2024.day6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Advent 2024 Day 6.
 *
 * @author David Charles Pollack
 */
public class Day6 {
//    private static final String INPUT_FILE = "../../inputs/2024/day6/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day6/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final GuardPosition[][] map;
    private final int height;
    private final int width;
    private int startX;
    private int startY;

    public Day6(GuardPosition[][] map) {
        this.map = map;
        this.height = map.length;
        this.width = map[0].length;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (map[x][y].isStart()) {
                    this.startX = x;
                    this.startY = y;
                }
            }
        }
    }

    public void run() {
        logger.info("Part 1: {}", part1());
        reset();
        logger.info("Part 2: {}", part2());
    }

    private int part1() {
        traverse();
        int total = 0;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (map[x][y].isVisited()) {
                    total++;
                }
            }
        }
        return total;
    }

    private int part2() {
        int cycles = 0;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (testCycle(x, y)) {
                    cycles++;
                }
                reset();
            }
        }
        return cycles;
    }

    private boolean testCycle(int x, int y) {
        if (startX == x && startY == y || map[x][y].isObstruction()) {
            return false;
        }
        map[x][y].setObstruction(true);
        return traverse();
    }

    private boolean traverse() {
        Direction direction = new Direction(-1, 0);
        int x = startX;
        int y = startY;
        while (isInbounds(x, y)) {
            if (map[x][y].isVisited(direction)) {
                return true;
            }
            int nextX = x + direction.dX();
            int nextY = y + direction.dY();
            if (isInbounds(nextX, nextY) && map[nextX][nextY].isObstruction()) {
                direction = turn(direction);
            } else {
                map[x][y].visit(direction);
                x += direction.dX();
                y += direction.dY();
            }
        }
        return false;
    }

    private void reset() {
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                map[x][y].reset();
            }
        }
    }

    private boolean isInbounds(int x, int y) {
        return x >= 0 && x < height && y >= 0 && y < width;
    }

    public static void main() throws IOException {
        List<GuardPosition[]> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(parseLine(line));
            }
        }
        Day6 solution = new Day6(list.toArray(new GuardPosition[list.size()][]));
        solution.run();
    }

    private static GuardPosition[] parseLine(String line) {
        char[] chars = line.toCharArray();
        GuardPosition[] positions = new GuardPosition[chars.length];
        for (int y = 0; y < chars.length; y++) {
            positions[y] = new GuardPosition(chars[y] == '^', chars[y] == '#');
        }
        return positions;
    }

    private static Direction turn(Direction direction) {
        return new Direction(direction.dY(), -1 * direction.dX());
    }
}
