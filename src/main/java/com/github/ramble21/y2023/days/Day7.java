package com.github.ramble21.y2023.days;

import com.github.ramble21.DaySolver;
import com.github.ramble21.y2023.classes.CamelCardsHand;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day7 extends DaySolver {
    private final CamelCardsHand[] hands;
    public Day7() throws IOException {
        List<String> input = getInputLines(2023, 7);
        hands = new CamelCardsHand[input.size()];
        for (int i = 0; i < input.size(); i++){
            hands[i] = new CamelCardsHand(input.get(i));
        }
    }
    public long solvePart1() throws IOException {
        int sum = 0;
        Arrays.sort(hands, Comparator
                .comparingInt((CamelCardsHand hand) -> hand.getType(false))
                .thenComparing(hand -> hand.getComparator(false), Arrays::compare)
        );
        for (int i = 0; i < hands.length; i++){
            sum += hands[i].getBid() * (i + 1);
        }
        return sum;
    }

    public long solvePart2() throws IOException {
        int sum = 0;
        Arrays.sort(hands, Comparator
                .comparingInt((CamelCardsHand hand) -> hand.getType(true))
                .thenComparing(hand -> hand.getComparator(true), Arrays::compare)
        );
        for (int i = 0; i < hands.length; i++){
            sum += hands[i].getBid() * (i + 1);
        }
        return sum;
    }
}