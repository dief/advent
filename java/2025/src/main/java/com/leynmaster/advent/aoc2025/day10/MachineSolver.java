package com.leynmaster.advent.aoc2025.day10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class MachineSolver {
    private final double[][] matrix;
    private final int[] freeVariables;
    private final int freeCount;
    private final int maxTarget;
    private final int height;
    private final int width;

    MachineSolver(int[][] buttons, int[] joltsTarget) {
        this.width = buttons.length + 1;
        double[][] augMatrix = new double[joltsTarget.length][width];
        for (int i = 0; i < buttons.length; i++) {
            for (int num : buttons[i]) {
                augMatrix[num][i] = 1;
            }
        }
        for (int i = 0; i < joltsTarget.length; i++) {
            augMatrix[i][width - 1] = joltsTarget[i];
        }
        GaussianEliminator eliminator = new GaussianEliminator(augMatrix);
        eliminator.performElimination();
        matrix = eliminator.getMatrix();
        this.height = matrix.length;
        this.freeVariables = eliminator.getFreeVariables();
        this.freeCount = freeVariables.length;
        this.maxTarget = max(joltsTarget);
    }

    long solve() {
        if (freeCount == 0) {
            double sum = 0.0;
            for (int i = 0; i < height; i++) {
                sum += matrix[i][width - 1];
            }
            return Math.round(sum);
        }
        return Math.round(solveFreeVars());
    }

    private double solveFreeVars() {
        double min = Double.MAX_VALUE;
        for (int i = 0; i <= maxTarget; i++) {
            List<int[]> results = new ArrayList<>();
            permutations(i, 0, new int[freeCount], results);
            for (int[] vars : results) {
                double solution = solveFreeVars(vars);
                if (solution > 0.0001 && solution - min < 0.0001) {
                    min = solution;
                }
            }
        }
        return min;
    }

    private double solveFreeVars(int[] vars) {
        double[] solution = solutionArray(vars);
        double sum = 0.0;
        for (double num : solution) {
            if (num < -0.0001) {
                return -1.0;
            }
            double remainder = num % 1.0;
            if (remainder > 0.0001 && remainder < 0.9999) {
                return -1.0;
            }
            sum += num;
        }
        return sum;
    }

    private double[] solutionArray(int[] vars) {
        double[] solution = new double[height + freeCount];
        for (int i = 0; i < height; i++) {
            double result = matrix[i][width - 1];
            for (int j = 0; j < freeVariables.length; j++) {
                result -= matrix[i][freeVariables[j]] * vars[j];
            }
            solution[i] = result;
        }
        for (int i = 0; i < freeCount; i++) {
            solution[height + i] = vars[i];
        }
        return solution;
    }

    private void permutations(int remaining, int index, int[] current, List<int[]> results) {
        if (index == freeCount - 1) {
            current[index] = remaining;
            results.add(Arrays.copyOf(current, current.length));
        } else {
            for (int i = 0; i <= remaining; i++) {
                current[index] = i;
                permutations(remaining - i, index + 1, current, results);
            }
        }
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
}
