package com.leynmaster.advent.aoc2023.day25;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class KargerSteinMinimumCut {
    private static final int MIN_GRAPH = 2;
    private static final int TARGET_SPLIT = 3;
    private final Random random = new Random();
    private final ComponentGraph graph;

    public KargerSteinMinimumCut(Map<String, Map<String, Integer>> connectionMap) {
        this.graph = new ComponentGraph(connectionMap);
    }

    public int minimumCut() {
        int edges = 0;
        int partitionSize = 0;
        while (edges != TARGET_SPLIT) {
            ComponentGraph workGraph = graph.copy();
            while (workGraph.size() > MIN_GRAPH) {
                String source = new ArrayList<>(workGraph.getVertices()).get(random.nextInt(workGraph.size()));
                List<String> targets = new ArrayList<>(workGraph.getEdges(source).keySet());
                String target = targets.get(random.nextInt(targets.size()));
                workGraph.merge(source, target);
            }
            String vertex = workGraph.getVertices().iterator().next();
            partitionSize = workGraph.getMergeCount(vertex);
            edges = workGraph.getEdges(vertex).values().iterator().next();
        }
        return partitionSize * (graph.size() - partitionSize);
    }
}
