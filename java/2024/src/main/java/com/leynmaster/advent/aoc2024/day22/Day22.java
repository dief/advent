package com.leynmaster.advent.aoc2024.day22;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Advent 2024 Day 22.
 *
 * @author David Charles Pollack
 */
public class Day22 {
//    private static final String INPUT_FILE = "../../inputs/2024/day22/test-2.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day22/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Long> seeds = new ArrayList<>();

    void main() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                seeds.add(Long.parseLong(line));
            }
        }
        logger.info("Starting");
        logger.info("Part 1: {}", part1());
        logger.info("Part 2: {}", part2());
    }

    private long part1() {
        long total = 0;
        for (Long seed : seeds) {
            long num = seed;
            for (int i = 0; i < 2000; i++) {
                num = nextSecret(num);
            }
            total += num;
        }
        return total;
    }

    private int part2() {
        Map<List<Integer>, Integer> sequences = new HashMap<>();
        for (Long seed : seeds) {
            long num = seed;
            int prevPrice = (int)(num % 10);
            Set<List<Integer>> seen = new HashSet<>();
            SequenceBuffer buffer = new SequenceBuffer();
            for (int i = 0; i < 2000; i++) {
                num = nextSecret(num);
                int price = (int)(num % 10);
                buffer.add(price - prevPrice);
                if (buffer.isFull()) {
                    List<Integer> sequence = buffer.getSequence();
                    if (seen.add(sequence)) {
                        sequences.put(sequence, sequences.getOrDefault(sequence, 0) + price);
                    }
                }
                prevPrice = price;
            }
        }
        return sequences.values().stream().max(Comparator.naturalOrder()).orElseThrow();
    }

    private static long nextSecret(long start) {
        long num = start;
        num = mixAndPrune(num, num * 64);
        num = mixAndPrune(num, num / 32);
        num = mixAndPrune(num, num * 2048);
        return num;
    }

    private static long mixAndPrune(long num1, long num2) {
        return (num1 ^ num2) % 16777216;
    }
}
