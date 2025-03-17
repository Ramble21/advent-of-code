package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.y2023.classes.SpringConditionRecord;

import java.io.IOException;
import java.util.List;

public class Day12 extends DaySolver {
    private final SpringConditionRecord[] records;
    public Day12() throws IOException {
        List<String> input = getInputLines(2023, 12);
        records = new SpringConditionRecord[input.size()];
        for (int i = 0; i < input.size(); i++) {
            records[i] = new SpringConditionRecord(input.get(i));
        }
    }
    public long solvePart1() throws IOException {
        int sum = 0;
        for (SpringConditionRecord record : records) {
            sum += record.getNumPossibilities();
        }
        return sum;
    }

    public long solvePart2() throws IOException {
        return 0;
    }
}