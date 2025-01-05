package com.github.Ramble21;

import com.github.Ramble21.classes.general.Location;

import java.io.IOException;
import java.util.*;

public class Day8 extends DaySolver{
    private final List<String> input;
    private char[][] grid;
    public Day8() throws IOException {
        input = getInputLines(8);
        inputToGrid();
    }
    public long solvePart1() throws IOException {
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

    public long solvePart2() throws IOException {
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

                    Location[] potentialAntinodes = getUpdatedAntinodes(antenna1, antenna2);
                    //System.out.println("Potential antinodes: " + Arrays.toString(potentialAntinodes) + " for locations " + antenna1 + " and " + antenna2);
                    for (Location potential : potentialAntinodes){
                        if (potential != null){
                            set.add(potential);
                            if (grid[potential.getY()][potential.getX()] == '.') grid[potential.getY()][potential.getX()] = '#';
                            else if (grid[potential.getY()][potential.getX()] != '#') grid[potential.getY()][potential.getX()] = '%';
                        }
                    }
                    //System.out.println(set.size());
                }
            }
        }
        ArrayList<Location> x = new ArrayList<>(set);
        x.sort(Comparator.comparing(Location::getY));
        //System.out.println(x);
        //print2DArr(grid);
        return set.size();
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
    public Location[] getUpdatedAntinodes(Location loc1, Location loc2){
        Location[] antinodes = new Location[100];
        antinodes[0] = loc1;
        antinodes[1] = loc2;
        int xDiff = loc1.getX() - loc2.getX();
        int yDiff = loc2.getY() - loc1.getY();
        int times = 2;
        for (int i = 2; i < antinodes.length; i+=2){
            antinodes[i] = new Location(loc1.getX()-times*xDiff, loc1.getY()+times*yDiff);
            antinodes[i+1] = new Location(loc2.getX()+times*xDiff, loc2.getY()-times*yDiff);
            times++;
        }

        for (int i = 0; i < antinodes.length; i++){
            assert antinodes[i] != null;
            if (!antinodes[i].isOnGrid(grid)){
                antinodes[i] = null;
            }
        }
        antinodes = Arrays.stream(antinodes).filter(Objects::nonNull).toArray(Location[]::new);
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