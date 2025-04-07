package com.github.Ramble21.y2023.classes;

import com.sun.jdi.IntegerType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class RatingCombo {
    private IntegerRange allowedX;
    private IntegerRange allowedM;
    private IntegerRange allowedA;
    private IntegerRange allowedS;
    public RatingCombo() {
        allowedX = new IntegerRange('x', 1, 4000);
        allowedM = new IntegerRange('m', 1, 4000);
        allowedA = new IntegerRange('a', 1, 4000);
        allowedS = new IntegerRange('s', 1, 4000);
    }
    public String toString() {
        return "{" + allowedX + "," + allowedM + ", " + allowedA + ", " + allowedS + "}";
    }
    public RatingCombo getAppended(Inequality i) {
        RatingCombo copy = new RatingCombo();
        copy.allowedA = allowedA.copy();
        copy.allowedX = allowedX.copy();
        copy.allowedS = allowedS.copy();
        copy.allowedM = allowedM.copy();
        copy.appendInequality(i);
        return copy;
    }
    public long getTotalCombos() {
        int X = allowedX.getNumValues(),
            M = allowedM.getNumValues(),
            A = allowedA.getNumValues(),
            S = allowedS.getNumValues();
        return (long) X * M * A * S;
    }
    public void appendInequality(Inequality i) {
        if (i.isDefault()) return;
        if (i.getInequalitySymbol() == '<') {
            addAllowedRange(new IntegerRange(i.getPartLabel(), 1, i.getComparisonNumber() - 1));
        }
        else {
            addAllowedRange(new IntegerRange(i.getPartLabel(), i.getComparisonNumber() + 1, 4000));
        }
    }
    private void addAllowedRange(IntegerRange range) {
        switch (range.getPartLabel()) {
            case 'x' -> allowedX = allowedX.trim(range);
            case 'm' -> allowedM = allowedM.trim(range);
            case 'a' -> allowedA = allowedA.trim(range);
            case 's' -> allowedS = allowedS.trim(range);
            default -> throw new IllegalArgumentException();
        }
    }
}
