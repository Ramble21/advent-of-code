package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;

import java.io.IOException;
import java.util.List;

public class Day3 extends DaySolver {
    private final List<String> input;
    private final char[][] grid;
    public Day3() throws IOException {
        input = getInputLines(2023, 3);
        grid = new char[input.size()][input.get(0).length()];
        for (int r = 0; r < grid.length; r++){
            for (int c = 0; c < grid[0].length; c++){
                grid[r][c] = input.get(r).charAt(c);
            }
        }
    }
    public long solvePart1() throws IOException {
        long sum = 0;
        for (int r = 0; r < grid.length; r++){
            int currentNumStartIndex = -1;
            int currentNumEndIndex = -1;
            for (int c = 0; c <= grid[0].length; c++){
                if (c == grid[0].length || !Character.isDigit(grid[r][c])) {
                    sum += getPartNumber(r, currentNumStartIndex, currentNumEndIndex);
                    currentNumEndIndex = -1;
                    currentNumStartIndex = -1;
                    if (c == grid[0].length) continue;
                    while (c < grid[0].length && !Character.isDigit(grid[r][c])){
                        c++;
                    }
                    c--;
                }
                else if (currentNumStartIndex == -1) {
                    currentNumStartIndex = c;
                    currentNumEndIndex = c;
                }
                else {
                    currentNumEndIndex++;
                }
            }
        }
        return sum;
    }
    private long getPartNumber(int rowNum, int start, int end){
        if (start == -1 || end == -1) return 0;
        int[][] directions = {
                {-1,  0}, {1,  0}, {0, -1}, {0,  1}, {-1, -1}, {-1,  1}, {1, -1}, {1,  1}
        };
        for (int i = start; i <= end; i++) {
            for (int[] dir : directions) {
                int newRow = rowNum + dir[0];
                int newCol = i + dir[1];
                if (isValid(newRow, newCol) && isSpecialCharacter(grid[newRow][newCol])) {
                    return parseCharArr(rowNum, start, end);
                }
            }
        }

        return 0;
    }
    private boolean isValid(int row, int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }
    private boolean isSpecialCharacter(char c) {
        return !Character.isDigit(c) && c != '.';
    }
    private long parseCharArr(int rowNum, int start, int end){
        StringBuilder result = new StringBuilder();
        for (int c = start; c <= end; c++){
            result.append(grid[rowNum][c]);
        }
        return Integer.parseInt(result.toString());
    }

    public long solvePart2() throws IOException {
        return 0;
    }
}