package com.leynmaster.advent.aoc2022.day16;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day16 {
//    private static final String INPUT = "../../inputs/2022/day16/test-1.txt";
    private static final String INPUT = "../../inputs/2022/day16/input.txt";
    private static final int INF = 1_000_000;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Map<String, Valve> valves = new HashMap<>();
    private final List<String> nodes = new ArrayList<>();
    private final List<String> flowNodes = new ArrayList<>();
    private final Map<Pair, Integer> distances = new HashMap<>();
    private final Map<Set<String>, Integer> results = new HashMap<>();

    void main() throws IOException {
        for (String line : FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8)) {
            String[] split = line.replaceAll("[=;,]", " ").split("\\s+");
            String name = split[1];
            int flow = Integer.parseInt(split[5]);
            Valve valve = new Valve(name, flow, new HashSet<>(Arrays.asList(split).subList(10, split.length)));
            valves.put(name, valve);
            nodes.add(name);
            if (flow > 0) {
                flowNodes.add(name);
            }
        }
        initDistances();
        for (String k : nodes) {
            shortenDistances(k);
        }
        logger.info("Starting");
        logger.info("Part 1: {}", maxPath(30, 0, valves.get("AA"), new HashSet<>()));
        results.clear();
        maxPath(26, 0, valves.get("AA"), new HashSet<>());
        logger.info("Part 2: {}", maxNonIntersecting());
    }

    private void initDistances() {
        for (String source : nodes) {
            for (String target : nodes) {
                if (source.equals(target)) {
                    distances.put(new Pair(source, target), 0);
                } else {
                    distances.put(new Pair(source, target), valves.get(source).next().contains(target) ? 1 : INF);
                }
            }
        }
    }

    private void shortenDistances(String intermediate) {
        for (String i : nodes) {
            for (String j : nodes) {
                Pair pair = new Pair(i, j);
                int newDistance = distances.get(new Pair(i, intermediate)) + distances.get(new Pair(intermediate, j));
                if (newDistance < distances.get(pair)) {
                    distances.put(pair, newDistance);
                }
            }
        }
    }

    private int maxPath(int minutes, int flow, Valve valve, Set<String> turnedOn) {
        int remaining = 0;
        for (String next : flowNodes) {
            int time = minutes - distances.get(new Pair(valve.name(), next)) - 1;
            if (!next.equals(valve.name()) && !turnedOn.contains(next) && time >= 0) {
                Set<String> nextOn = new HashSet<>(turnedOn);
                nextOn.add(next);
                int rate = time * valves.get(next).rate();
                int last = results.getOrDefault(nextOn, 0);
                results.put(nextOn, Math.max(last, flow + rate));
                remaining = Math.max(remaining, rate + maxPath(time, flow + rate, valves.get(next), nextOn));
            }
        }
        return remaining;
    }

    private int maxNonIntersecting() {
        int max = 0;
        List<Map.Entry<Set<String>, Integer>> list = new ArrayList<>(results.entrySet());
        list.sort((e1, e2) -> e2.getValue() - e1.getValue());
        for (Map.Entry<Set<String>, Integer> e1 : list) {
            for (Map.Entry<Set<String>, Integer> e2 : list) {
                if (nonIntersecting(e1.getKey(), e2.getKey())) {
                    max = Math.max(max, e1.getValue() + e2.getValue());
                    break;
                }
            }
        }
        return max;
    }

    private static boolean nonIntersecting(Set<String> s1, Set<String> s2) {
        for (String node : s1) {
            if (s2.contains(node)) {
                return false;
            }
        }
        return true;
    }

    private record Valve(String name, int rate, Set<String> next) { }

    private record Pair(String source, String target) { }
}
