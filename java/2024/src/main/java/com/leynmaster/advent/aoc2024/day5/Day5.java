package com.leynmaster.advent.aoc2024.day5;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Advent 2024 Day 5.
 *
 * @author David Charles Pollack
 */
public class Day5 {
//    private static final String INPUT_FILE = "../../inputs/2024/day5/test.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day5/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<Integer, Set<Integer>> ruleMap;
    private final List<List<Integer>> updates;

    public Day5(Map<Integer, Set<Integer>> ruleMap, List<List<Integer>> updates) {
        this.ruleMap = ruleMap;
        this.updates = updates;
    }

    public void run() {
        logger.info("Part 1: {}", part1());
        logger.info("Part 2: {}", part2());
    }

    private int part1() {
        int total = 0;
        for (List<Integer> update : updates) {
            if (valid(update)) {
                total += update.get(update.size() / 2);
            }
        }
        return total;
    }

    private int part2() {
        int total = 0;
        for (List<Integer> update : updates) {
            if (!valid(update)) {
                List<Integer> fixed = repair(update);
                total += fixed.get(fixed.size() / 2);
            }
        }
        return total;
    }

    private boolean valid(List<Integer> update) {
        for (int i = 0; i < update.size() - 1; i++) {
            Integer check = update.get(i);
            for (int j = i + 1; j < update.size(); j++) {
                if (!ruleMap.getOrDefault(check, Collections.emptySet()).contains(update.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<Integer> repair(List<Integer> invalid) {
        List<Integer> fixed = new ArrayList<>(invalid);
        fixed.sort((v1, v2) ->
                ruleMap.getOrDefault(v1, Collections.emptySet()).contains(v2) ? -1 : v1.equals(v2) ? 0 : 1);
        return fixed;
    }

    public static void main() throws IOException {
        Map<Integer, Set<Integer>> ruleMap = new HashMap<>();
        List<List<Integer>> updates = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = reader.readLine();
            while (line != null && !line.isBlank()) {
                String[] split = line.split("\\|");
                ruleMap.computeIfAbsent(Integer.parseInt(split[0]), _ -> new HashSet<>())
                        .add(Integer.parseInt(split[1]));
                line = reader.readLine();
            }
            while ((line = reader.readLine()) != null) {
                updates.add(Arrays.stream(line.split(",")).map(Integer::parseInt).toList());
            }
        }
        Day5 solution = new Day5(ruleMap, updates);
        solution.run();
    }
}
