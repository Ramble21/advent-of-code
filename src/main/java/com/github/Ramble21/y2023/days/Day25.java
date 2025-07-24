package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.y2023.classes.AbstractGraph;
import com.github.Ramble21.y2023.classes.AbstractGraphEdge;
import com.github.Ramble21.y2023.classes.AbstractGraphNode;

import java.io.IOException;
import java.util.*;

public class Day25 extends DaySolver {
    private final List<String> input;
    private final HashMap<String, HashSet<String>> wiringDiagram = new HashMap<>();
    public Day25() throws IOException {
        input = getInputLines(2023, 25);
    }
    public long solvePart1() throws IOException {
        for (String rawLine : input) {
            String key = rawLine.substring(0, 3);
            HashSet<String> value = new HashSet<>(Arrays.stream(rawLine.substring(5).trim().split(" ")).toList());
            for (String s : value) {
                HashSet<String> curr = wiringDiagram.getOrDefault(s, new HashSet<>());
                curr.add(key);
                wiringDiagram.put(s, curr);
            }
            wiringDiagram.put(key, value);
        }
        int cutSize = Integer.MAX_VALUE;
        int groupProduct = Integer.MAX_VALUE;
        while (cutSize > 3) {
            AbstractGraph g = new AbstractGraph(wiringDiagram);
            cutSize = g.getRandomCut();
            groupProduct = g.getGroupProduct();
        }
        return groupProduct;
    }
    public long solvePart2() throws IOException {
        return 0;
    }
}