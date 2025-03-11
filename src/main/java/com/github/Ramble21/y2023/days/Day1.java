package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;

import java.io.IOException;
import java.util.List;

public class Day1 extends DaySolver {
    private final List<String> input;
    public Day1() throws IOException {
        input = getInputLines(2023, 1);
    }
    public long solvePart1() throws IOException {
        long total = 0;
        for (String s : input){
            StringBuilder calibrationValue = new StringBuilder();
            for (char c : s.toCharArray()){
                if (Character.isDigit(c)) {
                    calibrationValue.append(c);
                    break;
                }
            }
            for (int i = s.length() - 1; i >= 0; i--){
                if (Character.isDigit(s.charAt(i))){
                    calibrationValue.append(s.charAt(i));
                    break;
                }
            }
            total += Integer.parseInt(calibrationValue.toString());
        }
        return total;
    }

    public long solvePart2() throws IOException {
        return 0;
    }
}