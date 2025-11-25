package com.leynmaster.advent.aoc2022.day1;

import com.leynmaster.advent.utils.input.NumberUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day1 {
//    private static final String INPUT = "../../inputs/2022/day1/test-1.txt";
    private static final String INPUT = "../../inputs/2022/day1/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());

    void main() throws IOException {
        logger.info("Starting");
        List<Integer> calories = new ArrayList<>();
        for (String chunk : FileUtils.readFileToString(new File(INPUT), StandardCharsets.UTF_8).split("\\R\\R")) {
            calories.add(sum(NumberUtils.parseInts(chunk)));
        }
        calories.sort(Comparator.naturalOrder());
        calories = calories.reversed();
        logger.info("Part 1: {}", calories.getFirst());
        logger.info("Part 2: {}", calories.get(0) + calories.get(1) + calories.get(2));
    }

    private static int sum(int[] chunk) {
        int sum = 0;
        for (int num : chunk) {
            sum += num;
        }
        return sum;
    }
}
