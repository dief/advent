package com.leynmaster.advent.aoc2023.day8;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Advent 2023 Day 8.
 *
 * @author David Charles Pollack
 */
public class Day8 {
//    private static final String INPUT_FILE = "../../inputs/2023/day8/test-1.txt";
//    private static final String INPUT_FILE = "../../inputs/2023/day8/test-2.txt";
//    private static final String INPUT_FILE = "../../inputs/2023/day8/test-3.txt";
    private static final String INPUT_FILE = "../../inputs/2023/day8/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Pattern nodeLine = Pattern.compile("^(\\S+)\\s*=\\s*\\(([^,]+), ([^)]+)\\)$");
    private final Map<String, Node> nodeMap = new HashMap<>();
    private char[] turns;

    void main() throws IOException {
        List<String> lines = FileUtils.readLines(new File(INPUT_FILE), StandardCharsets.UTF_8).stream()
                .filter(line -> !line.isBlank())
                .toList();
        turns = lines.getFirst().toCharArray();
        parseNodes(lines.subList(1, lines.size()));
        logger.info("Starting");
        logger.info("Part 1: {}", countSteps("AAA", false));
        logger.info("Part 2: {}", nodeMap.keySet().stream().filter(key -> key.endsWith("A"))
                .map(start -> countSteps(start, true))
                .reduce(1L, (a, b) -> a / gcd(a, b) * b));
    }

    private long countSteps(String start, boolean ghost) {
        String next = start;
        boolean searching = true;
        long steps = 0L;
        while (searching) {
            for (char turn : turns) {
                Node node = nodeMap.get(next);
                next = turn == 'L' ? node.left() : node.right();
            }
            steps += turns.length;
            searching = ghost ? !next.endsWith("Z") : !"ZZZ".equals(next);
        }
        return steps;
    }

    private void parseNodes(List<String> lines) {
        for (String line : lines) {
            Matcher matcher = nodeLine.matcher(line);
            if (matcher.matches()) {
                nodeMap.put(matcher.group(1), new Node(matcher.group(2), matcher.group(3)));
            }
        }
    }

    private static long gcd(long a, long b) {
        long num1 = a;
        long num2 = b;
        while (num1 != num2) {
            if (num1 > num2) {
                num1 -= num2;
            } else {
                num2 -= num1;
            }
        }
        return num1;
    }

    record Node(String left, String right) {}
}
