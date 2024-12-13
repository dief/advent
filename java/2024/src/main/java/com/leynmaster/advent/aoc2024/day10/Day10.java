package com.leynmaster.advent.aoc2024.day10;

import com.leynmaster.advent.aoc2024.common.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Advent 2024 Day 10.
 *
 * @author David Charles Pollack
 */
public class Day10 {
//    private static final String INPUT_FILE = "../../inputs/2024/day10/test-5.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day10/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final int[][] map;
    private final int height;
    private final int width;
    private final Set<Coordinate> trailheads = new HashSet<>();

    public Day10(int[][] map) {
        this.map = map;
        this.height = map.length;
        this.width = map[0].length;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (map[x][y] == 0) {
                    trailheads.add(new Coordinate(x, y));
                }
            }
        }
    }

    public void run() {
        int part1 = 0;
        int part2 = 0;
        for (Coordinate trailhead : trailheads) {
            Set<Coordinate> ends = new HashSet<>();
            part2 += findEnds(trailhead, 0, ends);
            part1 += ends.size();
        }
        logger.info("Part 1: {}", part1);
        logger.info("Part 2: {}", part2);
    }

    private int findEnds(Coordinate coordinate, int check, Set<Coordinate> ends) {
        int x = coordinate.x();
        int y = coordinate.y();
        int trails = 0;
        if (x >= 0 && x < height && y >= 0 && y < width && map[x][y] == check) {
            if (check == 9) {
                ends.add(coordinate);
                return 1;
            }
            trails += findEnds(new Coordinate(x - 1, y), check + 1, ends);
            trails += findEnds(new Coordinate(x + 1, y), check + 1, ends);
            trails += findEnds(new Coordinate(x, y - 1), check + 1, ends);
            trails += findEnds(new Coordinate(x, y + 1), check + 1, ends);
        }
        return trails;
    }

    public static void main() throws IOException {
        List<int[]> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(map(line));
            }
        }
        Day10 solution = new Day10(list.toArray(new int[list.size()][]));
        solution.run();
    }

    private static int[] map(String line) {
        char[] chars = line.toCharArray();
        int size = chars.length;
        int[] row = new int[size];
        for (int i = 0; i < size; i++) {
            row[i] = Character.isDigit(chars[i]) ? chars[i] - 48 : -1;
        }
        return row;
    }
}
