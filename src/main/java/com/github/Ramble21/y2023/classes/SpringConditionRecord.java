package com.github.Ramble21.y2023.classes;

import java.util.ArrayList;
import java.util.Arrays;

public class SpringConditionRecord {
    private final int[] groupsOfDamagedSprings;
    private final int numUnknowns;
    private final String springs;
    public SpringConditionRecord(String inputLine) {
        String[] split = inputLine.split("\\s+");
        this.springs = split[0];
        this.groupsOfDamagedSprings = Arrays.stream(split[1].split(",")).mapToInt(Integer::parseInt).toArray();
        int numUnknowns = 0;
        for (char s : springs.toCharArray()){
            if (s == '?') numUnknowns++;
        }
        this.numUnknowns = numUnknowns;
    }
    public int getNumPossibilities(){
        ArrayList<String> possibilities = new ArrayList<>();
        getPossibilities("", numUnknowns, possibilities);
        int sum = 0;
        for (String possibility : possibilities) {
            if (comboWorks(getCombo(possibility))) {
                sum++;
            }
        }
        return sum;
    }
    private String getCombo(String possibility) {
        char[] arr = springs.toCharArray();
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == '?'){
                arr[i] = possibility.charAt(index);
                index++;
            }
        }
        return new String(arr);
    }
    private boolean comboWorks(String example) {
        int[] result = new int[groupsOfDamagedSprings.length];
        int index = 0;
        char previousChar = 'X';
        for (char c : example.toCharArray()) {
            if (previousChar == '#' && c == '.'){
                index++;
            }
            else if (c == '#'){
                if (index >= result.length) return false;
                result[index]++;
            }
            previousChar = c;
        }
        for (int i = 0; i < result.length; i++) {
            if (result[i] != groupsOfDamagedSprings[i]) return false;
        }
        return true;
    }

    private void getPossibilities(String current, int remaining, ArrayList<String> results){
        if (remaining == 0) {
            results.add(current);
        }
        else {
            getPossibilities(current + "#", remaining - 1, results);
            getPossibilities(current + ".", remaining - 1, results);
        }
    }
}
