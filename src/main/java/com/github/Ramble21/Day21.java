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
        int complexities = 0;
        for (String code : input){
            System.out.println(code);

            HashSet<String> robotOne = Keypad.robot(code, false);

            HashSet<String> robotTwo = new HashSet<>();
            for (String s : robotOne){
                robotTwo.addAll(Keypad.robot(s, true));
            }

            HashSet<String> robotThree = new HashSet<>();
            for (String s : robotTwo){
                robotThree.addAll(Keypad.robot(s, true));
            }

            int minLength = robotThree.stream().mapToInt(String::length).min().orElse(0);
            robotThree.removeIf(s -> s.length() > minLength);
            complexities += minLength * Integer.parseInt(code.substring(0, 3));
        }
        return complexities;
    }

    public long solvePart2() throws IOException {
        return 0;
    }
}