package com.leynmaster.advent.aoc2024.day9;

public class DiskBlock {
    private final int index;
    int location;
    int length;

    public DiskBlock(int index, int location, int length) {
        this.index = index;
        this.location = location;
        this.length = length;
    }

    public int getIndex() {
        return index;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
