package com.leynmaster.advent.aoc2022.day4;

import com.leynmaster.advent.utils.input.NumberUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Predicate;

public class Day4 {
//    private static final String INPUT = "../../inputs/2022/day4/test-1.txt";
    private static final String INPUT = "../../inputs/2022/day4/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private List<SectionRange[]> ranges;

    void main() throws IOException {
        logger.info("Starting");
        ranges = FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8).stream()
                .map(line -> line.replaceAll("[-,]", " "))
                .map(NumberUtils::parseInts)
                .map(inputs -> new SectionRange[] { new SectionRange(inputs[0], inputs[1]),
                        new SectionRange(inputs[2], inputs[3])})
                .toList();
        logger.info("Part 1: {}", count(this::contains));
        logger.info("Part 2: {}", count(this::overlaps));
    }

    private long count(Predicate<SectionRange[]> check) {
        return ranges.stream().filter(check).count();
    }

    private boolean contains(SectionRange[] pair) {
        SectionRange range1 = pair[0];
        SectionRange range2 = pair[1];
        return range1.contains(range2) || range2.contains(range1);
    }

    private boolean overlaps(SectionRange[] pair) {
        SectionRange range1 = pair[0];
        SectionRange range2 = pair[1];
        return range1.overlaps(range2) || range2.overlaps(range1);
    }

    private record SectionRange(int start, int end) {
        boolean contains(SectionRange other) {
            return this.start <= other.start && this.end >= other.end;
        }

        boolean overlaps(SectionRange other) {
            return this.end() >= other.start() && other.end() >= this.end();
        }
    }
}
