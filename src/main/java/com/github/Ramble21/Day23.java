package com.github.Ramble21;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

import com.github.Ramble21.classes.days.Connection;

public class Day23 extends DaySolver{
    private final List<String> input;
    private final HashSet<String> result = new HashSet<>();
    private String part2;
    public Day23() throws IOException {
        input = getInputLines(23);
    }
    public long solvePart1() throws IOException {
        for (String s : input){
            new Connection(s);
        }
        HashSet<String> sets = Connection.findAllSets();
        result.addAll(sets);
        sets.removeIf(set -> set.endsWith("|"));
        return sets.size();
    }

    public long solvePart2() throws IOException {
        part2 = Connection.findLongestSet(result);
        return 0; // String needed
    }
    public String solvePart2String() throws IOException {
        timePart2();
        return part2;
    }
}