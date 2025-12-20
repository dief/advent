package com.leynmaster.advent.aoc2025.day11;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day11 {
//    private static final String INPUT = "../../inputs/2025/day11/test-1.txt";
//    private static final String INPUT = "../../inputs/2025/day11/test-2.txt";
    private static final String INPUT = "../../inputs/2025/day11/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<String, String[]> map = new HashMap<>();

    void main() throws IOException {
        for (String line : FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8)) {
            String[] split = line.split("\\s*:\\s*");
            map.put(split[0], split[1].split("\\s+"));
        }
        logger.info("Starting");
        logger.info("Part 1: {}", countPaths("you", "out", new HashMap<>()));
        logger.info("Part 2: {}", accumulatePaths("svr", "fft", "dac", "out") +
                accumulatePaths("svr", "dac", "fft", "out"));
    }

    private long accumulatePaths(String... nodes) {
        long total = 1L;
        for (int i = 1; i < nodes.length; i++) {
            total *= countPaths(nodes[i - 1], nodes[i], new HashMap<>());
        }
        return total;
    }

    private long countPaths(String start, String end, Map<String, Long> cache) {
        if (start.equals(end)) {
            return 1L;
        }
        if (cache.containsKey(start)) {
            return cache.get(start);
        }
        long total = 0L;
        for (String next : map.getOrDefault(start, new String[0])) {
            total += countPaths(next, end, cache);
        }
        cache.put(start, total);
        return total;
    }
}
