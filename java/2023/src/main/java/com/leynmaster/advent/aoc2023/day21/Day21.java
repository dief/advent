package com.leynmaster.advent.aoc2023.day21;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day21 {
    private static final String INPUT_FILE = "../../inputs/2023/day21/input.txt";
    private static final int PART_1 = 64;
    private static final int PART_2 = 26501365;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final StepMap map = new StepMap();

    public void setup(List<String> lines) {
        map.setup(lines);
    }

    public void run() {
        logger.info("Starting");
        logger.info("Part 1: {}", takeSteps(PART_1));
        map.reset();
        map.setExpanding(true);
        int width = map.getWidth();
        int offset = PART_2 % width;
        long y0 = takeSteps(offset);
        long y1 = takeSteps(width);
        long y2 = takeSteps(width);
        logger.info("Part 2: {}", quadratic(PART_2 / width, y0, y1, y2));
    }

    private long quadratic(long n, long y0, long y1, long y2) {
        long a = (y2 - 2*y1 + y0) / 2;
        long b = y1 - y0 - a;
        return a * (long)Math.pow(n, 2) + b * n + y0;
    }

    private int takeSteps(int iterations) {
        for (int i = 0; i < iterations; i++) {
            map.takeStep();
        }
        return map.getPlotCount();
    }

    public static void main(String[] args) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    lines.add(line.trim());
                }
            }
        }
        Day21 solution = new Day21();
        solution.setup(lines);
        solution.run();
    }
}
