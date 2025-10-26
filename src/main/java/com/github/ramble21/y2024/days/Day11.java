package com.github.ramble21.y2024.days;

import com.github.ramble21.DaySolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Day11 extends DaySolver {
    private final HashMap<Long, Long> frequencyMap = new HashMap<>();
    public Day11() throws IOException {
        List<String> input = getInputLines(2024, 11);
        for (String s : input.get(0).split(" ")){
            long startingNum = Long.parseLong(s);
            frequencyMap.put(startingNum, 1 + frequencyMap.getOrDefault(startingNum, 0L));
        }
    }
    public long solvePart1() throws IOException {
        for (int i = 0; i < 25; i++){
            blink();
        }
        long sum = 0;
        for (long value : frequencyMap.values()){
            sum += value;
        }
        return sum;
    }
    public long solvePart2() throws IOException {
        for (int i = 0; i < 50; i++){
            blink();
        }
        long sum = 0;
        for (long value : frequencyMap.values()){
            sum += value;
        }
        return sum;
    }
    private void blink() {
        HashMap<Long, Long> temp = new HashMap<>();
        for (long stone : frequencyMap.keySet()){
            long count = frequencyMap.get(stone);
            int numDigits = (int)Math.log10(stone) + 1;
            if (stone == 0){
                temp.put(1L, count + temp.getOrDefault(1L, 0L));
            }
            else if (numDigits % 2 == 0){
                int divider = (int) Math.pow(10, (double) numDigits / 2);
                long firstHalf = stone / divider;
                long secondHalf = stone % divider;
                temp.put(firstHalf, count + temp.getOrDefault(firstHalf, 0L));
                temp.put(secondHalf, count + temp.getOrDefault(secondHalf, 0L));
            }
            else {
                temp.put(stone * 2024, count + temp.getOrDefault(stone * 2024, 0L));
            }
        }
        frequencyMap.clear();
        frequencyMap.putAll(temp);
    }
}