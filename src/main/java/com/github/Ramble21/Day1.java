package com.github.Ramble21;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day1 extends DaySolver {

    public Day1() throws IOException {}

    public long solvePart1() throws IOException {
        List<String> inputLines = getInputLines(1);
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
        List<String> inputLines = getInputLines(1);
        int[] array1 = new int[inputLines.size()];
        int[] array2 = new int[inputLines.size()];

        int total = 0;
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
