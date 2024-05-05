package com.leynmaster.advent.aoc2023.day24;

public class MatrixRockCalculator {
    private final HailPosition pos1;
    private final HailPosition pos2;
    private final HailPosition pos3;
    private final HailPosition pos4;

    public MatrixRockCalculator(HailPosition pos1, HailPosition pos2, HailPosition pos3, HailPosition pos4) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.pos3 = pos3;
        this.pos4 = pos4;
    }

    public double[] findRockOrigin() {
        double[][] base = new double[6][6];
        base[0] = xyRow(pos1, pos2);
        base[1] = xzRow(pos1, pos2);
        base[2] = xyRow(pos1, pos3);
        base[3] = xzRow(pos1, pos3);
        base[4] = xyRow(pos1, pos4);
        base[5] = xzRow(pos1, pos4);
        double[] rhs = new double[] {
                xyRight(pos1, pos2), xzRight(pos1, pos2), xyRight(pos1, pos3), xzRight(pos1, pos3),
                xyRight(pos1, pos4), xzRight(pos1, pos4)
        };
        double baseDeterminant = determinant(base);
        double a1 = determinant(replaceColumn(base, rhs, 0));
        double a2 = determinant(replaceColumn(base, rhs, 1));
        double a3 = determinant(replaceColumn(base, rhs, 2));
        return new double[] {
                a1 / baseDeterminant,
                a2 / baseDeterminant,
                a3 / baseDeterminant
        };
    }

    public double determinant(double[][] matrix) {
        double determinant = 0;
        if (matrix.length == 1) {
            return matrix[0][0];
        }
        double[] firstRow = matrix[0];
        for (int i = 0; i < firstRow.length; i++) {
            double addOn = firstRow[i] * determinant(reduce(matrix, i));
            if (i % 2 == 0) {
                determinant += addOn;
            } else {
                determinant -= addOn;
            }
        }
        return determinant;
    }

    private double[][] reduce(double[][] matrix, int split) {
        int newSize = matrix.length - 1;
        double[][] reduced = new double[newSize][newSize];
        for (int i = 0; i < newSize; i++) {
            System.arraycopy(matrix[i + 1], 0, reduced[i], 0, split);
            System.arraycopy(matrix[i + 1], split + 1, reduced[i], split, newSize - split);
        }
        return reduced;
    }

    private double[][] replaceColumn(double[][] matrix, double[] rhs, int column) {
        int size = matrix.length;
        double[][] replaced = new double[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(matrix[i], 0, replaced[i], 0, size);
            replaced[i][column] = rhs[i];
        }
        return replaced;
    }

    private double[] xyRow(HailPosition p1, HailPosition p2) {
        HailCoordinate c1 = p1.location();
        HailCoordinate c2 = p2.location();
        return new double[] {p1.deltaY() - p2.deltaY(), p2.deltaX() - p1.deltaX(), 0,
                c2.y() - c1.y(), c1.x() - c2.x(), 0};
    }

    private double[] xzRow(HailPosition p1, HailPosition p2) {
        HailCoordinate c1 = p1.location();
        HailCoordinate c2 = p2.location();
        return new double[] {p1.deltaZ() - p2.deltaZ(), 0, p2.deltaX() - p1.deltaX(),
                c2.z() - c1.z(), 0, c1.x() - c2.x()};
    }

    private double xyRight(HailPosition p1, HailPosition p2) {
        HailCoordinate c1 = p1.location();
        HailCoordinate c2 = p2.location();
        return c1.x() * p1.deltaY() - c1.y() * p1.deltaX() - c2.x() * p2.deltaY() + c2.y() * p2.deltaX();
    }

    private long xzRight(HailPosition p1, HailPosition p2) {
        HailCoordinate c1 = p1.location();
        HailCoordinate c2 = p2.location();
        return c1.x() * p1.deltaZ() - c1.z() * p1.deltaX() - c2.x() * p2.deltaZ() + c2.z() * p2.deltaX();
    }
}
