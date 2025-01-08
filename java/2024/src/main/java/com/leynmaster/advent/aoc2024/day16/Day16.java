package com.leynmaster.advent.aoc2024.day16;

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
 * Advent 2024 Day 16.
 *
 * @author David Charles Pollack
 */
public class Day16 {
//    private static final String INPUT_FILE = "../../inputs/2024/day16/test-2.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day16/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private char[][] grid;
    private Position[][] scores;
    private boolean[][] visited;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    void main() throws IOException {
        List<char[]> gridLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                gridLines.add(line.toCharArray());
                line = reader.readLine();
            }
        }
        grid = gridLines.toArray(new char[0][]);
        init();
        run();
    }

    private void init() {
        scores = new Position[grid.length][];
        visited = new boolean[grid.length][];
        for (int y = 0; y < grid.length; y++) {
            scores[y] = new Position[grid[y].length];
            visited[y] = new boolean[grid[y].length];
            for (int x = 0; x < grid[y].length; x++) {
                scores[y][x] = new Position();
                visited[y][x] = false;
                if (grid[y][x] == 'S') {
                    startX = x;
                    startY = y;
                    grid[y][x] = '.';
                }
                if (grid[y][x] == 'E') {
                    endX = x;
                    endY = y;
                    grid[y][x] = '.';
                }
            }
        }
    }

    private void run() {
        logger.info("Starting");
        computeScores();
        logger.info("Part 1: {}", scores[endY][endX].getMinScore());
        checkVisited();
        int visitedCount = 0;
        for (boolean[] row : visited) {
            for (boolean cell : row) {
                visitedCount += cell ? 1 : 0;
            }
        }
        logger.info("Part 2: {}", visitedCount);
    }

    private void computeScores() {
        LinkedList<Step> steps = new LinkedList<>();
        scores[startY][startX].addScore(0);
        steps.add(new Step(new Coordinate(startX, startY), Direction.RIGHT, 0));
        while (!steps.isEmpty()) {
            Step step = steps.poll();
            if (step.coordinate().x() == endX && step.coordinate().y() == endY) {
                continue;
            }
            int current = step.score();
            Direction direction = step.direction();
            Coordinate next = step.coordinate().move(direction);
            int nextScore = current + 1;
            if (grid[next.y()][next.x()] != '#' && scores[next.y()][next.x()].addScore(nextScore)) {
                steps.offer(new Step(next, direction, nextScore));
            }
            nextScore += 1000;
            for (Direction nextDirection : getTurns(direction)) {
                next = step.coordinate().move(nextDirection);
                if (grid[next.y()][next.x()] != '#' && scores[next.y()][next.x()].addScore(nextScore)) {
                    steps.offer(new Step(next, nextDirection, nextScore));
                }
            }
        }
    }

    private void checkVisited() {
        LinkedList<Step> steps = new LinkedList<>();
        visited[endY][endX] = true;
        visited[startY][startX] = true;
        int nextScore = scores[endY][endX].getMinScore() - 1;
        if (scores[endY][endX - 1].containsScore(nextScore)) {
            steps.add(new Step(new Coordinate(endX - 1, endY), Direction.LEFT, nextScore));
        }
        if (scores[endY + 1][endX].containsScore(nextScore)) {
            steps.add(new Step(new Coordinate(endX, endY + 1), Direction.DOWN, nextScore));
        }
        while (!steps.isEmpty()) {
            Step step = steps.poll();
            int x = step.coordinate().x();
            int y = step.coordinate().y();
            if (x == startX && y == startY) {
                continue;
            }
            visited[y][x] = true;
            steps.addAll(getNextSteps(step));
        }
    }

    private LinkedList<Step> getNextSteps(Step step) {
        Direction direction = step.direction();
        LinkedList<Step> steps = new LinkedList<>();
        int nextScore = step.score() - 1;
        int turnScore = nextScore - 1000;
        Coordinate next = step.coordinate().move(direction);
        if (scores[next.y()][next.x()].containsScore(nextScore)) {
            steps.offer(new Step(next, direction, nextScore));
        }
        if (scores[next.y()][next.x()].containsScore(turnScore)) {
            steps.offer(new Step(next, direction, turnScore));
        }
        for (Direction nextDirection : getTurns(direction)) {
            next = step.coordinate().move(nextDirection);
            if (scores[next.y()][next.x()].containsScore(nextScore)) {
                steps.offer(new Step(next, nextDirection, nextScore));
            }
            if (scores[next.y()][next.x()].containsScore(turnScore)) {
                steps.offer(new Step(next, nextDirection, turnScore));
            }
        }
        return steps;
    }

    private static Direction[] getTurns(Direction direction) {
        return switch (direction) {
            case UP -> new Direction[]{Direction.LEFT, Direction.RIGHT};
            case DOWN -> new Direction[]{Direction.RIGHT, Direction.LEFT};
            case LEFT -> new Direction[]{Direction.DOWN, Direction.UP};
            case RIGHT -> new Direction[]{Direction.UP, Direction.DOWN};
        };
    }

    private record Step(Coordinate coordinate, Direction direction, int score) { }
}
