package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Day8 extends DaySolver {
    private final List<String> input;
    public Day8() throws IOException {
        input = getInputLines(2023, 8);
    }
    public long solvePart1() throws IOException {
        LinkedList<Character> instructions = new LinkedList<>();
        for (char c : input.get(0).toCharArray()) {
            instructions.add(c);
        }
        HashMap<String, String> leftNodes = new HashMap<>();
        HashMap<String, String> rightNodes = new HashMap<>();
        for (int i = 2; i < input.size(); i++){
            if (input.get(i).isBlank()) continue;
            String[] parsed = input.get(i).split("[^a-zA-Z]+");
            leftNodes.put(parsed[0], parsed[1]);
            rightNodes.put(parsed[0], parsed[2]);
        }
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
        return 0;
    }
}