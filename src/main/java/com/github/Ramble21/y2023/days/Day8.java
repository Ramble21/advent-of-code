package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;

import java.io.IOException;
import java.util.*;

public class Day8 extends DaySolver {
    private final LinkedList<Character> instructions;
    private final HashMap<String, String> leftNodes;
    private final HashMap<String, String> rightNodes;
    public Day8() throws IOException {
        List<String> input = getInputLines(2023, 8);
        instructions = new LinkedList<>();
        for (char c : input.get(0).toCharArray()) {
            instructions.add(c);
        }
        leftNodes = new HashMap<>();
        rightNodes = new HashMap<>();
        for (int i = 2; i < input.size(); i++){
            if (input.get(i).isBlank()) continue;
            String[] parsed = input.get(i).split("[^a-zA-Z0-9]+");
            leftNodes.put(parsed[0], parsed[1]);
            rightNodes.put(parsed[0], parsed[2]);
        }
    }
    public long solvePart1() throws IOException {
        String current = "AAA";
        int iterations = 0;
        while (!current.equals("ZZZ")){
            assert !instructions.isEmpty();
            char direction = instructions.poll();
            instructions.addLast(direction);
            if (direction == 'R'){
                current = rightNodes.get(current);
            }
            else{
                current = leftNodes.get(current);
            }
            iterations++;
        }
        return iterations;
    }

    public long solvePart2() throws IOException {
        ArrayList<String> currents = new ArrayList<>();
        int iterations = 0;
        for (String s : leftNodes.keySet()){
            if (s.endsWith("A")) currents.add(s);
        }
        long[] timeToFirstReachZ = new long[currents.size()];
        long[] timeToRepeatZ = new long[currents.size()];
        while (!arrIsDone(timeToRepeatZ)){
            assert !instructions.isEmpty();
            char direction = instructions.poll();
            instructions.addLast(direction);
            for (int i = 0; i < currents.size(); i++){
                if (direction == 'R'){
                    currents.set(i, rightNodes.get(currents.get(i)));
                }
                else{
                    currents.set(i, leftNodes.get(currents.get(i)));
                }
                if (currents.get(i).endsWith("Z")){
                    if (timeToFirstReachZ[i] == 0) timeToFirstReachZ[i] = iterations;
                    else if (timeToRepeatZ[i] == 0) timeToRepeatZ[i] = iterations - timeToFirstReachZ[i];
                }
            }
            iterations++;
        }
        return lcmOfArray(timeToRepeatZ);
    }
    private boolean arrIsDone(long[] timeToRepeatZ){
        for (long x : timeToRepeatZ){
            if (x == 0) return false;
        }
        return true;
    }
    public static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    public static long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }
    public static long lcmOfArray(long[] arr) {
        long result = arr[0];
        for (int i = 1; i < arr.length; i++) {
            result = lcm(result, arr[i]);
        }
        return result;
    }
}