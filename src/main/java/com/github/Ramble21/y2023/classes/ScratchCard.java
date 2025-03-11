package com.github.Ramble21.y2023.classes;

import java.util.Arrays;

public class ScratchCard {
    private final int ID;
    private final int[] winningNumbers;
    private final int[] actualNumbers;
    public ScratchCard(String inputLine) {
        // 2023: Advent of Parsing
        ID = Integer.parseInt(inputLine.substring(inputLine.indexOf(" ") + 1, inputLine.indexOf(":")).trim());
        winningNumbers = Arrays.stream(inputLine.substring(inputLine.indexOf(":") + 1, inputLine.indexOf("|")).trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        actualNumbers = Arrays.stream(inputLine.substring(inputLine.indexOf("|") + 1).trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
    }
    public int getPoints(){
        int count = 0;
        for (int x : winningNumbers){
            for (int y : actualNumbers){
                if (x == y){
                    if (count == 0) count = 1;
                    else count *= 2;
                    break;
                }
            }
        }
        return count;
    }
}
