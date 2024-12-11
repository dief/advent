package com.leynmaster.advent.aoc2024.day9;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Advent 2024 Day 9.
 *
 * @author David Charles Pollack
 */
public class Day9 {
//    private static final String INPUT_FILE = "../../inputs/2024/day9/test-2.txt";
    private static final String INPUT_FILE = "../../inputs/2024/day9/input.txt";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final Disk disk;

    public Day9(Disk disk) {
        this.disk = disk;
    }

    public void run() {
        logger.info("Part 1: {}", checksum(blockDefrag()));
        disk.defragment();
        logger.info("Part 2: {}", checksum(disk.getDiskArray()));
    }

    private long checksum(List<Integer> disk) {
        long total = 0L;
        for (int i = 0; i < disk.size(); i++) {
            int file = disk.get(i);
            if (file >= 0) {
                total += (long)i * file;
            }
        }
        return total;
    }

    private List<Integer> blockDefrag() {
        List<Integer> defrag = new ArrayList<>(disk.getDiskArray());
        int freePointer = nextFree(defrag, -1);
        int loc = defrag.size() - 1;
        while (freePointer < loc) {
            defrag.set(freePointer, defrag.get(loc));
            defrag.set(loc, -1);
            freePointer = nextFree(defrag, freePointer);
            loc = nextLocation(defrag, loc);
        }
        return defrag;
    }

    public static void main() throws IOException {
        Disk disk;
        try (BufferedReader reader = new BufferedReader(new FileReader(INPUT_FILE))) {
            disk = new Disk(reader.readLine().toCharArray());
        }
        Day9 solution = new Day9(disk);
        solution.run();
    }

    private static int nextFree(List<Integer> disk, int start) {
        for (int i = start + 1; i < disk.size(); i++) {
            if (disk.get(i) == -1) {
                return i;
            }
        }
        return disk.size();
    }

    private static int nextLocation(List<Integer> disk, int start) {
        for (int i = start - 1; i >= 0; i--) {
            if (disk.get(i) >= 0) {
                return i;
            }
        }
        return -1;
    }
}
