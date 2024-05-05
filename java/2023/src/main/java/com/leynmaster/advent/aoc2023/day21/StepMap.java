package com.leynmaster.advent.aoc2023.day21;

import com.leynmaster.advent.aoc2023.common.matrix.Coordinate;
import com.leynmaster.advent.aoc2023.common.matrix.Direction;

import java.util.*;

public class StepMap {
    private final Set<Coordinate> rocks = new HashSet<>();
    private Set<PlotCursor> plots = new HashSet<>();
    private Coordinate start;
    private int height;
    private int width;
    private boolean expanding = false;

    public int getWidth() {
        return width;
    }

    public int getPlotCount() {
        return plots.size();
    }

    public void setExpanding(boolean expanding) {
        this.expanding = expanding;
    }

    public void takeStep() {
        Set<PlotCursor> nextSteps = new HashSet<>();
        for (PlotCursor cursor : plots) {
            nextSteps(nextSteps, cursor, Direction.UP);
            nextSteps(nextSteps, cursor, Direction.DOWN);
            nextSteps(nextSteps, cursor, Direction.LEFT);
            nextSteps(nextSteps, cursor, Direction.RIGHT);
        }
        plots = nextSteps;
    }

    public void setup(List<String> lines) {
        height = lines.size();
        for (int i = 0; i < height; i++) {
            char[] line = lines.get(i).toCharArray();
            width = line.length;
            for (int j = 0; j < width; j++) {
                addCoordinate(i, j, line[j]);
            }
        }
        reset();
    }

    public void reset() {
        plots.clear();
        plots.add(new PlotCursor(new Coordinate(0, 0), start));
    }

    private void addCoordinate(int x, int y, char c) {
        if (c == 'S') {
            start = new Coordinate(x, y);
        } else if (c == '#') {
            rocks.add(new Coordinate(x, y));
        }
    }

    private void nextSteps(Set<PlotCursor> nextSteps, PlotCursor cursor, Direction direction) {
        Coordinate matrix = cursor.matrix();
        Coordinate next = direction.shift(cursor.plot());
        int x = next.x();
        int y = next.y();
        boolean add = true;
        if (x < 0 || x >= height || y < 0 || y >= width) {
            if (expanding) {
                matrix = direction.shift(matrix);
                next = fixOverflow(next);
            } else {
                add = false;
            }
        }
        if (add && !rocks.contains(next)) {
            nextSteps.add(new PlotCursor(matrix, next));
        }
    }

    private Coordinate fixOverflow(Coordinate overflow) {
        int x = overflow.x();
        int y = overflow.y();
        if (x < 0) {
            return new Coordinate(height - 1, y);
        }
        if (x >= height) {
            return new Coordinate(0, y);
        }
        if (y < 0) {
            return new Coordinate(x, width - 1);
        }
        return new Coordinate(x, 0);
    }

    private record PlotCursor(Coordinate matrix, Coordinate plot) { }
}
