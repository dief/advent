package com.leynmaster.advent.aoc2025.day7;

import com.leynmaster.advent.utils.map.Coordinate;
import com.leynmaster.advent.utils.map.Direction;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Day7 {
//    private static final String INPUT = "../../inputs/2025/day7/test-1.txt";
    private static final String INPUT = "../../inputs/2025/day7/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<Coordinate, Long> paths = new HashMap<>();
    private int splitters = 0;
    private char[][] grid;
    private int height;

    void main() throws IOException {
        grid = FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8).stream().map(String::toCharArray)
                .toArray(char[][]::new);
        height = grid.length;
        logger.info("Starting");
        long paths = numPaths(new Coordinate(grid[0].length / 2, 0));
        logger.info("Part 1: {}", splitters);
        logger.info("Part 2: {}", paths);
    }

    private long numPaths(Coordinate coordinate) {
        if (paths.containsKey(coordinate)) {
            return paths.get(coordinate);
        }
        long childPaths = coordinate.y() < height - 1 ? nextCalls(coordinate) : 1;
        paths.put(coordinate, childPaths);
        return childPaths;
    }

    private long nextCalls(Coordinate coordinate) {
        Coordinate next = coordinate.move(Direction.DOWN);
        if (grid[next.y()][next.x()] == '^') {
            splitters++;
            return numPaths(next.move(Direction.LEFT)) + numPaths(next.move(Direction.RIGHT));
        }
        return numPaths(next);
    }
}
