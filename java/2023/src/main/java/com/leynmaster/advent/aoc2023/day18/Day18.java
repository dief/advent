package com.leynmaster.advent.aoc2023.day18;

import com.leynmaster.advent.utils.map.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day18 {
    private static final String INPUT_FILE = "../../inputs/2023/day18/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<BuildStep> part1Steps = new ArrayList<>();
    private final List<BuildStep> part2Steps = new ArrayList<>();
    private final Map<String, Direction> directionMap = new HashMap<>();
    private final Map<Integer, Direction> hexDirectionMap = new HashMap<>();

    public Day18() {
        directionMap.put("U", Direction.UP);
        directionMap.put("D", Direction.DOWN);
        directionMap.put("L", Direction.LEFT);
        directionMap.put("R", Direction.RIGHT);

        hexDirectionMap.put(0, Direction.RIGHT);
        hexDirectionMap.put(1, Direction.DOWN);
        hexDirectionMap.put(2, Direction.LEFT);
        hexDirectionMap.put(3, Direction.UP);
    }

    public void run() {
        Lagoon lagoon = new Lagoon();
        lagoon.build(part1Steps);
        logger.info("Part 1: {}", lagoon.area());

        lagoon = new Lagoon();
        lagoon.build(part2Steps);
        logger.info("Part 2: {}", lagoon.area());
    }

    public void parseLine(String line) {
        String[] split = line.split(" ");
        String hexStep = split[2];
        part1Steps.add(new BuildStep(directionMap.get(split[0]), Integer.parseInt(split[1])));
        part2Steps.add(new BuildStep(hexDirectionMap.get(Integer.parseInt(hexStep.substring(7, 8))),
                Integer.parseInt(hexStep.substring(2, 7), 16)));
    }

    public static void main(String[] args) throws IOException {
        Day18 solution = new Day18();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!(line.isBlank() || line.trim().startsWith("#"))) {
                    solution.parseLine(line);
                }
            }
        }
        solution.run();
    }
}
