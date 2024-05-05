package com.leynmaster.advent.aoc2023.day25;

import java.util.*;

public class SWMinimumCut {
    private final ComponentGraph graph;
    private final String start;

    public SWMinimumCut(Map<String, Map<String, Integer>> connectionMap) {
        this.graph = new ComponentGraph(connectionMap);
        this.start = connectionMap.keySet().iterator().next();
    }

    public int minimumCut() {
        ComponentGraph workGraph = graph.copy();
        int bestWeight = Integer.MAX_VALUE;
        String lastMerged = null;
        while (workGraph.size() > 1 && bestWeight > 3) {
            PhaseCut cut = maximumAdjacency(workGraph);
            int weight = cut.weight();
            if (weight < bestWeight) {
                bestWeight = weight;
            }
            lastMerged = cut.target();
            workGraph.merge(cut.source(), cut.target());
            if (bestWeight == 3) {
                break;
            }
        }
        int partitionSize = workGraph.getMergeCount(lastMerged);
        return partitionSize * (graph.size() - partitionSize);
    }

    private PhaseCut maximumAdjacency(ComponentGraph workGraph) {
        List<String> found = new ArrayList<>();
        List<Integer> weights = new ArrayList<>();
        found.add(start);
        Map<String, Integer> edges = workGraph.getEdges(start);
        PriorityQueue<WeightSum> queue = new PriorityQueue<>(Comparator.comparingInt(WeightSum::getSum).reversed());
        for (String candidate : workGraph.getVertices()) {
            if (!candidate.equals(start)) {
                queue.offer(new WeightSum(candidate, edges.getOrDefault(candidate, 0)));
            }
        }
        while (!queue.isEmpty()) {
            WeightSum current = queue.poll();
            found.add(current.vertex);
            weights.add(current.sum);
            edges = workGraph.getEdges(current.vertex);
            for (WeightSum candidate : new ArrayList<>(queue)) {
                if (edges.containsKey(candidate.vertex)) {
                    queue.remove(candidate);
                    candidate.add(edges.get(candidate.vertex));
                    queue.offer(candidate);
                }
            }
        }
        int size = found.size();
        return new PhaseCut(found.get(size - 2), found.get(size - 1), weights.getLast());
    }

    private record PhaseCut(String source, String target, int weight) { }

    private static class WeightSum implements Comparable<WeightSum> {
        private final String vertex;
        private int sum;

        public WeightSum(String vertex, int sum) {
            this.vertex = vertex;
            this.sum = sum;
        }

        public void add(int add) {
            sum += add;
        }

        public int getSum() {
            return sum;
        }

        @Override
        public int compareTo(WeightSum o) {
            return sum - o.sum;
        }
    }
}
