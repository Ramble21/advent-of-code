package com.github.Ramble21.y2024.days;

import com.github.Ramble21.DaySolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day1 extends DaySolver {

    private final List<String> inputLines;
    public Day1() throws IOException {
        inputLines = getInputLines(2024, 1);
    }

    public long solvePart1() throws IOException {

        int[] array1 = new int[inputLines.size()];
        int[] array2 = new int[inputLines.size()];
        int total = 0;
        for (int i = 0; i < inputLines.size(); i++){
            String[] nums = inputLines.get(i).split(" {3}");
            array1[i] = (Integer.parseInt(nums[0]));
            array2[i] = (Integer.parseInt(nums[1]));
        }
        Arrays.sort(array1);
        Arrays.sort(array2);
        for (int i = 0; i < array1.length; i++){
            total += Math.abs(array1[i]-array2[i]);
        }
        return total;
    }

    public long solvePart2() throws IOException {

        int[] array1 = new int[inputLines.size()];
        int[] array2 = new int[inputLines.size()];
        for (int i = 0; i < inputLines.size(); i++){
            String[] nums = inputLines.get(i).split(" {3}");
            array1[i] = (Integer.parseInt(nums[0]));
            array2[i] = (Integer.parseInt(nums[1]));
        }
        int sum = 0;
        for (int num1 : array1){
            int amount = 0;
            for (int num2 : array2) {
                if (num1 == num2) {
                    amount++;
                }
            }
            sum += num1 * amount;
        }
        return sum;
    }
}
