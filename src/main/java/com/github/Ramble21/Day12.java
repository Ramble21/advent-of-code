package com.github.Ramble21;

import com.github.Ramble21.classes.Location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Day12 extends DaySolver{
    private final List<String> input;
    private char[][] grid;
    private final HashSet<Location> currentRegion = new HashSet<>();
    private final HashSet<Location> allRegions = new HashSet<>();
    public Day12() throws IOException {
        input = getInputLines(12);
        inputToGrid();
    }
    public int solvePart1() throws IOException {
        print2DArr(grid);
        int count = 0;
        for (int r = 0; r < grid.length; r++){
            for (int c = 0; c < grid[0].length; c++){
                Location loc = new Location(c, r);
                if (allRegions.contains(loc)) continue;

                getRegion(loc, grid[r][c]);
                allRegions.addAll(currentRegion);
                System.out.println("\nCurrent region: " + currentRegion + " of type " + grid[r][c]);

                int area = currentRegion.size();
                int perimeter = getPerimeter();
                count += area * perimeter;
                System.out.println(area + " * " + perimeter + " = " + area*perimeter);
                currentRegion.clear();
            }
        }
        return count;
    }

    public int solvePart2() throws IOException {
        return 0;
    }
    public void getRegion(Location loc, char target){
        Location[] possiblePaths = possiblePaths(loc.getY(), loc.getX());
        //System.out.println("\n"+loc+ " " + grid[loc.getY()][loc.getX()]);
        //System.out.print(Arrays.toString(possiblePaths) + " ");
        if (grid[loc.getY()][loc.getX()] == target) currentRegion.add(loc);
        if (possiblePaths == null) return;
        for (Location path : possiblePaths){
            if (!currentRegion.contains(path)) getRegion(path, target);
        }
    }
    public int getPerimeter(){
        int count = 0;
        for (Location l : currentRegion){
            int x = 4;
            if (currentRegion.contains(new Location(l.getX()+1, l.getY()))) x--;
            if (currentRegion.contains(new Location(l.getX()-1, l.getY()))) x--;
            if (currentRegion.contains(new Location(l.getX(), l.getY()+1))) x--;
            if (currentRegion.contains(new Location(l.getX(), l.getY()-1))) x--;
            count += x;
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
    public Location[] possiblePaths(int r, int c){
        ArrayList<Location> list = new ArrayList<>();
        char x = grid[r][c];

        if (r+1 < grid.length &&
                grid[r+1][c] == x &&
                !currentRegion.contains(new Location(c, r+1)))
            list.add(new Location(c, r+1));

        if (r-1 >= 0 &&
                grid[r-1][c] == x &&
                !currentRegion.contains(new Location(c, r-1)))
            list.add(new Location(c, r-1));

        if (c+1 < grid[0].length &&
                grid[r][c+1] == x &&
                !currentRegion.contains(new Location(c+1, r)))
            list.add(new Location(c+1, r));

        if (c-1 >= 0 &&
                grid[r][c-1] == x &&
                !currentRegion.contains(new Location(c-1, r)))
            list.add(new Location(c-1, r));

        if (list.isEmpty()) return null;

        Location[] fin = new Location[list.size()];
        for (int i = 0; i < list.size(); i++){
            fin[i] = list.get(i);
        }
        return fin;
    }
    public static void print2DArr(char[][] array) {
        for (char[] row : array) {
            System.out.println(row);
        }
    }
}