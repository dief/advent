package com.leynmaster.advent.aoc2023.day14;

public class ReflectorDish {
    private char[][] matrix;
    private int rows;
    private int columns;

    public ReflectorDish(char[][] matrix) {
        this.matrix = matrix;
        this.rows = matrix.length;
        this.columns = matrix[0].length;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                buf.append(matrix[i][j]);
            }
            buf.append('|');
        }
        return buf.toString();
    }

    public int score() {
        int score = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (matrix[i][j] == 'O') {
                    score += rows - i;
                }
            }
        }
        return score;
    }

    public void cycle() {
        for (int i = 0; i < 4; i++) {
            roll();
            rotate();
        }
    }

    public void roll() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                char space = matrix[i][j];
                if (space == 'O') {
                    roll(i, j);
                }
            }
        }
    }

    private void roll(int row, int column) {
        boolean blocked = false;
        for (int i = row; i > 0 && !blocked; i--) {
            if (matrix[i - 1][column] == '.') {
                matrix[i - 1][column] = matrix[i][column];
                matrix[i][column] = '.';
            } else {
                blocked = true;
            }
        }
    }

    private void rotate() {
        char[][] newMatrix = new char[columns][rows];
        for (int j = 0; j < columns; j++) {
            for (int i = rows - 1; i >= 0; i--) {
                newMatrix[j][rows - i - 1] = matrix[i][j];
            }
        }
        matrix = newMatrix;
        rows = newMatrix.length;
        columns = newMatrix[0].length;
    }
}
