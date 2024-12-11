package com.github.Ramble21;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        int totalMiddles = 0;
        for (String update : updates){

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
            if (!originalList.equals(updateList)){
                totalMiddles += Integer.parseInt(updateList.get(updateList.size() / 2));
            }
        }
        return totalMiddles;
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
    public String switchNumLocations(String update, String num1, String num2){
        if (!(update.contains(num1) && update.contains(num2))) return "ERROR";
        String[] arr = update.split(",");
        String[] fixed = new String[arr.length];
        int swap1 = -1;
        int swap2 = -1;
        for (int i = 0; i < arr.length; i++){
            if (arr[i].equals(num1)) swap1 = i;
            else if (arr[i].equals(num2)) swap2 = i;
            else fixed[i] = arr[i];
        }
        fixed[swap1] = arr[swap2];
        fixed[swap2] = arr[swap1];
        StringBuilder result = new StringBuilder(fixed[0]);
        for (int i = 1; i < fixed.length; i++){
            result.append(",").append(fixed[i]);
        }
        return result.toString();
    }

}