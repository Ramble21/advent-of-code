package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;

import java.io.IOException;
import java.util.*;

public class Day15 extends DaySolver {
    private final List<String> input;
    private final String[] references;
    private final int[] values;
    public Day15() throws IOException {
        input = getInputLines(2023, 15);
        references = input.get(0).split(",");
        values = new int[references.length];
    }
    public long solvePart1() throws IOException {
        int total = 0;
        for (int i = 0; i < references.length; i++) {
            total += hash(references[i]);
            values[i] = hash(getHeader(references[i]));
        }
        return total;
    }

    public long solvePart2() throws IOException {
        HashMap<Integer, LinkedList<String>> boxes = new HashMap<>();
        HashMap<String, Integer> focalLengths = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            String lens = references[i];
            String lensHeader = getHeader(lens);
            int boxNo = values[i];
            if (lens.charAt(lens.length() - 1) == '-') {
                if (boxes.containsKey(boxNo)) {
                    boxes.get(boxNo).remove(lensHeader);
                }
            }
            else {
                focalLengths.put(lensHeader, Integer.parseInt(lens.substring(lens.indexOf("=") + 1)));

                LinkedList<String> box = boxes.getOrDefault(boxNo, new LinkedList<>());
                boolean skibidiToilet = box.contains(lensHeader);
                if (!skibidiToilet) box.addLast(lensHeader);
                boxes.put(boxNo, box);
            }
        }
        int focusingPower = 0;
        for (Map.Entry<Integer, LinkedList<String>> box : boxes.entrySet()) {
            int boxNo = box.getKey();
            LinkedList<String> sussy = box.getValue();
            for (int i = 0; i < sussy.size(); i++) {
                focusingPower += (1 + boxNo) * (1 + i) * focalLengths.get(sussy.get(i));
            }
        }
        return focusingPower;
    }

    private int hash(String s) {
        int current = 0;
        for (char c : s.toCharArray()) {
            current += c;
            current = (current * 17) % 256;
        }
        return current;
    }
    private String getHeader(String s) {
        if (s.contains("=")) {
            return s.substring(0, s.indexOf("="));
        }
        return s.substring(0, s.indexOf("-"));
    }
}