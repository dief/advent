package com.leynmaster.advent.aoc2024.day9;

import java.util.ArrayList;
import java.util.List;

public class Disk {
    private final List<Integer> diskArray = new ArrayList<>();
    private final List<DiskBlock> fileList = new ArrayList<>();
    private final List<DiskBlock> freeList = new ArrayList<>();

    public Disk(char[] input) {
        int counter = 0;
        int location = 0;
        for (int i = 0; i < input.length; i++) {
            int blockSize = Integer.parseInt(String.valueOf(input[i]));
            if (i % 2 == 0) {
                append(diskArray, counter, blockSize);
                fileList.add(new DiskBlock(counter++, location, blockSize));
            } else {
                append(diskArray, -1, blockSize);
                freeList.add(new DiskBlock(-1, location, blockSize));
            }
            location += blockSize;
        }
    }

    public List<Integer> getDiskArray() {
        return diskArray;
    }

    public void defragment() {
        for (DiskBlock file : fileList.reversed()) {
            DiskBlock freeBlock = findFree(file);
            if (freeBlock != null) {
                swap(freeBlock, file);
                freeBlock.setLocation(freeBlock.getLocation() + file.getLength());
                freeBlock.setLength(freeBlock.getLength() - file.getLength());
            }
        }
    }

    private void swap(DiskBlock free, DiskBlock file) {
        for (int i = 0; i < file.getLength(); i++) {
            diskArray.set(free.getLocation() + i, file.getIndex());
            diskArray.set(file.getLocation() + i, -1);
        }
    }

    private DiskBlock findFree(DiskBlock file) {
        for (DiskBlock block : freeList) {
            if (block.getLocation() >= file.getLocation()) {
                return null;
            }
            if (block.getLength() >= file.getLength()) {
                return block;
            }
        }
        return null;
    }

    private static void append(List<Integer> list, int number, int times) {
        for (int i = 0; i < times; i++) {
            list.add(number);
        }
    }
}
