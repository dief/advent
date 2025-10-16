package com.leynmaster.advent.aoc2024.day8;

import com.leynmaster.advent.utils.map.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Advent 2024 Day 8.
 *
 * @author David Charles Pollack
 */
public class Day8 {
//    private static final String INPUT_FILE = "../../inputs/2024/day8/test-3.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day8/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<Character, List<Coordinate>> antennaMap;
    private final int height;
    private final int width;
    private final boolean[][] antinodes;

    Day8(Map<Character, List<Coordinate>> antennaMap, int height, int width) {
        this.antennaMap = antennaMap;
        this.height = height;
        this.width = width;
        antinodes = new boolean[height][width];
        reset();
    }

    public void run() {
        logger.info("Part 1: {}", part1());
        reset();
        logger.info("Part 2: {}", part2());
    }

    private int part1() {
        for (Map.Entry<Character, List<Coordinate>> entry : antennaMap.entrySet()) {
            List<Coordinate> antennas = entry.getValue();
            for (int i = 0; i < antennas.size(); i++) {
                for (int j = i + 1; j < antennas.size(); j++) {
                    findAntinodes1(antennas.get(i), antennas.get(j));
                }
            }
        }
        return count();
    }

    private int part2() {
        for (Map.Entry<Character, List<Coordinate>> entry : antennaMap.entrySet()) {
            List<Coordinate> antennas = entry.getValue();
            for (int i = 0; i < antennas.size(); i++) {
                for (int j = i + 1; j < antennas.size(); j++) {
                    findAntinodes2(antennas.get(i), antennas.get(j));
                }
            }
        }
        return count();
    }

    private int count() {
        int total = 0;
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (antinodes[x][y]) {
                    total++;
                }
            }
        }
        return total;
    }

    private void reset() {
        for (int x = 0; x < height; x++) {
            Arrays.fill(antinodes[x], false);
        }
    }

    private void findAntinodes1(Coordinate antenna1, Coordinate antenna2) {
        int deltaX = antenna2.x() - antenna1.x();
        int deltaY = antenna2.y() - antenna1.y();
        setAntinode(antenna2.x() + deltaX, antenna2.y() + deltaY);
        setAntinode(antenna1.x() - deltaX, antenna1.y() - deltaY);
    }

    private void findAntinodes2(Coordinate antenna1, Coordinate antenna2) {
        int deltaX = antenna2.x() - antenna1.x();
        int deltaY = antenna2.y() - antenna1.y();
        setAntinodes(antenna2, deltaX, deltaY);
        setAntinodes(antenna1, -deltaX, -deltaY);
    }

    private void setAntinodes(Coordinate start, int deltaX, int deltaY) {
        int x = start.x();
        int y = start.y();
        while (x >= 0 && x < height && y >= 0 && y < width) {
            setAntinode(x, y);
            x += deltaX;
            y += deltaY;
        }
    }

    private void setAntinode(int x, int y) {
        if (x >= 0 && x < height && y >= 0 && y < width) {
            antinodes[x][y] = true;
        }
    }

    public static void main() throws IOException {
        Map<Character, List<Coordinate>> antennaMap = new HashMap<>();
        int i = 0;
        int j = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                char[] row = line.toCharArray();
                j = row.length;
                for (int y = 0; y < row.length; y++) {
                    if (row[y] != '.') {
                        antennaMap.computeIfAbsent(row[y], _ -> new ArrayList<>()).add(new Coordinate(i, y));
                    }
                }
                i++;
            }
        }
        Day8 solution = new Day8(antennaMap, i, j);
        solution.run();
    }
}
