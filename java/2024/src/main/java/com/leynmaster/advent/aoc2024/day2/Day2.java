package com.leynmaster.advent.aoc2024.day2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Advent 2024 Day 2.
 *
 * @author David Charles Pollack
 */
public class Day2 {
//    private static final String INPUT_FILE = "../../inputs/2024/day2/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day2/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<List<Integer>> reports;

    public Day2(List<List<Integer>> reports) {
        this.reports = reports;
    }

    public void run() {
        logger.info("Part 1: {}", part1());
        logger.info("Part 2: {}", part2());
    }

    private int part1() {
        int safe = 0;
        for (List<Integer> report : reports) {
            if (isSafe(report)) {
                safe++;
            }
        }
        return safe;
    }

    private int part2() {
        int safe = 0;
        for (List<Integer> report : reports) {
            if (isAllSafe(report)) {
                safe++;
            }
        }
        return safe;
    }

    private boolean isAllSafe(List<Integer> report) {
        for (List<Integer> permutation : permutations(report)) {
            if (isSafe(permutation)) {
                return true;
            }
        }
        return false;
    }

    private boolean isSafe(List<Integer> report) {
        return (distinctlyOrdered(report) || distinctlyOrdered(report.reversed())) && largestGap(report) < 4;
    }

    private List<List<Integer>> permutations(List<Integer> report) {
        List<List<Integer>> permutations = new ArrayList<>();
        permutations.add(report);
        for (int i = 0; i < report.size(); i++) {
            List<Integer> permutation = new ArrayList<>();
            for (int j = 0; j < report.size(); j++) {
                if (i != j) {
                    permutation.add(report.get(j));
                }
            }
            permutations.add(permutation);
        }
        return permutations;
    }

    private boolean distinctlyOrdered(List<Integer> report) {
        int prev = report.getFirst();
        for (int i = 1; i < report.size(); i++) {
            int next = report.get(i);
            if (prev >= next) {
                return false;
            }
            prev = next;
        }
        return true;
    }

    private int largestGap(List<Integer> report) {
        int max = 0;
        int prev = report.getFirst();
        for (int i = 1; i < report.size(); i++) {
            int next = report.get(i);
            int gap = Math.abs(prev - next);
            if (gap > max) {
                max = gap;
            }
            prev = next;
        }
        return max;
    }

    public static void main() throws IOException {
        List<List<Integer>> reports = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                reports.add(Arrays.stream(line.split("\\s+")).map(Integer::parseInt).toList());
            }
        }
        Day2 solution = new Day2(reports);
        solution.run();
    }
}
