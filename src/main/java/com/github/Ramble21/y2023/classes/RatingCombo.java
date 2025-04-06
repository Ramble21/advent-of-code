package com.github.Ramble21.y2023.classes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class RatingCombo {
    private final HashSet<IntegerRange> disallowedX;
    private final HashSet<IntegerRange> disallowedM;
    private final HashSet<IntegerRange> disallowedA;
    private final HashSet<IntegerRange> disallowedS;
    public RatingCombo() {
        disallowedA = new HashSet<>();
        disallowedS = new HashSet<>();
        disallowedX = new HashSet<>();
        disallowedM = new HashSet<>();
    }
    public String toString() {
        return "{\n" + disallowedX + ",\n" + disallowedM + ",\n" + disallowedA + ",\n" + disallowedS + "\n}";
    }
    public void initializeInequalities(ArrayList<Inequality> inequalities) {
        for (Inequality i : inequalities) {
            if (i.isDefault()) continue;
            if (i.getInequalitySymbol() == '<') {
                addDisallowedRange(new IntegerRange(i.getPartLabel(), i.getComparisonNumber(), 4000));
            }
            else {
                addDisallowedRange(new IntegerRange(i.getPartLabel(), 1, i.getComparisonNumber()));
            }
        }
    }
    public void addDisallowedRange(IntegerRange range) {
        switch (range.getPartLabel()) {
            case 'x' ->  {
                trimSet(range, disallowedX);
                disallowedX.add(range);
            }
            case 'm' -> {
                trimSet(range, disallowedM);
                disallowedM.add(range);
            }
            case 'a' -> {
                trimSet(range, disallowedA);
                disallowedA.add(range);
            }
            case 's' -> {
                trimSet(range, disallowedS);
                disallowedS.add(range);
            }
            default -> throw new IllegalArgumentException();
        }
    }
    public void addAcceptances(RatingCombo other) {

    }

    private static HashSet<IntegerRange> getAllowed(HashSet<IntegerRange> disallowed) {
        if (disallowed.isEmpty()) {
            return new HashSet<>();
        }
        char c = '$';
        for (IntegerRange range : disallowed) {
            c = range.getPartLabel();
            break;
        }
        HashSet<IntegerRange> result = new HashSet<>();
        ArrayList<IntegerRange> disallowedList = new ArrayList<>(disallowed);
        disallowedList.sort(Comparator.comparingInt(IntegerRange::getMin));
        if (disallowedList.get(0).getMin() != 1) {
            result.add(new IntegerRange(c, 1, disallowedList.get(0).getMin() - 1));
        }
        if (disallowedList.get(disallowedList.size() - 1).getMax() != 4000) {
            result.add(new IntegerRange(c, disallowedList.get(disallowedList.size() - 1).getMax() + 1, 4000));
        }
        for (int i = 1; i < disallowedList.size(); i++) {
            result.add(new IntegerRange(c, disallowedList.get(i - 1).getMax() + 1, disallowedList.get(i).getMin() - 1));
        }
        return result;
    }
    private static boolean numAllowed(HashSet<IntegerRange> disallowed, int n) {
        for (IntegerRange range : disallowed) {
            if (range.isInRange(n)) return false;
        }
        return true;
    }
    public long getNumCombos() {
        long result = getNumAllowed(disallowedX);
        result *= getNumAllowed(disallowedM);
        result *= getNumAllowed(disallowedA);
        result *= getNumAllowed(disallowedS);
        return result;
    }
    private static int getNumAllowed(HashSet<IntegerRange> set) {
        int result = 4000;
        for (IntegerRange range : set) {
            result -= range.getNumValues();
        }
        return result;
    }
    private static void trimSet(IntegerRange nuevo, HashSet<IntegerRange> set) {
        for (IntegerRange viejo : set) {
            nuevo.trim(viejo);
        }
    }

}
