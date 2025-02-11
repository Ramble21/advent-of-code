package com.github.Ramble21;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.github.Ramble21.classes.general.*;

public class Day22 extends DaySolver{
    private final List<String> input;
    private final long[][] secretNums;
    private final int[][] changes;
    private final int[][] bananas;

    public Day22() throws IOException {
        input = getInputLines(22);
        changes = new int[input.size()][2000];
        secretNums = new long[input.size()][2000];
        bananas = new int[input.size()][2000];
    }
    public long solvePart1() throws IOException {
        long sum = 0;
        for (int i = 0; i < input.size(); i++){
            long init = Long.parseLong(input.get(i));
            long next = nextXSecretNos(init, i);
            sum += next;
        }
        return sum;
    }
    public long solvePart2() throws IOException {
        initPC();
        long maxBananas = 0;
        for (int i = 0; i < possibleCombos.length; i++){
            if (i % 1000 == 0) System.out.println(i/1000 + "/130");
            long temp = getMaxBananas(possibleCombos[i]);
            if (temp > maxBananas){
                maxBananas = temp;
                System.out.println(Arrays.toString(possibleCombos[i]) + ": " + temp);
            }
        }
        return maxBananas;
    }
    private long getMaxBananas(int[] combo){
        long total = 0;
        for (int i = 0; i < changes.length; i++){
            total += getBananas(bananas[i], combo, changes[i]);
        }
        return total;
    }
    private long getBananas(int[] bananas, int[] combo, int[] buyer){
        for (int i = 0; i < buyer.length-3; i++){
            if (buyer[i] == combo[0] && buyer[i+1] == combo[1] && buyer[i+2] == combo[2] && buyer[i+3] == combo[3]){
                return bananas[i+3];
            }
        }
        return 0;
    }

    private long nextXSecretNos(long initial, int index){
        long current = initial;
        for (int i = 0; i < 2000; i++){
            current = nextSecretNo(current);
            secretNums[index][i] = current;
            bananas[index][i] = (int) (current % 10);
            changes[index][i] = i == 0 ? bananas[index][i] - Integer.parseInt(input.get(index)) % 10 : bananas[index][i] - bananas[index][i-1];
        }
        return current;
    }
    private long nextSecretNo(long secretNo){
        long next = secretNo;
        next ^= next * 64;
        next %= 16777216;
        next ^= next / 32;
        next %= 16777216;
        next ^= next * 2048;
        next %= 16777216;
        return next;
    }
    private static int[][] possibleCombos;
    private void initPC(){
        int min = -9, max = 9;
        int[][] result = new int[(int) Math.pow(max - min + 1, 4)][4];
        int index = 0;
        for (int a = min; a <= max; a++) {
            for (int b = min; b <= max; b++) {
                for (int c = min; c <= max; c++) {
                    for (int d = min; d <= max; d++) {
                        result[index++] = new int[]{a, b, c, d};
                    }
                }
            }
        }
        possibleCombos = result;
    }
}