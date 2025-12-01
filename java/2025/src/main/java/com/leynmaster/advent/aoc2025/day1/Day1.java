package com.leynmaster.advent.aoc2025.day1;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Day1 {
//    private static final String INPUT = "../../inputs/2025/day1/test-1.txt";
    private static final String INPUT = "../../inputs/2025/day1/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private int zeroCount = 0;
    private int pastZeroCount = 0;

    void main() throws IOException {
        logger.info("Starting");
        int dial = 50;
        for (String instruction : FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8)) {
            char direction = instruction.charAt(0);
            int amount = reduce(Integer.parseInt(instruction.substring(1)));
            dial = direction == 'R' ? spinRight(dial, amount) : spinLeft(dial, amount);
            if (dial == 0) {
                zeroCount++;
            }
        }
        logger.info("Part 1: {}", zeroCount);
        logger.info("Part 2: {}", pastZeroCount);
    }

    private int spinRight(int current, int amount) {
        int next = current + amount;
        if (next > 99) {
            pastZeroCount++;
            return next - 100;
        }
        return next;
    }

    private int spinLeft(int current, int amount) {
        int next = current - amount;
        if (next < 0) {
            if (current != 0) {
                pastZeroCount++;
            }
            return 100 + next;
        }
        if (next == 0) {
            pastZeroCount++;
        }
        return next;
    }

    private int reduce(int amount) {
        int hundreds = amount / 100;
        pastZeroCount += hundreds;
        return amount - 100 * hundreds;
    }
}
