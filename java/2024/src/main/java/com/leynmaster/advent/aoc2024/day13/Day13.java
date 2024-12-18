package com.leynmaster.advent.aoc2024.day13;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent 2024 Day 13.
 *
 * @author David Charles Pollack
 */
public class Day13 {
//    private static final String INPUT_FILE = "../../inputs/2024/day13/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day13/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<ClawMachine> machines = new ArrayList<>();

    public void addMachine(ClawMachine machine) {
        machines.add(machine);
    }

    public void run() {
        logger.info("Part 1: {}", tokensNeeded());
        for (ClawMachine machine : machines) {
            machine.addLocation(10_000_000_000_000L);
        }
        logger.info("Part 2: {}", tokensNeeded());
    }

    public long tokensNeeded() {
        long total = 0;
        for (ClawMachine machine : machines) {
            total += machine.tokensNeeded();
        }
        return total;
    }

    public static void main() throws IOException {
        Day13 solution = new Day13();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ClawMachine machine = new ClawMachine();
                machine.setButtonA(line);
                machine.setButtonB(reader.readLine());
                machine.setPrize(reader.readLine());
                solution.addMachine(machine);
                reader.readLine();
            }
        }
        solution.run();
    }
}
