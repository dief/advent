package com.leynmaster.advent.aoc2023.day2;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Advent 2023 Day 2.
 *
 * @author David Charles Pollack
 */
public class Day2 {
//    private static final String INPUT_FILE = "../../inputs/2023/day2/test.txt";
    private static final String INPUT_FILE = "../../inputs/2023/day2/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<List<int[]>> games = new ArrayList<>();

    void main() throws IOException {
        for (String line : FileUtils.readLines(new File(INPUT_FILE), StandardCharsets.UTF_8)) {
            if (!line.isBlank()) {
                games.add(parseLine(line));
            }
        }
        logger.info("Starting");
        logger.info("Part 1: {}", partOne());
        logger.info("Part 2: {}", partTwo());
    }

    private int partOne() {
        int sum = 0;
        for (int i = 0; i < games.size(); i++) {
            boolean possible = true;
            for (int[] subset : games.get(i)) {
                possible = possible && checkPossible(subset);
            }
            if (possible) {
                sum += i + 1;
            }
        }
        return sum;
    }

    private int partTwo() {
        int sum = 0;
        for (List<int[]> game : games) {
            int[] mins = new int[3];
            Arrays.fill(mins, 0);
            for (int[] subset : game) {
                fillMins(subset, mins);
            }
            sum += mins[0] * mins[1] * mins[2];
        }
        return sum;
    }

    private static boolean checkPossible(int[] rgb) {
        return rgb[0] <= 12 && rgb[1] <= 13 && rgb[2] <= 14;
    }

    private static void fillMins(int[] subset, int[] mins) {
        for (int i = 0; i < subset.length; i++) {
            if (subset[i] > mins[i]) {
                mins[i] = subset[i];
            }
        }
    }

    private static List<int[]> parseLine(String line) {
        String[] split = line.split("\\s*:\\s*")[1].split("\\s*;\\s*");
        List<int[]> subsets = new ArrayList<>();
        for (String segment : split) {
            subsets.add(parseSubset(segment));
        }
        return subsets;
    }

    private static int[] parseSubset(String subset) {
        int[] rgb = new int[3];
        Arrays.fill(rgb, 0);
        for (String segment : subset.split("\\s*,\\s*")) {
            parseColor(segment, rgb);
        }
        return rgb;
    }

    private static void parseColor(String segment, int[] rgb) {
        String[] split = segment.split(" ");
        int number = Integer.parseInt(split[0]);
        if ("red".equals(split[1])) {
            rgb[0] = number;
        }
        if ("green".equals(split[1])) {
            rgb[1] = number;
        }
        if ("blue".equals(split[1])) {
            rgb[2] = number;
        }
    }
}
