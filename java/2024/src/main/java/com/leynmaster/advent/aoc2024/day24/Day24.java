package com.leynmaster.advent.aoc2024.day24;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Advent 2024 Day 24.
 *
 * @author David Charles Pollack
 */
public class Day24 {
//    private static final String INPUT_FILE = "../../inputs/2024/day24/test-1.txt";
//    private static final String INPUT_FILE = "../../inputs/2024/day24/test-2.txt";
//    private static final String INPUT_FILE = "../../inputs/2024/day24/test-3.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day24/input.txt";
    private static final int Z_CAP = 45;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Pattern gatePattern = Pattern.compile("^(\\w+)\\s*(\\w+)\\s*(\\w+)\\s*->\\s*(\\w+)$");
    private final Map<String, Boolean> wiresStart = new TreeMap<>();
    private final List<Gate> gates = new ArrayList<>();
    private final GateChecker checker = new GateChecker(gates);
    private final TreeSet<String> gatesSwapped = new TreeSet<>();

    void main() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            parseWires(reader);
            parseGates(reader);
        }
        logger.info("Starting");
        logger.info("Part one: {}", partOne());
        logger.info("Part two: {}", String.join(",", partTwo()));
    }

    private void parseWires(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        while (line != null && !line.isBlank()) {
            String[] split = line.split("\\s*:\\s*");
            wiresStart.put(split[0], "1".equals(split[1]));
            line = reader.readLine();
        }
    }

    private void parseGates(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            Matcher matcher = gatePattern.matcher(line);
            if (matcher.matches()) {
                String input1 = addWire(matcher.group(1));
                String input2 = addWire(matcher.group(3));
                String destination = addWire(matcher.group(4));
                Gate gate = Gate.create(input1, input2, destination, matcher.group(2));
                gates.add(gate);
            }
        }
    }

    private String addWire(String wire) {
        if (!wiresStart.containsKey(wire)) {
            wiresStart.put(wire, null);
        }
        return wire;
    }

    private long partOne() {
        Map<String, Boolean> wires = new TreeMap<>(wiresStart);
        int i = 0;
        while (!valuesSet(wires) && i++ < gates.size()) {
            for (Gate gate : gates) {
                gate.process(wires);
            }
        }
        return compute(wires);
    }

    private TreeSet<String> partTwo() {
        int highest = checker.check(Z_CAP);
        while (highest < Z_CAP) {
//            logger.info("Reached: {}", highest);
            swapUntilReaches(highest);
            highest = checker.check(Z_CAP);

        }
        return gatesSwapped;
    }

    private void swapUntilReaches(int limit) {
        boolean swapping = true;
        for (int i = 0; i < gates.size() - 1 && swapping; i++) {
            for (int j = i + 1; j < gates.size() && swapping; j++) {
                swapping = trySwap(limit, i, j);
            }
        }
    }

    private boolean trySwap(int limit, int i, int j) {
        Gate gate1 = gates.get(i);
        Gate gate2 = gates.get(j);
        String dest1 = gate1.getDestination();
        String dest2 = gate2.getDestination();
        if (!gatesSwapped.contains(dest1) && !gatesSwapped.contains(dest2)) {
            gate1.setDestination(dest2);
            gate2.setDestination(dest1);
            if (checker.check(Z_CAP) > limit) {
                gatesSwapped.add(dest1);
                gatesSwapped.add(dest2);
                return false;
            } else {
                gate1.setDestination(dest1);
                gate2.setDestination(dest2);
            }
        }
        return true;
    }

    private static boolean valuesSet(Map<String, Boolean> values) {
        for (Map.Entry<String, Boolean> entry : values.entrySet()) {
            if (entry.getKey().startsWith("z") && entry.getValue() == null) {
                return false;
            }
        }
        return true;
    }

    private static long compute(Map<String, Boolean> wires) {
        long result = 0;
        for (Map.Entry<String, Boolean> entry : wires.entrySet()) {
            String wire = entry.getKey();
            if (wire.startsWith("z")) {
                Boolean value = entry.getValue();
                if (value != null && value) {
                    result += (long) Math.pow(2, Integer.parseInt(wire.substring(1)));
                }
            }
        }
        return result;
    }
}
