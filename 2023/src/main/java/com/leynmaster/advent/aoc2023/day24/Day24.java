package com.leynmaster.advent.aoc2023.day24;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day24 {
    private static final String INPUT_FILE = "inputs/day24/input.txt";
    private static final long MIN = 200000000000000L;
    private static final long MAX = 400000000000000L;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<HailPosition> positions = new ArrayList<>();

    public void addPosition(HailPosition position) {
        positions.add(position);
    }

    public void run() {
        logger.info("Part 1: {}", new LinearIntersectionCalculator(MIN, MAX, positions).findIntersections());
        double[] rockOrigin = new MatrixRockCalculator(
                positions.get(0), positions.get(1), positions.get(2), positions.get(3)).findRockOrigin();
        logger.info("Part 2: {}", (long)(rockOrigin[0] + rockOrigin[1] + rockOrigin[2]));
    }

    public static void main(String[] args) throws IOException {
        Day24 solution = new Day24();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\\s*@\\s*");
                String[] posSplit = split[0].split("\\s*,\\s*");
                String[] velSplit = split[1].split("\\s*,\\s*");
                solution.addPosition(new HailPosition(new HailCoordinate(
                        Long.parseLong(posSplit[0]), Long.parseLong(posSplit[1]),
                        Long.parseLong(posSplit[2])), Long.parseLong(velSplit[0]),
                        Long.parseLong(velSplit[1]), Long.parseLong(velSplit[2])));
            }
        }
        solution.run();
    }
}
