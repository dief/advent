package com.leynmaster.advent.aoc2022.day15;

import com.leynmaster.advent.utils.map.Coordinate;

class SensorArea {
    private final Coordinate sensor;
    private final int distance;

    SensorArea(Coordinate sensor, Coordinate beacon) {
        this.sensor = sensor;
        this.distance = Math.abs(beacon.x() - sensor.x()) + Math.abs(beacon.y() - sensor.y());
    }

    Range range(int y) {
        int diff = distance - Math.abs(sensor.y() - y);
        return diff >= 0 ? new Range(sensor.x() - diff, sensor.x() + diff) : null;
    }
}
