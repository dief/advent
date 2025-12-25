package com.leynmaster.advent.aoc2022.day14;

import com.leynmaster.advent.utils.map.Coordinate;
import com.leynmaster.advent.utils.map.Direction;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class Day14 {
//    private static final String INPUT = "../../inputs/2022/day14/test-1.txt";
    private static final String INPUT = "../../inputs/2022/day14/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private int maxX = 0;
    private int maxY = 0;
    private char[][] matrix;

    void main() throws IOException {
        List<RockLine> lines = FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8).stream()
                .flatMap(RockLine::parse).toList();
        int minX = lines.stream().map(line -> line.getStart().x()).reduce(Integer.MAX_VALUE, Math::min) - 200;
        maxX = lines.stream().map(line -> line.getEnd().x()).reduce(0, Math::max) - minX + 200;
        maxY = lines.stream().map(line -> line.getEnd().y()).reduce(0, Math::max) + 1;
        matrix = new char[maxY][maxX];
        for (char[] row : matrix) {
            Arrays.fill(row, '.');
        }
        lines.forEach(line -> line.translate(minX).draw(matrix));
        logger.info("Starting");
        Coordinate opening = new Coordinate(500 - minX, 0);
        int restingCount = 0;
        while (dropRock(opening)) {
            restingCount++;
        }
        logger.info("Part 1: {}", restingCount);
        addFloor();
        while (dropRock(opening)) {
            restingCount++;
        }
        logger.info("Part 2: {}", restingCount);
    }

    private boolean dropRock(Coordinate opening) {
        if (!free(opening)) {
            return false;
        }
        Coordinate current = opening;
        Coordinate next = nextSpot(opening);
        while (inbounds(next) && !current.equals(next)) {
            current = next;
            next = nextSpot(next);
        }
        if (inbounds(next)) {
            matrix[next.y()][next.x()] = 'o';
            return true;
        }
        return false;
    }

    private Coordinate nextSpot(Coordinate current) {
        Coordinate next = current.move(Direction.DOWN);
        if (free(next)) {
            return next;
        }
        next = current.move(new Coordinate(-1, 1));
        if (free(next)) {
            return next;
        }
        next = current.move(new Coordinate(1, 1));
        if (free(next)) {
            return next;
        }
        return current;
    }

    private boolean free(Coordinate current) {
        return !inbounds(current) || matrix[current.y()][current.x()] == '.';
    }

    private boolean inbounds(Coordinate current) {
        return current.x() >= 0 && current.x() < maxX && current.y() < maxY;
    }

    private void addFloor() {
        char[][] copy = new char[maxY + 2][maxX];
        for (int i = 0; i < maxY; i++) {
            copy[i] = Arrays.copyOf(matrix[i], maxX);
        }
        Arrays.fill(copy[maxY], '.');
        Arrays.fill(copy[maxY + 1], '#');
        matrix = copy;
        maxY += 2;
    }
}
