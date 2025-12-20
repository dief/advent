package com.leynmaster.advent.aoc2025.day10;

import com.leynmaster.advent.utils.input.NumberUtils;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Day10 {
//    private static final String INPUT = "../../inputs/2025/day10/test-1.txt";
    private static final String INPUT = "../../inputs/2025/day10/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final LinkedList<State> queue = new LinkedList<>();
    private final Set<int[]> seen = new HashSet<>();

    void main() throws IOException {
        List<Machine> machines =
                FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8).stream().map(Day10::parse).toList();
        logger.info("Starting");
        int total = 0;
        for (Machine machine : machines) {
            total += minPresses(machine);
        }
        logger.info("Part 1: {}", total);
        long solution = 0L;
        for (Machine machine : machines) {
            solution += new MachineSolver(machine.buttons(), machine.joltsTarget).solve();
        }
        logger.info("Part 2: {}", solution);
    }

    private int minPresses(Machine machine) {
        queue.clear();
        seen.clear();
        queue.offer(new State(new int[machine.target().length], 0));
        while (!queue.isEmpty()) {
            State state = queue.poll();
            seen.add(state.state());
            for (int[] button : machine.buttons()) {
                int presses = pressButton(machine, queue, state, button);
                if (presses > 0) {
                    return presses;
                }
            }
        }
        return -1;
    }

    private int pressButton(Machine machine, LinkedList<State> queue, State state, int[] button) {
        int[] newState = new int[state.state().length];
        System.arraycopy(state.state(), 0, newState, 0, state.state().length);
        for (int num : button) {
            newState[num]++;
        }
        if (matches(machine, newState)) {
            return state.presses() + 1;
        } else if (!seen.contains(newState)) {
            queue.offer(new State(newState, state.presses() + 1));
        }
        return -1;
    }

    private static boolean matches(Machine machine, int[] state) {
        for (int i = 0; i < machine.target().length; i++) {
            if (machine.target()[i] && state[i] % 2 == 0 || !machine.target()[i] && state[i] % 2 == 1) {
                return false;
            }
        }
        return true;
    }

    private static Machine parse(String line) {
        String[] components = line.replaceAll("[\\[\\](){}]", "").split("\\s+");
        return new Machine(parseTarget(components[0]), NumberUtils.parseInts(components[components.length - 1]),
                parseButtons(components));
    }

    private static boolean[] parseTarget(String targetStr) {
        boolean[] target = new boolean[targetStr.length()];
        for (int i = 0; i < targetStr.length(); i++) {
            target[i] = targetStr.charAt(i) == '#';
        }
        return target;
    }

    private static int[][] parseButtons(String[] components) {
        int numButtons = components.length - 2;
        int[][] buttons = new int[numButtons][];
        for (int i = 0; i < numButtons; i++) {
            buttons[i] = NumberUtils.parseInts(components[i + 1]);
        }
        Arrays.sort(buttons, Comparator.comparingInt(a -> a.length));
        return buttons;
    }

    private record State(int[] state, int presses) { }

    private record Machine(boolean[] target, int[] joltsTarget, int[][] buttons) { }
}
