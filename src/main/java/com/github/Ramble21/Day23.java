package com.github.Ramble21;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.github.Ramble21.classes.days.Connection;
import com.github.Ramble21.classes.general.*;

public class Day23 extends DaySolver{
    private final List<String> input;
    public Day23() throws IOException {
        input = getInputLines(23);
    }
    public long solvePart1() throws IOException {
        for (String s : input){
            Connection c  = new Connection(s);
        }
        HashSet<String> sets = Connection.findAllSets();
        sets.removeIf(set -> set.endsWith("f"));
        return sets.size();
    }

    public long solvePart2() throws IOException {
        return 0;
    }
}