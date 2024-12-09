package com.leynmaster.advent.aoc2024.day7;

import java.util.List;

public record Equation(long solution, List<Long> values) {
    public boolean isValid(boolean includeOr) {
        long first = values.getFirst();
        if (includeOr) {
            return compute(first, 1, Operation.ADD, true)
                    || compute(first, 1, Operation.MULTIPLY, true)
                    || compute(first, 1, Operation.CONCAT, true);
        }
        return compute(first, 1, Operation.ADD, false)
                || compute(first, 1, Operation.MULTIPLY, false);
    }

    private boolean compute(long current, int index, Operation operation, boolean includeOr) {
        if (index >= values.size()) {
            return current == solution;
        }
        if (current > solution) {
            return false;
        }
        long nextVal = values.get(index);
        long nextCompute = switch(operation) {
            case ADD -> current + nextVal;
            case MULTIPLY -> current * nextVal;
            case CONCAT -> concat(current, nextVal);
        };
        if (includeOr) {
            return compute(nextCompute, index + 1, Operation.ADD, true)
                    || compute(nextCompute, index + 1, Operation.MULTIPLY, true)
                    || compute(nextCompute, index + 1, Operation.CONCAT, true);
        }
        return compute(nextCompute, index + 1, Operation.ADD, false)
                || compute(nextCompute, index + 1, Operation.MULTIPLY, false);
    }

    private static long concat(long val1, long val2) {
        return Long.parseLong(String.valueOf(val1) + val2);
    }

    private enum Operation {
        ADD, MULTIPLY, CONCAT
    }
}
