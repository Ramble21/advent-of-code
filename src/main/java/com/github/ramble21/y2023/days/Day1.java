package com.github.ramble21.y2023.days;

import com.github.ramble21.DaySolver;

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
        long total = 0;
        for (String s : input){
            StringBuilder calibrationValue = new StringBuilder();
            for (int i = 0; i < s.length(); i++){
                if (Character.isDigit(s.charAt(i))) {
                    calibrationValue.append(s.charAt(i));
                    break;
                }
                int wordNumber = numberAsWord(s, i);
                if (wordNumber != -1){
                    calibrationValue.append(wordNumber);
                    break;
                }
            }
            for (int i = s.length() - 1; i >= 0; i--){
                if (Character.isDigit(s.charAt(i))){
                    calibrationValue.append(s.charAt(i));
                    break;
                }
                int wordNumber = numberAsWord(s, i);
                if (wordNumber != -1){
                    calibrationValue.append(wordNumber);
                    break;
                }
            }
            total += Integer.parseInt(calibrationValue.toString());
        }
        return total;
    }
    private int numberAsWord(String s, int startingIndex){
        if (s.substring(startingIndex).startsWith("one")) return 1;
        if (s.substring(startingIndex).startsWith("two")) return 2;
        if (s.substring(startingIndex).startsWith("three")) return 3;
        if (s.substring(startingIndex).startsWith("four")) return 4;
        if (s.substring(startingIndex).startsWith("five")) return 5;
        if (s.substring(startingIndex).startsWith("six")) return 6;
        if (s.substring(startingIndex).startsWith("seven")) return 7;
        if (s.substring(startingIndex).startsWith("eight")) return 8;
        if (s.substring(startingIndex).startsWith("nine")) return 9;
        return -1;
    }
}