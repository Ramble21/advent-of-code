package com.github.Ramble21;

import java.io.IOException;
import java.util.List;

public class Day3 extends DaySolver{
    private final List<String> input;
    public Day3() throws IOException {
        input = getInputLines(3);
    }
    public long solvePart1() throws IOException {
        int sum = 0;
        StringBuilder wallOfText = new StringBuilder();
        for (String line : input){
            wallOfText.append(line);
        }
        for (int i = 0; i < wallOfText.length()-4; i++){
            if (wallOfText.substring(i, i+4).equals("mul(")){
                int number1 = -1;
                int number2StartIndex = -1;
                int number2EndIndex = -1;
                int number2 = -1;
                for (int j = 1; j <= 3; j++){ // 1 to 3-digit numbers
                    if (canParseInt(wallOfText.substring(i+4, i+4+j))) {
                        number1 = Integer.parseInt(wallOfText.substring(i+4, i+4+j));
                        number2StartIndex = i+4+j+1;
                    }
                }
                if (number2StartIndex == -1) continue;
                for (int j = 1; j <= 3; j++){
                    if (canParseInt(wallOfText.substring(number2StartIndex, number2StartIndex+j))) {
                        number2 = Integer.parseInt(wallOfText.substring(number2StartIndex, number2StartIndex+j));
                        number2EndIndex = number2StartIndex + j;
                    }
                }
                if (number2 == -1 || !(wallOfText.charAt(number2EndIndex) == ')')) continue;
                sum += number1 * number2;
            }
        }
        return sum;
    }
    public boolean canParseInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public long solvePart2() throws IOException {
        int sum = 0;
        boolean isDisabled = false;
        StringBuilder wallOfText = new StringBuilder();
        for (String line : input){
            wallOfText.append(line);
        }
        for (int i = 0; i < wallOfText.length()-6; i++){
            if (wallOfText.substring(i, i+4).equals("do()")){
                isDisabled = false;
            }
            else if (wallOfText.substring(i, i+7).equals("don't()")){
                isDisabled = true;
            }
            else if (wallOfText.substring(i, i+4).equals("mul(") && !isDisabled){
                int number1 = -1;
                int number2StartIndex = -1;
                int number2EndIndex = -1;
                int number2 = -1;
                for (int j = 1; j <= 3; j++){ // 1 to 3-digit numbers
                    if (canParseInt(wallOfText.substring(i+4, i+4+j))) {
                        number1 = Integer.parseInt(wallOfText.substring(i+4, i+4+j));
                        number2StartIndex = i+4+j+1;
                    }
                }
                if (number2StartIndex == -1) continue;
                for (int j = 1; j <= 3; j++){
                    if (canParseInt(wallOfText.substring(number2StartIndex, number2StartIndex+j))) {
                        number2 = Integer.parseInt(wallOfText.substring(number2StartIndex, number2StartIndex+j));
                        number2EndIndex = number2StartIndex + j;
                    }
                }
                if (number2 == -1 || !(wallOfText.charAt(number2EndIndex) == ')')) continue;
                sum += number1 * number2;
            }
        }
        return sum;
    }
}
