package com.leynmaster.advent.aoc2024.day1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Advent 2024 Day 1.
 *
 * @author David Charles Pollack
 */
public class Day1 {
//    private static final String INPUT_FILE = "../../inputs/2024/day1/test.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day1/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Integer> list1;
    private final List<Integer> list2;

    public Day1(List<Integer> list1, List<Integer> list2) {
        this.list1 = list1;
        this.list2 = list2;
        list1.sort(Comparator.naturalOrder());
        list2.sort(Comparator.naturalOrder());
    }

    public void run() {
        logger.info("Part 1: {}", part1());
        logger.info("Part 2: {}", part2());
    }

    private int part1() {
        int len = list1.size();
        int distance = 0;
        for (int i = 0; i < len; i++) {
            distance += Math.abs(list1.get(i) - list2.get(i));
        }
        return distance;
    }

    private int part2() {
        int similarity = 0;
        for (Integer num : list1) {
            similarity += num * count(num);
        }
        return similarity;
    }

    private int count(int num) {
        int count = 0;
        for (int next : list2) {
            if (num == next) {
                count++;
            }
        }
        return count;
    }

    public static void main() throws IOException {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\\s+");
                list1.add(Integer.parseInt(split[0]));
                list2.add(Integer.parseInt(split[1]));
            }
        }
        Day1 solution = new Day1(list1, list2);
        solution.run();
    }
}
