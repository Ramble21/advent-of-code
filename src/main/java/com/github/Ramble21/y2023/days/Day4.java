package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.y2023.classes.ScratchCard;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day4 extends DaySolver {
    private final List<String> input;
    public Day4() throws IOException {
        input = getInputLines(2023, 4);
    }
    public long solvePart1() throws IOException {
        long count = 0;
        for (String s : input){
            count += new ScratchCard(s).getPoints();
        }
        return count;
    }

    public long solvePart2() throws IOException {
        HashMap<Integer, Integer> numCardsWon = ScratchCard.getNumCardsWon();
        int maxNum = 0;
        long count = 0;
        for (int n : numCardsWon.keySet()){
            maxNum = Math.max(n, maxNum);
        }
        for (int x : numCardsWon.keySet()){
            count += addNewCards(x, maxNum, numCardsWon);
        }
        return count;
    }
    private final HashMap<Integer, Integer> memo = new HashMap<>();
    public int addNewCards(int ID, int max, HashMap<Integer, Integer> numCardsWon){
        if (memo.containsKey(ID)){
            return memo.get(ID);
        }
        if (ID > max){
            memo.put(ID, 0);
            return 0;
        }
        if (ID == max){
            memo.put(ID, 1);
            return 1;
        }
        int result = 1;
        for (int i = 1; i <= numCardsWon.get(ID); i++){
            result += addNewCards(ID + i, max, numCardsWon);
        }
        memo.put(ID, result);
        return result;
    }
}