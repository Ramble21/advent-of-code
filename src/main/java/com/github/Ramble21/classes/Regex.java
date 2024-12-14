package com.github.Ramble21.classes;

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
}
