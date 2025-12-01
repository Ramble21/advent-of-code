package com.github.ramble21.y2024.days;

import com.github.ramble21.DaySolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day25 extends DaySolver {
    private final ArrayList<int[]> locks = new ArrayList<>();
    private final ArrayList<int[]> keys = new ArrayList<>();

    public Day25() throws IOException {
        List<String> input = getInputLines(2024, 25);
        int j = 0;
        char[][] template = new char[7][5];
        for (int i = 0; i < input.size(); i++){
            String s = input.get(i);
            if (s.isEmpty() || i == input.size() - 1){
                if (i == input.size() - 1) template[j] = s.toCharArray();
                j = 0;
                if (isLock(template)) locks.add(pinHeights(template, true));
                else keys.add(pinHeights(template, false));
                template = new char[7][5];
            }
            else{
                template[j] = s.toCharArray();
                j++;
            }
        }
    }
    public long solvePart1() throws IOException {
        int combos = 0;
        for (int[] key : keys){
            for (int[] lock : locks){
                if (keyFits(key, lock)) combos++;
            }
        }
        return combos;
    }
    public long solvePart2() throws IOException {
        return 42;
    }
    private boolean keyFits(int[] key, int[] lock){
        for (int i = 0; i < key.length; i++){
            if (key[i] + lock[i] > 5){
                return false;
            }
        }
        return true;
    }
    private boolean isLock(char[][] arr){
        for (char c : arr[0]){
            if (c == '.') return false;
        }
        return true;
    }
    private int[] pinHeights(char[][] arr, boolean isLock){
        int[] result = new int[5];
        for (int c = 0; c < arr[0].length; c++){
            int count = 0;
            for (int r = (isLock ? 1 : arr.length - 1); isLock ? r < arr.length : r >= 0; r += isLock ? 1 : -1){
                if (arr[r][c] == '.') break;
                else count++;
            }
            result[c] = isLock ? count : count - 1;
        }
        return result;
    }
}