package com.github.Ramble21;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day5 extends DaySolver{
    private final List<String> input;
    private List<String> orderingRules = new ArrayList<>();
    private List<String> updates = new ArrayList<>();

    public Day5() throws IOException {
        input = getInputLines(5);
        splitInput();
    }
    public int solvePart1() throws IOException {
        System.out.println(orderingRules.size() + " " + updates.size());
        int totalMiddles = 0;
        for (String update : updates){
            boolean works = true;
            for (String rule : orderingRules){
                String num1 = (rule.substring(0, 2));
                String num2 = (rule.substring(3));
                if (update.contains(num1) && update.contains(num2)){
                    if (!(update.indexOf(num1) < update.indexOf(num2))){
                        works = false;
                        break;
                    }
                }
            }
            if (works){
                String[] updateAsArr = update.split(",");
                totalMiddles += Integer.parseInt(updateAsArr[(updateAsArr.length/2)]);
            }
        }
        return totalMiddles;
    }

    public int solvePart2() throws IOException {
        return 0;
    }

    public void splitInput(){
        for (String s : input){
            if (s.contains("|")){
                orderingRules.add(s);
            }
            else if (!s.isEmpty()){
                updates.add(s);
            }
        }
    }

}