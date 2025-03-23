package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.helper_classes.Direction;
import com.github.Ramble21.helper_classes.Location;

import java.io.IOException;

public class Day14 extends DaySolver {
    private final char[][] grid;
    public Day14() throws IOException {
        grid = getInputAsGrid(2023, 14);
    }
    public long solvePart1() throws IOException {
        int sum = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 'O') {
                    Location result = shiftNorth(new Location(c , r));
                    sum += grid.length - result.y;
                }
            }
        }
        return sum;
    }
    public long solvePart2() throws IOException {
        return 0;
    }
    private Location shiftNorth(Location l) {
        Location shifted = new Location(l.x, l.y);
        while (true) {
            Location next = shifted.getDirectionalLoc(Direction.UP);
            if (!next.isOnGrid(grid) || grid[next.y][next.x] != '.') {
                break;
            }
            shifted = next;
        }
        grid[l.y][l.x] = '.';
        grid[shifted.y][shifted.x] = 'O';
        return shifted;
    }
}