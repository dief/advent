package com.leynmaster.advent.aoc2022.day15;

import com.leynmaster.advent.utils.map.Coordinate;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day15 {
//    private static final int TARGET = 10;
//    private static final int LIMIT = 20;
//    private static final String INPUT = "../../inputs/2022/day15/test-1.txt";
    private static final int TARGET = 2_000_000;
    private static final int LIMIT = 4_000_000;
    private static final String INPUT = "../../inputs/2022/day15/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Set<Coordinate> beacons = new HashSet<>();
    private final List<SensorArea> sensors = new ArrayList<>();

    void main() throws IOException {
        for (String line : FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8)) {
            String[] split = line.split("\\s+");
            Coordinate sensor = new Coordinate(parse(split[2]), parse(split[3]));
            Coordinate beacon = new Coordinate(parse(split[8]), parse(split[9]));
            beacons.add(beacon);
            sensors.add(new SensorArea(sensor, beacon));
        }
        logger.info("Starting");
        logger.info("Part 1: {}", emptySpaces());
        Coordinate beacon = findBeacon();
        logger.info("Part 2: {}", beacon.x() * 4_000_000L + beacon.y());
    }

    private int emptySpaces() {
        int count = rowRanges(TARGET).stream().map(Range::size).reduce(0, Integer::sum);
        for (Coordinate beacon : beacons) {
            if (beacon.y() == TARGET) {
                count--;
            }
        }
        return count;
    }

    private Coordinate findBeacon() {
        for (int y = 0; y <= LIMIT; y++) {
            List<Range> ranges = rowRanges(y);
            if (ranges.size() > 1) {
                return new Coordinate(ranges.getFirst().end() + 1, y);
            }
        }
        throw new IllegalStateException("Could not find the beacon");
    }

    private List<Range> rowRanges(int y) {
        List<Range> ranges = new ArrayList<>();
        for (SensorArea sensor : sensors) {
            Range gapRange = sensor.range(y);
            if (gapRange != null) {
                ranges.add(gapRange);
            }
        }
        return Range.merge(ranges);
    }

    private static int parse(String s) {
        return Integer.parseInt(s.split("[^-\\w]")[1]);
    }
}
