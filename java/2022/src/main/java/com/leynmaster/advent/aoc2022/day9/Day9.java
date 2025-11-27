package com.leynmaster.advent.aoc2022.day9;

import com.leynmaster.advent.utils.map.Coordinate;
import com.leynmaster.advent.utils.map.Direction;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day9 {
//    private static final String INPUT = "../../inputs/2022/day9/test-1.txt";
//    private static final String INPUT = "../../inputs/2022/day9/test-2.txt";
    private static final String INPUT = "../../inputs/2022/day9/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Coordinate> knots = new ArrayList<>();
    private final Set<Coordinate> visited = new HashSet<>();
    private List<Step> steps;

    void main() throws IOException {
        steps = FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8).stream()
                .map(line -> line.split("\\s+"))
                .map(this::parseLine)
                .toList();
        logger.info("Starting");
        logger.info("Part 1: {}", run(2));
        logger.info("Part 2: {}", run(10));
    }

    private int run(int count) {
        knots.clear();
        visited.clear();
        for (int i = 0; i < count; i++) {
            knots.add(new Coordinate(0, 0));
        }
        visited.add(knots.getLast());
        for (Step step : steps) {
            for (int i = 0; i < step.amount(); i++) {
                move(step.direction());
            }
        }
        return visited.size();
    }

    private void move(Direction direction) {
        knots.set(0, knots.getFirst().move(direction));
        for (int i = 1; i < knots.size(); i++) {
            moveKnot(i);
        }
        visited.add(knots.getLast());
    }

    private void moveKnot(int i) {
        Coordinate current = knots.get(i - 1);
        Coordinate next = knots.get(i);
        int xh = current.x();
        int yh = current.y();
        int xt = next.x();
        int yt = next.y();
        int xDiff = xh - xt;
        int yDiff = yh - yt;
        if (Math.abs(xDiff) > 1 && Math.abs(yDiff) > 1) {
            knots.set(i, next.move(new Coordinate(xDiff / 2, yDiff / 2)));
        } else if (Math.abs(xDiff) > 1) {
            knots.set(i, next.move(new Coordinate(xDiff / 2, yDiff)));
        } else if (Math.abs(yDiff) > 1) {
            knots.set(i, next.move(new Coordinate(xDiff, yDiff / 2)));
        }
    }

    private Step parseLine(String[] pair) {
        return new Step(switch (pair[0]) {
            case "R" -> Direction.RIGHT;
            case "L" -> Direction.LEFT;
            case "U" -> Direction.UP;
            case "D" -> Direction.DOWN;
            default -> throw new IllegalArgumentException(pair[0]);
        }, Integer.parseInt(pair[1]));
    }

    private record Step(Direction direction, int amount) {}
}
