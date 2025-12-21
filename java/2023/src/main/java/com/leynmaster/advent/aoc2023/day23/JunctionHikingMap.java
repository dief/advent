package com.leynmaster.advent.aoc2023.day23;

import com.leynmaster.advent.utils.map.Coordinate;

import java.util.HashSet;
import java.util.Set;

public class JunctionHikingMap implements HikingMap {
    private final Node start;
    private final Coordinate endCoordinate;

    public JunctionHikingMap(Node start, Coordinate endCoordinate) {
        this.start = start;
        this.endCoordinate = endCoordinate;
    }

    @Override
    public int longestPath() {
        return longestPath(start, new HashSet<>());
    }

    private int longestPath(Node node, Set<Coordinate> seen) {
        Coordinate current = node.getCoordinate();
        if (endCoordinate.equals(current)) {
            return 0;
        }
        seen.add(current);
        int max = -1;
        for (Vertex vertex : node.getVertices()) {
            int next = traverse(vertex, seen);
            if (next > max) {
                max = next;
            }
        }
        return max;
    }

    private int traverse(Vertex vertex, Set<Coordinate> seen) {
        if (!seen.contains(vertex.end().getCoordinate())) {
            int path = longestPath(vertex.end(), new HashSet<>(seen));
            if (path >= 0) {
                return vertex.distance() + path;
            }
        }
        return -1;
    }
}
