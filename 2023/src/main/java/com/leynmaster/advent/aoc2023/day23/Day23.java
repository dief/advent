package com.leynmaster.advent.aoc2023.day23;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day23 {
    private static final String INPUT_FILE = "inputs/day23/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final char[][] mapMatrix;

    public Day23(List<char[]> rows) {
        mapMatrix = rows.toArray(new char[0][]);
    }

    public void run() {
        logger.info("Starting");
        logger.info("Part 1: {}", new SlipperyHikingMap(mapMatrix).longestPath());
        JunctionGraphBuilder builder = new JunctionGraphBuilder(mapMatrix);
        logger.info("Part 2: {}", new JunctionHikingMap(builder.buildGraph(), builder.getEnd()).longestPath());
    }

    public static void main(String[] args) throws IOException {
        List<char[]> rows = new ArrayList<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            while ((line = reader.readLine()) != null) {
                rows.add(line.toCharArray());
            }
        }
        new Day23(rows).run();
    }
}
