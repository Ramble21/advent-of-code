package com.github.Ramble21;

import java.io.IOException;
import java.util.List;

public class Day2 extends DaySolver{
    private final List<String> input;
    public Day2() throws IOException {
        input = getInputLines(2);
    }
    public long solvePart1() throws IOException {
        int safes = 0;
        for (String line : input){
            String[] temp = line.split(" ");
            int[] nums = new int[temp.length];
            for (int i = 0; i < temp.length; i++){
                nums[i] = Integer.parseInt(temp[i]); // there might be a method that does all of this for me but i don't know it
            }
            if (isSafe(nums)) safes++;
        }
        return safes;
    }
    public boolean isSafe(int[] nums){
        boolean isSafeDec = true;
        boolean isSafeInc = true;
        for (int i = 1; i < nums.length; i++){
            if (nums[i] < nums[i - 1] || Math.abs(nums[i] - nums[i - 1]) > 3 || Math.abs(nums[i] - nums[i - 1]) < 1) {
                isSafeInc = false;
                break;
            }
        }
        for (int i = 1; i < nums.length; i++){
            if (nums[i] > nums[i - 1] || Math.abs(nums[i] - nums[i - 1]) > 3 || Math.abs(nums[i] - nums[i - 1]) < 1) {
                isSafeDec = false;
                break;
            }
        }
        return isSafeInc || isSafeDec;
    }
    public boolean isSafeByDampening(int[] nums){
        for (int i = 0; i < nums.length; i++){
            if (isSafe(excludeIndex(nums, i))) return true;
        }
        return false;
    }
    public int[] excludeIndex(int[] nums, int excludeIndex){
        int[] result = new int[nums.length-1];
        int resultIndex = 0;
        for (int i = 0; i < nums.length; i++){
            if (i != excludeIndex) {
                result[resultIndex++] = nums[i];
            }
        }
        return result;
    }

    public long solvePart2() throws IOException {
        int safes = 0;
        for (String line : input){
            String[] temp = line.split(" ");
            int[] nums = new int[temp.length];
            for (int i = 0; i < temp.length; i++){
                nums[i] = Integer.parseInt(temp[i]); // there might be a method that does all of this for me but i don't know it
            }
            if (isSafe(nums) || isSafeByDampening(nums)) safes++;
        }
        return safes;
    }
}
