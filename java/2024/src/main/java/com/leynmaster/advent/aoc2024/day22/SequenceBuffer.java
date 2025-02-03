package com.leynmaster.advent.aoc2024.day22;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SequenceBuffer {
    private static final int BUFFER_SIZE = 4;
    private final LinkedList<Integer> buffer = new LinkedList<>();

    public void add(int value) {
        buffer.offer(value);
        if (buffer.size() > BUFFER_SIZE) {
            buffer.poll();
        }
    }

    public boolean isFull() {
        return buffer.size() == BUFFER_SIZE;
    }

    public List<Integer> getSequence() {
        return new ArrayList<>(buffer);
    }
}
