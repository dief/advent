package com.leynmaster.advent.aoc2023.day4;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Advent 2023 Day 4.
 *
 * @author David Charles Pollack
 */
public class Day4 {
//    private static final String INPUT_FILE = "../../inputs/2023/day4/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2023/day4/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Integer> wins = new ArrayList<>();
    private int cards;
    private int[] cardCount;

    void main() throws IOException {
        logger.info("Starting");
        for (String line : FileUtils.readLines(new File(INPUT_FILE), StandardCharsets.UTF_8)) {
            if (!line.isBlank()) {
                parseLine(line);
            }
        }
        cards = wins.size();
        logger.info("Part 1: {}", partOne());
        cardCount = new int[cards];
        Arrays.fill(cardCount, 1);
        logger.info("Part 2: {}", partTwo());
    }

    private int partOne() {
        int points = 0;
        for (int win : wins) {
            points += (int)Math.pow(2, win - 1);
        }
        return points;
    }

    private int partTwo() {
        for (int i = 0; i < cards; i++) {
            for (int j = i + 1; j < i + wins.get(i) + 1 && j < cards; j++) {
                cardCount[j] += cardCount[i];
            }
        }
        int sum = 0;
        for (int j : cardCount) {
            sum += j;
        }
        return sum;
    }

    private void parseLine(String line) {
        int lineWins = 0;
        String[] split = line.split("\\s*:\\s*")[1].split("\\s*\\|\\s*");
        Set<Integer> winningNumbers = new HashSet<>(parseNumbers(split[0]));
        for (Integer num : parseNumbers(split[1])) {
            if (winningNumbers.contains(num)) {
                lineWins++;
            }
        }
        wins.add(lineWins);
    }

    private List<Integer> parseNumbers(String line) {
        return Arrays.stream(line.split("\\s+")).map(Integer::parseInt).toList();
    }
}
