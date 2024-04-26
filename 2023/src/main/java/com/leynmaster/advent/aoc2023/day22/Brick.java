package com.leynmaster.advent.aoc2023.day22;

import java.util.*;

public class Brick {
    private final int label;
    private final boolean vertical;
    private final Map<Integer, Brick> supportedBy = new HashMap<>();
    private final Map<Integer, Brick> supported = new HashMap<>();
    private List<BrickCoordinate> coordinates = new ArrayList<>();
    private boolean settled;

    public Brick(int label, String line) {
        this.label = label;
        String[] split = line.split("~");
        BrickCoordinate start = new BrickCoordinate(Arrays.stream(split[0].split(",")).map(Integer::parseInt).toList());
        BrickCoordinate end = new BrickCoordinate(Arrays.stream(split[1].split(",")).map(Integer::parseInt).toList());
        if (start.x() < end.x()) {
            fillX(start, end);
            vertical = false;
        } else if (start.y() < end.y()) {
            fillY(start, end);
            vertical = false;
        } else {
            fillZ(start, end);
            vertical = true;
        }
    }

    public int getLabel() {
        return label;
    }

    public boolean isVertical() {
        return vertical;
    }

    public int getLevel() {
        return getCoordinates().getFirst().z();
    }

    public Collection<Brick> getSupported() {
        return supported.values();
    }

    public Collection<Brick> getSupportedBy() {
        return supportedBy.values();
    }

    public List<BrickCoordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<BrickCoordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }

    public void addSupportedBy(Brick brick) {
        supportedBy.put(brick.getLabel(), brick);
    }

    public void addSupported(Brick brick) {
        supported.put(brick.getLabel(), brick);
    }

    public boolean canDisintegrate() {
        for (Brick check : supported.values()) {
            if (check.supportedBy.size() == 1) {
                return false;
            }
        }
        return true;
    }

    private void fillX(BrickCoordinate start, BrickCoordinate end) {
        int y = start.y();
        int z = start.z();
        for (int x = start.x(); x <= end.x(); x++) {
            coordinates.add(new BrickCoordinate(x, y, z));
        }
    }

    private void fillY(BrickCoordinate start, BrickCoordinate end) {
        int x = start.x();
        int z = start.z();
        for (int y = start.y(); y <= end.y(); y++) {
            coordinates.add(new BrickCoordinate(x, y, z));
        }
    }

    private void fillZ(BrickCoordinate start, BrickCoordinate end) {
        int x = start.x();
        int y = start.y();
        for (int z = start.z(); z <= end.z(); z++) {
            coordinates.add(new BrickCoordinate(x, y, z));
        }
    }
}
