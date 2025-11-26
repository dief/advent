package com.leynmaster.advent.aoc2022.day6;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Day6 {
//    private static final String INPUT = "mjqjpqmgbljsphdztnvjfqwrcgsmlb";
//    private static final String INPUT = "bvwbjplbgvbhsrlpgdmjqwftvncz";
//    private static final String INPUT = "nppdvjthqldpwncqszvftbrmjlhg";
//    private static final String INPUT = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg";
//    private static final String INPUT = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw";
    private static final String INPUT = "../../inputs/2022/day6/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    void main() throws IOException {
        logger.info("Starting");
//        String input = INPUT;
        String input = FileUtils.readFileToString(new File(INPUT), StandardCharsets.UTF_8).trim();
        logger.info("Part 1: {}", findStart(input, 4));
        logger.info("Part 2: {}", findStart(input, 14));
    }

    private static int findStart(String input, int length) {
        for (int i = length; i < input.length(); i++) {
            if (uniqueChars(input, i - length, length)) {
                return i;
            }
        }
        return -1;
    }

    private static boolean uniqueChars(String input, int index, int length) {
        for (int i = index; i < index + length - 1; i++) {
            for (int j = i + 1; j < index + length; j++) {
                if (input.charAt(i) == input.charAt(j)) {
                    return false;
                }
            }
        }
        return true;
    }
}
