package com.leynmaster.advent.aoc2022.day7;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Directory {
    private final Map<String, Directory> childDirectories = new TreeMap<>();
    private final List<FileEntry> files = new ArrayList<>();
    private Directory parent;
    private int size;

    void computeSize() {
        size = 0;
        for (Directory child : childDirectories.values()) {
            child.computeSize();
            size += child.getSize();
        }
        for (FileEntry file : files) {
            size += file.size();
        }
    }

    int getSize() {
        return size;
    }

    Map<String, Directory> getChildDirectories() {
        return childDirectories;
    }

    Directory getParent() {
        return parent;
    }

    void setParent(Directory parent) {
        this.parent = parent;
    }

    void addFile(String name, int size) {
        files.add(new FileEntry(name, size));
    }

    void addDirectory(String name) {
        Directory dir = new Directory();
        dir.setParent(this);
        childDirectories.put(name, dir);
    }

    record FileEntry(String name, int size) {}
}
