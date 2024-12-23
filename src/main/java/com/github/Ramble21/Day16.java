package com.github.Ramble21;

import com.github.Ramble21.classes.Maze;

import java.io.IOException;
import java.util.List;

public class Day16 extends DaySolver{
    private final List<String> input;
    private char[][] grid;
    public Day16() throws IOException {
        input = getInputLines(16);
        inputToGrid();
    }
    public int solvePart1() throws IOException {
        Maze maze = new Maze(grid);
        print2DArr(grid);
        int count = 0;
        while (!maze.hasReachedEnd()){
            maze.executeAlgorithm();
            print2DArr(grid);
            count++;
        }
        return maze.getTotalPoints();
    }

    public int solvePart2() throws IOException {
        return 0;
    }

    public void inputToGrid(){
        grid = new char[input.size()][input.get(0).length()];
        for (int r = 0; r < input.size(); r++){
            for (int c = 0; c < input.get(0).length(); c++){
                grid[r][c] = input.get(r).charAt(c);
            }
        }
    }
}