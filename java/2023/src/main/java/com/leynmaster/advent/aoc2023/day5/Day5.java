package com.leynmaster.advent.aoc2023.day5;

import com.leynmaster.advent.utils.input.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent 2023 Day 5.
 *
 * @author David Charles Pollack
 */
public class Day5 {
//    private static final String INPUT_FILE = "../../inputs/2023/day5/test.txt";
    private static final String INPUT_FILE = "../../inputs/2023/day5/input.txt";
    private static final Long MAX_SEARCH = 10_000_000L;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<SeedMap> maps = new ArrayList<>();
    private long[] seeds;

    void main() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            seeds = NumberUtils.parseLongs(reader.readLine().split("\\s*:\\s*")[1]);
            reader.readLine();
            while (reader.readLine() != null) {
                maps.add(parseMap(reader));
            }
        }
        logger.info("Starting");
        logger.info("Part 1: {}", partOne());
        logger.info("Part 2: {}", partTwo());
    }

    private long partOne() {
        long lowest = Long.MAX_VALUE;
        for (long seed : seeds) {
            for (SeedMap map : maps) {
                seed = map.map(seed);
            }
            if (seed < lowest) {
                lowest = seed;
            }
        }
        return lowest;
    }

    private long partTwo() {
        for (long i = 0; i < MAX_SEARCH; i++) {
            long next = i;
            for (SeedMap map : maps.reversed()) {
                next = map.reverseMap(next);
            }
            if (seedRangeMatch(next)) {
                return i;
            }
        }
        return -1L;
    }

    private boolean seedRangeMatch(long seed) {
        for (int i = 0; i < seeds.length; i += 2) {
            if (seed >= seeds[i] && seed < seeds[i] + seeds[i + 1]) {
                return true;
            }
        }
        return false;
    }

    private static SeedMap parseMap(BufferedReader reader) throws IOException {
        SeedMap map = new SeedMap();
        String line = reader.readLine();
        while (line != null && !line.isBlank()) {
            map.addRange(NumberUtils.parseLongs(line));
            line = reader.readLine();
        }
        return map;
    }
}
