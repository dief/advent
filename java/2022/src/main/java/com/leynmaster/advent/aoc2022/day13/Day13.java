package com.leynmaster.advent.aoc2022.day13;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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
        List<Node> list = new ArrayList<>();
        LinkedList<List<Node>> listStack = new LinkedList<>();
        for (int i = 1; i < line.length() - 1; i++) {
            if (line.charAt(i) == '[') {
                listStack.push(list);
                list = new ArrayList<>();
            } else if (line.charAt(i) == ']') {
                Node node = new Node(list);
                list = listStack.pop();
                list.add(node);
            } else {
                i = addValue(list, line, i);
            }
        }
        return list;
    }

    private static int addValue(List<Node> list, String line, int start) {
        char c = line.charAt(start);
        int index = start;
        while (index < line.length() - 1 && c != ',' && c != ']') {
            c = line.charAt(++index);
        }
        if (index > start) {
            list.add(new Node(Integer.parseInt(line.substring(start, index))));
        }
        return c == ']' ? index - 1 : index;
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
