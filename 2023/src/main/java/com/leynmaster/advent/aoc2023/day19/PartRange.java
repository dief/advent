package com.leynmaster.advent.aoc2023.day19;

import java.util.Map;
import java.util.function.Function;

record PartRange(long minX, long maxX, long minM, long maxM, long minA, long maxA, long minS, long maxS) {
    private static final Map<Character, Function<PartRange, Long>> minMap = Map.of(
            'x', PartRange::minX,
            'm', PartRange::minM,
            'a', PartRange::minA,
            's', PartRange::minS
    );
    private static final Map<Character, Function<PartRange, Long>> maxMap = Map.of(
            'x', PartRange::maxX,
            'm', PartRange::maxM,
            'a', PartRange::maxA,
            's', PartRange::maxS
    );

    public PartRange(long max) {
        this(1, max, 1, max, 1, max, 1, max);
    }

    long combinations() {
        return (maxX - minX + 1) * (maxM - minM + 1) * (maxA - minA + 1) * (maxS - minS + 1);
    }

    RangeResult split(char label, boolean checkLess, int split) {
        long min = minMap.get(label).apply(this);
        long max = maxMap.get(label).apply(this);
        if (checkLess) {
            split--;
        }
        if (checkLess ? max <= split : min > split) {
            return new RangeResult(this, null);
        }
        if (checkLess ? min > split : max <= split) {
            return new RangeResult(null, this);
        }
        return switch (label) {
            case 'x' -> splitX(split, checkLess);
            case 'm' -> splitM(split, checkLess);
            case 'a' -> splitA(split, checkLess);
            case 's' -> splitS(split, checkLess);
            default -> throw new IllegalArgumentException();
        };
    }
    
    RangeResult splitX(int split, boolean checkLess) {
        PartRange lower = new PartRange(minX, split, minM, maxM, minA, maxA, minS, maxS);
        PartRange upper = new PartRange(split + 1, maxX, minM, maxM, minA, maxA, minS, maxS);
        return checkLess ? new RangeResult(lower, upper) : new RangeResult(upper, lower);
    }

    RangeResult splitM(int split, boolean checkLess) {
        PartRange lower = new PartRange(minX, maxX, minM, split, minA, maxA, minS, maxS);
        PartRange upper = new PartRange(minX, maxX, split + 1, maxM, minA, maxA, minS, maxS);
        return checkLess ? new RangeResult(lower, upper) : new RangeResult(upper, lower);
    }

    RangeResult splitA(int split, boolean checkLess) {
        PartRange lower = new PartRange(minX, maxX, minM, maxM, minA, split, minS, maxS);
        PartRange upper = new PartRange(minX, maxX, minM, maxM, split + 1, maxA, minS, maxS);
        return checkLess ? new RangeResult(lower, upper) : new RangeResult(upper, lower);
    }

    RangeResult splitS(int split, boolean checkLess) {
        PartRange lower = new PartRange(minX, maxX, minM, maxM, minA, maxA, minS, split);
        PartRange upper = new PartRange(minX, maxX, minM, maxM, minA, maxA, split + 1, maxS);
        return checkLess ? new RangeResult(lower, upper) : new RangeResult(upper, lower);
    }
}
