package com.leynmaster.advent.aoc2023.day25;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day25 {
    private static final String INPUT_FILE = "../../inputs/2023/day25/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<String, Map<String, Integer>> connectionMap = new HashMap<>();

    public void add(String source, String[] targets) {
        Map<String, Integer> connections = connectionMap.computeIfAbsent(source, _ -> new HashMap<>());
        for (String target : targets) {
            connections.put(target, 1);
            connectionMap.computeIfAbsent(target, _ -> new HashMap<>()).put(source, 1);
        }
    }

    public void run() {
        logger.info("Starting...");
        logger.info("Stoer-Wagner: {}", new SWMinimumCut(connectionMap).minimumCut());
        logger.info("Karger-Stein: {}", new KargerSteinMinimumCut(connectionMap).minimumCut());
    }

    public static void main(String[] args) throws IOException {
        Day25 solution = new Day25();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\\s*:\\s*");
                solution.add(split[0], split[1].split("\\s+"));
            }
        }
        solution.run();
    }
}
