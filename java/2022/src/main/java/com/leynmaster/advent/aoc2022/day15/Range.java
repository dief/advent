package com.leynmaster.advent.aoc2022.day15;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

record Range(int start, int end) {
    int size() {
        return end - start + 1;
    }

    static List<Range> merge(List<Range> ranges) {
        List<Range> merged = new ArrayList<>();
        ranges.sort(Comparator.comparing(Range::start));
        for (int i = 0; i < ranges.size() - 1; i++) {
            Range check = ranges.get(i);
            int newEnd = check.end();
            for (int j = i + 1; j < ranges.size(); j++) {
                Range next = ranges.get(j);
                if (next.start() > newEnd + 1) {
                    break;
                } else {
                    newEnd = Math.max(newEnd, next.end());
                    i++;
                }
            }
            merged.add(new Range(check.start(), newEnd));
        }
        return merged;
    }
}
