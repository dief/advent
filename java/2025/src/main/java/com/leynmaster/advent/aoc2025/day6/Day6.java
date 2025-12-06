package com.leynmaster.advent.aoc2025.day6;

import com.leynmaster.advent.utils.input.NumberUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Day6 {
//    private static final String INPUT = "../../inputs/2025/day6/test-1.txt";
    private static final String INPUT = "../../inputs/2025/day6/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private List<String> lines;
    private char[] operators;

    void main() throws IOException {
        lines = FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8);
        operators = lines.getLast().replaceAll("\\s", "").toCharArray();
        logger.info("Starting");
        logger.info("Part 1: {}", verticalNumbers());
        logger.info("Part 2: {}", columnNumbers());
    }

    private long verticalNumbers() {
        List<int[]> numbers = new ArrayList<>();
        List<int[]> rows = lines.subList(0, lines.size() - 1).stream().map(NumberUtils::parseInts).toList();
        for (int j = 0; j < operators.length; j++) {
            int[] problem = new int[lines.size() - 1];
            for (int i = 0; i < lines.size() - 1; i++) {
                problem[i] = rows.get(i)[j];
            }
            numbers.add(problem);
        }
        return total(numbers);
    }

    private long columnNumbers() {
        List<int[]> numbers = new ArrayList<>();
        String operatorLine = lines.getLast();
        for (int i = 0; i < operatorLine.length(); i++) {
            if (isOperator(operatorLine.charAt(i))) {
                int end = problemEnd(operatorLine, i);
                numbers.add(columnNumbers(i, end));
                i = end;
            }
        }
        return total(numbers);
    }

    private int[] columnNumbers(int start, int end) {
        int[] numbers = new int[end - start];
        for (int j = start; j < end; j++) {
            numbers[j - start] = 0;
            int factor = 1;
            for (int i = lines.size() - 1; i >= 0; i--) {
                char c = lines.get(i).charAt(j);
                if (Character.isDigit(c)) {
                    numbers[j - start] += (c - '0') * factor;
                    factor *= 10;
                }
            }
        }
        return numbers;
    }

    private long total(List<int[]> numbers) {
        long total = 0;
        for (int i = 0; i < operators.length; i++) {
            total += operators[i] == '+' ? add(numbers.get(i)) : multiply(numbers.get(i));
        }
        return total;
    }

    private static long add(int[] numbers) {
        long sum = 0;
        for (int num : numbers) {
            sum += num;
        }
        return sum;
    }

    private static long multiply(int[] numbers) {
        long product = 1;
        for (int num : numbers) {
            product *= num;
        }
        return product;
    }

    private static int problemEnd(String line, int start) {
        for (int i = start + 1; i < line.length(); i++) {
            if (isOperator(line.charAt(i))) {
                return i - 1;
            }
        }
        return line.length();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '*';
    }
}
