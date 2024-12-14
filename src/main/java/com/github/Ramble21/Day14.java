package com.github.Ramble21;

import com.github.Ramble21.classes.BathroomRobot;
import com.github.Ramble21.classes.Regex;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day14 extends DaySolver{
    private final List<String> input;
    public Day14() throws IOException {
        input = getInputLines(14);
    }
    public int solvePart1() throws IOException {
        BathroomRobot[] robots = new BathroomRobot[input.size()];
        for (int i = 0; i < input.size(); i++){
            int[] data = Regex.parseFirstFourIntegers(input.get(i));
            BathroomRobot robot = new BathroomRobot(data[0], data[1], data[2], data[3]);
            robots[i] = robot;
        }
        for (BathroomRobot robot : robots){
            robot.predictPosition(100);
        }
        print2DArr(BathroomRobot.getGrid());
        int[] quadrants = BathroomRobot.countRobotsInQuadrants();
        System.out.println(Arrays.toString(quadrants));
        return quadrants[0] * quadrants[1] * quadrants[2] * quadrants[3];
    }

    public int solvePart2() throws IOException {
        return 0;
    }
    public static void print2DArr(char[][] array) {
        for (char[] row : array) {
            System.out.println(row);
        }
    }
}