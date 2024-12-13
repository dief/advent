package com.leynmaster.advent.aoc2024.day11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Advent 2024 Day 11.
 *
 * @author David Charles Pollack
 */
public class Day11 {
    //    private static final String INPUT_FILE = "../../inputs/2024/day11/test-2.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day11/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<StoneCall, Long> cache = new HashMap<>();
    private final List<Long> stones;

    public Day11(List<Long> stones) {
        this.stones = stones;
    }

    public void run() {
        logger.info("Part 1: {}", split(25));
        logger.info("Part 2: {}", split(75));
    }

    private long split(int times) {
        long total = 0;
        for (Long stone : stones) {
            total += split(stone, times);
        }
        return total;
    }

    private long split(long stone, int times) {
        StoneCall call = new StoneCall(stone, times);
        if (cache.containsKey(call)) {
            return cache.get(call);
        }
        long result = doSplit(stone, times);
        cache.put(call, result);
        return result;
    }

    private long doSplit(long stone, int times) {
        if (times <= 0) {
            return 1;
        }
        if (stone == 0) {
            return split(1, times - 1);
        }
        String str = String.valueOf(stone);
        int length = str.length();
        if (length % 2 == 0) {
            int midPoint = length / 2;
            return split(Long.parseLong(str.substring(0, midPoint)), times - 1)
                    + split(Long.parseLong(str.substring(midPoint)), times - 1);
        }
        return split(stone * 2024, times - 1);
    }

    public static void main() throws IOException {
        List<Long> stones;
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            stones = Arrays.stream(reader.readLine().split("\\s+")).map(Long::parseLong).toList();
        }
        new Day11(stones).run();
    }

    private record StoneCall(long value, int times) { }
}
