package com.leynmaster.advent.aoc2025.day10;

class GaussianEliminator {
    private double[][] matrix;
    private int height;
    private final int width;
    private int[] freeVariables;

    GaussianEliminator(double[][] matrix) {
        this.matrix = matrix;
        height = matrix.length;
        width = matrix[0].length;
    }

    double[][] getMatrix() {
        return matrix;
    }

    int[] getFreeVariables() {
        return freeVariables;
    }

    void performElimination() {
        boolean[] freeVars = new boolean[width - 1];
        int freeCount = 0;
        int row = 0;
        int col = 0;
        while (row < height && col < width - 1) {
            int rowMax = rowMax(row, col);
            if (rowMax < 0) {
                freeVars[col++] = true;
                freeCount++;
            } else {
                swapRows(row, rowMax);
                reduceRow(row, col);
                row++;
                col++;
            }
        }
        while (col < width - 1) {
            freeVars[col++] = true;
            freeCount++;
        }
        removeZeroRows();
        reducePivots();
        setFreeVariables(freeVars, freeCount);
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

    private void reduceRow(int row, int col) {
        for (int i = 0; i < height; i++) {
            if (i != row) {
                double factor = matrix[i][col] / matrix[row][col];
                for (int j = 0; j < width; j++) {
                    matrix[i][j] -= matrix[row][j] * factor;
                }
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

    private void reducePivots() {
        for (int i = 0; i < height; i++) {
            double factor = pivot(i);
            for (int j = 0; j < width; j++) {
                matrix[i][j] /= factor;
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

    private void setFreeVariables(boolean[] freeVars, int count) {
        freeVariables = new int[count];
        int index = 0;
        for (int i = 0; i < freeVars.length; i++) {
            if (freeVars[i]) {
                freeVariables[index++] = i;
            }
        }
    }

    private static boolean nonZero(double num) {
        return num < -0.0001 || num > 0.0001;
    }
}
