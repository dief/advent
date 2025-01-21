package com.leynmaster.advent.aoc2024.day20;

import com.leynmaster.advent.aoc2024.common.Coordinate;
import com.leynmaster.advent.aoc2024.common.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Advent 2024 Day 20.
 *
 * @author David Charles Pollack
 */
public class Day20 {
//    private static final String INPUT_FILE = "../../inputs/2024/day20/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day20/input.txt";
    private static final int THRESHOLD = 100;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private char[][] grid;
    private int[][] steps;
    private int size;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    void main() throws IOException {
        List<char[]> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.toCharArray());
            }
        }
        grid = lines.toArray(new char[0][]);
        size = grid.length;
        steps = new int[size][size];
        init();
        run();
    }

    private void run() {
        logger.info("Starting");
        traverse();
        logger.info("Part 1: {}", findCheats(2));
        logger.info("Part 2: {}", findCheats(20));
    }

    private void traverse() {
        LinkedList<Step> queue = new LinkedList<>();
        queue.offer(new Step(new Coordinate(startX, startY), 0));
        while (!queue.isEmpty()) {
            Step step = queue.poll();
            if (endX == step.coordinate().x() && endY == step.coordinate().y()) {
                continue;
            }
            int nextScore = step.score() + 1;
            for (Direction direction : Direction.values()) {
                Coordinate nextPos = step.coordinate().move(direction);
                if (isSpace(nextPos) && steps[nextPos.y()][nextPos.x()] > nextScore) {
                    steps[nextPos.y()][nextPos.x()] = nextScore;
                    queue.offer(new Step(nextPos, nextScore));
                }
            }
        }
    }

    private int findCheats(int maxCheat) {
        int helpfulCheats = 0;
        List<Coordinate> cheats = getCheats(maxCheat);
        Set<Coordinate> visited = new HashSet<>();
        LinkedList<Coordinate> queue = new LinkedList<>();
        queue.offer(new Coordinate(startX, startY));
        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            visited.add(current);
            helpfulCheats += findCheats(current, cheats);
            for (Direction direction : Direction.values()) {
                Coordinate next = current.move(direction);
                if (!visited.contains(next) && isSpace(next)) {
                    queue.offer(next);
                }
            }
        }
        return helpfulCheats;
    }

    private int findCheats(Coordinate start, List<Coordinate> cheats) {
        int helpfulCheats = 0;
        int startScore = steps[start.y()][start.x()];
        for (Coordinate cheat : cheats) {
            Coordinate check = start.move(cheat);
            if (isSpace(check) && steps[check.y()][check.x()] - startScore - Math.abs(cheat.x()) - Math.abs(cheat.y()) >= THRESHOLD) {
                helpfulCheats++;
            }
        }
        return helpfulCheats;
    }

    private List<Coordinate> getCheats(int maxCheat) {
        List<Coordinate> cheats = new ArrayList<>();
        for (int x = -1 * maxCheat; x <= maxCheat; x++) {
            int boundary = maxCheat - Math.abs(x);
            for (int y = -1 * boundary; y <= boundary; y++) {
                cheats.add(new Coordinate(x, y));
            }
        }
        return cheats;
    }

    private void init() {
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                steps[y][x] = Integer.MAX_VALUE;
                if (grid[y][x] == 'S') {
                    startX = x;
                    startY = y;
                    grid[y][x] = '.';
                }
                if (grid[y][x] == 'E') {
                    endX = x;
                    endY = y;
                    grid[y][x] = '.';
                }
            }
        }
        steps[startY][startX] = 0;
    }

    private boolean isSpace(Coordinate coordinate) {
        return coordinate.x() >= 0 && coordinate.x() < size && coordinate.y() >= 0 && coordinate.y() < size
                && grid[coordinate.y()][coordinate.x()] == '.';
    }

    private record Step(Coordinate coordinate, int score) { }
}
