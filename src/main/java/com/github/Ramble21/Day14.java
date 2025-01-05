package com.github.Ramble21;

import com.github.Ramble21.classes.day15down.BathroomRobot;
import com.github.Ramble21.classes.general.Regex;

import java.io.IOException;
import java.util.List;

public class Day14 extends DaySolver{
    private final List<String> input;
    public Day14() throws IOException {
        input = getInputLines(14);
    }
    public long solvePart1() throws IOException {
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
        return (int)getDangerLevel();
    }

    public long solvePart2() throws IOException {
        BathroomRobot[] robots = new BathroomRobot[input.size()];
        for (int i = 0; i < input.size(); i++){
            int[] data = Regex.parseFirstFourIntegers(input.get(i));
            BathroomRobot robot = new BathroomRobot(data[0], data[1], data[2], data[3]);
            robots[i] = robot;
        }
        long minDangerLevel = Long.MAX_VALUE;
        int treeIndex = -1;
        for (int i = 0; i < 10000; i++){
            for (BathroomRobot robot : robots){
                robot.predictPosition(1);
            }

            if (getDangerLevel() < minDangerLevel){
                minDangerLevel = getDangerLevel();
                treeIndex = i+1;
                System.out.println(i+1 + " (" + getDangerLevel() + ")");
                print2DArr(BathroomRobot.getGrid());
                System.out.println();
            }
        }
        return treeIndex;
    }
    public long getDangerLevel(){
        long[] quadrants = BathroomRobot.countRobotsInQuadrants();
        return quadrants[0] * quadrants[1] * quadrants[2] * quadrants[3];
    }
}