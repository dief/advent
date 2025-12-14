package com.leynmaster.advent.aoc2025.day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MachineSolver {
    private final boolean[] freeCols;
    private final int maxTarget;
    private final int width;
    private int height;
    private int freeCount;
    private double[][] matrix;

    public MachineSolver(int[] joltsTarget, int[][] buttons) {
        this.height = joltsTarget.length;
        this.width = buttons.length + 1;
        this.maxTarget = max(joltsTarget);
        freeCols = new boolean[buttons.length];
        matrix = new double[height][width];
        for (int i = 0; i < buttons.length; i++) {
            for (int num : buttons[i]) {
                matrix[num][i] = 1;
            }
        }
        for (int i = 0; i < height; i++) {
            matrix[i][width - 1] = joltsTarget[i];
        }
        gaussEliminate();
        removeZeroRows();
        reducePivots();
    }

    long solve() {
        if (freeCount == 0) {
            double sum = 0;
            for (int i = 0; i < height; i++) {
                sum += matrix[i][width - 1];
            }
            return Math.round(sum);
        }
        return solveFreeVars();
    }

    private long solveFreeVars() {
        double min = -1;
        for (int i = maxTarget; i >= 0; i--) {
            for (int[] vars : permutations(i)) {
                double solution = solve(vars);
                if (solution > 0.0001 && (min < 0 || solution - min < 0.0001)) {
                    min = solution;
                }
            }
        }
        return Math.round(min);
    }

    private double solve(int[] vars) {
        double[] solution = solutionArray(vars);
        double sum = 0;
        for (double num : solution) {
            if (num < -0.0001) {
                return -1;
            }
            double remainder = num % 1;
            if (remainder > 0.0001 && remainder < 0.9999) {
                return -1;
            }
            sum += num;
        }
        return sum;
    }

    private double[] solutionArray(int[] vars) {
        double[] solution = new double[height + freeCount];
        for (int i = 0; i < height; i++) {
            int varCounter = 0;
            double result = matrix[i][width - 1];
            for (int j = 0; j < width - 1; j++) {
                if (freeCols[j]) {
                    result -= matrix[i][j] * vars[varCounter++];
                }
            }
            solution[i] = result;
        }
        addVariables(solution, vars);
        return solution;
    }

    private void addVariables(double[] solution, int[] vars) {
        for (int i = 0; i < freeCount; i++) {
            solution[height + i] = vars[i];
        }
    }

    private List<int[]> permutations(int max) {
        List<int[]> results = new ArrayList<>();
        int[] current = new int[freeCount];
        doPermutations(max, 0, current, results);
        return results;
    }

    private void doPermutations(int remaining, int index, int[] current, List<int[]> results) {
        if (index == freeCount - 1) {
            current[index] = remaining;
            results.add(Arrays.copyOf(current, current.length));
        } else {
            for (int i = 0; i <= remaining; i++) {
                current[index] = i;
                doPermutations(remaining - i, index + 1, current, results);
            }
        }
    }

    private void gaussEliminate() {
        int row = 0;
        int col = 0;
        while (row < height && col < width - 1) {
            int rowMax = rowMax(row, col);
            if (rowMax < 0) {
                freeCols[col++] = true;
                freeCount++;
            } else {
                swapRows(row, rowMax);
                reduceRow(row, col);
                row++;
                col++;
            }
        }
        while (col < width - 1) {
            freeCols[col++] = true;
            freeCount++;
        }
    }

    private void reduceRow(int row, int col) {
        for (int i = 0; i < height; i++) {
            if (i != row) {
                double factor = matrix[i][col] / matrix[row][col];
                for (int j = 0; j < width; j++) {
                    matrix[i][j] = matrix[i][j] - matrix[row][j] * factor;
                }
            }
        }
    }

    private void reducePivots() {
        for (int i = 0; i < height; i++) {
            double factor = pivot(i);
            for (int j = 0; j < width; j++) {
                matrix[i][j] = matrix[i][j] / factor;
            }
        }
    }

    private double pivot(int row) {
        for (int j = 0; j < width; j++) {
            if (nonZero(matrix[row][j])) {
                return matrix[row][j];
            }
        }
        return 0;
    }

    private int rowMax(int row, int col) {
        double maxValue = 0;
        int maxRow = -1;
        for (int i = row; i < height; i++) {
            double value = Math.abs(matrix[i][col]);
            if (value - maxValue > 0.0001) {
                maxValue = value;
                maxRow = i;
            }
        }
        return maxRow;
    }

    private void swapRows(int r1, int r2) {
        if (r1 != r2) {
            for (int j = 0; j < width; j++) {
                double tmp = matrix[r1][j];
                matrix[r1][j] = matrix[r2][j];
                matrix[r2][j] = tmp;
            }
        }
    }

    private void removeZeroRows() {
        for (int i = 0; i < height; i++) {
            if (zeroRow(i)) {
                double[][] tmp = matrix;
                matrix = new double[i][width];
                height = i;
                for (int k = 0; k < i; k++) {
                    System.arraycopy(tmp[k], 0, matrix[k], 0, width);
                }
                break;
            }
        }
    }

    private boolean zeroRow(int row) {
        for (int j = 0; j < width - 1; j++) {
            if (nonZero(matrix[row][j])) {
                return false;
            }
        }
        return true;
    }

    private static int max(int[] values) {
        int max = 0;
        for (int value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private static boolean nonZero(double num) {
        return num < -0.0001 || num > 0.0001;
    }
}
