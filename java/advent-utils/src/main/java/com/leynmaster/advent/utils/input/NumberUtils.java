package com.leynmaster.advent.utils.input;

public class NumberUtils {

    public static long[] parseLongs(String input) {
        String[] numStrings = input.trim().split("\\s+");
        long[] numbers = new long[numStrings.length];
        for (int i = 0; i < numStrings.length; i++) {
            numbers[i] = Long.parseLong(numStrings[i]);
        }
        return numbers;
    }
}
