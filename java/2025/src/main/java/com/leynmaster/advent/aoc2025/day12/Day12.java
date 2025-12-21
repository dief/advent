package com.leynmaster.advent.aoc2025.day12;

import com.leynmaster.advent.utils.input.NumberUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Day12 {
//    private static final String INPUT = "../../inputs/2025/day12/test-1.txt";
        private static final String INPUT = "../../inputs/2025/day12/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private int[] areas;

    void main() throws IOException {
        String[] sections = FileUtils.readFileToString(new File(INPUT), StandardCharsets.UTF_8).trim().split("\\R\\R");
        int shapes = sections.length - 1;
        areas = new int[shapes];
        for (int i = 0; i < shapes; i++) {
            areas[i] = area(sections[i]);
        }
        int count = 0;
        for (String region : sections[shapes].split("\\R")) {
            if (fits(region)) {
                count++;
            }
        }
        logger.info("Part 1: {}", count);
    }

    private boolean fits(String regionLine) {
        String[] split = regionLine.split("\\s*:\\s*");
        int[] dimensions = NumberUtils.parseInts(split[0]);
        int[] shapeCount = NumberUtils.parseInts(split[1]);
        int giftArea = 0;
        for (int i = 0; i < shapeCount.length; i++) {
            giftArea += shapeCount[i] * areas[i];
        }
        return giftArea <= dimensions[0] * dimensions[1];
    }

    private static int area(String section) {
        String[] lines = section.split("\\R");
        int area = 0;
        for (int i = 1; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                area += lines[i].charAt(j) == '#' ? 1 : 0;
            }
        }
        return area;
    }
}
