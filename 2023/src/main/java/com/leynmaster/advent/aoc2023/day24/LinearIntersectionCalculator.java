package com.leynmaster.advent.aoc2023.day24;

import java.util.List;

public class LinearIntersectionCalculator {
    private final long min;
    private final long max;
    private final List<HailPosition> positions;

    public LinearIntersectionCalculator(long min, long max, List<HailPosition> positions) {
        this.min = min;
        this.max = max;
        this.positions = positions;
    }

    public int findIntersections() {
        int intersections = 0;
        for (int i = 0; i < positions.size() - 1; i++) {
            HailPosition pos1 = positions.get(i);
            for (int j = i + 1; j < positions.size(); j++) {
                HailPosition pos2 = positions.get(j);
                if (intersection(pos1, pos2) != null) {
                    intersections++;
                }
            }
        }
        return intersections;
    }

    private double[] intersection(HailPosition pos1, HailPosition pos2) {
        HailCoordinate c1 = pos1.location();
        HailCoordinate c2 = pos2.location();
        long x1 = c1.x();
        long x2 = c2.x();
        double a = (double)pos1.deltaY() / pos1.deltaX();
        double c = (double)pos2.deltaY() / pos2.deltaX();
        if (Math.abs(a - c) < 0.001) {
            return null;
        }
        double b = c1.y() - a * x1;
        double d = c2.y() - c * x2;
        double xMatch = (d - b) / (a - c);
        double yMatch = a * xMatch + b;
        if (inbounds(pos1, xMatch, yMatch) && inbounds(pos2, xMatch, yMatch)) {
            return new double[] { xMatch, yMatch };
        }
        return null;
    }

    private boolean inbounds(HailPosition position, double xMatch, double yMatch) {
        return xMatch >= min && xMatch <= max && yMatch >= min && yMatch <= max &&
                (xMatch - position.location().x()) / position.deltaX() >= 0;
    }
}
