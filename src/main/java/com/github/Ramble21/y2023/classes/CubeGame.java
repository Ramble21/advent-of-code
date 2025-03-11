package com.github.Ramble21.y2023.classes;

import java.util.HashMap;

public class CubeGame {
    private final int ID;
    private final HashMap<String, Integer> maxDiceShown = new HashMap<>();
    public CubeGame(String inputLine) {
        ID = Integer.parseInt(inputLine.substring(inputLine.indexOf(" ") + 1, inputLine.indexOf(":")));
        for (String round : inputLine.split(", |; |: ")){
            if (round.startsWith("Game")) continue;
            String color = round.substring(round.indexOf(" ") + 1);
            int number = Integer.parseInt(round.substring(0, round.indexOf(" ")));
            maxDiceShown.put(color, Math.max(maxDiceShown.getOrDefault(color, 0), number));
        }
    }
    public HashMap<String, Integer> getMaxDiceShown() {
        return maxDiceShown;
    }
    public int getID() {
        return ID;
    }
}
