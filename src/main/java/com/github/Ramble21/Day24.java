package com.github.Ramble21;

import com.github.Ramble21.classes.days.LogicGate;
import com.github.Ramble21.classes.days.LogicGateInstruction;

import java.io.IOException;
import java.util.*;

public class Day24 extends DaySolver {
    private final List<String> input;

    public Day24() throws IOException {
        input = getInputLines(24);
    }

    public long solvePart1() throws IOException {
        LogicGateInstruction[] instructions = null;
        int i = 0;
        for (int j = 0; j < input.size(); j++) {
            String s = input.get(j);
            if (s.length() == 6) {
                new LogicGate(s.substring(0, 3), Integer.parseInt(s.substring(5)));
            } else if (s.length() > 6) {
                assert instructions != null;
                instructions[i] = new LogicGateInstruction(s);
                i++;
            } else {
                instructions = new LogicGateInstruction[input.size() - j - 1];
            }
        }
        assert instructions != null;
        while (containsNull(instructions)) {
            for (LogicGateInstruction instruction : instructions) {
                if (instruction.getResult() == null) instruction.run();
            }
        }

        ArrayList<String> zGates = new ArrayList<>();
        HashMap<String, Integer> map = LogicGate.getLogicGates();
        for (String identifier : map.keySet()) {
            if (identifier.startsWith("z")) zGates.add(identifier);
        }
        zGates.sort((s1, s2) -> {
            int num1 = Integer.parseInt(s1.substring(1));
            int num2 = Integer.parseInt(s2.substring(1));
            return Integer.compare(num2, num1);
        });
        StringBuilder binaryNum = new StringBuilder();
        for (String x : zGates) {
            binaryNum.append(map.get(x));
        }
        return binaryToLong(binaryNum.toString());
    }

    public long solvePart2() throws IOException {
        return 0;
    }

    public boolean containsNull(LogicGateInstruction[] arr) {
        for (LogicGateInstruction l : arr) {
            if (l.getResult() == null) return true;
        }
        return false;
    }

    public long binaryToLong(String binary) {
        long result = 0;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                result = (result << 1) | 1;
            } else {
                result = result << 1;
            }
        }
        return result;
    }
}
