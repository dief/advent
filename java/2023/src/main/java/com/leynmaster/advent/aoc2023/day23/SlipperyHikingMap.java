package com.leynmaster.advent.aoc2023.day23;

import com.leynmaster.advent.utils.map.Coordinate;
import com.leynmaster.advent.utils.map.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Predicate;

public class SlipperyHikingMap implements HikingMap {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final LinkedList<PathCursor> queue = new LinkedList<>();
    private final char[][] map;
    private final int height;
    private final int width;
    private final Coordinate start;
    private final Coordinate end;

    public SlipperyHikingMap(char[][] map) {
        this.map = map;
        this.height = map.length;
        this.width = map[0].length;
        start = new Coordinate(0, findOpening(map[0]));
        end = new Coordinate(height - 1, findOpening(map[height - 1]));
    }

    @Override
    public int longestPath() {
        int maxEnd = 0;
        Set<Coordinate> initialSeen = new HashSet<>();
        initialSeen.add(start);
        queue.addFirst(new PathCursor(start.move(Direction.DOWN), initialSeen, 1));
        while (!queue.isEmpty()) {
            PathCursor cursor = queue.removeFirst();
            Coordinate coordinate = cursor.coordinate();
            Set<Coordinate> seen = cursor.seen();
            int distance = cursor.distance();
            if (coordinate.equals(end)) {
                if (distance > maxEnd) {
                    maxEnd = cursor.distance();
                    logger.info("New max end: {}", maxEnd);
                }
            } else if (!seen.contains(coordinate)) {
                seen.add(coordinate);
                List<Coordinate> unseen = unseenNeighbors(coordinate, seen, map[coordinate.x()][coordinate.y()]);
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
        if (position == '.' || position == '^') {
            next.add(coordinate.move(Direction.UP));
        }
        if (position == '.' || position == 'v') {
            next.add(coordinate.move(Direction.DOWN));
        }
        if (position == '.' || position == '<') {
            next.add(coordinate.move(Direction.LEFT));
        }
        if (position == '.' || position == '>') {
            next.add(coordinate.move(Direction.RIGHT));
        }
        return next.stream().filter(this::inbounds).filter(Predicate.not(seen::contains)).toList();
    }

    private boolean inbounds(Coordinate coordinate) {
        int x = coordinate.x();
        int y = coordinate.y();
        return x >= 0 && x < height && y >= 0 && y < width && map[x][y] != '#';
    }

    private static int findOpening(char[] row) {
        for (int i = 0; i < row.length; i++) {
            if (row[i] == '.') {
                return i;
            }
        }
        return -1;
    }

    private record PathCursor(Coordinate coordinate, Set<Coordinate> seen, int distance) { }
}
