package com.leynmaster.advent.aoc2023.day25;

import java.util.*;

public class ComponentGraph {
    private final Map<String, Map<String, Integer>> connectionMap;
    private final Map<String, List<String>> mergeMap;

    public ComponentGraph(Map<String, Map<String, Integer>> connectionMap) {
        this(connectionMap, initMergeMap(connectionMap.keySet()));
    }

    private ComponentGraph(Map<String, Map<String, Integer>> connectionMap, Map<String, List<String>> mergeMap) {
        this.connectionMap = connectionMap;
        this.mergeMap = mergeMap;
    }

    public ComponentGraph copy() {
        Map<String, Map<String, Integer>> copyConnections = new HashMap<>();
        Map<String, List<String>> copyMerges = new HashMap<>();
        for (String vertex : connectionMap.keySet()) {
            copyConnections.put(vertex, new HashMap<>(connectionMap.get(vertex)));
            copyMerges.put(vertex, new ArrayList<>(mergeMap.get(vertex)));
        }
        return new ComponentGraph(copyConnections, copyMerges);
    }

    public int size() {
        return connectionMap.size();
    }

    public int getMergeCount(String vertex) {
        return mergeMap.get(vertex).size();
    }

    public Map<String, Integer> getEdges(String vertex) {
        return connectionMap.get(vertex);
    }

    public Set<String> getVertices() {
        return connectionMap.keySet();
    }

    public void merge(String source, String target) {
        Map<String, Integer> sourceEdges = getEdges(source);
        Map<String, Integer> targetEdges = getEdges(target);
        mergeMap.get(source).addAll(mergeMap.get(target));
        targetEdges.remove(source);
        for (Map.Entry<String, Integer> edge : targetEdges.entrySet()) {
            String key = edge.getKey();
            Integer newWeight = sourceEdges.getOrDefault(key, 0) + edge.getValue();
            sourceEdges.put(key, newWeight);
            connectionMap.get(key).remove(target);
            connectionMap.get(key).put(source, newWeight);
        }
        connectionMap.remove(target);
        sourceEdges.remove(target);
    }

    private static Map<String, List<String>> initMergeMap(Set<String> vertices) {
        Map<String, List<String>> mergeMap = new HashMap<>();
        for (String vertex : vertices) {
            List<String> init = new ArrayList<>();
            init.add(vertex);
            mergeMap.put(vertex, init);
        }
        return mergeMap;
    }
}
