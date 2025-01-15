package com.leynmaster.advent.aoc2024.day17;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Advent 2024 Day 17.
 *
 * @author David Charles Pollack
 */
public class Day17 {
//    private static final String INPUT_FILE = "../../inputs/2024/day17/test-4.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day17/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Integer> instructions = new ArrayList<>();
    private final List<Integer> output = new ArrayList<>();
    private long startA = 0;
    private long startB = 0;
    private long startC = 0;
    private long registerA = 0;
    private long registerB = 0;
    private long registerC = 0;

    void main() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            startA = parseInput(reader.readLine()).getFirst();
            startB = parseInput(reader.readLine()).getFirst();
            startC = parseInput(reader.readLine()).getFirst();
            reader.readLine();
            instructions.addAll(parseInput(reader.readLine()));
        }
        run();
    }

    private void run() {
        logger.info("Starting");
        reset();
        runProgram();
        logger.info("Part 1: {}", String.join(",", output.stream().map(String::valueOf).toList()));
        logger.info("Part 2: {}", part2(0, 0));
        logger.info("Finished");
    }

    private long part2(long a, int i) {
        reset();
        registerA = a;
        runProgram();
        if (output.equals(instructions)) {
            return a;
        }
        long minA = Long.MAX_VALUE;
        if (i == 0 || output.equals(instructions.subList(instructions.size() - i, instructions.size()))) {
            for (int j = 0; j < 8; j++) {
                long result = part2((a << 3) + j, i + 1);
                if (result < minA) {
                    minA = result;
                }
            }
        }
        return minA;
    }

    private void reset() {
        registerA = startA;
        registerB = startB;
        registerC = startC;
        output.clear();
    }

    private void runProgram() {
        int i = 0;
        while (i < instructions.size()) {
            int instruction = instructions.get(i);
            switch (instruction) {
                case 0 -> {
                    registerA = adv(i);
                    i += 2;
                }
                case 1 -> {
                    registerB ^= instructions.get(i + 1);
                    i += 2;
                }
                case 2 -> {
                    registerB = combo(instructions.get(i + 1)) % 8;
                    i += 2;
                }
                case 3 -> {
                    if (registerA == 0L) {
                        i += 2;
                    } else {
                        i = instructions.get(i + 1);
                    }
                }
                case 4 -> {
                    registerB ^= registerC;
                    i += 2;
                }
                case 5 -> {
                    output.add((int)(combo(instructions.get(i + 1)) % 8));
                    i += 2;
                }
                case 6 -> {
                    registerB = adv(i);
                    i += 2;
                }
                case 7 -> {
                    registerC = adv(i);
                    i += 2;
                }
                default -> throw new IllegalArgumentException("Invalid instruction: " + instruction);
            }
        }
    }

    private long adv(int i) {
        return registerA >> combo(instructions.get(i + 1));
    }

    private long combo(int operand) {
        if (operand < 4) {
            return operand;
        }
        return switch (operand) {
            case 4 -> registerA;
            case 5 -> registerB;
            case 6 -> registerC;
            default -> throw new IllegalArgumentException("Invalid operand: " + operand);
        };
    }

    private static List<Integer> parseInput(String input) {
        return Arrays.stream(input.split(":\\s+")[1].split(",")).map(Integer::parseInt).toList();
    }
}
