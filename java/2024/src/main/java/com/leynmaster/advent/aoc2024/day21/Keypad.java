package com.leynmaster.advent.aoc2024.day21;

import com.leynmaster.advent.utils.map.Direction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Keypad {
    private final Map<Character, Map<Character, List<RobotAction>>> keypadPaths;
    private final Map<Character, Map<Character, List<RobotAction>>> directionPaths;

    public Keypad() {
        keypadPaths = pathMap(new char[][] {
            {'7', '8', '9'},
            {'4', '5', '6'},
            {'1', '2', '3'},
            {' ', '0', 'A'}
        }, 3);
        directionPaths = pathMap(new char[][] {
            {' ', '^', 'A'},
            {'<', 'v', '>'}
        }, 0);
    }

    public List<RobotAction> getNumericPath(String code) {
        return getPath(code, keypadPaths);
    }

    public List<RobotAction> getDirectionPath(Character source, Character target) {
        return directionPaths.get(source).get(target);
    }

    private static List<RobotAction> getPath(String code, Map<Character, Map<Character, List<RobotAction>>> pathMap) {
        char[] chars = code.toCharArray();
        char from = 'A';
        List<RobotAction> actions = new ArrayList<>();
        for (char next : chars) {
            actions.addAll(pathMap.get(from).get(next));
            actions.add(new RobotAction());
            from = next;
        }
        return actions;
    }

    private static Map<Character, Map<Character, List<RobotAction>>> pathMap(char[][] pad, int spaceRow) {
        Map<Character, Map<Character, List<RobotAction>>> paths = new HashMap<>();
        for (int y = 0; y < pad.length; y++) {
            for (int x = 0; x < pad[0].length; x++) {
                paths.put(pad[y][x], computePaths(pad, x, y, spaceRow));
            }
        }
        return paths;
    }

    private static Map<Character, List<RobotAction>> computePaths(char[][] pad, int startX, int startY, int spaceRow) {
        int height = pad.length;
        int width = pad[0].length;
        Map<Character, List<RobotAction>> paths = new HashMap<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                paths.put(pad[y][x], computePath(startX, startY, x, y, spaceRow));
            }
        }
        return paths;
    }

    private static List<RobotAction> computePath(int startX, int startY, int x, int y, int spaceRow) {
        List<RobotAction> actions = new ArrayList<>();
        int dx = x - startX;
        int dy = y - startY;
        if (dx < 0) {
            actions.addAll(actions(new RobotAction(Direction.LEFT), -dx));
        }
        if (dy > 0) {
            actions.addAll(actions(new RobotAction(Direction.DOWN), dy));
        } else {
            actions.addAll(actions(new RobotAction(Direction.UP), -dy));
        }
        if (dx > 0) {
            actions.addAll(actions(new RobotAction(Direction.RIGHT), dx));
        }
        if (startY == spaceRow && x == 0 || y == spaceRow && startX == 0) {
            actions = actions.reversed();
        }
        actions.add(new RobotAction());
        return actions;
    }

    private static List<RobotAction> actions(RobotAction action, int count) {
        List<RobotAction> actions = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            actions.add(action);
        }
        return actions;
    }
}
