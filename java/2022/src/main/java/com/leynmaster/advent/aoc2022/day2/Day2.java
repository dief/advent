package com.leynmaster.advent.aoc2022.day2;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class Day2 {
//    private static final String INPUT = "../../inputs/2022/day2/test-1.txt";
    private static final String INPUT = "../../inputs/2022/day2/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<Turn, Turn> winMap = Map.of(
            Turn.ROCK, Turn.SCISSORS,
            Turn.SCISSORS, Turn.PAPER,
            Turn.PAPER, Turn.ROCK);
    private final Map<Turn, Turn> lossMap = Map.of(
            Turn.ROCK, Turn.PAPER,
            Turn.SCISSORS, Turn.ROCK,
            Turn.PAPER, Turn.SCISSORS);

    void main() throws IOException {
        logger.info("Starting");
        List<Round> rounds = FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8).stream()
                .map(line -> line.split(" "))
                .map(split -> new Round(turn(split[0]), split[1]))
                .toList();
        logger.info("Part 1: {}", rounds.stream().map(this::partOne).reduce(0, Integer::sum));
        logger.info("Part 2: {}", rounds.stream().map(this::partTwo).reduce(0, Integer::sum));
    }

    private int partOne(Round round) {
        return score(turn(round.outcome()), round.opponent());
    }

    private int partTwo(Round round) {
        Turn opponent = round.opponent();
        String outcome = round.outcome();
        return score(switch (outcome) {
            case "X" -> winMap.get(opponent);
            case "Y" -> opponent;
            case "Z" -> lossMap.get(opponent);
            default -> throw new IllegalArgumentException(outcome);
        }, opponent);
    }

    private int score(Turn you, Turn opponent) {
        return you.ordinal() + 1 + (you == opponent ? 3 : opponent == winMap.get(you) ? 6 : 0);
    }

    private static Turn turn(String input) {
        return switch (input) {
            case "A", "X" -> Turn.ROCK;
            case "B", "Y" -> Turn.PAPER;
            case "C", "Z" -> Turn.SCISSORS;
            default -> throw new IllegalArgumentException(input);
        };
    }

    private enum Turn {
        ROCK, PAPER, SCISSORS
    }

    private record Round(Turn opponent, String outcome) {}
}
