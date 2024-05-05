package com.leynmaster.advent.aoc2023.day19;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Day19 {
    private static final String INPUT_FILE = "inputs/day19/input.txt";
    private static final long RANGE_MAX = 4000;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final WorkflowConductor conductor = new WorkflowConductor();
    private final List<Part> parts = new ArrayList<>();

    Day19() throws IOException {
        String line;
        boolean workflowParsing = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    workflowParsing = false;
                } else {
                    parseLine(workflowParsing, line);
                }
            }
        }
    }

    void run() {
        logger.info("Part 1: {}", part1());
        logger.info("Part 2: {}", part2());
    }

    private int part1() {
        int result = 0;
        for (Part part : parts) {
            result += conductor.accepted(part) ? part.rating() : 0;
        }
        return result;
    }

    private long part2() {
        return conductor.getCombinations(new PartRange(RANGE_MAX));
    }

    private void parseLine(boolean workflowParsing, String line) {
        if (workflowParsing) {
            conductor.addWorkflow(line);
        } else {
            parts.add(new Part(line));
        }
    }

    static void main(String[] args) throws IOException {
        new Day19().run();
    }
}
