package com.leynmaster.advent.aoc2024.day18;

import com.leynmaster.advent.aoc2024.common.Coordinate;
import com.leynmaster.advent.aoc2024.common.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Advent 2024 Day 18
 *
 * @author David Charles Pollack
 */
public class Day18 {
//    private static final String INPUT_FILE = "../../inputs/2024/day18/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day18/input.txt";
    private static final int LENGTH = 71;
    private static final int FALL_AMT = 1024;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Coordinate> blocks = new ArrayList<>();
    private final char[][] grid = new char[LENGTH][LENGTH];
    private final int[][] scores = new int[LENGTH][LENGTH];

    void main() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                blocks.add(new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
            }
        }
        run();
    }

    private void run() {
        Coordinate block = null;
        logger.info("Starting");
        for (int i = 0; i < FALL_AMT; i++) {
            block = blocks.get(i);
            grid[block.y()][block.x()] = '#';
        }
        computeScores();
        logger.info("Part 1: {}", scores[LENGTH - 1][LENGTH - 1]);
        for (int i = FALL_AMT; i < blocks.size(); i++) {
            block = blocks.get(i);
            grid[block.y()][block.x()] = '#';
            computeScores();
            if (scores[LENGTH - 1][LENGTH - 1] == Integer.MAX_VALUE) {
                break;
            }
        }
        logger.info("Part 2: {},{}", block.x(), block.y());
    }

    private void computeScores() {
        reset();
        LinkedList<Step> steps = new LinkedList<>();
        steps.offer(new Step(new Coordinate(0, 0), 0));
        while (!steps.isEmpty()) {
            Step step = steps.poll();
            int nextScore = step.score() + 1;
            for (Direction direction : Direction.values()) {
                Coordinate nextPos = step.coordinate().move(direction);
                int x = nextPos.x();
                int y = nextPos.y();
                if (x >= 0 && x < LENGTH && y >= 0 && y < LENGTH && grid[y][x] != '#' && scores[y][x] > nextScore) {
                    scores[y][x] = nextScore;
                    steps.offer(new Step(nextPos, nextScore));
                }
            }
        }
    }

    private void reset() {
        for (int y = 0; y < LENGTH; y++) {
            for (int x = 0; x < LENGTH; x++) {
                scores[y][x] = Integer.MAX_VALUE;
            }
        }
        scores[0][0] = 0;
    }

    private record Step(Coordinate coordinate, int score) { }
}
