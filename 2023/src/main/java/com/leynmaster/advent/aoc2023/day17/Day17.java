package com.leynmaster.advent.aoc2023.day17;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day17 {
    private static final String INPUT_FILE = "inputs/day17/input.txt";
    private static final int MIN1 = -1;
    private static final int MAX1 = 2;
    private static final int MIN2 = 2;
    private static final int MAX2 = 9;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void run(int[][] matrix) {
        logger.info("Part 1: {}", new CityMap(matrix, MIN1, MAX1).heat());
        logger.info("Part 2: {}", new CityMap(matrix, MIN2, MAX2).heat());
    }

    public static void main(String[] args) throws IOException {
        List<int[]> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    int[] cols = new int[line.length()];
                    for (int i = 0; i < line.length(); i++) {
                        cols[i] = Integer.parseInt(STR."\{line.charAt(i)}");
                    }
                    rows.add(cols);
                }
            }
        }
        new Day17().run(rows.toArray(new int[0][]));
    }
}
