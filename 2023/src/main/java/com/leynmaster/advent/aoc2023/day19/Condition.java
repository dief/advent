package com.leynmaster.advent.aoc2023.day19;

import java.util.function.Function;

class Condition {
    private final Function<Part, Boolean> check;
    private final Result result;
    private char checkLabel;
    private boolean checkLess;
    private int checkValue;

    Condition(String str) {
        int split = str.indexOf(':');
        if (split < 0) {
            check = (_) -> true;
        } else {
            checkLabel = str.charAt(0);
            checkLess = str.charAt(1) == '<';
            checkValue = Integer.parseInt(str.substring(2, split));
            check = switch (checkLabel) {
                case 'x' -> (part) -> checkLess ? part.getX() < checkValue : part.getX() > checkValue;
                case 'm' -> (part) -> checkLess ? part.getM() < checkValue : part.getM() > checkValue;
                case 'a' -> (part) -> checkLess ? part.getA() < checkValue : part.getA() > checkValue;
                case 's' -> (part) -> checkLess ? part.getS() < checkValue : part.getS() > checkValue;
                default -> throw new IllegalArgumentException();
            };
        }
        result = new Result(str.substring(split + 1));
    }

    Result getResult() {
        return result;
    }

    Result matches(Part part) {
        return check.apply(part) ? result : null;
    }

    RangeResult matchesRange(PartRange range) {
        if (checkLabel == 0) {
            return new RangeResult(range, null);
        }
        return range.split(checkLabel, checkLess, checkValue);
    }
}
