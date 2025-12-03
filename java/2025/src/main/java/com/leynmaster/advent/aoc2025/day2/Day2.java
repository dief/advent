package com.leynmaster.advent.aoc2025.day2;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Day2 {
//    private static final String INPUT = "../../inputs/2025/day2/test-1.txt";
    private static final String INPUT = "../../inputs/2025/day2/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    void main() throws IOException {
        String[] ranges = FileUtils.readFileToString(new File(INPUT), StandardCharsets.UTF_8).trim().split("[\\s,]+");
        long partOne = 0;
        long partTwo = 0;
        logger.info("Starting");
        for (String range : ranges) {
            String[] split = range.split("-");
            long start = Long.parseLong(split[0]);
            long end = Long.parseLong(split[1]);
            partOne += addInvalidIds(start, end, false);
            partTwo += addInvalidIds(start, end, true);
        }
        logger.info("Part 1: {}", partOne);
        logger.info("Part 2: {}", partTwo);
    }

    private static long addInvalidIds(long start, long end, boolean repeating) {
        long total = 0;
        for (long i = start; i <= end; i++) {
            String num = Long.toString(i);
            if (repeating ? repeating(num) : repeatTwice(num)) {
                total += i;
            }
        }
        return total;
    }

    private static boolean repeatTwice(String num) {
        int length = num.length();
        return num.length() % 2 == 0 && num.substring(0, length / 2).equals(num.substring(length / 2));
    }

    private static boolean repeating(String num) {
        int length = num.length();
        for (int i = 1; i <= length / 2; i++) {
            if (length % i == 0 && checkSequence(num, i)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkSequence(String num, int size) {
        String sample = num.substring(0, size);
        for (int i = size; i < num.length(); i += size) {
            if (!sample.equals(num.substring(i, i + size))) {
                return false;
            }
        }
        return true;
    }
}
