package com.leynmaster.advent.aoc2024.day19;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent 2024 Day 19.
 *
 * @author David Charles Pollack
 */
public class Day19 {
//    private static final String INPUT_FILE = "../../inputs/2024/day19/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day19/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Long> cache = new ArrayList<>();
    private String[] blankets;


    void main() throws IOException {
        int matches = 0;
        long allPaths = 0L;
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            blankets = reader.readLine().split("\\s*,\\s*");
            reader.readLine();
            String line;
            logger.info("Starting");
            while ((line = reader.readLine()) != null) {
                reset(line.length());
                long paths = match(line, 0);
                if (paths > 0L) {
                    matches++;
                    allPaths += paths;
                }
            }
        }
        logger.info("Part 1: {}", matches);
        logger.info("Part 2: {}", allPaths);
    }

    private void reset(int size) {
        cache.clear();
        for (int i = 0; i < size; i++) {
            cache.add(0L);
        }
    }

    private long match(String input, int index) {
        if (index == input.length()) {
            return 1;
        }
        if (cache.get(index) > 0L) {
            return cache.get(index);
        }
        long paths = 0;
        String sub = input.substring(index);
        for (String blanket : blankets) {
            if (sub.startsWith(blanket)) {
                paths += match(input, index + blanket.length());
            }
        }
        cache.set(index, paths);
        return paths;
    }
}
