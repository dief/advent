package com.leynmaster.advent.aoc2024.day3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Advent 2024 Day 3.
 *
 * @author David Charles Pollack
 */
public class Day3 {
    private static final String INPUT_FILE = "../../inputs/2024/day3/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Pattern mulPattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)");
    private final Pattern onPattern = Pattern.compile("do\\(\\)");
    private final Pattern offPattern = Pattern.compile("don't\\(\\)");
    private final String input;

    public Day3(String input) {
        this.input = input;
    }

    public void run() {
        logger.info("Part 1: {}", part1());
        logger.info("Part 2: {}", part2());
    }

    private int part1() {
        return multiply(input);
    }

    private int part2() {
        int total = 0;
        String[] sections = offPattern.split(input);
        total += multiply(sections[0]);
        for (int i = 1; i < sections.length; i++) {
            String[] split = onPattern.split(sections[i], 2);
            if (split.length > 1) {
                total += multiply(split[1]);
            }
        }
        return total;
    }

    private int multiply(String segment) {
        int total = 0;
        Matcher matcher = mulPattern.matcher(segment);
        while (matcher.find()) {
            total += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
        }
        return total;
    }

    public static void main() throws IOException {
        StringBuilder buf = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buf.append(line);
            }
        }
        Day3 solution = new Day3(buf.toString());
        solution.run();
    }
}
