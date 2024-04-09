package com.leynmaster.advent.aoc2023.day16;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day16 {
    private static final String INPUT_FILE = "inputs/day16/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void run(char[][] matrix) {
        LightGrid grid = new LightGrid(matrix);
        logger.info("Part 1: {}", grid.part1());
        logger.info("Part 2: {}", grid.part2());
    }

    public static void main(String[] args) throws IOException {
        List<char[]> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    lines.add(line.toCharArray());
                }
            }
        }
        new Day16().run(lines.toArray(new char[0][]));
    }
}
