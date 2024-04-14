package com.leynmaster.advent.aoc2023.day19;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Part {
    private static final Pattern PART = Pattern.compile("^\\{x=(\\d+),m=(\\d+),a=(\\d+),s=(\\d+)}$");
    private final int x;
    private final int m;
    private final int a;
    private final int s;

    Part(String str) {
        Matcher matcher = PART.matcher(str);
        if (!matcher.matches()) {
            throw new IllegalArgumentException();
        }
        x = Integer.parseInt(matcher.group(1));
        m = Integer.parseInt(matcher.group(2));
        a = Integer.parseInt(matcher.group(3));
        s = Integer.parseInt(matcher.group(4));
    }

    int rating() {
        return x + m + a + s;
    }

    int getX() {
        return x;
    }

    int getM() {
        return m;
    }

    int getA() {
        return a;
    }

    int getS() {
        return s;
    }
}
