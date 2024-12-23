package com.github.Ramble21;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class Day6 extends DaySolver{
    private final List<String> input;
    private char[][] grid;
    private final HashSet<String> alreadyDone;
    public Day6() throws IOException {
        input = getInputLines(6);
        inputToGrid();
        alreadyDone = new HashSet<>();
    }
    public int solvePart1() throws IOException {
        String location = getStartingLoc();
        String previousLocation;
        int count = 0;
        int rotation = 0; // 0 = ^, 1 = >, 2 = v, 3 = <, modulus applied
        while (true){
            previousLocation = location;
            location = getNextLoc(rotation, location);
            String[] locationArr = location.split(",");

            if (Integer.parseInt(locationArr[0]) >= grid.length ||
                Integer.parseInt(locationArr[0]) < 0 ||
                Integer.parseInt(locationArr[1]) >= grid[0].length ||
                Integer.parseInt(locationArr[1]) < 0
            ){
                break;
            }

            char currentChar = grid[Integer.parseInt(locationArr[0])][Integer.parseInt(locationArr[1])];
            // System.out.println(location +  " " + getRotationSymbol(rotation) + " " + currentChar);
            if (currentChar == '#'){
                location = previousLocation;
                rotation++;
            }
            else {
                if (!alreadyDone.contains(location)){
                    count++;
                    alreadyDone.add(location);
                }
            }
        }
        return count;
    }

    public int solvePart2() throws IOException {
        int count = 0;
        for (String location : alreadyDone) {
            int r = Integer.parseInt(location.split(",")[0]);
            int c = Integer.parseInt(location.split(",")[1]);
            if (grid[r][c] != '.') continue;

            char[][] edited = new char[grid.length][];
            for (int i = 0; i < grid.length; i++) {
                edited[i] = grid[i].clone();
            }

            edited[r][c] = '#';
            if (checkGrid(edited)){
                count++;
//                System.out.println(r + "," + c + " works");
            }
//            else{
//                System.out.println(r + "," + c + " doesn't work");
//            }
//            print2DArray(edited);
        }
        return count;
    }

    public void inputToGrid(){
        char[][] g = new char[input.size()][input.get(0).length()];
        for (int r = 0; r < g.length; r++){
            for (int c = 0; c < input.get(1).length(); c++){
                g[r][c] = input.get(r).charAt(c);
            }
        }
        grid = g;
    }
    public String getStartingLoc(){
        for (int r = 0; r < grid.length; r++){
            for (int c = 0; c < grid[0].length; c++){
                if (grid[r][c] == '^') return r + "," + c;
            }
        }
        return "bug";
    }
    public String getNextLoc(int rotation, String location){
        int trueRot = rotation % 4;
        String[] locAsArr = location.split(",");
        int commaIndex = location.indexOf(",");
        if (trueRot == 0){
            return (Integer.parseInt(location.substring(0, commaIndex))-1) + "," + location.substring(commaIndex+1);
        }
        else if (trueRot == 1){
            return (Integer.parseInt(location.substring(0, commaIndex)) + "," + (Integer.parseInt(location.substring(commaIndex+1))+1));
        }
        else if (trueRot == 2){
            return (Integer.parseInt(location.substring(0, commaIndex))+1 + "," + location.substring(commaIndex+1));
        }
        else if (trueRot == 3){
            return (Integer.parseInt(location.substring(0, commaIndex)) + "," + (Integer.parseInt(location.substring(commaIndex+1))-1));
        }
        else return "bug";
    }
    public String getRotationSymbol(int rotation){
        return switch (rotation % 4) {
            case 0 -> "^";
            case 1 -> ">";
            case 2 -> "v";
            case 3 -> "<";
            default -> "bug";
        };
    }
    public boolean checkGrid(char[][] newGrid){
        String location = getStartingLoc();
        String previousLocation;
        int rotation = 0; // 0 = ^, 1 = >, 2 = v, 3 = <, modulus applied
        int iterations = 0;

        while (iterations < 20000){
            iterations++;
            previousLocation = location;
            location = getNextLoc(rotation, location);
            String[] locationArr = location.split(",");

            if (Integer.parseInt(locationArr[0]) >= newGrid.length ||
                    Integer.parseInt(locationArr[0]) < 0 ||
                    Integer.parseInt(locationArr[1]) >= newGrid[0].length ||
                    Integer.parseInt(locationArr[1]) < 0
            ){
                return false;
            }

            char currentChar = newGrid[Integer.parseInt(locationArr[0])][Integer.parseInt(locationArr[1])];
            if (currentChar == '#'){
                location = previousLocation;
                rotation++;
            }
            if (currentChar == '.') newGrid[Integer.parseInt(locationArr[0])][Integer.parseInt(locationArr[1])] = 'X';
        }
        return true;

    }
}