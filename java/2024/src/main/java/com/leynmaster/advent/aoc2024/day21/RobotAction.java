package com.leynmaster.advent.aoc2024.day21;

import com.leynmaster.advent.utils.map.Direction;

public class RobotAction {
    private boolean push;
    private Direction direction;

    public RobotAction() {
        this.push = true;
    }

    public RobotAction(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return push ? "A" : switch (direction) {
            case UP -> "^";
            case DOWN -> "v";
            case LEFT -> "<";
            case RIGHT -> ">";
        };
    }
}
