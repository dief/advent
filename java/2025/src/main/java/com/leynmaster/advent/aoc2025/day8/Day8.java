package com.leynmaster.advent.aoc2025.day8;

import com.leynmaster.advent.utils.input.NumberUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day8 {
//    private static final String INPUT = "../../inputs/2025/day8/test-1.txt";
    private static final String INPUT = "../../inputs/2025/day8/input.txt";
    private static final int LIMIT = 1000;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<JunctionBox> boxes = new ArrayList<>();
    private final List<BoxDistance> distances = new ArrayList<>();
    private final List<Set<JunctionBox>> circuits = new ArrayList<>();

    void main() throws IOException {
        for (String line : FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8)) {
            int[] split = NumberUtils.parseInts(line);
            boxes.add(new JunctionBox(split[0], split[1], split[2]));
        }
        for (int i = 0; i < boxes.size() - 1; i++) {
            for (int j = i + 1; j < boxes.size(); j++) {
                distances.add(new BoxDistance(boxes.get(i), boxes.get(j)));
            }
        }
        distances.sort(Comparator.comparingDouble(BoxDistance::getDistance));
        logger.info("Starting");
        logger.info("Part 1: {}", connectUntilLimit());
        circuits.clear();
        logger.info("Part 2: {}", connectUntilUnified());
    }

    private int connectUntilLimit() {
        for (BoxDistance distance : distances.subList(0, LIMIT)) {
            connect(distance.box1, distance.box2);
        }
        circuits.sort(Comparator.comparing(Set<JunctionBox>::size).reversed());
        return circuits.get(0).size() * circuits.get(1).size() * circuits.get(2).size();
    }

    private int connectUntilUnified() {
        for (BoxDistance distance : distances) {
            connect(distance.box1, distance.box2);
            if (circuits.size() == 1 && circuits.getFirst().size() == boxes.size()) {
                return distance.box1.x() * distance.box2.x();
            }
        }
        return -1;
    }

    private void connect(JunctionBox box1, JunctionBox box2) {
        int circuit1 = findCircuit(box1);
        int circuit2 = findCircuit(box2);
        if (circuit1 < 0 && circuit2 < 0) {
            Set<JunctionBox> set = new HashSet<>();
            set.add(box1);
            set.add(box2);
            circuits.add(set);
        } else if (circuit1 >= 0 && circuit2 >= 0 && circuit1 != circuit2) {
            circuits.get(circuit1).addAll(circuits.get(circuit2));
            circuits.remove(circuit2);
        } else if (circuit1 < 0) {
            circuits.get(circuit2).add(box1);
        } else {
            circuits.get(circuit1).add(box2);
        }
    }

    private int findCircuit(JunctionBox box) {
        for (int i = 0; i < circuits.size(); i++) {
            if (circuits.get(i).contains(box)) {
                return i;
            }
        }
        return -1;
    }

    private static class BoxDistance {
        private final JunctionBox box1;
        private final JunctionBox box2;
        private final double distance;

        BoxDistance(JunctionBox box1, JunctionBox box2) {
            this.box1 = box1;
            this.box2 = box2;
            distance = Math.sqrt(Math.pow(box2.x() - box1.x(), 2) + Math.pow(box2.y() - box1.y(), 2) +
                    Math.pow(box2.z() - box1.z(), 2));
        }

        double getDistance() {
            return distance;
        }
    }

    private record JunctionBox(int x, int y, int z) { }
}
