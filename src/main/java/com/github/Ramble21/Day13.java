package com.github.Ramble21;

import com.github.Ramble21.classes.days.ClawButton;
import com.github.Ramble21.classes.days.Prize;
import com.github.Ramble21.classes.general.Regex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Day13 extends DaySolver{
    private final ArrayList<String> inputWithoutEmpty;
    private ClawButton[] buttonsA;
    private ClawButton[] buttonsB;
    private Prize[] prizes;
    public Day13() throws IOException {
        List<String> input = getInputLines(13);
        inputWithoutEmpty = new ArrayList<>(); // input is immutable
        inputWithoutEmpty.addAll(input);
        inputWithoutEmpty.removeIf(s -> s.isEmpty() || s.isBlank());
        initializeArrays();
    }
    public long solvePart1() throws IOException {
        int count = 0;
        for (int i = 0; i < prizes.length; i++){
            count += getMinTokens(i);
        }
        return count;
    }
    public long solvePart2(){
        long count = 0;
        for (int i = 0; i < prizes.length; i++){
            count += getMinTokensNoCap(i);
        }
        return count;
    }
    public int getMinTokens(int i){
        ArrayList<Prize> workingPairs = new ArrayList<>();
        int xCoeff1 = buttonsA[i].getDeltaX();
        int xCoeff2 = buttonsB[i].getDeltaX();
        int yCoeff1 = buttonsA[i].getDeltaY();
        int yCoeff2 = buttonsB[i].getDeltaY();
        int xGoal = prizes[i].getX();
        int yGoal = prizes[i].getY();
        for (int a = 0; a <= 100; a++){
            for (int b = 0; b <= 100; b++){
                if (a*xCoeff1 + b*xCoeff2 == xGoal && a*yCoeff1 + b*yCoeff2 == yGoal){
                    workingPairs.add(new Prize(a,b)); // represents ordered pair with x being amount of times to hit a, y being amount of times to hit b
                }
            }
        }
        if (workingPairs.isEmpty()) return 0;
        int minTokens = workingPairs.get(0).getX()*3 + workingPairs.get(0).getY();
        for (Prize workingPair : workingPairs) {
            if (minTokens < workingPair.getX() * 3 + workingPair.getY())
                minTokens = workingPair.getX() * 3 + workingPair.getY();
        }
        return minTokens;
    }
    public long getMinTokensNoCap(int i){
        long[] aAndB = solveForAAndB(i);
        return aAndB[0]*3 + aAndB[1];
    }
    public void initializeArrays(){
        buttonsA = new ClawButton[inputWithoutEmpty.size()/3];
        buttonsB = new ClawButton[inputWithoutEmpty.size()/3];
        prizes = new Prize[inputWithoutEmpty.size()/3];
        int j = 0;
        for (int i = 0; i < inputWithoutEmpty.size(); i+=3){
            buttonsA[j] = parseButton(inputWithoutEmpty.get(i), 'A');
            buttonsB[j] = parseButton(inputWithoutEmpty.get(i+1), 'B');
            prizes[j] = parsePrize(inputWithoutEmpty.get(i+2));
            j++;
        }
    }
    public ClawButton parseButton(String s, char name){
        int[] nums = Regex.parseFirstTwoIntegers(s);
        return new ClawButton(nums[0], nums[1], name);
    }
    public Prize parsePrize(String s){
        int[] nums = Regex.parseFirstTwoIntegers(s);
        return new Prize(nums[0], nums[1]);
    }
    public long[] solveForAAndB(int i) {
        int xCoeff1 = buttonsA[i].getDeltaX();
        int xCoeff2 = buttonsB[i].getDeltaX();
        int yCoeff1 = buttonsA[i].getDeltaY();
        int yCoeff2 = buttonsB[i].getDeltaY();
        long xGoal = prizes[i].getMassiveX();
        long yGoal = prizes[i].getMassiveY();

        long determinant = (long) xCoeff1 * yCoeff2 - (long) xCoeff2 * yCoeff1;

        if (determinant == 0) {
            return new long[]{0, 0};
        }

        long aNumerator = xGoal * yCoeff2 - xCoeff2 * yGoal;
        long bNumerator = xCoeff1 * yGoal - xGoal * yCoeff1;

        if (aNumerator % determinant != 0 || bNumerator % determinant != 0) {
            return new long[]{0, 0};
        }

        long a = aNumerator / determinant;
        long b = bNumerator / determinant;
        return new long[]{a, b};
    }
}