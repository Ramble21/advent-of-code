package com.github.Ramble21;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day11 extends DaySolver{
    private final List<String> input;
    public Day11() throws IOException {
        input = getInputLines(11);
    }
    public int solvePart1() throws IOException {
        return 0; // long
    }
    public long solvePart1Long() throws IOException {
        String longAssString = input.get(0);
        for (int i = 0; i < 25; i++){
            longAssString = blink(longAssString);
            System.out.println(i+1);
            System.out.println(longAssString);
        }
        System.out.println();
        return longAssString.split(" ").length;
    }

    public int solvePart2() throws IOException {
        return 0;
    }

    public String blink(String longAssString){
        long[] currentOrder = Arrays.stream(longAssString.split(" ")).filter(s -> !s.isEmpty()).mapToLong(Long::parseLong).toArray();
        HashMap<Integer, Long> newStones = new HashMap<>(); // key = index, value = value
        int x = 1;
        //System.out.println(Arrays.toString(currentOrder));
        for (int i = 0; i < currentOrder.length; i++){
            if (currentOrder[i] == 0) currentOrder[i] = 1;
            else if (Long.toString(currentOrder[i]).length() % 2 == 0){
                String toS = Long.toString(currentOrder[i]);
                currentOrder[i] = Long.parseLong(toS.substring(0, toS.length()/2));
                newStones.put(i, Long.parseLong(toS.substring(toS.length()/2)));
            }
            else currentOrder[i] *= 2024;
        }
        //System.out.println(Arrays.toString(currentOrder) + " " + newStones);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < currentOrder.length; i++){
            s.append(currentOrder[i]).append(" ");
            if (newStones.containsKey(i)){
                s.append(newStones.get(i)).append(" ");
            }
        }
        return s.toString();
    }
}