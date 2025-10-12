package com.leynmaster.advent.aoc2024.day25;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent 2024 Day 25.
 *
 * @author David Charles Pollack
 */
public class Day25 {
//    private static final String INPUT_FILE = "../../inputs/2024/day25/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day25/input.txt";
    private static final int PIN_THRESHOLD = 5;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<int[]> keys = new ArrayList<>();
    private final List<int[]> locks = new ArrayList<>();

    void main() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(".")) {
                    keys.add(parseKey(comboGrid(line, reader)));
                } else {
                    locks.add(parseLock(comboGrid(line, reader)));
                }
            }
        }
        logger.info("Starting");
        logger.info("Part one: {}", partOne());
    }

    private int partOne() {
        int counter = 0;
        for (int[] key : keys) {
            for (int[] lock : locks) {
                if (matches(key, lock)) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private static boolean matches(int[] key, int[] lock) {
        for (int i = 0; i < lock.length; i++) {
            if (key[i] + lock[i] > PIN_THRESHOLD) {
                return false;
            }
        }
        return true;
    }

    private static int[] parseKey(char[][] comboGrid) {
        int rows = comboGrid.length;
        int[] combo = new int[comboGrid[0].length];
        for (int j = 0; j < combo.length; j++) {
            boolean searching = true;
            for (int i = rows - 1; i >= 0 && searching; i--) {
                if (comboGrid[i][j] == '.') {
                    combo[j] = rows - i - 2;
                    searching = false;
                }
            }
        }
        return combo;
    }

    private static int[] parseLock(char[][] comboGrid) {
        int[] combo = new int[comboGrid[0].length];
        for (int j = 0; j < combo.length; j++) {
            for (int i = 0; i < comboGrid.length; i++) {
                if (comboGrid[i][j] == '.') {
                    combo[j] = i - 1;
                    break;
                }
            }
        }
        return combo;
    }

    private static char[][] comboGrid(String firstLine, BufferedReader reader) throws IOException {
        List<char[]> lines = new ArrayList<>();
        lines.add(firstLine.toCharArray());
        String line = reader.readLine();
        while (line != null && !line.isBlank()) {
            lines.add(line.toCharArray());
            line = reader.readLine();
        }
        return lines.toArray(new char[0][]);
    }
}
