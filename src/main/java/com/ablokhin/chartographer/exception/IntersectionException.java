package com.ablokhin.chartographer.exception;

public class IntersectionException extends Exception {
    public IntersectionException(String firstVariableName, int firstVariable, int firstMin, int firstMax,
                                 String secondVariableName, int secondVariable, int secondMin, int secondMax) {
        super(String.format("%s = %o not in constraints %o %o OR %s = %o not in constraints %o %o", firstVariableName, firstVariable, firstMin, firstMax,
                secondVariableName, secondVariable, secondMin, secondMax));
    }
}