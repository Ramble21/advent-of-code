package com.github.Ramble21;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import com.github.Ramble21.classes.day21.Keypad;
import com.github.Ramble21.classes.general.*;

public class Day21 extends DaySolver{
    private final List<String> input;
    public Day21() throws IOException {
        input = getInputLines(21);
    }
    public long solvePart1() throws IOException {
        long complexities = 0;
        for (String code : input){
            complexities += getComplexity(code, 2);
        }
        return complexities;
    }
    public long solvePart2() throws IOException {
        return 0;
    }
    public long getComplexity(String code, int numDirectionalRobots){
        HashSet<String> previousRobot = Keypad.robot(code, false);
        for (int i = 0; i < numDirectionalRobots; i++){
            HashSet<String> newDirectionalRobot = new HashSet<>();
            for (String s : previousRobot){
                newDirectionalRobot.addAll(Keypad.robot(s, true));
            }
            previousRobot = newDirectionalRobot;
        }
        long minLength = previousRobot.stream().mapToInt(String::length).min().orElse(0);
        return minLength * Long.parseLong(code.substring(0, 3));
    }
}