package com.leynmaster.advent.aoc2024.day12;

import com.leynmaster.advent.utils.map.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Advent 2024 Day 12.
 *
 * @author David Charles Pollack
 */
public class Day12 {
//    private static final String INPUT_FILE = "../../inputs/2024/day12/test-5.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day12/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<Region> regions = new ArrayList<>();
    private final char[][] map;
    private final Region[][] regionMap;
    private final int height;
    private final int width;

    public Day12(char[][] map) {
        this.map = map;
        this.height = map.length;
        this.width = map[0].length;
        regionMap = new Region[height][width];
    }

    public void run() {
        fillRegions();
        logger.info("Part 1: {}", regions.stream().map(Region::price).reduce(0, Integer::sum));
        logger.info("Part 2: {}", regions.stream().map(Region::bulkPrice).reduce(0, Integer::sum));
    }

    private void fillRegions() {
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (regionMap[x][y] == null) {
                    fillRegion(x, y);
                }
            }
        }
    }

    private void fillRegion(int startX, int startY) {
        Region region = new Region();
        regionMap[startX][startY] = region;
        char plot = map[startX][startY];
        LinkedList<Coordinate> queue = new LinkedList<>();
        queue.addLast(new Coordinate(startX, startY));
        while (!queue.isEmpty()) {
            Coordinate next = queue.removeLast();
            int x = next.x();
            int y = next.y();
            boolean top = fillNext(queue, region, plot, x - 1, y);
            boolean bottom = fillNext(queue, region, plot, x + 1, y);
            boolean left = fillNext(queue, region, plot, x, y - 1);
            boolean right = fillNext(queue, region, plot, x, y + 1);
            int corners = 0;
            corners += innerCorner(top, left);
            corners += innerCorner(top, right);
            corners += innerCorner(bottom, left);
            corners += innerCorner(bottom, right);
            corners += outerCorner(plot, x, y, -1, 1);
            corners += outerCorner(plot, x, y, -1, -1);
            corners += outerCorner(plot, x, y, 1, 1);
            corners += outerCorner(plot, x, y, 1, -1);
            region.addCoordinate(new Coordinate(x, y), corners,
                    bound(top) + bound(bottom) + bound(left) + bound(right));
        }
        regions.add(region);
    }

    private boolean fillNext(LinkedList<Coordinate> queue, Region region, char plot, int x, int y) {
        if (x >= 0 && x < height && y >= 0 && y < width && map[x][y] == plot) {
            if (regionMap[x][y] == null) {
                regionMap[x][y] = region;
                queue.addLast(new Coordinate(x, y));
            }
            return false;
        }
        return true;
    }

    private int outerCorner(char plot, int x, int y, int dX, int dY) {
        int nX = x + dX;
        int nY = y + dY;
        return nX >= 0 && nX < height && nY >= 0 && nY < width && plot == map[x][nY] && plot == map[nX][y]
                && plot != map[nX][nY] ? 1 : 0;
    }

    public static void main() throws IOException {
        List<char[]> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line.toCharArray());
            }
        }
        Day12 solution = new Day12(list.toArray(new char[list.size()][]));
        solution.run();
    }

    private static int bound(boolean bool) {
        return bool ? 1 : 0;
    }

    private static int innerCorner(boolean side1, boolean side2) {
        return side1 && side2 ? 1 : 0;
    }
}
