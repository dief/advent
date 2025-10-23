package com.leynmaster.advent.aoc2023.day5;

import java.util.ArrayList;
import java.util.List;

public class SeedMap {
    private final List<SeedRange> ranges = new ArrayList<>();

    long map(long seed) {
        for (SeedRange range : ranges) {
            long min = range.source;
            if (seed >= min && seed < min + range.length) {
                return range.target + seed - min;
            }
        }
        return seed;
    }

    long reverseMap(long location) {
        for (SeedRange range : ranges) {
            long min = range.target;
            if (location >= min && location < min + range.length) {
                return range.source + location - min;
            }
        }
        return location;
    }

    void addRange(long[] numbers) {
        ranges.add(new SeedRange(numbers[0], numbers[1], numbers[2]));
    }

    record SeedRange(long target, long source, long length) {}
}
