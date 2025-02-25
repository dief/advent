package com.leynmaster.advent.aoc2024.day23;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Advent 2024 Day 23.
 *
 * @author David Charles Pollack
 */
public class Day23 {
//    private static final String INPUT_FILE = "../../inputs/2024/day23/test-1.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day23/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<String, Set<String>> nodes = new HashMap<>();

    void main() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("-");
                addNode(parts[0], parts[1]);
                addNode(parts[1], parts[0]);
            }
        }
        logger.info("Starting");
        logger.info("Part 1: {}", partOne());
        logger.info("Part 2: {}", String.join(",", partTwo()));
    }

    private int partOne() {
        Set<Set<String>> parties = new HashSet<>();
        for (String node : nodes.keySet().stream().filter(n -> n.charAt(0) == 't').toList()) {
            parties.addAll(getTrioParties(node));
        }
        return parties.size();
    }

    private List<String> partTwo() {
        List<List<String>> parties = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : nodes.entrySet()) {
            for (List<String> subset : getAllSubsets(new ArrayList<>(entry.getValue()))) {
                if (isAllConnected(subset)) {
                    List<String> party = new ArrayList<>(subset);
                    party.add(entry.getKey());
                    parties.add(party.stream().sorted().toList());
                }
            }
        }
        return findLargest(parties);
    }

    private Set<Set<String>> getTrioParties(String node) {
        Set<Set<String>> parties = new HashSet<>();
        List<String> children = new ArrayList<>(nodes.get(node));
        for (int i = 0; i < children.size() - 1; i++) {
            for (int j = i + 1; j < children.size(); j++) {
                String child1 = children.get(i);
                String child2 = children.get(j);
                if (nodes.get(child1).contains(child2)) {
                    Set<String> party = new HashSet<>();
                    party.add(node);
                    party.add(child1);
                    party.add(child2);
                    parties.add(party);
                }
            }
        }
        return parties;
    }

    private List<List<String>> getAllSubsets(List<String> nodes) {
        List<List<String>> subsets = new ArrayList<>();
        if (nodes.isEmpty()) {
            subsets.add(Collections.emptyList());
            return subsets;
        }
        List<List<String>> nextSubsets = getAllSubsets(nodes.subList(1, nodes.size()));
        for (List<String> subset : nextSubsets) {
            List<String> newSubset = new ArrayList<>(subset);
            newSubset.add(nodes.getFirst());
            subsets.add(newSubset);
            subsets.add(subset);
        }
        return subsets;
    }

    private boolean isAllConnected(List<String> set) {
        for (int i = 0; i < set.size(); i++) {
            for (int j = i + 1; j < set.size(); j++) {
                if (!nodes.get(set.get(i)).contains(set.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    private List<String> findLargest(List<List<String>> parties) {
        List<String> largest = null;
        int largestSize = 0;
        for (List<String> party : parties) {
            int size = party.size();
            if (size > largestSize) {
                largest = party;
                largestSize = size;
            }
        }
        return largest;
    }

    private void addNode(String node, String child) {
        nodes.computeIfAbsent(node, _ -> new HashSet<>()).add(child);
    }
}
