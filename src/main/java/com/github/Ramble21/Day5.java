package com.github.Ramble21;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 extends DaySolver {
    private final List<String> input;
    private final List<String> orderingRules = new ArrayList<>();
    private final List<String> updates = new ArrayList<>();

    public Day5() throws IOException {
        input = getInputLines(5);
        splitInput();
    }

    public long solvePart1() throws IOException {
        int totalMiddles = 0;
        for (String update : updates) {
            boolean works = true;
            for (String rule : orderingRules) {
                String num1 = (rule.substring(0, 2));
                String num2 = (rule.substring(3));
                if (update.contains(num1) && update.contains(num2)) {
                    if (!(update.indexOf(num1) < update.indexOf(num2))) {
                        works = false;
                        break;
                    }
                }
            }
            if (works) {
                String[] updateAsArr = update.split(",");
                totalMiddles += Integer.parseInt(updateAsArr[(updateAsArr.length / 2)]);
            }
        }
        return totalMiddles;
    }

    public long solvePart2() throws IOException {
        int totalMiddles = 0;
        for (String update : updates) {

            List<String> updateList = Arrays.asList(update.split(","));
            List<String> originalList = new ArrayList<>(updateList);

            updateList.sort((a, b) -> {
                for (String rule : orderingRules) {
                    String num1 = rule.substring(0, 2);
                    String num2 = rule.substring(3);

                    if (a.equals(num1) && b.equals(num2)) return -1;
                    if (a.equals(num2) && b.equals(num1)) return 1;
                }
                return 0;
            });
            if (!originalList.equals(updateList)) {
                totalMiddles += Integer.parseInt(updateList.get(updateList.size() / 2));
            }
        }
        return totalMiddles;
    }

    public void splitInput() {
        for (String s : input) {
            if (s.contains("|")) {
                orderingRules.add(s);
            } else if (!s.isEmpty()) {
                updates.add(s);
            }
        }
    }
}