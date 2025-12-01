package com.leynmaster.advent.aoc2022.day11;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day11 {
//    private static final String INPUT = "../../inputs/2022/day11/test-1.txt";
    private static final String INPUT = "../../inputs/2022/day11/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Monkey> monkeyRules = new ArrayList<>();
    private final List<LinkedList<Long>> queues = new ArrayList<>();

    void main() throws IOException {
        for (String section : FileUtils.readFileToString(new File(INPUT), StandardCharsets.UTF_8).split("\\R\\R")) {
            monkeyRules.add(new Monkey(section));
        }
        logger.info("Starting");
        logger.info("Part 1: {}", runRounds(20));
        long supermod = monkeyRules.stream().map(Monkey::getTest).reduce(1L, (a, b) -> a * b);
        monkeyRules.forEach(monkey -> {
            monkey.setStrong(true);
            monkey.setSupermod(supermod);
        });
        logger.info("Part 2: {}", runRounds(10_000));
    }

    private long runRounds(int rounds) {
        resetQueues();
        long[] counters = new long[monkeyRules.size()];
        for (int i = 0; i < rounds; i++) {
            for (int index = 0; index < monkeyRules.size(); index++) {
                Monkey monkey = monkeyRules.get(index);
                LinkedList<Long> queue = new LinkedList<>(queues.get(index));
                queues.get(index).clear();
                for (long item : queue) {
                    counters[index]++;
                    long nextValue = monkey.nextWorryLevel(item);
                    queues.get(monkey.nextMonkey(nextValue)).offer(nextValue);
                }
            }
        }
        Arrays.sort(counters);
        return counters[counters.length - 1] * counters[counters.length - 2];
    }

    private void resetQueues() {
        queues.clear();
        for (Monkey rule : monkeyRules) {
            LinkedList<Long> queue = new LinkedList<>();
            for (long item : rule.getStartItems()) {
                queue.offer(item);
            }
            queues.add(queue);
        }
    }
}
