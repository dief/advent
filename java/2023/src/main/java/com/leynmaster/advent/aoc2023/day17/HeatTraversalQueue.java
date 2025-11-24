package com.leynmaster.advent.aoc2023.day17;

import com.leynmaster.advent.utils.map.Coordinate;
import com.leynmaster.advent.utils.map.Direction;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class HeatTraversalQueue {
    private final PriorityQueue<CoordinateCall> queue =
            new PriorityQueue<>(Comparator.comparingInt(CoordinateCall::totalHeat));
    private final HashSet<CallTracker> visited = new HashSet<>();

    public void offer(CoordinateCall call) {
        CallTracker tracker = new CallTracker(call);
        if (visited.contains(tracker)) {
            return;
        }
        queue.offer(call);
        visited.add(tracker);
    }

    public CoordinateCall poll() {
        return queue.poll();
    }

    private record CallTracker(Coordinate coordinate, Direction direction, int steps) {

        CallTracker(CoordinateCall call) {
            this(call.coordinate(), call.direction(), call.steps());
        }
    }
}
