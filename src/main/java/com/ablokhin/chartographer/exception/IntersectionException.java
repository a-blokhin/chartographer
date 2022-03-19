package com.ablokhin.chartographer.exception;

public class IntersectionException extends Exception {
    public IntersectionException(String firstVariableName, Integer firstVariable, Integer firstMin, Integer firstMax,
                                 String secondVariableName, Integer secondVariable, Integer secondMin, Integer secondMax) {
        super(firstVariableName + " = " + firstVariable + " not in constraints " + firstMin + " " + firstMax
                + " OR " + secondVariableName + " = " + secondVariable + " not in constraints " + secondMin + " " + secondMax);
    }
}