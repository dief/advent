package com.leynmaster.advent.aoc2022.day5;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day5 {
//    private static final String INPUT = "../../inputs/2022/day5/test-1.txt";
    private static final String INPUT = "../../inputs/2022/day5/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<LinkedList<String>> stacks = new ArrayList<>();

    void main() throws IOException {
        logger.info("Starting");
        String[] sections = FileUtils.readFileToString(new File(INPUT), StandardCharsets.UTF_8).split("\\R\\R");
        String[] instructions = sections[1].split("\\R");
        parseStacks(sections[0]);
        for (String instruction : instructions) {
            processInstruction(instruction, false);
        }
        logger.info("Part 1: {}", stacks.stream().map(LinkedList::peek).collect(Collectors.joining()));
        stacks.clear();
        parseStacks(sections[0]);
        for (String instruction : instructions) {
            processInstruction(instruction, true);
        }
        logger.info("Part 2: {}", stacks.stream().map(LinkedList::peek).collect(Collectors.joining()));
    }

    private void processInstruction(String row, boolean reverse) {
        String[] split = row.split("\\s+");
        int amount = Integer.parseInt(split[1]);
        LinkedList<String> source = stacks.get(Integer.parseInt(split[3]) - 1);
        LinkedList<String> target = stacks.get(Integer.parseInt(split[5]) - 1);
        LinkedList<String> moveSet = new LinkedList<>();
        for (int i = 0; i < amount; i++) {
            moveSet.add(source.pop());
        }
        if (reverse) {
            moveSet = moveSet.reversed();
        }
        for (String move : moveSet) {
            target.push(move);
        }

    }

    private void parseStacks(String section) {
        String[] lines = section.split("\\R");
        int numStacks = lines[lines.length - 1].trim().split("\\s+").length;
        for (int i = 0; i < numStacks; i++) {
            stacks.add(new LinkedList<>());
        }
        for (int i = 0; i < lines.length - 1; i++) {
            for (int j = 0; j < numStacks; j++) {
                addToStack(j, lines[i]);
            }
        }
    }

    private void addToStack(int stackNum, String row) {
        int pos = stackNum * 4 + 1;
        if (row.length() > pos) {
            char c = row.charAt(pos);
            if (c != ' ') {
                stacks.get(stackNum).offer(c + "");
            }
        }
    }
}
