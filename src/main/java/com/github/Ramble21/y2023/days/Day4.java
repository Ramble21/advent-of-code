package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.y2023.classes.ScratchCard;

import java.io.IOException;
import java.util.List;

public class Day4 extends DaySolver {
    private final List<String> input;
    public Day4() throws IOException {
        input = getInputLines(2023, 4);
    }
    public long solvePart1() throws IOException {
        long count = 0;
        for (String s : input){
            count += new ScratchCard(s).getPoints();
        }
        return count;
    }

    public long solvePart2() throws IOException {
        return 0;
    }
}