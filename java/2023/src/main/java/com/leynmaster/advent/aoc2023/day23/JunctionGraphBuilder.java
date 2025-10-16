package com.leynmaster.advent.aoc2023.day23;

import com.leynmaster.advent.utils.map.Coordinate;
import com.leynmaster.advent.utils.map.Direction;

import java.util.*;
import java.util.function.Predicate;

public class JunctionGraphBuilder {
    private final Map<Coordinate, Node> nodeMap = new HashMap<>();
    private final char[][] matrix;
    private final int height;
    private final int width;
    private final Coordinate start;
    private final Coordinate end;
    
    public JunctionGraphBuilder(char[][] matrix) {
        this.matrix = matrix;
        this.height = matrix.length;
        this.width = matrix[0].length;
        start = new Coordinate(0, findOpening(matrix[0]));
        end = new Coordinate(height - 1, findOpening(matrix[height - 1]));
    }

    public Coordinate getEnd() {
        return end;
    }

    public Node buildGraph() {
        LinkedList<Node> queue = new LinkedList<>();
        Set<Coordinate> seen = new HashSet<>();
        Node startNode = new Node(start);
        nodeMap.put(start, startNode);
        queue.addLast(startNode);
        while (!queue.isEmpty()) {
            Node node = queue.removeFirst();
            Coordinate coordinate = node.getCoordinate();
            if (!seen.contains(coordinate)) {
                seen.add(coordinate);
                List<Coordinate> split = new ArrayList<>();
                split.add(coordinate.move(Direction.UP));
                split.add(coordinate.move(Direction.DOWN));
                split.add(coordinate.move(Direction.LEFT));
                split.add(coordinate.move(Direction.RIGHT));
                split.stream().filter(this::inbounds).forEach(next ->
                    computeVertex(coordinate, next).ifPresent(vertex -> {
                        node.addVertex(vertex);
                        queue.addLast(vertex.end());
                    })
                );
            }
        }
        return startNode;
    }

    private Optional<Vertex> computeVertex(Coordinate node, Coordinate next) {
        int distance = 1;
        Set<Coordinate> seen = new HashSet<>();
        seen.add(node);
        Coordinate search = next;
        List<Coordinate> unseen = unseenNeighbors(search, seen);
        if (unseen.isEmpty()) {
            return Optional.empty();
        }
        while (unseen.size() == 1 && search != end) {
            distance++;
            seen.add(search);
            search = unseen.getFirst();
            unseen = unseenNeighbors(search, seen);
        }
        Node nextNode = nodeMap.get(search);
        if (nextNode == null) {
            nextNode = new Node(search);
            nodeMap.put(search, nextNode);
        }
        return Optional.of(new Vertex(nextNode, distance));
    }

    private List<Coordinate> unseenNeighbors(Coordinate coordinate, Set<Coordinate> seen) {
        List<Coordinate> next = new ArrayList<>();
        next.add(coordinate.move(Direction.UP));
        next.add(coordinate.move(Direction.DOWN));
        next.add(coordinate.move(Direction.LEFT));
        next.add(coordinate.move(Direction.RIGHT));
        return next.stream().filter(this::inbounds).filter(Predicate.not(seen::contains)).toList();
    }

    private boolean inbounds(Coordinate coordinate) {
        int x = coordinate.x();
        int y = coordinate.y();
        return x >= 0 && x < height && y >= 0 && y < width && matrix[x][y] != '#';
    }

    private static int findOpening(char[] row) {
        for (int i = 0; i < row.length; i++) {
            if (row[i] == '.') {
                return i;
            }
        }
        return -1;
    }
}
