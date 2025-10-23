package com.leynmaster.advent.aoc2023.day7;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

class Hand {
    private static final Map<Character, Integer> valueMap = new HashMap<>();
    private final char[] cards;
    private final int bid;
    private final int score;
    private final int jokerScore;

    static {
        valueMap.put('2', 2);
        valueMap.put('3', 3);
        valueMap.put('4', 4);
        valueMap.put('5', 5);
        valueMap.put('6', 6);
        valueMap.put('7', 7);
        valueMap.put('8', 8);
        valueMap.put('9', 9);
        valueMap.put('T', 10);
        valueMap.put('J', 11);
        valueMap.put('Q', 12);
        valueMap.put('K', 13);
        valueMap.put('A', 14);
    }

    Hand(String cardString, int bid) {
        this.cards = cardString.toCharArray();
        this.bid = bid;
        int[] cardCount = new int[15];
        Arrays.fill(cardCount, 0);
        for (char card : cards) {
            cardCount[cardValue(card, false)]++;
        }
        this.score = computeBaseScore(cardCount);
        this.jokerScore = switch(cardCount[11]) {
            case 4 -> score + 1;
            case 3 -> score + 2;
            case 2 -> score == 4 ? 6 : score == 2 ? 5 : 3;
            case 1 -> score > 0 && score < 4 ? score + 2 : score + 1;
            default -> score;
        };
    }

    public int getBid() {
        return bid;
    }

    public int getScore() {
        return score;
    }

    public int getJokerScore() {
        return jokerScore;
    }

    static Comparator<Hand> comparator(boolean jokers) {
        return Comparator.comparing(jokers ? Hand::getJokerScore : Hand::getScore).thenComparing((hand1, hand2) -> {
            for (int i = 0; i < 5; i++) {
                if (hand1.cards[i] != hand2.cards[i]) {
                    return cardValue(hand1.cards[i], jokers) - cardValue(hand2.cards[i], jokers);
                }
            }
            return 0;
        });
    }

    private static int computeBaseScore(int[] cardCount) {
        if (findInstances(cardCount, 5) > 0) {
            return 6;
        }
        if (findInstances(cardCount, 4) > 0) {
            return 5;
        }
        if (findInstances(cardCount, 3) > 0) {
            return findInstances(cardCount, 2) > 0 ? 4 : 3;
        }
        return findInstances(cardCount, 2);
    }

    private static int findInstances(int[] cardCount, int amount) {
        int instances = 0;
        for (int count : cardCount) {
            if (count == amount) {
                instances++;
            }
        }
        return instances;
    }

    private static int cardValue(char card, boolean joker) {
        return joker && card == 'J' ? 1 : valueMap.get(card);
    }
}
