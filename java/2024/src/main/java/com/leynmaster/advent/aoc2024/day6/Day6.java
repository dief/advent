package com.leynmaster.advent.aoc2024.day6;

import com.leynmaster.advent.aoc2024.common.Direction;
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
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (map[y][x].isStart()) {
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
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (testCycle(x, y)) {
                    cycles++;
                }
                reset();
            }
        }
        return cycles;
    }

    private boolean testCycle(int x, int y) {
        if (startX == x && startY == y || map[y][x].isObstruction()) {
            return false;
        }
        map[y][x].setObstruction(true);
        return traverse();
    }

    private boolean traverse() {
        Direction direction = Direction.UP;
        int x = startX;
        int y = startY;
        while (isInbounds(x, y)) {
            if (map[y][x].isVisited(direction)) {
                return true;
            }
            int nextX = x + direction.getDeltaX();
            int nextY = y + direction.getDeltaY();
            if (isInbounds(nextX, nextY) && map[nextY][nextX].isObstruction()) {
                direction = turn(direction);
            } else {
                map[y][x].visit(direction);
                x += direction.getDeltaX();
                y += direction.getDeltaY();
            }
        }
        return false;
    }

    private void reset() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                map[y][x].reset();
            }
        }
    }

    private boolean isInbounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
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
        for (int x = 0; x < chars.length; x++) {
            positions[x] = new GuardPosition(chars[x] == '^', chars[x] == '#');
        }
        return positions;
    }

    private static Direction turn(Direction direction) {
        return switch (direction) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
        };
    }
}
