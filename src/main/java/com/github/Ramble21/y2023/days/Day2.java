package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.y2023.classes.CubeGame;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Day2 extends DaySolver {
    private final List<String> input;
    public Day2() throws IOException {
        input = getInputLines(2023, 2);
    }
    public long solvePart1() throws IOException {
        long total = 0;
        for (String s : input){
            CubeGame game = new CubeGame(s);
            HashMap<String, Integer> maxDiceShown = game.getMaxDiceShown();
            if (maxDiceShown.get("red") <= 12 && maxDiceShown.get("green") <= 13 && maxDiceShown.get("blue") <= 14){
                total += game.getID();
            }
        }
        return total;
    }

    public long solvePart2() throws IOException {
        long total = 0;
        for (String s : input){
            HashMap<String, Integer> maxDiceShown = new CubeGame(s).getMaxDiceShown();
            total += (long) maxDiceShown.get("red") * maxDiceShown.get("green") * maxDiceShown.get("blue");
        }
        return total;
    }
}