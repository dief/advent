package com.leynmaster.advent.aoc2022.day17;

import com.leynmaster.advent.utils.map.Direction;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day17 {
//    private static final String INPUT = "../../inputs/2022/day17/test-1.txt";
    private static final String INPUT = "../../inputs/2022/day17/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final char[][] cavern = new char[32768][7];
    private final List<Direction> vent = new ArrayList<>();
    private final List<CavernProfile> cache = new ArrayList<>();
    private int yMax = -1;
    private int ventPointer = 0;

    void main() throws IOException {
        String input = FileUtils.readFileToString(new File(INPUT), StandardCharsets.UTF_8).trim();
        for (char c : input.toCharArray()) {
            vent.add(c == '>' ? Direction.RIGHT : Direction.LEFT);
        }
        initCavern();
        for (int i = 0; i < 2022; i++) {
            dropRock(i);
        }
        logger.info("Part 1: {}", yMax + 1);
        for (int i = 2022; i < 5000; i++) {
            dropRock(i);
        }
        logger.info("Part 2: {}", findCycle());
    }

    private void dropRock(int i) {
        Rock rock = switch (i % 5) {
            case 0 -> Rock.createRock0(yMax + 5);
            case 1 -> Rock.createRock1(yMax + 5);
            case 2 -> Rock.createRock2(yMax + 5);
            case 3 -> Rock.createRock3(yMax + 5);
            case 4 -> Rock.createRock4(yMax + 5);
            default -> throw new IllegalStateException();
        };
        while (rock.move(Direction.UP, cavern)) {
            rock.move(vent.get(ventPointer), cavern);
            ventPointer = (ventPointer + 1) % vent.size();
        }
        yMax = Math.max(yMax, rock.yMax());
        rock.draw(cavern);
        if (yMax > 28) {
            cache.add(getProfile(i));
        }
    }

    private CavernProfile getProfile(int rock) {
        StringBuilder topProfileBuilder = new StringBuilder();
        for (int i = 0; i < 30; i++) {
            topProfileBuilder.append(cavern[yMax - i]);
        }
        return new CavernProfile(rock % 5, ventPointer, topProfileBuilder.toString(), rock, yMax);
    }

    private long findCycle() {
        for (int i = 1; i < cache.size(); i++) {
            CavernProfile end = cache.get(i);
            for (CavernProfile start : cache.subList(0, i - 1)) {
                if (start.match(end)) {
                    int cacheOffset = cache.getFirst().rockNum();
                    int period = end.rockNum() - start.rockNum();
                    long target = 1_000_000_000_000L - start.rockNum();
                    long cycles = target / period;
                    int off = (int)(target - (cycles * period));
                    CavernProfile p1 = cache.get(start.rockNum() - cacheOffset + off);
                    CavernProfile p2 = cache.get(start.rockNum() - cacheOffset + off + period);
                    return (p2.yMax() - p1.yMax()) * cycles + p1.yMax();
                }
            }
        }
        return -1L;
    }

    private void initCavern() {
        for (char[] row : cavern) {
            Arrays.fill(row, '.');
        }
    }

    private record CavernProfile(int rockPointer, int ventPointer, String topProfile, int rockNum, int yMax) {
        private boolean match(CavernProfile other) {
            return rockPointer == other.rockPointer && ventPointer == other.ventPointer
                    && topProfile.equals(other.topProfile);
        }
    }
}
