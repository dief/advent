package com.leynmaster.advent.aoc2023.day9;

import com.leynmaster.advent.utils.input.NumberUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent 2023 Day 9.
 *
 * @author David Charles Pollack
 */
public class Day9 {
//    private static final String INPUT_FILE = "../../inputs/2023/day9/test.txt";
    private static final String INPUT_FILE = "../../inputs/2023/day9/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    void main() throws IOException {
        logger.info("Starting");
        List<List<int[]>> allDiffs = FileUtils.readLines(new File(INPUT_FILE), StandardCharsets.UTF_8).stream()
                .filter(line -> !line.isBlank())
                .map(NumberUtils::parseInts)
                .map(Day9::diffs)
                .toList();
        int part1 = 0;
        int part2 = 0;
        for (List<int[]> diffs : allDiffs) {
            int guessBefore = 0;
            int guessAfter = 0;
            for (int[] diff : diffs) {
                guessAfter += diff[diff.length - 1];
                guessBefore = diff[0] - guessBefore;
            }
            part1 += guessAfter;
            part2 += guessBefore;
        }
        logger.info("Part 1: {}", part1);
        logger.info("Part 2: {}", part2);
    }

    private static List<int[]> diffs(int[] numbers) {
        List<int[]> diffs = new ArrayList<>();
        int[] next = numbers;
        while (nonZero(next)) {
            diffs.add(next);
            int[] prev = next;
            next = new int[prev.length - 1];
            for (int i = 0; i < next.length; i++) {
                next[i] = prev[i + 1] - prev[i];
            }
        }
        return diffs.reversed();
    }

    private static boolean nonZero(int[] numbers) {
        for (int num : numbers) {
            if (num != 0) {
                return true;
            }
        }
        return false;
    }
}
