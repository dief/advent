package com.leynmaster.advent.aoc2023.day17;

import com.leynmaster.advent.utils.map.Coordinate;
import com.leynmaster.advent.utils.map.Direction;

import java.util.*;

public class HeatTraversalQueue {
    private final PriorityQueue<CoordinateCall> queue =
            new PriorityQueue<>(Comparator.comparingInt(CoordinateCall::totalHeat));
    private final Map<CallTracker, CoordinateCall> queueTracker = new HashMap<>();
    private final HashSet<CallTracker> visited = new HashSet<>();

    public void offer(CoordinateCall call) {
        CallTracker tracker = new CallTracker(call);
        if (visited.contains(tracker)) {
            return;
        }
        CoordinateCall existingCall = queueTracker.get(tracker);
        if (existingCall == null) {
            queue.offer(call);
            queueTracker.put(tracker, call);
        }
        else if (existingCall.totalHeat() > call.totalHeat()) {
            queue.remove(existingCall);
            queue.offer(call);
            queueTracker.put(tracker, call);
        }
    }

    public CoordinateCall poll() {
        CoordinateCall nextCall = queue.poll();
        if (nextCall == null) {
            return null;
        }
        CallTracker tracker = new CallTracker(nextCall);
        visited.add(tracker);
        queueTracker.remove(tracker);
        return nextCall;
    }

    private record CallTracker(Coordinate coordinate, Direction direction, int steps) {

        CallTracker(CoordinateCall call) {
            this(call.coordinate(), call.direction(), call.steps());
        }
    }
}
