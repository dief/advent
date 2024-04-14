package com.leynmaster.advent.aoc2023.day19;

class Result {
    private boolean approved;
    private String nextWorkflow;

    Result(String str) {
        if ("A".equals(str)) {
            approved = true;
        } else if (!"R".equals(str)) {
            nextWorkflow = str;
        }
    }

    Result() { }

    boolean isApproved() {
        return approved;
    }

    String getNextWorkflow() {
        return nextWorkflow;
    }
}
