package com.github.ramble21.y2024.days;

import com.github.ramble21.DaySolver;
import com.github.ramble21.y2024.classes.InstructionReader;
import com.github.ramble21.y2024.classes.Regex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Day17 extends DaySolver {

    private final List<String> input;
    private final int[] instructions;
    private final int[] parsedInput;
    private String part1;

    public Day17() throws IOException {
        input = getInputLines(2024, 17);
        instructions = Regex.parseAllIntegers(input.get(4).substring(9));
        parsedInput = parseInput();
    }
    public long solvePart1(){
        InstructionReader reader = new InstructionReader(instructions, parsedInput[0], parsedInput[1], parsedInput[2]);
        reader.read();
        ArrayList<Integer> output = reader.getOutput();
        StringBuilder s = new StringBuilder();
        s.append(output.get(0));
        for (int i = 1; i < output.size(); i++){
            s.append(",").append(output.get(i));
        }
        part1 = s.toString();
        return 0; // String needed
    }
    public String solvePart1String() throws IOException {
        timePart1();
        return part1;
    }

    public long solvePart2() throws IOException {
        HashSet<Long> solutions = new HashSet<>();
        int[] test = instructions;
        solveRecursive(test.length-2, 3, test, solutions);
        long min = Long.MAX_VALUE;
        for (Long l : solutions){
            testNum(l);
            if (l < min) min = l;
        }
        return min;
    }

    public void solveRecursive(int index, long A, int[] output, HashSet<Long> solutions){
        if (index < 0){
            solutions.add(A);
            return;
        }
        int E_mod = output[index];
        for (long B = 0; B < 8; B++){
            long newA = (A * 8) + (B ^ 3);
            long C = (newA / (1L << B));
            long D = B ^ C;
            long E = D ^ 3;
            if (E % 8 != E_mod) continue;
            long computedB = ((newA % 8) ^ 3);
            long computedC = newA / (1L << B);
            if (B == computedB && C == computedC){
                solveRecursive(index-1, newA, output, solutions);
            }
        }
    }

    public int[] parseInput(){
        int[] output = new int[3];
        output[0] = (Integer.parseInt(input.get(0).substring(12)));
        output[1] = (Integer.parseInt(input.get(1).substring(12)));
        output[2] = (Integer.parseInt(input.get(2).substring(12)));
        return output;
    }

    public void testNum(long A){
        InstructionReader reader = new InstructionReader(instructions, A, parsedInput[1], parsedInput[2]);
        reader.read();
    }
    public static String decimalToOctal(long decimal) {
        return Long.toOctalString(decimal);
    }
}