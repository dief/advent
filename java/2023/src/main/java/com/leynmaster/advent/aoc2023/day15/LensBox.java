package com.leynmaster.advent.aoc2023.day15;

import java.util.*;

public class LensBox {
    private final Map<String, Lens> labels = new HashMap<>();
    private final LinkedList<Lens> lenses = new LinkedList<>();
    private final int boxNum;

    public LensBox(int boxNum) {
        this.boxNum = boxNum;
    }

    public int power() {
        int power = 0;
        int boxPosition = boxNum + 1;
        for (int i = 0; i < lenses.size(); i++) {
            power += boxPosition * (i + 1) * lenses.get(i).getLength();
        }
        return power;
    }

    public void setLens(String label, int length) {
        Lens lens = labels.get(label);
        if (lens == null) {
            lens = new Lens(label, length);
            lenses.addLast(lens);
            labels.put(label, lens);
        } else {
            lens.setLength(length);
        }
    }

    public void removeLens(String label) {
        Lens lens = labels.get(label);
        if (lens != null) {
            lenses.remove(lens);
            labels.remove(label);
        }
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append("Box ").append(boxNum).append(':');
        for (Lens lens : lenses) {
            buf.append(' ').append(lens);
        }
        return buf.toString();
    }
}

class Lens {
    private final String label;
    private int length;

    public Lens(String label, int length) {
        this.label = label;
        this.length = length;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return String.format("[%s %s]", label, length);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Lens lens) {
            return length == lens.length && Objects.equals(label, lens.label);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, length);
    }
}
