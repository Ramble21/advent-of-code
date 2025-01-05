package com.github.Ramble21;

import com.github.Ramble21.classes.day17.InstructionReader;
import com.github.Ramble21.classes.general.Regex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day17 extends DaySolver{

    private final List<String> input;
    private final InstructionReader reader;
    private final int[] instructions;

    public Day17() throws IOException {
        input = getInputLines(17);
        instructions = Regex.parseAllIntegers(input.get(4).substring(9));
        int[] parsedInput = parseInput();
        reader = new InstructionReader(instructions, parsedInput[0], parsedInput[1], parsedInput[2]);
    }
    public long solvePart1(){
        return 0; // String needed
    }
    public String solvePart1S() throws IOException {
        ArrayList<Long> output = reader.getOutput();
        StringBuilder s = new StringBuilder();
        s.append(output.get(0));
        for (int i = 1; i < output.size(); i++){
            s.append(",").append(output.get(i));
        }
        return s.toString();
    }

    public long solvePart2() throws IOException {
        return 0;
    }

    public int[] parseInput(){
        int[] output = new int[3];
        output[0] = (Integer.parseInt(input.get(0).substring(12)));
        output[1] = (Integer.parseInt(input.get(1).substring(12)));
        output[2] = (Integer.parseInt(input.get(2).substring(12)));
        return output;
    }
}