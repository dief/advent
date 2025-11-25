package com.leynmaster.advent.aoc2023.day11;

import com.leynmaster.advent.utils.map.Coordinate;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent 2023 Day 11.
 *
 * @author David Charles Pollack
 */
public class Day11 {
//    private static final String INPUT_FILE = "../../inputs/2023/day11/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2023/day11/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Coordinate> galaxyStart = new ArrayList<>();
    private int height;
    private int width;
    private boolean[] rowCheck;
    private boolean[] colCheck;

    void main() throws IOException {
        char[][] grid = FileUtils.readLines(new File(INPUT_FILE), StandardCharsets.UTF_8).stream()
                .filter(line -> !line.isBlank())
                .map(String::toCharArray)
                .toArray(char[][]::new);
        height = grid.length;
        width = grid[0].length;
        rowCheck = new boolean[height];
        colCheck = new boolean[height];
        parseGalaxies(grid);
        logger.info("Starting");
        List<Coordinate> galaxies = expand(1);
        logger.info("Part 1: {}", totalDistance(galaxies));
        galaxies = expand(999_999);
        logger.info("Part 2: {}", totalDistance(galaxies));
    }

    private long totalDistance(List<Coordinate> galaxies) {
        long total = 0;
        for (int i = 0; i < galaxies.size() - 1; i++) {
            Coordinate galaxy1 = galaxies.get(i);
            for (int j = i + 1; j < galaxies.size(); j++) {
                Coordinate galaxy2 = galaxies.get(j);
                total += Math.abs(galaxy2.x() - galaxy1.x()) + Math.abs(galaxy2.y() - galaxy1.y());
            }
        }
        return total;
    }

    private List<Coordinate> expand(int expansion) {
        return expandCols(expandRows(galaxyStart, expansion), expansion);
    }

    private List<Coordinate> expandRows(List<Coordinate> galaxies, int expansion) {
        List<Coordinate> expanded = new ArrayList<>(galaxies);
        for (int i = height - 1; i >= 0; i--) {
            if (!rowCheck[i]) {
                expanded = expandRow(i, expansion, expanded);
            }
        }
        return expanded;
    }

    private List<Coordinate> expandCols(List<Coordinate> galaxies, int expansion) {
        List<Coordinate> expanded = new ArrayList<>(galaxies);
        for (int j = width - 1; j >= 0; j--) {
            if (!colCheck[j]) {
                expanded = expandCol(j, expansion, expanded);
            }
        }
        return expanded;
    }

    private List<Coordinate> expandRow(int row, int expansion, List<Coordinate> galaxies) {
        List<Coordinate> expanded = new ArrayList<>();
        for (Coordinate galaxy : galaxies) {
            expanded.add(galaxy.y() > row ? new Coordinate(galaxy.x(), galaxy.y() + expansion) : galaxy);
        }
        return expanded;
    }

    private List<Coordinate> expandCol(int col, int expansion, List<Coordinate> galaxies) {
        List<Coordinate> expanded = new ArrayList<>();
        for (Coordinate galaxy : galaxies) {
            expanded.add(galaxy.x() > col ? new Coordinate(galaxy.x() + expansion, galaxy.y()) : galaxy);
        }
        return expanded;
    }

    private void parseGalaxies(char[][] grid) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grid[i][j] == '#') {
                    galaxyStart.add(new Coordinate(j, i));
                    rowCheck[i] = true;
                    colCheck[j] = true;
                }
            }
        }
    }
}
