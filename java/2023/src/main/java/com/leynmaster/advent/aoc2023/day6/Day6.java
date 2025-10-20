package com.leynmaster.advent.aoc2023.day6;

import com.leynmaster.advent.utils.input.NumberUtils;
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
    private long longTime;
    private long longDistance;

    void main() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String timesStr = reader.readLine().split("\\s*:\\s*")[1];
            String distanceStr = reader.readLine().split("\\s*:\\s*")[1];
            times = NumberUtils.parseLongs(timesStr);
            distances = NumberUtils.parseLongs(distanceStr);
            longTime = Long.parseLong(timesStr.replaceAll("\\s+", ""));
            longDistance = Long.parseLong(distanceStr.replaceAll("\\s+", ""));
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
        return waysToWin(longTime, longDistance);
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
}
