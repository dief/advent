package com.leynmaster.advent.aoc2023.day14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day14 {
    private static final int LIMIT = 1_000_000_000;
//    private static final String INPUT_FILE = "../../inputs/2023/day14/test.txt";
    private static final String INPUT_FILE = "../../inputs/2023/day14/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<String, Score> seenMap = new HashMap<>();
    private final List<Score> scores = new ArrayList<>();

    public void run() throws IOException {
        ReflectorDish dish = createDish();
        dish.roll();
        logger.info("Part 1: {}", dish.score());
        dish = createDish();
        int cycleStart = -1;
        int cycleLength = -1;
        for (int i = 0; i < LIMIT; i++) {
            dish.cycle();
            String hash = dish.toString();
            Score score = seenMap.get(hash);
            if (score == null) {
                score = new Score(i, dish.score());
                seenMap.put(hash, score);
                scores.add(score);
            } else {
                cycleStart = score.iteration();
                cycleLength = i - cycleStart;
                break;
            }
        }
        int scoreLocation = (LIMIT - cycleStart) % cycleLength + cycleStart - 1;
        logger.info("Part 2: {}", scores.get(scoreLocation).score());

    }

    private ReflectorDish createDish() throws IOException {
        List<char[]> rows = new ArrayList<>();
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    rows.add(line.toCharArray());
                }
            }
        }
        return new ReflectorDish(rows.toArray(new char[0][]));
    }

    public static void main(String[] args) throws IOException {
        new Day14().run();
    }

    private record Score(int iteration, int score) { }
}
