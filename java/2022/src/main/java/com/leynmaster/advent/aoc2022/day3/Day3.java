package com.leynmaster.advent.aoc2022.day3;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day3 {
//    private static final String INPUT = "../../inputs/2022/day3/test-1.txt";
    private static final String INPUT = "../../inputs/2022/day3/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    void main() throws IOException {
        logger.info("Starting");
        List<Rucksack> sacks = FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8).stream()
                .map(Day3::parse)
                .toList();
        logger.info("Part 1: {}", sacks.stream().map(Day3::findDuplicate).reduce(0, Integer::sum));
        int sum = 0;
        for (int i = 0; i < sacks.size(); i += 3) {
            sum += findDuplicate(sacks.get(i).getAll(), sacks.get(i + 1).getAll(), sacks.get(i + 2).getAll());
        }
        logger.info("Part 2: {}", sum);
    }

    private static int findDuplicate(Set<Integer> sack1, Set<Integer> sack2, Set<Integer> sack3) {
        for (Integer item : sack1) {
            if (sack2.contains(item) && sack3.contains(item)) {
                return item;
            }
        }
        return 0;
    }

    private static int findDuplicate(Rucksack sack) {
        for (Integer item : sack.compartment1()) {
            if (sack.compartment2().contains(item)) {
                return item;
            }
        }
        return 0;
    }

    private static Rucksack parse(String input) {
        int half = input.length() / 2;
        return new Rucksack(priorities(input.substring(0, half)), priorities(input.substring(half)));
    }

    private static Set<Integer> priorities(String input) {
        Set<Integer> priorities = new HashSet<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            priorities.add(Character.isUpperCase(c) ? c - 'A' + 27 : c - 'a' + 1);
        }
        return priorities;
    }

    private record Rucksack(Set<Integer> compartment1, Set<Integer> compartment2) {
        Set<Integer> getAll() {
            Set<Integer> set = new HashSet<>();
            set.addAll(compartment1());
            set.addAll(compartment2());
            return set;
        }
    }
}
