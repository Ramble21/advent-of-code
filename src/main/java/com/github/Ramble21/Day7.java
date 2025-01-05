package com.github.Ramble21;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day7 extends DaySolver{
    private final List<String> input;
    public Day7() throws IOException {
        input = getInputLines(7);
    }

    public long solvePart1() throws IOException {
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
    public long solvePart2() throws IOException {
        // WARNING: I WAS LAZY AND SOLVED THIS USING BRUTE FORCE, IT WILL TAKE FOREVER
        long count = 0;
        for (String s : input) {
            String skibidi = s.substring(s.indexOf(":") + 2);
            long[] nums = Arrays.stream(skibidi.split(" ")).mapToLong(Long::parseLong).toArray();
            long total = Long.parseLong(s.substring(0, s.indexOf(":")));
            count += calibrationResultWithConcatenation(nums, total);
        }
        System.out.println();
        return count;
    }

    public long calibrationResult(long[] nums, long goal){
        boolean[] isMultiplication = new boolean[nums.length]; // represents index of first number in a pair that is multiplied

        int totalCombinations = (int)(Math.pow(2, isMultiplication.length));
        int comboNo = 0;
        while (comboNo <= totalCombinations){

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
    public long calibrationResultWithConcatenation(long[] nums, long goal){
        int[] sign = new int[nums.length]; // represents index of first number in a pair that is added/concatenated/multiplied- 0 = +. 1 = *, 2 = ||
        int totalCombinations = (int)(Math.pow(3, sign.length));

        int comboNo = 0;
        while (comboNo <= totalCombinations){

            long total = 0;
            for (int i = 0; i < nums.length; i++){
                if (total != 0 && sign[i-1] == 1) total = nums[i] * total;
                else if (i != 0 && sign[i-1] == 0) total += nums[i];
                else if (i != 0 && sign[i-1] == 2) total = Long.parseLong(Long.toString(total) + Long.toString(nums[i]));
                else if (i == 0) total += nums[i];
            }

            if (true) {
                System.out.print("\n" + total + " " + goal);
                System.out.print(" (" + nums[0]);
                for (int i = 1; i < nums.length; i++){
                    String symbol = "+";
                    if (sign[i-1] == 1) symbol = "*";
                    else if (sign[i-1] == 2) symbol = "||";
                    System.out.print(" " + symbol + " " + nums[i]);
                }
                System.out.print(")");
            }

            if (total == goal) {
                System.out.print(" ✓");
                return total;
            }


            for (int i = sign.length - 2; i >= 0; i--) {
                sign[i] = (sign[i] + 1) % 3;
                if (sign[i] != 0) {
                    break;
                }
            }
            System.out.print("\n" + Arrays.toString(sign) + " " + comboNo);
            comboNo++;
        }
        return 0;
    }
}