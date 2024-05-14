package com.leynmaster.advent.aoc2023.day12;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Advent 2023 Day 12.
 *
 * @author David Charles Pollack
 */
public class Day12 {
    private static final String INPUT_FILE = "../../inputs/2023/day12/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final BufferedReader reader;

    public Day12(BufferedReader reader) {
        this.reader = reader;
    }

    public void run() throws IOException {
        String line;
        int lineNum = 0;
        long part1 = 0;
        long part2 = 0;
        while ((line = reader.readLine()) != null) {
            part1 += new Springs(line, ++lineNum, 0).paths();
            part2 += new Springs(line, lineNum, 4).paths();
        }
        logger.info("Part 1: {}", part1);
        logger.info("Part 2: {}", part2);
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            Day12 day = new Day12(reader);
            day.run();
        }
    }

    private static class Springs {
        private final Logger logger = LoggerFactory.getLogger(getClass());
        private final Map<IntPair, Long> cache = new HashMap<>();
        private final List<Integer> spans = new ArrayList<>();
        private final int lineNum;
        private final int append;
        private final String springStr;
        private final int strLen;

        public Springs(String line, int lineNum, int append) {
            this.lineNum = lineNum;
            this.append = append;
            String[] split = line.split("\\s+");
            String strSeed = split[0];
            StringBuilder buf = new StringBuilder(strSeed);
            List<Integer> spanSeeds = Arrays.stream(split[1].split(",")).map(Integer::parseInt).toList();
            spans.addAll(spanSeeds);
            for (int i = 0; i < append; i++) {
                buf.append('?').append(strSeed);
                spans.addAll(spanSeeds);
            }
            springStr = buf.toString();
            strLen = springStr.length();
        }

        public long paths() {
            return paths(0, 0);
        }

        private long paths(int springPos, int spanPos) {
            IntPair args = new IntPair(springPos, spanPos);
            if (cache.containsKey(args)) {
                return cache.get(args);
            }
            if (springPos >= strLen) {
                return spanPos >= spans.size() ? 1L : 0L;
            }
            if (spanPos >= spans.size()) {
                return springStr.substring(springPos, strLen).contains("#") ? 0L : 1L;
            }
            long result = checkPos(springPos, spanPos);
            cache.put(args, result);
            return result;
        }

        private long checkPos(int springPos, int spanPos) {
            long paths = 0;
            char next = springStr.charAt(springPos);
            if (next == '.' || next == '?') {
                paths += paths(springPos + 1, spanPos);
            }
            int endPos = springPos + spans.get(spanPos);
            if (endPos > strLen || springStr.substring(springPos, endPos).contains(".")
                    || (endPos != strLen && springStr.charAt(endPos) == '#')) {
                return paths;
            }
            return paths + paths(endPos + 1, spanPos + 1);
        }
    }

    private record IntPair(int num1, int num2) {}
}
