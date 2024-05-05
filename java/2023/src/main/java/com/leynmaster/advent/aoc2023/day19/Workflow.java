package com.leynmaster.advent.aoc2023.day19;

import java.util.ArrayList;
import java.util.List;

class Workflow {
    private final List<Condition> conditions = new ArrayList<>();

    Workflow(String str) {
        String[] split = str.split(",");
        for (String conditionStr : split) {
            conditions.add(new Condition(conditionStr));
        }
    }

    Condition getCondition(int index) {
        return conditions.get(index);
    }

    Result result(Part part) {
        Result result;
        for (Condition condition : conditions) {
            result = condition.matches(part);
            if (result != null) {
                return result;
            }
        }
        throw new IllegalStateException();
    }
}
