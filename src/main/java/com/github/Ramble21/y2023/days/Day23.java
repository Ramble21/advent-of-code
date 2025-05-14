package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.helper_classes.Direction;
import com.github.Ramble21.helper_classes.Location;

import javax.swing.plaf.IconUIResource;
import java.io.IOException;
import java.util.*;

public class Day23 extends DaySolver {
    private final char[][] grid;
    private Location start;
    private Location end;
    public Day23() throws IOException {
        grid = getInputAsGrid(2023, 23);
        for (int c = 0; c < grid[0].length; c++) {
            if (grid[0][c] != '#') {
                start = new Location(c, 0);
            }
            if (grid[grid.length - 1][c] != '#') {
                end = new Location(c, grid.length - 1);
            }
        }
    }
    public long solvePart1() throws IOException {
        return dfsLongestPathPart1(start, new HashSet<>());
    }
    public long solvePart2() throws IOException {
        return dfsLongestPathPart2(start, new HashSet<>());
    }
    private int dfsLongestPathPart2(Location current, HashSet<Location> visited) {
        return 0;
    }
    private int dfsLongestPathPart1(Location current, HashSet<Location> visited) {
        if (current.equals(end)) {
            return 0;
        }
        ArrayList<Location> neighbors = getNeighbors(current);
        visited.add(current);
        int max = 0;
        for (Location neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                max = Math.max(max, dfsLongestPathPart1(neighbor, visited) + 1);
            }
        }
        visited.remove(current);
        return max;
    }
    private ArrayList<Location> getNeighbors(Location current) {
        ArrayList<Location> result = Location.getNeighbors(current, grid);
        if (grid[current.y][current.x] != '.') {
            result = new ArrayList<>(List.of(current.getDirectionalLoc(Direction.charToDir(grid[current.y][current.x]))));
        }
        result.removeIf(l -> grid[l.y][l.x] == '#');
        return result;
    }

}

