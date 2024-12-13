package com.github.Ramble21;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day11 extends DaySolver{
    private final List<String> input;
    private final HashMap<Long, Long> frequencyMap = new HashMap<>();
    public Day11() throws IOException {
        input = getInputLines(11);
    }
    public int solvePart1() throws IOException {
        return 0; // long
    }
    public long solvePart1Long() throws IOException {
        String longAssString = input.get(0);
        long[] initial = Arrays.stream(longAssString.split(" ")).mapToLong(Long::parseLong).toArray();
        initializeHashmap(initial);
        System.out.println(frequencyMap);
        for (int i = 0; i < 25; i++){
            blink();
            System.out.println(i+1);
        }
        System.out.println();
        return amountOfStones();
    }

    public int solvePart2() throws IOException {
        return 0; // long
    }
    public long solvePart2Long() {
        String longAssString = input.get(0);
        long[] initial = Arrays.stream(longAssString.split(" ")).mapToLong(Long::parseLong).toArray();
        initializeHashmap(initial);
        System.out.println(frequencyMap);
        for (int i = 0; i < 75; i++){
            System.out.println(i+1);
            blink();
        }
        System.out.println();
        return amountOfStones();
    }
    public void blink(){
        HashMap<Long, Long> updatedMap = new HashMap<>();
        for (long key : frequencyMap.keySet()){
            long newKey;
            long newKey2 = -1;
            if (key == 0) newKey = 1;
            else if (Long.toString(key).length() % 2 == 0){
                String toS = Long.toString(key);
                newKey = Long.parseLong(toS.substring(0, toS.length()/2));
                newKey2 = Long.parseLong(toS.substring(toS.length()/2));
            }
            else newKey = key * 2024;

            updatedMap.put(newKey, (updatedMap.getOrDefault(newKey, 0L) + frequencyMap.get(key)));
            if (newKey2 != -1) updatedMap.put(newKey2, (updatedMap.getOrDefault(newKey2, 0L) + frequencyMap.get(key)));
        }
        frequencyMap.clear();
        frequencyMap.putAll(updatedMap);
        System.out.println(frequencyMap);
    }
    public void initializeHashmap(long[] initial){
        for (long l : initial) {
           frequencyMap.put(l, frequencyMap.getOrDefault(l, 0L)+1);
        }
    }
    public long amountOfStones(){
        long count = 0;
        for (long key : frequencyMap.keySet()){
            count += frequencyMap.get(key);
        }
        return count;
    }
}