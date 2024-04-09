package com.leynmaster.advent.aoc2023.day15;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day15 {
    private static final String INPUT_FILE = "inputs/day15/input.txt";
    private static final int BOX_NUM = 256;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final LensBox[] boxes = new LensBox[BOX_NUM];
    private final String[] inputs;

    public Day15(String[] inputs) {
        this.inputs = inputs;
        for (int i = 0; i < BOX_NUM; i++) {
            boxes[i] = new LensBox(i);
        }
    }

    public void run() {
        logger.info("Part 1: {}", part1());
        logger.info("Part 2: {}", part2());
    }

    private int part2() {
        for (String input : inputs) {
            if (input.endsWith("-")) {
                String label = input.substring(0, input.length() - 1);
                boxes[hash(label)].removeLens(label);
            } else {
                String[] inputPair = input.split("=");
                boxes[hash(inputPair[0])].setLens(inputPair[0], Integer.parseInt(inputPair[1]));
            }
        }
        int power = 0;
        for (LensBox box : boxes) {
            power += box.power();
        }
        return power;
    }

    private int part1() {
        int totalHash = 0;
        for (String input : inputs) {
            totalHash += hash(input);
        }
        return totalHash;
    }

    private int hash(String input) {
        int hash = 0;
        for (char c : input.toCharArray()) {
            hash = (hash + c) * 17 % 256;
        }
        return hash;
    }

    public static void main(String[] args) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            new Day15(reader.readLine().split(",")).run();
        }
    }
}
