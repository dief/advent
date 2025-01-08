package com.leynmaster.advent.aoc2024.day16;

import java.util.HashSet;
import java.util.Set;

public class Position {
    private final Set<Integer> scores = new HashSet<>();
    private int minScore = Integer.MAX_VALUE;

    public boolean containsScore(int score) {
        return scores.contains(score);
    }

    public boolean addScore(int score) {
        this.scores.add(score);
        if (score < minScore) {
            minScore = score;
            return true;
        }
        return false;
    }

    public int getMinScore() {
        return minScore;
    }
}
