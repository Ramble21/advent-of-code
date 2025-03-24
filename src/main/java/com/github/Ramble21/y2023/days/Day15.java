package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;

import java.io.IOException;
import java.util.List;

public class Day15 extends DaySolver {
    private final List<String> input;
    public Day15() throws IOException {
        input = getInputLines(2023, 15);
    }
    public long solvePart1() throws IOException {
        String[] sussy = input.get(0).split(",");
        int total = 0;
        for (String s : sussy) {
            total += hash(s);
        }
        return total;
    }

    public long solvePart2() throws IOException {
        return 0;
    }

    private int hash(String s) {
        int current = 0;
        for (char c : s.toCharArray()) {
            current += c;
            current = (current * 17) % 256;
        }
        return current;
    }
}