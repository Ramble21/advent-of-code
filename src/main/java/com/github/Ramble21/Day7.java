package com.github.Ramble21;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Day7 extends DaySolver{
    private final List<String> input;
    public Day7() throws IOException {
        input = getInputLines(7);
    }
    public int solvePart1() throws IOException {
        return 0; // the values are very large so i need to use a long and `solvePart1Long()` instead
    }
    public int solvePart2() throws IOException {
        return 0; // same here
    }

    public long solvePart1Long() throws IOException {
        long count = 0;
        for (String s : input){
            String skibidi = s.substring(s.indexOf(":") + 2);
            long[] nums = Arrays.stream(skibidi.split(" ")).mapToLong(Long::parseLong).toArray();
            long total = Long.parseLong(s.substring(0, s.indexOf(":")));
            count += calibrationResult(nums, total);
        }
        System.out.println();
        return count;
    }
    public long solvePart2Long() throws IOException {
        return 0;
    }

    public long calibrationResult(long[] nums, long goal){
        boolean[] isMultiplication = new boolean[nums.length]; // represents index of first number in a pair that is multiplied

        int totalCombinations = (int)(Math.pow(2, isMultiplication.length));
        int comboNo = 0;
        while (comboNo < totalCombinations){

            long total = 0;
            for (int i = 0; i < nums.length; i++){
                if (total != 0 && isMultiplication[i-1]) total = nums[i] * total;
                else if (i != 0 && !isMultiplication[i-1]) total += nums[i];
                else if (i == 0) total += nums[i];
            }

            if (total == goal) {
                System.out.print("\n" + total + " " + goal);

                System.out.print(" (" + nums[0]);
                for (int i = 1; i < nums.length; i++){
                    String symbol = "+";
                    if (isMultiplication[i-1]) symbol = "*";
                    System.out.print(" " + symbol + " " + nums[i]);
                }
                System.out.print(")");
                System.out.print(" ✓");
                return total;
            }

            for (int i = isMultiplication.length-2; i >= 0; i--) {
                isMultiplication[i] = !isMultiplication[i];
                if (isMultiplication[i]) {
                    break;
                }
            }
            comboNo++;
        }
        return 0;
    }
}