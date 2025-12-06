package com.leynmaster.advent.aoc2025.day5;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;
import java.util.LinkedList;

public class Day5 {
//    private static final String INPUT = "../../inputs/2025/day5/test-1.txt";
    private static final String INPUT = "../../inputs/2025/day5/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final LinkedList<Range> ranges = new LinkedList<>();

    void main() throws IOException {
        String[] sections = FileUtils.readFileToString(new File(INPUT), StandardCharsets.UTF_8).trim().split("\\R\\R");
        for (String range : sections[0].trim().split("\\R")) {
            String[] split = range.split("-");
            ranges.add(new Range(Long.parseLong(split[0]), Long.parseLong(split[1])));
        }
        logger.info("Starting");
        logger.info("Part 1: {}", freshIngredients(sections[1].trim().split("\\R")));
        long freshIds = 0;
        for (Range range : mergeRanges()) {
            freshIds += range.end() - range.start() + 1;
        }
        logger.info("Part 2: {}", freshIds);
    }

    private LinkedList<Range> mergeRanges() {
        LinkedList<Range> mergedRanges = new LinkedList<>();
        ranges.sort(Comparator.comparingLong(Range::start));
        while (!ranges.isEmpty()) {
            Range range = ranges.poll();
            Range next = ranges.peek();
            while (next != null && next.start() <= range.end()) {
                range = new Range(range.start(), Math.max(range.end(), next.end()));
                ranges.poll();
                next = ranges.peek();
            }
            mergedRanges.add(range);
        }
        return mergedRanges;
    }

    private int freshIngredients(String[] ingredients) {
        int totalFresh = 0;
        for (String ingredient : ingredients) {
            if (checkFresh(Long.parseLong(ingredient))) {
                totalFresh++;
            }
        }
        return totalFresh;
    }

    private boolean checkFresh(long ingredient) {
        for (Range range : ranges) {
            if (ingredient >= range.start() && ingredient <= range.end()) {
                return true;
            }
        }
        return false;
    }

    private record Range(long start, long end) {}
}
