package com.leynmaster.advent.aoc2023.day23;

import com.leynmaster.advent.utils.map.Coordinate;
import com.leynmaster.advent.utils.map.Direction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public class SlipperyHikingMap extends AbstractGraphBuilder implements HikingMap {
    private final LinkedList<PathCursor> queue = new LinkedList<>();
    private final Map<Character, Direction[]> directionMap = Map.of(
            '.', Direction.values(),
            '^', new Direction[] { Direction.UP },
            'v', new Direction[] { Direction.DOWN },
            '<', new Direction[] { Direction.LEFT },
            '>', new Direction[] { Direction.RIGHT }
    );

    public SlipperyHikingMap(char[][] map) {
        super(map);
    }

    @Override
    public int longestPath() {
        int maxEnd = 0;
        queue.addFirst(new PathCursor(getStart(), new HashSet<>(), 0));
        while (!queue.isEmpty()) {
            PathCursor cursor = queue.removeFirst();
            Coordinate coordinate = cursor.coordinate();
            Set<Coordinate> seen = cursor.seen();
            int distance = cursor.distance();
            if (coordinate.equals(getEnd()) && distance > maxEnd) {
                maxEnd = cursor.distance();
            } else if (!seen.contains(coordinate)) {
                seen.add(coordinate);
                List<Coordinate> unseen = unseenNeighbors(coordinate, seen, location(coordinate));
                unseen.stream()
                        .map(next -> new PathCursor(next, unseen.size() == 1 ? seen : new HashSet<>(seen),
                                distance + 1))
                        .forEach(queue::addFirst);
            }
        }
        return maxEnd;
    }

    private List<Coordinate> unseenNeighbors(Coordinate coordinate, Set<Coordinate> seen, char position) {
        List<Coordinate> next = new ArrayList<>();
        for (Direction direction : directionMap.get(position)) {
            next.add(coordinate.move(direction));
        }
        return next.stream().filter(this::inbounds).filter(Predicate.not(seen::contains)).toList();
    }

    private record PathCursor(Coordinate coordinate, Set<Coordinate> seen, int distance) { }
}
