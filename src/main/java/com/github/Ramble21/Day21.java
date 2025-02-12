package com.github.Ramble21;

import java.io.IOException;
import java.util.List;

import com.github.Ramble21.classes.days.Keypad;

public class Day21 extends DaySolver{
    private final List<String> input;
    public Day21() throws IOException {
        input = getInputLines(21);
    }
    public long solvePart1() throws IOException {
        return solvePart(2);
    }
    public long solvePart2() throws IOException {
        return solvePart(25);
    }
    public long solvePart(int directionalRobots){
        long complexities = 0;
        for (String code : input){
            String original = Keypad.encode(code, false);
            long len = Keypad.getEncodedLength(original, directionalRobots);
            complexities += len * Long.parseLong(code.substring(0, 3));
        }
        return complexities;
    }
}