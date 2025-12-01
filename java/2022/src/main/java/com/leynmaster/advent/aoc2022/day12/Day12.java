package com.leynmaster.advent.aoc2022.day12;

import com.leynmaster.advent.utils.map.Coordinate;
import com.leynmaster.advent.utils.map.Direction;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Day12 {
//    private static final String INPUT = "../../inputs/2022/day12/test-1.txt";
    private static final String INPUT = "../../inputs/2022/day12/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Set<Coordinate> visited = new HashSet<>();
    private char[][] grid;
    int height;
    int width;
    private Coordinate start;
    private Coordinate end;

    void main() throws IOException {
        parseInput(FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8));
        logger.info("Starting");
        logger.info("Part 1: {}", shortestPath());
        logger.info("Part 2: {}", findClosest());
    }

    private int shortestPath() {
        visited.clear();
        PriorityQueue<Step> queue = new PriorityQueue<>(Comparator.comparing(Step::distance));
        queue.offer(new Step(start, 0));
        visited.add(start);
        while (!queue.isEmpty()) {
            Step step = queue.poll();
            if (end.equals(step.coordinate())) {
                return step.distance();
            }
            processStep(queue, step, Direction.UP);
            processStep(queue, step, Direction.DOWN);
            processStep(queue, step, Direction.LEFT);
            processStep(queue, step, Direction.RIGHT);
        }
        return -1;
    }

    private int findClosest() {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int next = check(i, j);
                if (next >= 0 && next < min) {
                    min = next;
                }
            }
        }
        return min;
    }

    private int check(int i, int j) {
        if (grid[i][j] == 'a') {
            start = new Coordinate(j, i);
            return shortestPath();
        }
        return Integer.MAX_VALUE;
    }

    private void processStep(PriorityQueue<Step> queue, Step step, Direction direction) {
        Coordinate location = step.coordinate();
        Coordinate next = location.move(direction);
        int x = next.x();
        int y = next.y();
        if (x >= 0 && x < width && y >= 0 && y < height) {
            int heightDiff = grid[y][x] - grid[location.y()][location.x()];
            if (heightDiff < 2) {
                if (!visited.contains(next)) {
                    queue.offer(new Step(next, step.distance() + 1));
                    visited.add(next);
                }
            }
        }
    }

    private void parseInput(List<String> lines) {
        height = lines.size();
        width = lines.getFirst().length();
        grid = new char[height][width];
        for (int i = 0; i < height; i++) {
            String line = lines.get(i);
            for (int j = 0; j < width; j++) {
                setGrid(line.charAt(j), i, j);
            }
        }
    }

    private void setGrid(char c, int i, int j) {
        char height = c;
        if (c == 'S') {
            height = 'a';
            start = new Coordinate(j, i);
        } else if (c == 'E') {
            height = 'z';
            end = new Coordinate(j, i);
        }
        grid[i][j] = height;
    }

    private record Step(Coordinate coordinate, int distance) {}
}
