package com.github.Ramble21.classes.general;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static int[] parseFirstTwoIntegers(String input) {
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(input);
        int[] result = new int[2];
        int count = 0;
        while (matcher.find() && count < 2) {
            result[count] = Integer.parseInt(matcher.group());
            count++;
        }
        return result;
    }
    public static int[] parseFirstFourIntegers(String input) {
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(input);
        int[] result = new int[4];
        int count = 0;
        while (matcher.find() && count < 4) {
            result[count] = Integer.parseInt(matcher.group());
            count++;
        }
        return result;
    }
    public static int[] parseAllIntegers(String input){
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(input);
        ArrayList<Integer> tempList = new ArrayList<>();
        while (matcher.find()) {
            tempList.add(Integer.parseInt(matcher.group()));
        }
        int[] result = new int[tempList.size()];
        for (int i = 0; i < tempList.size(); i++) {
            result[i] = tempList.get(i);
        }
        return result;
    }
    public static int parseFirstInteger(String input){
        Pattern pattern = Pattern.compile("-?\\d+");
        Matcher matcher = pattern.matcher(input);
        int[] result = new int[1];
        int count = 0;
        while (matcher.find() && count < 1) {
            result[count] = Integer.parseInt(matcher.group());
            count++;
        }
        return result[0];
    }
}
