package com.leynmaster.advent.aoc2023.day19;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

class WorkflowConductor {
    private final Map<String, Workflow> map = new HashMap<>();

    void addWorkflow(String str) {
        int split = str.indexOf('{');
        map.put(str.substring(0, split), new Workflow(str.substring(split + 1, str.length() - 1)));
    }

    boolean accepted(Part part) {
        Workflow workflow = map.get("in");
        Result result = new Result();
        while (workflow != null) {
            result = workflow.result(part);
            String name = result.getNextWorkflow();
            if (name == null) {
                workflow = null;
            } else {
                workflow = map.get(name);
            }
        }
        return result.isApproved();
    }

    long getCombinations(PartRange initialRange) {
        long combinations = 0;
        LinkedList<WorkflowCursor> queue = new LinkedList<>();
        queue.addLast(new WorkflowCursor(initialRange, map.get("in"), 0));
        while (!queue.isEmpty()) {
            WorkflowCursor cursor = queue.removeFirst();
            Condition condition = cursor.condition();
            RangeResult result = condition.matchesRange(cursor.range());
            PartRange in = result.in();
            PartRange out = result.out();
            if (in != null) {
                combinations += inCombinations(queue, in, condition.getResult());
            }
            if (out != null) {
                queue.addLast(new WorkflowCursor(out, cursor.workflow(), cursor.index() + 1));
            }
        }
        return combinations;
    }

    private long inCombinations(LinkedList<WorkflowCursor> queue, PartRange in, Result inResult) {
        long combinations = 0;
        String nextWorkflow = inResult.getNextWorkflow();
        if (nextWorkflow != null) {
            queue.addLast(new WorkflowCursor(in, map.get(nextWorkflow), 0));
        }
        else if (inResult.isApproved()) {
            return in.combinations();
        }
        return combinations;
    }

    private record WorkflowCursor(PartRange range, Workflow workflow, int index) {
        Condition condition() {
            return workflow.getCondition(index);
        }
    }
}
