package com.github.ramble21.y2023.days;

import com.github.ramble21.DaySolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day9 extends DaySolver {
    private final int[][] nums;
    public Day9() throws IOException {
        List<String> input = getInputLines(2023, 9);
        nums = new int[input.size()][input.get(0).split("\\s+").length];
        for (int i = 0; i < nums.length; i++){
            nums[i] = Arrays.stream(input.get(i).split("\\s+")).mapToInt(Integer::parseInt).toArray();
        }
    }
    public long solvePart1() throws IOException {
        long sum = 0;
        for (int[] num : nums) {
            sum += getNextNum(num);
        }
        return sum;
    }
    public long solvePart2() throws IOException {
        long sum = 0;
        for (int[] num : nums) {
            sum += getPreviousNum(num);
        }
        return sum;
    }
    private int getNextNum(int[] pattern) {
        int[] rateOfChange = getRateOfChange(pattern);
        if (isConstant(rateOfChange)) {
            return pattern[pattern.length - 1] + rateOfChange[0];
        }
        return pattern[pattern.length - 1] + getNextNum(rateOfChange);
    }
    private int getPreviousNum(int[] pattern) {
        int[] rateOfChange = getRateOfChange(pattern);
        if (isConstant(rateOfChange)){
            return pattern[0] - rateOfChange[0];
        }
        return pattern[0] - getPreviousNum(rateOfChange);
    }
    private int[] getRateOfChange(int[] pattern) {
        int[] rateOfChange = new int[pattern.length - 1];
        for (int i = 1; i < pattern.length; i++){
            rateOfChange[i - 1] = pattern[i] - pattern[i - 1];
        }
        return rateOfChange;
    }
    private boolean isConstant(int[] rateOfChange) {
        for (int i = 1; i < rateOfChange.length; i++){
            if (rateOfChange[i] != rateOfChange[i - 1]) return false;
        }
        return true;
    }
}