package com.leynmaster.advent.aoc2023.day20;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day20 {
    private static final String INPUT_FILE = "inputs/day20/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ModuleConfiguration moduleConfig = new ModuleConfiguration();

    public void setup(BufferedReader reader) throws IOException {
        List<String[]> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.isBlank()) {
                lines.add(line.trim().split("\\s*->\\s*"));
            }
        }
        moduleConfig.setup(lines);
    }

    public void part1() {
        long highPulses = 0L;
        long lowPulses = 0L;
        for (long i = 0L; i < 1000L; i++) {
            ButtonResult result = moduleConfig.pushButton(i);
            highPulses += result.highPulses();
            lowPulses += result.lowPulses();
        }
        logger.info("Part 1: {}", highPulses * lowPulses);
    }

    public void part2() {
        moduleConfig.reset();
        Map<String, Long> highMap = Collections.emptyMap();
        for (long i = 0; i < 5000L && highMap.isEmpty(); i++) {
            moduleConfig.pushButton(i);
            highMap = moduleConfig.highStatuses("ks", "pm", "dl", "vk");
        }
        Collection<Long> highMarks = highMap.values();
        logger.info("Part 2: {}", highMarks.stream().reduce(1L, (a, b) -> a * b) /
                highMarks.stream().reduce(1L, this::gcd));
    }

    private long gcd(long a, long b) {
        long divisor = a;
        long remainder = b;
        while (remainder > 0) {
            long temp = remainder;
            remainder = divisor % remainder;
            divisor = temp;
        }
        return divisor;
    }

    public static void main(String[] args) throws IOException {
        Day20 solution = new Day20();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            solution.setup(reader);
        }
        solution.part1();
        solution.part2();
    }
}
