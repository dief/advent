package com.leynmaster.advent.aoc2023.day23;

import com.leynmaster.advent.utils.map.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final List<Vertex> vertices = new ArrayList<>();
    private final Coordinate coordinate;

    public Node(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }
}
