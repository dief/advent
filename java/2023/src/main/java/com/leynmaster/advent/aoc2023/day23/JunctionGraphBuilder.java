package com.leynmaster.advent.aoc2023.day23;

import com.leynmaster.advent.utils.map.Coordinate;
import com.leynmaster.advent.utils.map.Direction;

import java.util.*;
import java.util.function.Predicate;

public class JunctionGraphBuilder extends AbstractGraphBuilder {
    private final Map<Coordinate, Node> nodeMap = new HashMap<>();
    
    public JunctionGraphBuilder(char[][] matrix) {
        super(matrix);
    }

    public Node buildGraph() {
        LinkedList<Node> queue = new LinkedList<>();
        Set<Coordinate> seen = new HashSet<>();
        Node startNode = new Node(getStart());
        nodeMap.put(getStart(), startNode);
        queue.addFirst(startNode);
        while (!queue.isEmpty()) {
            Node node = queue.removeFirst();
            Coordinate coordinate = node.getCoordinate();
            if (!seen.contains(coordinate)) {
                unseenNeighbors(coordinate, seen).stream().filter(this::inbounds)
                        .map(next -> computeVertex(coordinate, next))
                        .filter(Optional::isPresent)
                        .map(Optional::get).forEach(vertex -> {
                            node.addVertex(vertex);
                            queue.addFirst(vertex.end());
                        });
            }
        }
        return startNode;
    }

    private Optional<Vertex> computeVertex(Coordinate node, Coordinate next) {
        int distance = 1;
        Set<Coordinate> seen = new HashSet<>();
        seen.add(node);
        seen.add(next);
        Coordinate search = next;
        List<Coordinate> unseen = unseenNeighbors(search, seen);
        if (unseen.isEmpty()) {
            return Optional.empty();
        }
        while (unseen.size() == 1 && search != getEnd()) {
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
        seen.add(coordinate);
        List<Coordinate> next = new ArrayList<>();
        next.add(coordinate.move(Direction.UP));
        next.add(coordinate.move(Direction.DOWN));
        next.add(coordinate.move(Direction.LEFT));
        next.add(coordinate.move(Direction.RIGHT));
        return next.stream().filter(this::inbounds).filter(Predicate.not(seen::contains)).toList();
    }
}
