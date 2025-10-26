package com.github.ramble21.y2024.days;

import com.github.ramble21.DaySolver;
import com.github.ramble21.y2024.classes.Calibrator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day7 extends DaySolver {
    private final ArrayList<Calibrator> calibrators = new ArrayList<>();
    public Day7() throws IOException {
        List<String> input = getInputLines(2024, 7);
        for (String s : input){
            calibrators.add(new Calibrator(s));
        }
    }
    public long solvePart1() throws IOException {
        long total = 0;
        for (Calibrator c : calibrators){
            total += c.getCalibrationResult(false);
        }
        return total;
    }
    public long solvePart2() throws IOException {
        long total = 0;
        for (Calibrator c : calibrators){
            total += c.getCalibrationResult(true);
        }
        return total;
    }
}