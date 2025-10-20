package com.leynmaster.advent.aoc2023.day6;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Advent 2023 Day 6.
 *
 * @author David Charles Pollack
 */
public class Day6 {
//    private static final String INPUT_FILE = "../../inputs/2023/day6/test.txt";
    private static final String INPUT_FILE = "../../inputs/2023/day6/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private long[] times;
    private long[] distances;

    void main() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            times = parseNumbers(reader.readLine().split("\\s*:\\s*")[1]);
            distances = parseNumbers(reader.readLine().split("\\s*:\\s*")[1]);
        }
        logger.info("Starting");
        logger.info("Part 1: {}", partOne());
        logger.info("Part 2: {}", partTwo());
    }

    private int partOne() {
        int total = 1;
        for (int i = 0; i < times.length; i++) {
            total *= waysToWin(times[i], distances[i]);
        }
        return total;
    }

    private int partTwo() {
        return waysToWin(join(times), join(distances));
    }

    private static int waysToWin(long time, long distance) {
        int waysToWin = 0;
        for (int i = 0; i < time; i++) {
            if (i * (time - i) > distance) {
                waysToWin++;
            }
        }
        return waysToWin;
    }

    private static long join(long[] numbers) {
        StringBuilder buf = new StringBuilder();
        for (long number : numbers) {
            buf.append(number);
        }
        return Long.parseLong(buf.toString());
    }

    private static long[] parseNumbers(String line) {
        String[] numStrings = line.trim().split("\\s+");
        long[] numbers = new long[numStrings.length];
        for (int i = 0; i < numStrings.length; i++) {
            numbers[i] = Long.parseLong(numStrings[i]);
        }
        return numbers;
    }
}
