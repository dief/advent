package com.leynmaster.advent.aoc2024.day15;

import java.util.ArrayList;
import java.util.List;

public enum Step {
    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0);

    private final int deltaX;
    private final int deltaY;

    Step(int deltaX, int deltaY) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public static List<Step> fromLine(String line) {
        List<Step> steps = new ArrayList<>();
        for (char c : line.toCharArray()) {
            steps.add(fromChar(c));
        }
        return steps;
    }

    public static Step fromChar(char c) {
        return switch (c) {
            case '^' -> UP;
            case 'v' -> DOWN;
            case '<' -> LEFT;
            case '>' -> RIGHT;
            default -> throw new IllegalArgumentException("Invalid step character: " + c);
        };
    }
}
