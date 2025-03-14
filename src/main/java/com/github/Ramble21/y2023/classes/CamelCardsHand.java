package com.github.Ramble21.y2023.classes;

import java.util.HashMap;

public class CamelCardsHand {
    private final CamelCard[] hand;
    private final int bid;
    public CamelCardsHand(String inputLine){
        char[] handAsChars = inputLine.substring(0, inputLine.indexOf(" ")).toCharArray();
        hand = new CamelCard[handAsChars.length];
        for (int i = 0; i < hand.length; i++){
            hand[i] = new CamelCard(handAsChars[i]);
        }
        bid = Integer.parseInt(inputLine.substring(inputLine.indexOf(" ") + 1));
    }
    public int getType(boolean jacksAreJokers){
        HashMap<Integer, Integer> counter = new HashMap<>();
        for (CamelCard card : hand){
            counter.put(card.getValue(), counter.getOrDefault(card.getValue(), 0) + 1);
        }
        int numJokers = 0;
        if (jacksAreJokers && counter.containsKey(11)) numJokers = counter.remove(11);
        int maxOfKind = 0;
        int numPairs = 0;
        for (int value : counter.values()){
            maxOfKind = Math.max(maxOfKind, value);
            if (value > 1) numPairs++;
        }
        maxOfKind += numJokers;
        if (maxOfKind == 5) return 6;
        if (maxOfKind == 4) return 5;
        if (maxOfKind == 3 && numPairs == 2) return 4;
        if (maxOfKind == 3) return 3;
        if (numPairs == 2) return 2;
        if (maxOfKind == 2) return 1;
        return 0;
    }
    public int getBid(){
        return bid;
    }
    public int[] getComparator(boolean jacksAreJokers){
        int[] result = new int[hand.length];
        for (int i = 0; i < result.length; i++){
            result[i] = hand[i].getValue();
            if (jacksAreJokers && result[i] == 11) result[i] = 1;
        }
        return result;
    }
}
class CamelCard {
    private final int value;
    public CamelCard(char c){
        if (Character.isDigit(c)) value = c - '0';
        else value = switch (c){
            case 'T' -> 10;
            case 'J' -> 11;
            case 'Q' -> 12;
            case 'K' -> 13;
            case 'A' -> 14;
            default -> throw new IllegalArgumentException();
        };
    }
    public int getValue(){
        return value;
    }
}
