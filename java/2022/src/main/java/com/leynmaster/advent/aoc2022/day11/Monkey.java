package com.leynmaster.advent.aoc2022.day11;

import com.leynmaster.advent.utils.input.NumberUtils;

public class Monkey {
    private final long[] startItems;
    private final boolean multiply;
    private final boolean selfOp;
    private final long valueOp;
    private final long test;
    private final int nextMonkey1;
    private final int nextMonkey2;
    private boolean strong;
    private long supermod;

    Monkey(String section) {
        String[] lines = section.split("\\R");
        startItems = NumberUtils.parseLongs(lines[1].split(":")[1]);
        String[] operation = lines[2].trim().split("\\s+");
        multiply = "*".equals(operation[4]);
        if ("old".equals(operation[5])) {
            selfOp = true;
            valueOp = -1;
        } else {
            selfOp = false;
            valueOp = Long.parseLong(operation[5]);
        }
        test = Long.parseLong(lines[3].trim().split("\\s+")[3]);
        nextMonkey1 = Integer.parseInt(lines[4].trim().split("\\s+")[5]);
        nextMonkey2 = Integer.parseInt(lines[5].trim().split("\\s+")[5]);
    }

    long[] getStartItems() {
        return startItems;
    }

    public long getTest() {
        return test;
    }

    public void setStrong(boolean strong) {
        this.strong = strong;
    }

    public void setSupermod(long supermod) {
        this.supermod = supermod;
    }

    long nextWorryLevel(long level) {
        long operand = selfOp ? level : valueOp;
        long result = multiply ? level * operand : level + operand;
        return strong ? result % supermod : result / 3;
    }

    int nextMonkey(long level) {
        return level % test == 0 ? nextMonkey1 : nextMonkey2;
    }
}
