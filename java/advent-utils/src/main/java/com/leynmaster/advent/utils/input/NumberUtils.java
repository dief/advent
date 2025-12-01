package com.leynmaster.advent.utils.input;

public class NumberUtils {
    public static int[] parseInts(String input) {
        String[] numStrings = input.trim().split("(\\s|[^-\\d])+");
        int[] numbers = new int[numStrings.length];
        for (int i = 0; i < numStrings.length; i++) {
            numbers[i] = Integer.parseInt(numStrings[i]);
        }
        return numbers;
    }

    public static long[] parseLongs(String input) {
        String[] numStrings = input.trim().split("(\\s|[^-\\d])+");
        long[] numbers = new long[numStrings.length];
        for (int i = 0; i < numStrings.length; i++) {
            numbers[i] = Long.parseLong(numStrings[i]);
        }
        return numbers;
    }

    private NumberUtils() { }
}
