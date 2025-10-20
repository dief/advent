package com.leynmaster.advent.aoc2023.day5;

import java.util.ArrayList;
import java.util.List;

public class SeedMap {
    private final List<SeedRange> ranges = new ArrayList<>();

    long map(long seed) {
        for (SeedRange range : ranges) {
            long min = range.source;
            long max = min + range.length;
            if (seed >= min && seed < max) {
                return range.target + seed - min;
            }
        }
        return seed;
    }

    long reverseMap(long location) {
        for (SeedRange range : ranges) {
            long min = range.target;
            long max = min + range.length;
            if (location >= min && location < max) {
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
