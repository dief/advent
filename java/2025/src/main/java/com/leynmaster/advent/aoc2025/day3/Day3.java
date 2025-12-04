package com.leynmaster.advent.aoc2025.day3;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class Day3 {
//    private static final String INPUT = "../../inputs/2025/day3/test-1.txt";
    private static final String INPUT = "../../inputs/2025/day3/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private List<int[]> banks;

    void main() throws IOException {
        banks = FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8).stream().map(Day3::parse).toList();
        logger.info("Starting");
        logger.info("Part 1: {}", totalJolts(2));
        logger.info("Part 2: {}", totalJolts(12));
    }

    private long totalJolts(int numBatteries) {
        long total = 0;
        for (int[] bank : banks) {
            int[] digits = new int[numBatteries];
            int last = -1;
            for (int i = numBatteries - 1; i >= 0; i--) {
                last = maxIndex(bank, last + 1, bank.length - i);
                digits[i] = bank[last];
            }
            long jolts = digits[0];
            long factor = 10;
            for (int i = 1; i < digits.length; i++, factor *= 10) {
                jolts += digits[i] * factor;
            }
            total += jolts;
        }
        return total;
    }

    private static int maxIndex(int[] bank, int start, int end) {
        int maxNum = bank[start];
        int maxIndex = start;
        for (int i = start + 1; i < end; i++) {
            if (bank[i] > maxNum) {
                maxNum = bank[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    private static int[] parse(String bankStr) {
        int[] bank = new int[bankStr.length()];
        for (int i = 0; i < bankStr.length(); i++) {
            bank[i] = bankStr.charAt(i) - '0';
        }
        return bank;
    }
}
