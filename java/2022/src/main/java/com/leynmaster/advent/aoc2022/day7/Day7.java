package com.leynmaster.advent.aoc2022.day7;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class Day7 {
//    private static final String INPUT = "../../inputs/2022/day7/test-1.txt";
    private static final String INPUT = "../../inputs/2022/day7/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Directory root = new Directory();
    private Directory current;

    void main() throws IOException {
        logger.info("Starting");
        parseInput(FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8));
        root.computeSize();
        logger.info("Part 1: {}", partOne());
        logger.info("Part 2: {}", partTwo());
    }

    private int partOne() {
        int sum = 0;
        LinkedList<Directory> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Directory next = queue.poll();
            next.getChildDirectories().values().forEach(queue::offer);
            int size = next.getSize();
            if (size <= 100000) {
                sum += size;
            }
        }
        return sum;
    }

    private int partTwo() {
        int freeUp = root.getSize() - 40_000_000;
        int min = Integer.MAX_VALUE;
        LinkedList<Directory> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Directory next = queue.poll();
            next.getChildDirectories().values().forEach(queue::offer);
            int size = next.getSize();
            if (size > freeUp && size < min) {
                min = size;
            }
        }
        return min;
    }

    private void parseInput(List<String> lines) {
        for (String line : lines) {
            boolean output = line.startsWith("$");
            if (output) {
                processCommand(line);
            } else {
                processOutput(line);
            }
        }
    }

    private void processCommand(String instruction) {
        String[] split = instruction.split("\\s+");
        if ("cd".equals(split[1])) {
            String dirName = split[2];
            current = switch (dirName) {
                case "/" -> root;
                case ".." -> current.getParent();
                default -> current.getChildDirectories().get(dirName);
            };
        }
    }

    private void processOutput(String output) {
        String[] split = output.split("\\s+");
        if ("dir".equals(split[0])) {
            current.addDirectory(split[1]);
        } else {
            current.addFile(split[1], Integer.parseInt(split[0]));
        }
    }
}
