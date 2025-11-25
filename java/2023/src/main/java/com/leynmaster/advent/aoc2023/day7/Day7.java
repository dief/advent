package com.leynmaster.advent.aoc2023.day7;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent 2023 Day 7.
 *
 * @author David Charles Pollack
 */
public class Day7 {
//    private static final String INPUT_FILE = "../../inputs/2023/day7/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2023/day7/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    void main() throws IOException {
        logger.info("Starting");
        List<Hand> hands = new ArrayList<>();
        for (String line : FileUtils.readLines(new File(INPUT_FILE), StandardCharsets.UTF_8)) {
            if (!line.isBlank()) {
                String[] split = line.split("\\s+");
                hands.add(new Hand(split[0], Integer.parseInt(split[1])));
            }
        }
        hands.sort(Hand.comparator(false));
        logger.info("Part 1: {}", getWinnings(hands));
        hands.sort(Hand.comparator(true));
        logger.info("Part 2: {}", getWinnings(hands));
    }

    private static long getWinnings(List<Hand> hands) {
        long winnings = 0L;
        for (int i = 0; i < hands.size(); i++) {
            winnings += (i + 1L) * hands.get(i).getBid();
        }
        return winnings;
    }
}
