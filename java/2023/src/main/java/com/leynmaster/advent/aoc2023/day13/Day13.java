package com.leynmaster.advent.aoc2023.day13;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day13 {
    private static final String INPUT_FILE = "../../inputs/2023/day13/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private int summary1;
    private int summary2;

    public void run() throws IOException {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            List<char[]> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    processMap(lines, count++);
                    lines.clear();
                } else {
                    lines.add(line.toCharArray());
                }
            }
            if (!lines.isEmpty()) {
                processMap(lines, count++);
            }
        }
        logger.info("Part 1: {}", summary1);
        logger.info("Part 2: {}", summary2);
    }

    private void processMap(List<char[]> lines, int i) {
        LavaMap map = new LavaMap(lines);
        summary1 += map.summary(0);
        summary2 += map.summary(1);;
    }

    public static void main(String[] args) throws IOException {
        new Day13().run();
    }

    private static class LavaMap {
        private final char[][] mapMatrix;
        private final int rows;
        private final int columns;

        LavaMap(List<char[]> lines) {
            mapMatrix = lines.toArray(new char[0][]);
            rows = mapMatrix.length;
            columns = mapMatrix[0].length;
        }

        int summary(int threshold) {
            return checkRows(threshold) * 100 + checkColumns(threshold);
        }

        private int checkRows(int threshold) {
            for (int i = 1; i < rows; i++) {
                if (searchMirrorRow(threshold, i - 1, i)) {
                    return i;
                }
            }
            return 0;
        }

        private int checkColumns(int threshold) {
            for (int i = 1; i < columns; i++) {
                if (searchMirrorColumn(threshold, i - 1, i)) {
                    return i;
                }
            }
            return 0;
        }

        private boolean searchMirrorRow(int threshold, int row1, int row2) {
            int diffs = 0;
            for (int i = row1, j = row2; i >= 0 && j < rows; i--, j++) {
                diffs += rowDiffs(i, j);
                if (diffs > threshold) {
                    return false;
                }
            }
            return diffs == threshold;
        }

        private boolean searchMirrorColumn(int threshold, int col1, int col2) {
            int diffs = 0;
            for (int i = col1, j = col2; i >= 0 && j < columns; i--, j++) {
                diffs += columnDiffs(i, j);
                if (diffs > threshold) {
                    return false;
                }
            }
            return diffs == threshold;
        }

        private int rowDiffs(int row1, int row2) {
            int diffs = 0;
            for (int i = 0; i < columns; i++) {
                if (mapMatrix[row1][i] != mapMatrix[row2][i]) {
                    diffs++;
                }
            }
            return diffs;
        }

        private int columnDiffs(int col1, int col2) {
            int diffs = 0;
            for (int i = 0; i < rows; i++) {
                if (mapMatrix[i][col1] != mapMatrix[i][col2]) {
                    diffs++;
                }
            }
            return diffs;
        }
    }
}
