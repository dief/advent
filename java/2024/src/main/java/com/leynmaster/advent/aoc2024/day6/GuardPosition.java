package com.leynmaster.advent.aoc2024.day6;

import com.leynmaster.advent.utils.map.Direction;

import java.util.HashSet;
import java.util.Set;

public class GuardPosition {
    private final Set<Direction> visitedDirections = new HashSet<>();
    private final boolean start;
    private final boolean originalObstruction;
    private boolean obstruction;
    private boolean visited;

    public GuardPosition(boolean start, boolean obstruction) {
        this.start = start;
        this.originalObstruction = obstruction;
        this.obstruction = obstruction;
    }

    public boolean isStart() {
        return start;
    }

    public boolean isObstruction() {
        return obstruction;
    }

    public void setObstruction(boolean obstruction) {
        this.obstruction = obstruction;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isVisited(Direction direction) {
        return visitedDirections.contains(direction);
    }

    public void visit(Direction direction) {
        this.visited = true;
        this.visitedDirections.add(direction);
    }

    public void reset() {
        obstruction = originalObstruction;
        visited = false;
        visitedDirections.clear();
    }
}
