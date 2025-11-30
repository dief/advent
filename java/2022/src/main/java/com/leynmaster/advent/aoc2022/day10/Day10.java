package com.leynmaster.advent.aoc2022.day10;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Day10 {
//    private static final String INPUT = "../../inputs/2022/day10/test-1.txt";
    private static final String INPUT = "../../inputs/2022/day10/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Integer> history = new ArrayList<>();

    void main() throws IOException {
        logger.info("Starting");
        runProgram(FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8));
        logger.info("Part 1: {}", strengths());
        logger.info("Part 2: {}", draw());
    }

    private int strengths() {
        int sum = 0;
        for (int i = 19; i < history.size(); i += 40) {
            sum += (i + 1) * history.get(i);
        }
        return sum;
    }

    private String draw() {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < history.size(); i++) {
            int position = i % 40;
            if (position == 0) {
                line.append('\n');
            }
            line.append(Math.abs(position - history.get(i)) < 2 ? '#' : '.');
        }
        return line.toString();
    }

    private void runProgram(List<String> lines) {
        int register = 1;
        for (String instruction : lines) {
            if ("noop".equals(instruction)) {
                history.add(register);
            } else {
                history.add(register);
                history.add(register);
                register += Integer.parseInt(instruction.split("\\s+")[1]);
            }
        }
    }
}
