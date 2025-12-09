package com.leynmaster.advent.aoc2025.day9;

import com.leynmaster.advent.utils.input.NumberUtils;
import com.leynmaster.advent.utils.map.Coordinate;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day9 {
//    private static final String INPUT = "../../inputs/2025/day9/test-1.txt";
    private static final String INPUT = "../../inputs/2025/day9/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final List<TileArea> areas = new ArrayList<>();
    private final List<Coordinate> redGreenTiles = new ArrayList<>();

    void main() throws IOException {
        List<Coordinate> coordinateList =
                FileUtils.readLines(new File(INPUT), StandardCharsets.UTF_8).stream().map(Day9::parse).toList();
        logger.info("Starting");
        for (int i = 0; i < coordinateList.size() - 1; i++) {
            addTiles(coordinateList.get(i), coordinateList.get(i + 1));
            for (int j = i + 1; j < coordinateList.size(); j++) {
                areas.add(new TileArea(coordinateList.get(i), coordinateList.get(j)));
            }
        }
        areas.sort(Comparator.comparingLong(TileArea::area).reversed());
        logger.info("Part 1: {}", areas.getFirst().area());
        logger.info("Part 2: {}", largestEnclosed());

    }

    private long largestEnclosed() {
        for (TileArea shape : areas) {
            if (enclosed(shape)) {
                return shape.area();
            }
        }
        return -1;
    }

    private boolean enclosed(TileArea shape) {
        int xStart = Math.min(shape.c1().x(), shape.c2().x());
        int xEnd = Math.max(shape.c1().x(), shape.c2().x());
        int yStart = Math.min(shape.c1().y(), shape.c2().y());
        int yEnd = Math.max(shape.c1().y(), shape.c2().y());
        for (Coordinate coordinate : redGreenTiles) {
            if (coordinate.x() > xStart && coordinate.x() < xEnd && coordinate.y() > yStart && coordinate.y() < yEnd) {
                return false;
            }
        }
        return true;
    }

    private void addTiles(Coordinate c1, Coordinate c2) {
        if (c1.x() == c2.x()) {
            for (int i = Math.min(c1.y(), c2.y()); i <= Math.max(c1.y(), c2.y()); i++) {
                redGreenTiles.add(new Coordinate(c1.x(), i));
            }
        } else {
            for (int i = Math.min(c1.x(), c2.x()); i <= Math.max(c1.x(), c2.x()); i++) {
                redGreenTiles.add(new Coordinate(i, c1.y()));
            }
        }
    }

    private static Coordinate parse(String line) {
        int[] numbers = NumberUtils.parseInts(line);
        return new Coordinate(numbers[0], numbers[1]);
    }

    private record TileArea(Coordinate c1, Coordinate c2, long area) {
        TileArea(Coordinate c1, Coordinate c2) {
            this(c1, c2, (Math.abs(c2.x() - c1.x()) + 1L) * (Math.abs(c2.y() - c1.y()) + 1L));
        }
    }
}
