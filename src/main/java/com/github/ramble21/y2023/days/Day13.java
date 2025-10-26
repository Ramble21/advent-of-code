package com.github.ramble21.y2023.days;

import com.github.ramble21.DaySolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13 extends DaySolver {
    private final ArrayList<char[][]> grids = new ArrayList<>();

    public Day13() throws IOException {
        List<String> input = getInputLines(2023, 13);
        ArrayList<char[]> temp2D = new ArrayList<>();
        for (int i = 0; i <= input.size(); i++) {
            if (i == input.size() || input.get(i).isBlank()) {
                grids.add(temp2D.toArray(new char[0][]));
                temp2D.clear();
            }
            else {
                temp2D.add(input.get(i).toCharArray());
            }
        }
    }
    public long solvePart1() throws IOException {
        long sum = 0;
        for (char[][] grid : grids) {
            sum += getReflectionValue(grid, -1);
        }
        return sum;
    }

    public long solvePart2() throws IOException {
        long sum = 0;
        for (char[][] grid : grids) {
            sum += getAltReflectionValue(grid);
        }
        return sum;
    }
    private int getAltReflectionValue(char[][] grid) {
        int reflectionValue = getReflectionValue(grid, -1);
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                int temp = getReflectionValue(swapLocation(grid, c, r), reflectionValue);
                if (temp != 0) {
                    return temp;
                }
            }
        }
        return reflectionValue;
    }
    private char[][] swapLocation(char[][] grid, int x, int y) {
        char[][] result = new char[grid.length][grid[0].length];
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (y == r && x == c) {
                    if (grid[r][c] == '#') result[r][c] = '.';
                    else result[r][c] = '#';
                }
                else {
                    result[r][c] = grid[r][c];
                }
            }
        }
        return result;
    }
    private int getReflectionValue(char[][] grid, int ignoredValue) {
        for (int r = 0; r < grid.length - 1; r++) {
            if (reflectsHorizontally(grid, r)) {
                int result = (1 + r) * 100;
                if (ignoredValue != result) return result;
            }
        }
        for (int c = 0; c < grid[0].length - 1; c++) {
            if (reflectsVertically(grid, c)) {
                int result = 1 + c;
                if (ignoredValue != result) return result;
            }
        }
        return 0;
    }
    private boolean reflectsHorizontally(char[][] grid, int indexBeforeReflection) {
        int i = indexBeforeReflection, j = indexBeforeReflection + 1;
        while (i >= 0 && j < grid.length) {
            if (!Arrays.equals(grid[i--], grid[j++])) return false;
        }
        return true;
    }
    private boolean reflectsVertically(char[][] grid, int indexBeforeReflection) {
        int i = indexBeforeReflection, j = indexBeforeReflection + 1;
        while (i >= 0 && j < grid[0].length) {
            if (!Arrays.equals(getColumn(grid, i--), getColumn(grid, j++))) return false;
        }
        return true;
    }
    private char[] getColumn(char[][] grid, int colIndex) {
        char[] column = new char[grid.length];
        for (int i = 0; i < grid.length; i++) {
            column[i] = grid[i][colIndex];
        }
        return column;
    }
}