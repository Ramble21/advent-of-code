package com.github.Ramble21;

import com.github.Ramble21.classes.Location;

import java.io.IOException;
import java.util.List;

public class Day15 extends DaySolver{
    private final List<String> input;
    private String movements;
    private char[][] grid;

    public Day15() throws IOException {
        input = getInputLines(15);
        splitInput();
    }
    public int solvePart1() throws IOException {
        for (int i = 0; i < movements.length(); i++){
            Location currentLoc = null;
            for (int r = 0; r < grid.length; r++){
                for (int c = 0; c < grid[0].length; c++){
                    if (grid[r][c] == '@'){
                        currentLoc = new Location(c, r);
                        break;
                    }
                }
            }
            assert currentLoc != null;
            moveRobot(movements.charAt(i), currentLoc);
            //System.out.println(movements.charAt(i));
            //print2DArr(grid);
            //System.out.println();
        }
        int sum = 0;
        for (int r = 0; r < grid.length; r++){
            for (int c = 0; c < grid[0].length; c++){
                if (grid[r][c] == 'O'){
                    sum += 100*r + c;
                }
            }
        }
        System.out.println(movements);
        return sum;
    }

    public int solvePart2() throws IOException {
        return 0;
    }
    public void moveRobot(char direction, Location currentLoc){
        if (direction != '^' && direction != 'v' && direction != '<' && direction != '>') return;

        int deltaX = 0;
        int deltaY = 0;

        if (direction == '^') deltaY = -1;
        else if (direction == 'v') deltaY = 1;
        else if (direction == '<') deltaX = -1;
        else deltaX = 1;

        int currentX = currentLoc.getX();
        int currentY = currentLoc.getY();
        tryToMove(currentX, currentY, deltaX, deltaY);
    }
    public void tryToMove(int currentX, int currentY, int deltaX, int deltaY){
        int targetY = currentY+deltaY;
        int targetX = currentX+deltaX;
        if (grid[targetY][targetX] == '.'){
            grid[targetY][targetX] = '@';
            grid[currentY][currentX] = '.';
        }
        else if (grid[targetY][targetX] == '#'){
            return;
        }
        else{
            boolean moved = tryToMoveBox(targetX, targetY, deltaX, deltaY, '.');
            if (moved){
                grid[targetY][targetX] = '@';
                grid[currentY][currentX] = '.';
            }
        }
    }
    public boolean tryToMoveBox(int boxX, int boxY, int deltaX, int deltaY, char currentChar){
        int targetY = boxY+deltaY;
        int targetX = boxX+deltaX;

        if (grid[targetY][targetX] == '.'){
            grid[targetY][targetX] = 'O';
            grid[boxY][boxX] = currentChar;
            return true;
        }
        else if (grid[targetY][targetX] == '#'){
            return false;
        }
        else{
            return tryToMoveBox(targetX, targetY, deltaX, deltaY, 'O');
        }
    }
    public void splitInput(){
        int newLineIndex = -1;
        for (int i = 0; i < input.size(); i++){
            if (input.get(i).isBlank()){
                newLineIndex = i;
                break;
            }
        }
        grid = new char[newLineIndex][input.get(0).length()];
        for (int r = 0; r < grid.length; r++){
            for (int c = 0; c < grid[0].length; c++){
                grid[r][c] = input.get(r).charAt(c);
            }
        }
        StringBuilder s = new StringBuilder();
        for (int i = newLineIndex+1; i < input.size(); i++){
            s.append(input.get(i));
        }
        movements = s.toString();
    }
    public static void print2DArr(char[][] array) {
        for (char[] row : array) {
            System.out.println(row);
        }
    }
}