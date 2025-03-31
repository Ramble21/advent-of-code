package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.helper_classes.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day11 extends DaySolver {
    private final char[][] grid;
    ArrayList<Integer> expandedRows = new ArrayList<>();
    ArrayList<Integer> expandedColumns = new ArrayList<>();
    ArrayList<Location> galaxies = new ArrayList<>();
    public Day11() throws IOException {
        grid = getInputAsGrid(2023, 11);
        expand();
        for (int r = 0; r < grid.length; r++){
            for (int c = 0; c < grid[0].length; c++){
                if (grid[r][c] == '#') galaxies.add(new Location(c, r));
            }
        }
    }
    public long solvePart1() throws IOException {
        long sum = 0;
        HashSet<String> seenPairs = new HashSet<>();
        for (Location loc1 : galaxies){
            for (Location loc2 : galaxies){
                String pairKey = loc1.toString().compareTo(loc2.toString()) < 0 ? loc1 + "-" + loc2 : loc2 + "-" + loc1;
                if (seenPairs.contains(pairKey)) continue;
                seenPairs.add(pairKey);
                sum += getTaxicab(loc1, loc2, false);
            }
        }
        return sum;
    }
    public long solvePart2() throws IOException {
        long sum = 0;
        HashSet<String> seenPairs = new HashSet<>();
        for (Location loc1 : galaxies){
            for (Location loc2 : galaxies){
                String pairKey = loc1.toString().compareTo(loc2.toString()) < 0 ? loc1 + "-" + loc2 : loc2 + "-" + loc1;
                if (seenPairs.contains(pairKey)) continue;
                seenPairs.add(pairKey);
                sum += getTaxicab(loc1, loc2, true);
            }
        }
        return sum;
    }
    private long getTaxicab(Location one, Location two, boolean part2){
        int numExpandedRows = 0;
        int numExpandedColumns = 0;
        for (int y : expandedRows){
            if ((one.y < y && two.y > y) || (two.y < y && one.y > y)) numExpandedRows++;
        }
        for (int x : expandedColumns){
            if ((one.x < x && two.x > x) || (two.x < x && one.x > x)) numExpandedColumns++;
        }
        return Location.getTaxicabDistance(one, two) + ((part2)  ? (999999L * numExpandedColumns + 999999L * numExpandedRows) : numExpandedColumns + numExpandedRows);
    }
    private void expand(){
        outerLoop:
        for (int r = 0; r < grid.length; r++){
            for (int c = 0; c < grid[0].length; c++){
                if (grid[r][c] == '#') continue outerLoop;
            }
            expandedRows.add(r);
        }
        outerLoop:
        for (int c = 0; c < grid[0].length; c++){
            for (int r = 0; r < grid[0].length; r++){
                if (grid[r][c] == '#') continue outerLoop;
            }
            expandedColumns.add(c);
        }
    }
}