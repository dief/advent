package com.leynmaster.advent.aoc2024.day13;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClawMachine {
    private static final Pattern linePattern = Pattern.compile("^[^:]+: X.(\\d+), Y.(\\d+)$");
    private final long[][] matrix = new long[2][3];

    public void setButtonA(String line) {
        setColumn(line, 0);
    }

    public void setButtonB(String line) {
        setColumn(line, 1);
    }

    public void setPrize(String line) {
        setColumn(line, 2);
    }

    public void addLocation(long position) {
        matrix[0][2] += position;
        matrix[1][2] += position;
    }

    public long tokensNeeded() {
        long detAll = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        long detA = matrix[0][2] * matrix[1][1] - matrix[0][1] * matrix[1][2];
        long detB = matrix[0][0] * matrix[1][2] - matrix[0][2] * matrix[1][0];
        long a = detA / detAll;
        long b = detB / detAll;
        long xResult = matrix[0][0] * a + matrix[0][1] * b;
        long yResult = matrix[1][0] * a + matrix[1][1] * b;
        if (xResult == matrix[0][2] && yResult == matrix[1][2]) {
            return a * 3 + b;
        }
        return 0;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(matrix);
    }

    private void setColumn(String line, int column) {
        Matcher matcher = linePattern.matcher(line);
        if (matcher.matches()) {
            matrix[0][column] = Long.parseLong(matcher.group(1));
            matrix[1][column] = Long.parseLong(matcher.group(2));
        }
    }
}
