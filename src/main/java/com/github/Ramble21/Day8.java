package com.github.Ramble21;

import com.github.Ramble21.classes.Location;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day8 extends DaySolver{
    private final List<String> input;
    private char[][] grid;
    public Day8() throws IOException {
        input = getInputLines(8);
        inputToGrid();
    }
    public int solvePart1() throws IOException {
        HashMap<Location, Character> map = new HashMap<>();
        HashSet<Location> set = new HashSet<>();
        HashSet<HashSet<Location>> done = new HashSet<>();

        for (int r = 0; r < grid.length; r++){
            for (int c = 0; c < grid[0].length; c++){
                if (grid[r][c] == '.') continue;
                map.put(new Location(c, r), grid[r][c]);
            }
        }
        for (char frequency : map.values()){
            for (Location antenna1 : map.keySet()){
                if (map.get(antenna1) != frequency) continue;
                for (Location antenna2 : map.keySet()){
                    if (map.get(antenna2) != frequency || antenna1 == antenna2) continue;

                    HashSet<Location> lox = new HashSet<>();
                    lox.add(antenna1); lox.add(antenna2);
                    if (done.contains(lox)) continue;
                    done.add(lox);

                    Location[] potentialAntinodes = getAntinodes(antenna1, antenna2);
                    //System.out.println("Potential antinodes: " + Arrays.toString(potentialAntinodes) + " for locations " + antenna1 + " and " + antenna2);
                    for (Location potential : potentialAntinodes){
                        if (potential != null) set.add(potential);
                    }
                }
            }
        }
        //System.out.println(set);
        return set.size();
    }

    public int solvePart2() throws IOException {
        return 0;
    }
    public Location[] getAntinodes(Location loc1, Location loc2){
        Location[] antinodes = new Location[2];
        int xDiff = loc1.getX() - loc2.getX();
        int yDiff = loc2.getY() - loc1.getY();
        antinodes[0] = new Location(loc1.getX()-2*xDiff, loc1.getY()+2*yDiff);
        antinodes[1] = new Location(loc2.getX()+2*xDiff, loc2.getY()-2*yDiff);
        for (int i = 0; i < antinodes.length; i++){
            assert antinodes[i] != null;
            if (!antinodes[i].isOnGrid(grid)){
                antinodes[i] = null;
            }
        }
        return antinodes;
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
}