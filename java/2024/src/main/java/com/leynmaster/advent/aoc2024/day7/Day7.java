package com.leynmaster.advent.aoc2024.day7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Advent 2024 Day 7.
 *
 * @author David Charles Pollack
 */
public class Day7 {
//    private static final String INPUT_FILE = "../../inputs/2024/day7/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day7/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Equation> equations;

    public Day7(List<Equation> equations) {
        this.equations = equations;
    }

    public void run() {
        logger.info("Part 1: {}", compute(false));
        logger.info("Part 2: {}", compute(true));
    }

    private long compute(boolean includeOr) {
        long total = 0;
        for (Equation equation : equations) {
            if (equation.isValid(includeOr)) {
                total += equation.solution();
            }
        }
        return total;
    }

    public static void main() throws IOException {
        List<Equation> equations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(":\\s*");
                equations.add(new Equation(Long.parseLong(split[0]),
                        Arrays.stream(split[1].split("\\s+")).map(Long::parseLong).toList()));
            }
        }
        Day7 solution = new Day7(equations);
        solution.run();
    }
}
