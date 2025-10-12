package com.leynmaster.advent.aoc2024.day24;

import java.util.List;
import java.util.Optional;

public record GateChecker(List<Gate> gates) {

    public int check(int limit) {
        Optional<Gate> z0 = findGate(wire("x", 0), wire("y", 0), "XOR");
        if (z0.isEmpty() || !"z00".equals(z0.get().getDestination())) {
            return 0;
        }
        Optional<Gate> c1 = findGate(wire("x", 0), wire("y", 0), "AND");
        Optional<Gate> c2 = findGate(wire("x", 1), wire("y", 1), "XOR");
        String prev1;
        String prev2;
        if (c1.isEmpty() || c2.isEmpty()) {
            return 1;
        } else {
            prev1 = c1.get().getDestination();
            prev2 = c2.get().getDestination();
        }
        Optional<Gate> z1 = findGate(prev1, prev2, "XOR");
        if (z1.isEmpty() || !"z01".equals(z1.get().getDestination())) {
            return 1;
        }
        return check(limit, prev1, prev2);
    }

    private int check(int limit, String startPrev1, String startPrev2) {
        String prev1 = startPrev1;
        String prev2 = startPrev2;
        for (int i = 2; i < limit; i++) {
            String[] nextPrev = passes(i, prev1, prev2);
            if (nextPrev == null) {
                return i;
            }
            prev1 = nextPrev[0];
            prev2 = nextPrev[1];
        }
        return limit;
    }

    private String[] passes(int i, String prev1, String prev2) {
        Optional<Gate> icPrev = findGate(prev1, prev2, "AND");
        Optional<Gate> carryPrev = findGate(wire("x", i - 1), wire("y", i - 1), "AND");
        if (icPrev.isEmpty() || carryPrev.isEmpty()) {
            return null;
        }
        Optional<Gate> carry = findGate(icPrev.get().getDestination(), carryPrev.get().getDestination(), "OR");
        Optional<Gate> sum = findGate(wire("x", i), wire("y", i), "XOR");
        if (carry.isEmpty() || sum.isEmpty()) {
            return null;
        }
        prev1 = carry.get().getDestination();
        prev2 = sum.get().getDestination();
        Optional<Gate> result = findGate(prev1, prev2, "XOR");
        if (result.isEmpty() || !wire("z", i).equals(result.get().getDestination())) {
            return null;
        }
        return new String[] { prev1, prev2 };
    }

    private Optional<Gate> findGate(String input1, String input2, String type) {
        return gates.stream().filter(gate -> gate.match(input1, input2, type)).findFirst();
    }

    private static String wire(String prefix, int number) {
        return prefix + (number < 10 ? "0" : "") + number;
    }
}
