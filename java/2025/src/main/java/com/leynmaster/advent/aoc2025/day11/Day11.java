package com.leynmaster.advent.aoc2025.day11;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Day11 {
//    private static final String INPUT = "../../inputs/2025/day11/test-1.txt";
//    private static final String INPUT = "../../inputs/2025/day11/test-2.txt";
    private static final String INPUT = "../../inputs/2025/day11/input.txt";
    private static final int LIMIT = 18;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<String, List<String>> map = new HashMap<>();
    private final Map<String, List<String>> reverseMap = new HashMap<>();

    void main() throws IOException {
        for (String line : FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8)) {
            String[] split = line.split("\\s*:\\s*");
            add(split[0], split[1].split("\\s+"));
        }
        logger.info("Starting");
        logger.info("Part 1: {}", countPaths(map, "you", "out"));
        long p1 = countPaths(reverseMap, "fft", "svr");
        long p2 = countPaths(reverseMap, "dac", "fft");
        long p3 = countPaths(map, "dac", "out");
        logger.info("Part 2: {}", p1 * p2 * p3);
    }

    private void add(String from, String[] to) {
        map.put(from, Arrays.asList(to));
        for (String node : to) {
            List<String> value = reverseMap.getOrDefault(node, new ArrayList<>());
            value.add(from);
            reverseMap.put(node, value);
        }
    }

    private static int countPaths(Map<String, List<String>> lookup, String start, String end) {
        int count = 0;
        LinkedList<List<String>> stack = new LinkedList<>();
        List<String> path = new ArrayList<>();
        path.add(start);
        stack.push(path);
        while (!stack.isEmpty()) {
            path = stack.pop();
            String current = path.getLast();
            if (end.equals(current)) {
                count++;
            } else if (path.size() < LIMIT) {
                search(stack, path, lookup.getOrDefault(current, Collections.emptyList()));
            }
        }
        return count;
    }

    private static void search(LinkedList<List<String>> stack, List<String> path, List<String> nextList) {
        for (String next : nextList) {
            if (!path.contains(next)) {
                List<String> nextPath = new ArrayList<>(path);
                nextPath.add(next);
                stack.push(nextPath);
            }
        }
    }
}
