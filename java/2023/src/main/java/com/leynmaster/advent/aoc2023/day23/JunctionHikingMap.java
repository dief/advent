package com.leynmaster.advent.aoc2023.day23;

import com.leynmaster.advent.utils.map.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class JunctionHikingMap implements HikingMap {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Node start;
    private final Coordinate endCoordinate;

    public JunctionHikingMap(Node start, Coordinate endCoordinate) {
        this.start = start;
        this.endCoordinate = endCoordinate;
    }

    @Override
    public int longestPath() {
        int longest = 0;
        LinkedList<NodeCursor> queue = new LinkedList<>();
        queue.add(new NodeCursor(start, new HashSet<>(), 0));
        while (!queue.isEmpty()) {
            NodeCursor cursor = queue.removeFirst();
            Node node = cursor.node();
            Coordinate coordinate = node.getCoordinate();
            Set<Coordinate> seen = cursor.seen();
            if (endCoordinate.equals(coordinate)) {
                int distance = cursor.distance();
                if (distance > longest) {
                    logger.info("Path found: {}", distance);
                    longest = distance;
                }
            } else if (!seen.contains(coordinate)) {
                seen.add(coordinate);
                for (Vertex vertex : node.getVertices()) {
                    queue.addFirst(new NodeCursor(vertex.end(), new HashSet<>(seen), cursor.distance() + vertex.distance()));
                }
                seen.add(coordinate);
            }
        }
        return longest;
    }

    private record NodeCursor(Node node, Set<Coordinate> seen, int distance) { }
}
