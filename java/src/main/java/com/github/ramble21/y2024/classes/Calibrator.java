package com.github.ramble21.y2024.classes;

import java.util.ArrayList;
import java.util.Arrays;

public class Calibrator {
    private final int[] nums;
    private final long goalNum;

    public Calibrator(String inputLine) {
        goalNum = Long.parseLong(inputLine.substring(0, inputLine.indexOf(":")));
        nums = Arrays.stream(inputLine.substring(inputLine.indexOf(":") + 2).split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
    public long getCalibrationResult(boolean includeConcatenation){
        char[] operatorChoices = includeConcatenation ? new char[]{'*', '+', '|'} : new char[]{'*', '+'};
        ArrayList<char[]> possibilities = generateCombos(operatorChoices, nums.length - 1);

        outerLoop:
        for (char[] possibility : possibilities){
            long total = nums[0];
            for (int i = 1; i < nums.length; i++){
                char currentOperation = possibility[i-1];
                if (currentOperation == '*') total *= nums[i];
                else if (currentOperation == '+') total += nums[i];
                else total = Long.parseLong(total + "" + nums[i]);
                if (total > goalNum) {
                    continue outerLoop;
                }
            }
            if (total == goalNum){
                return goalNum;
            }
        }
        return 0;
    }
    private ArrayList<char[]> generateCombos(char[] arr, int size){
        ArrayList<char[]> result = new ArrayList<>();
        generateCombinationsRecur(arr, new ArrayList<>(), size, result);
        if (arr.length == 3 && nums[nums.length - 1] % 10 != goalNum % 10){
            result.removeIf(c -> c[c.length - 1] == '|');
        }
        return result;
    }
    private static void generateCombinationsRecur(char[] arr, ArrayList<Character> temp, int size, ArrayList<char[]> result) {
        if (temp.size() == size) {
            char[] combo = new char[temp.size()];
            for (int i = 0; i < temp.size(); i++) {
                combo[i] = temp.get(i);
            }
            result.add(combo);
            return;
        }
        for (char c : arr) {
            temp.add(c);
            generateCombinationsRecur(arr, temp, size, result);
            temp.remove(temp.size() - 1);
        }
    }
}