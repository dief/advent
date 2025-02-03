package com.leynmaster.advent.aoc2024.day21;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Advent 2024 Day 21.
 *
 * @author David Charles Pollack
 */
public class Day21 {
//    private static final String INPUT_FILE = "../../inputs/2024/day21/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day21/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<CacheKey, Long> cache = new HashMap<>();
    private final List<String> codes = new ArrayList<>();
    private final Keypad keypad = new Keypad();

    void main() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                codes.add(line);
            }
        }
        logger.info("Starting");
        logger.info("Part 1: {}", run(2));
        logger.info("Part 2: {}", run(25));
    }

    private long run(int hops) {
        long total = 0;
        for (String code : codes) {
            total += Long.parseLong(code.substring(0, 3)) *
                    (getComplexity(keypad.getNumericPath(code), hops) - code.length());
        }
        return total;
    }

    private long getComplexity(List<RobotAction> actions, int level) {
        CacheKey key = new CacheKey(actions.toString(), level);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        if (level == 0) {
            return actions.size();
        }
        long total = 0;
        Character previous = 'A';
        for (RobotAction action : actions) {
            Character next = action.toString().charAt(0);
            total += getComplexity(keypad.getDirectionPath(previous, next), level - 1);
            previous = next;
        }
        cache.put(key, total);
        return total;
    }

    private record CacheKey(String sequence, int level) { }
}
