package com.leynmaster.advent.aoc2023.day22;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Day22 {
    private static final String INPUT_FILE = "../../inputs/2023/day22/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<int[][]> levels = new ArrayList<>();
    private List<Brick> bricks;
    private BrickTower tower;
    private int maxX;
    private int maxY;
    private int maxZ;

    public void setBricks(List<Brick> bricks) {
        this.bricks = bricks;
        bricks.forEach(this::checkMaximums);
        for (int i = 0; i < maxZ + 1; i++) {
            levels.add(new int[maxX + 1][maxY + 1]);
        }
        bricks.forEach(this::setupBrick);
        tower = new BrickTower(levels, bricks);
    }

    public void run() {
        tower.settleBricks();
        logger.info("Part 1: {}", tower.bricksToDisintegrate());
        long fallingSum = 0;
        for (Brick brick : bricks) {
            LinkedList<Brick> queue = new LinkedList<>(brick.getSupported());
            Set<Integer> falling = new HashSet<>();
            falling.add(brick.getLabel());
            while (!queue.isEmpty()) {
                Brick current = queue.removeFirst();
                if (current.getSupportedBy().stream().map(Brick::getLabel).allMatch(falling::contains)) {
                    falling.add(current.getLabel());
                    queue.addAll(current.getSupported());
                }
            }
            fallingSum += falling.size() - 1;
        }
        logger.info("Part 2: {}", fallingSum);
    }

    private void setupBrick(Brick brick) {
        int label = brick.getLabel();
        for (BrickCoordinate coordinate : brick.getCoordinates()) {
            levels.get(coordinate.z())[coordinate.x()][coordinate.y()] = label;
        }
    }

    private void checkMaximums(Brick brick) {
        BrickCoordinate end = brick.getCoordinates().getLast();
        if (end.x() > maxX) {
            maxX = end.x();
        }
        if (end.y() > maxY) {
            maxY = end.y();
        }
        if (end.z() > maxZ) {
            maxZ = end.z();
        }
    }

    public static void main(String[] args) throws IOException {
        List<Brick> bricks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            int counter = 0;
            while ((line = reader.readLine()) != null) {
                bricks.add(new Brick(++counter, line));
            }
        }
        Day22 solution = new Day22();
        solution.setBricks(bricks);
        solution.run();
    }
}
