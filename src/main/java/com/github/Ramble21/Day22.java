package com.github.Ramble21;

import java.io.IOException;
import java.util.*;

public class Day22 extends DaySolver{
    private final List<String> input;
    private final int[][] changes;
    private final int[][] bananas;

    private final HashMap<String, Long> numBananasFromDeltas = new HashMap<>();

    public Day22() throws IOException {
        input = getInputLines(22);
        changes = new int[input.size()][2000];
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
        long maxBananas = 0;
        for (int buyerNo = 0; buyerNo < changes.length; buyerNo++) {
            HashSet<String> seen = new HashSet<>();
            int[] buyer = changes[buyerNo];
            for (int i = 0; i < buyer.length - 3; i++){
                int[] possibleCombo = new int[]{buyer[i], buyer[i+1], buyer[i+2], buyer[i+3]};
                String mapKey = possibleCombo[0] + "," + possibleCombo[1] + "," + possibleCombo[2] + "," + possibleCombo[3];
                if (seen.contains(mapKey)) continue;
                seen.add(mapKey);
                long current = numBananasFromDeltas.getOrDefault(mapKey, 0L);
                long numBananasGotten = getBananas(bananas[buyerNo], possibleCombo, buyer);
                numBananasFromDeltas.put(mapKey, numBananasGotten + current);
            }
        }
        for (long l : numBananasFromDeltas.values()){
            if (l > maxBananas) maxBananas = l;
        }
        return maxBananas;
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
}