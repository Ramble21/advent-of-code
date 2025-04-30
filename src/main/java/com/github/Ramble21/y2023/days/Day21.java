package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.helper_classes.Location;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Day21 extends DaySolver {
    private final char[][] grid;
    private final Location startingPoint;
    public Day21() throws IOException {
        grid = getInputAsGrid(2023, 21);
        Location startingPoint = null;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 'S') {
                    startingPoint = new Location(c, r);
                }
            }
        }
        assert startingPoint != null;
        this.startingPoint = startingPoint;
    }
    public long solvePart1() throws IOException {
        HashSet<Location> locs = new HashSet<>(Set.of(startingPoint));
        for (int i = 0; i < 64; i++) {
            locs = getNextLocs(locs);
        }
        return locs.size();
    }
    private HashSet<Location> getNextLocs(HashSet<Location> currentLocs) {
        HashSet<Location> nextLocs = new HashSet<>();
        for (Location l : currentLocs) {
            for (Location possibility : Location.getNeighbors(l, grid)) {
                if (grid[possibility.y][possibility.x] != '#') {
                    nextLocs.add(possibility);
                }
            }
        }
        return nextLocs;
    }
    public long solvePart2() throws IOException {
        return 0;
    }
}