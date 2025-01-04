package com.github.Ramble21;

import com.github.Ramble21.classes.Maze;

import java.io.IOException;
import java.util.List;

public class Day16 extends DaySolver{
    private final List<String> input;
    private Maze maze;
    private char[][] grid;
    public Day16() throws IOException {
        input = getInputLines(16);
        inputToGrid();
        initializeMaze();
    }
    public int solvePart1() throws IOException {
        return maze.getTotalPoints();
    }

    public int solvePart2() throws IOException {
        maze.findAllPaths();
        return maze.getTotalLocationsInBestPaths();
    }

    public void inputToGrid(){
        grid = new char[input.size()][input.get(0).length()];
        for (int r = 0; r < input.size(); r++){
            for (int c = 0; c < input.get(0).length(); c++){
                grid[r][c] = input.get(r).charAt(c);
            }
        }
    }
    public void initializeMaze(){
        maze = new Maze(grid);
        while (!maze.hasFinishedAllNodes()){
            maze.executeAlgorithm();
        }
    }
}