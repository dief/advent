package com.leynmaster.advent.aoc2024.day24;

import java.util.Map;

public abstract class Gate {
    private String input1;
    private String input2;
    private String destination;
    private String type;
    private boolean output;

    public void setInput1(String input1) {
        this.input1 = input1;
    }

    public void setInput2(String input2) {
        this.input2 = input2;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return input1 + " " + type + " " + input2 + " -> " + destination;
    }

    void process(Map<String, Boolean> gates) {
        if (output) {
            return;
        }
        Boolean b1 = gates.get(input1);
        Boolean b2 = gates.get(input2);
        if (b1 != null && b2 != null) {
            gates.put(destination, result(b1, b2));
            output = true;
        }
    }

    boolean match(String input1, String input2, String type) {
        return this.type.equals(type) && (this.input1.equals(input1) && this.input2.equals(input2)
                || this.input1.equals(input2) && this.input2.equals(input1));
    }

    abstract boolean result(boolean b1, boolean b2);

    static Gate create(String input1, String input2, String destination, String type) {
        Gate gate = switch (type) {
            case "AND" -> new AndGate();
            case "OR" -> new OrGate();
            default -> new XorGate();
        };
        gate.setInput1(input1);
        gate.setInput2(input2);
        gate.setDestination(destination);
        gate.setType(type);
        return gate;
    }

    static class AndGate extends Gate {

        @Override
        boolean result(boolean b1, boolean b2) {
            return b1 && b2;
        }
    }

    static class OrGate extends Gate {

        @Override
        boolean result(boolean b1, boolean b2) {
            return b1 || b2;
        }
    }

    static class XorGate extends Gate {

        @Override
        boolean result(boolean b1, boolean b2) {
            return b1 != b2;
        }
    }
}
