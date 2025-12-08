package com.leynmaster.advent.aoc2022.day13;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day13 {
//    private static final String INPUT = "../../inputs/2022/day13/test-1.txt";
    private static final String INPUT = "../../inputs/2022/day13/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private List<List<Node>> nodeLists;

    void main() throws IOException {
        nodeLists = new ArrayList<>(FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8).stream()
                .filter(line -> !line.isBlank()).map(Day13::parseList).toList());
        logger.info("Starting");
        logger.info("Part 1: {}", matchingPairs());
        logger.info("Part 2: {}", dividerRows());
    }

    private int matchingPairs() {
        int total = 0;
        for (int i = 0; i < nodeLists.size(); i += 2) {
            if (compare(nodeLists.get(i), nodeLists.get(i + 1)) <= 0) {
                total += i / 2 + 1;
            }
        }
        return total;
    }

    private int dividerRows() {
        List<Node> divider1 = Collections.singletonList(new Node(Collections.singletonList(new Node(2))));
        List<Node> divider2 = Collections.singletonList(new Node(Collections.singletonList(new Node(6))));
        nodeLists.add(divider1);
        nodeLists.add(divider2);
        nodeLists.sort(Day13::compare);
        int key = 1;
        for (int i = 0; i < nodeLists.size(); i++) {
            if (compare(nodeLists.get(i), divider1) == 0 || compare(nodeLists.get(i), divider2) == 0) {
                key *= i + 1;
            }
        }
        return key;
    }

    private static int compare(List<Node> left, List<Node> right) {
        for (int i = 0; i < left.size() && i < right.size(); i++) {
            int compare = compare(left.get(i), right.get(i));
            if (compare != 0) {
                return compare;
            }
        }
        return left.size() - right.size();
    }

    private static int compare(Node left, Node right) {
        if (left.list == null && right.list == null) {
            return left.value - right.value;
        }
        List<Node> leftList = left.list;
        List<Node> rightList = right.list;
        if (leftList == null) {
            leftList = Collections.singletonList(new Node(left.value));
        }
        if (rightList == null) {
            rightList = Collections.singletonList(new Node(right.value));
        }
        return compare(leftList, rightList);
    }

    private static List<Node> parseList(String line) {
        return parseList(line, 1, line.length() - 1);
    }

    private static List<Node> parseList(String line, int start, int end) {
        List<Node> nodes = new ArrayList<>();
        for (int i = start; i < end; i++) {
            if (line.charAt(i) == '[') {
                int listEnd = endList(line, i);
                nodes.add(new Node(parseList(line, i + 1, listEnd)));
                i = listEnd + 1;
            } else {
                int valueEnd = endValue(line, i);
                nodes.add(new Node(Integer.parseInt(line.substring(i, valueEnd))));
                i = valueEnd;
            }
        }
        return nodes;
    }

    private static int endList(String line, int start) {
        int count = 1;
        int index = start + 1;
        for (; index < line.length() && count > 0; index++) {
            if (line.charAt(index) == '[') {
                count++;
            } else if (line.charAt(index) == ']') {
                count--;
            }
        }
        return index - 1;
    }

    private static int endValue(String line, int start) {
        int index = start + 1;
        char c = line.charAt(index++);
        for (; index < line.length() && c != ',' && c != ']'; index++) {
            c = line.charAt(index);
        }
        return index - 1;
    }

    private static class Node {
        private int value;
        private List<Node> list;

        public Node(int value) {
            this.value = value;
        }

        public Node(List<Node> list) {
            this.list = list;
        }
    }
}
