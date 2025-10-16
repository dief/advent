package com.leynmaster.advent.aoc2023.day1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Advent 2023 Day 1.
 *
 * @author David Charles Pollack
 */
public class Day1 {
//    private static final String INPUT_FILE = "../../inputs/2023/day1/test-1.txt";
//    private static final String INPUT_FILE = "../../inputs/2023/day1/test-2.txt";
    private static final String INPUT_FILE = "../../inputs/2023/day1/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<String, Integer> numberMap = new HashMap<>();

     void main() throws IOException {
        int part1 = 0;
        int part2 = 0;
        numberMap.put("zero", 0);
        numberMap.put("one", 1);
        numberMap.put("two", 2);
        numberMap.put("three", 3);
        numberMap.put("four", 4);
        numberMap.put("five", 5);
        numberMap.put("six", 6);
        numberMap.put("seven", 7);
        numberMap.put("eight", 8);
        numberMap.put("nine", 9);
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    part1 += calibration(line, false);
                    part2 += calibration(line, true);
                }
            }
        }
        logger.info("Starting");
        logger.info("Part 1: {}", part1);
        logger.info("Part 2: {}", part2);
    }

    private int calibration(String line, boolean lexical) {
        int first = -1;
        int digit = -1;
        for (int i = 0; i < line.length(); i++) {
            int next = getDigit(line.substring(i), lexical);
            if (next >= 0) {
                digit = next;
                if (first < 0) {
                    first = digit;
                }
            }
        }
        return first * 10 + digit;
    }

    private int getDigit(String segment, boolean lexical) {
        if (Character.isDigit(segment.charAt(0))) {
            return Integer.parseInt(segment.substring(0, 1));
        }
        if (lexical) {
            return getLexicalDigit(segment);
        }
        return -1;
    }

    private int getLexicalDigit(String segment) {
        for (Map.Entry<String, Integer> entry : numberMap.entrySet()) {
            if (segment.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return -1;
    }
}
