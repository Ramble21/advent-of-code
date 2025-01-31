package com.github.Ramble21;

import java.io.IOException;
import java.util.*;

import com.github.Ramble21.classes.general.*;

public class Day20 extends DaySolver{
    private final List<String> input;
    private char[][] grid;
    private char[][] ogGrid;
    private final Location end;
    private final Location start;

    public Day20() throws IOException {
        input = getInputLines(20);
        inputToGrid();
        Location start = null;
        Location end = null;
        for (int r = 0; r < grid.length; r++){
            for (int c = 0; c < grid[0].length; c++){
                if (grid[r][c] == 'S') start = new Location(c, r);
                else if (grid[r][c] == 'E') end = new Location(c, r);
            }
        }
        this.end = end;
        this.start = start;
        inputToGrid();
        ogGrid = new char[grid.length][grid[0].length];
        for (int r = 0; r < grid.length; r++){
            System.arraycopy(grid[r], 0, ogGrid[r], 0, grid[0].length);
        }
    }
    public long solvePart1() throws IOException {
        inputToGrid();
        return bfsWithCheats(start, end, grid, 100, false);
    }
    public long solvePart2() throws IOException {
        inputToGrid();
        return bfsWithCheats(start, end, grid, 100, true);
    }
    public int bfsWithCheats(Location start, Location end, char[][] grid, int minTimeSaved, boolean part2){
        HashSet<Location> visited = new HashSet<>();
        Queue<Location> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        BreadthFirstSearch control = new BreadthFirstSearch(start, end, grid);
        HashMap<Route, Integer> cheats = new HashMap<>();

        while (!queue.isEmpty()){
            Location current = queue.poll();
            if (grid[current.getY()][current.getX()] == '.') grid[current.getY()][current.getX()] = 'x';
            if (current.equals(end)){
                return cheats.size();
            }
            for (Location neighbor : getNeighbors(current, true)){
                if (!visited.contains(neighbor) && grid[neighbor.getY()][neighbor.getX()] != '#'){
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
            HashSet<Location> potentialEndpoints = new HashSet<>();
            HashMap<Location, Integer> originalDistances = control.getDistances();
            if (part2){
                for (int r = 0; r < grid.length; r++){
                    for (int c = 0; c < grid[0].length; c++){
                        Location test = new Location(c, r);
                        if (isWithinTwenty(test, current, grid) && grid[r][c] != '#'){
                            potentialEndpoints.add(test);
                        }
                    }
                }
            }
            else{
                potentialEndpoints.add(new Location(current.getX(), current.getY() + 2));
                potentialEndpoints.add(new Location(current.getX(), current.getY() - 2));
                potentialEndpoints.add(new Location(current.getX() + 2, current.getY()));
                potentialEndpoints.add(new Location(current.getX() - 2, current.getY()));
                potentialEndpoints.removeIf(loc -> !loc.isOnGrid(grid) || grid[loc.getY()][loc.getX()] == '#');
            }
            for (Location endpoint : potentialEndpoints){
                int timeSaved = originalDistances.get(endpoint) - originalDistances.get(current) - Location.getTaxicabDistance(current, endpoint);
                if (timeSaved >= minTimeSaved){
                    cheats.put(new Route(current, endpoint), timeSaved);
                }
            }
        }
        throw new RuntimeException("No solution");
    }
    public boolean isWithinTwenty(Location idk, Location center, char[][] grid){
        if (!idk.isOnGrid(grid)) return false;
        return Location.getTaxicabDistance(idk, center) <= 20;
    }
    public ArrayList<Location> getNeighbors(Location l, boolean all){
        ArrayList<Location> output = new ArrayList<>();
        output.add(new Location(l.getX(), l.getY() + 1));
        output.add(new Location(l.getX(), l.getY() - 1));
        output.add(new Location(l.getX() + 1, l.getY()));
        output.add(new Location(l.getX() - 1, l.getY()));
        for (int i = 0; i < output.size(); i++){
            if (output.get(i).getX() >= grid.length || output.get(i).getX() < 0 || output.get(i).getY() >= grid[0].length || output.get(i).getY() < 0){
                output.remove(i);
                i--;
                continue;
            }
            if (!all && (grid[output.get(i).getY()][output.get(i).getX()] == '#' || grid[output.get(i).getY()][output.get(i).getX()] == 'x')){
                output.remove(i);
                i--;
            }
        }
        if (!all && canParseInt(grid[l.getY()][l.getX()])){
            output.removeIf(a -> {
                if (isFinalInt(l)){
                   return grid[a.getY()][a.getX()] != 'x' && grid[a.getY()][a.getX()] != '.' && grid[a.getY()][a.getX()] != 'E';
                } else {
                    return charToInt(grid[a.getY()][a.getX()]) - 1 != charToInt(grid[l.getY()][l.getX()]);
                }
            });
        }
        return output;
    }
    public boolean canParseInt(char c){
        try {
            Integer.parseInt(Character.toString(c));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public char charToInt(char c){
        if (!canParseInt(c)) return c;
        return (Integer.toString(Integer.parseInt(Character.toString(c)))).charAt(0);
    }
    public boolean isFinalInt(Location l){
        int curr = charToInt(grid[l.getY()][l.getX()]);
        for (Location neighbor : getNeighbors(l, true)){
            if (charToInt(grid[neighbor.getY()][neighbor.getX()]) - 1 == curr) return false;
        }
        return true;
    }
    public void inputToGrid(){
        grid = new char[input.size()][input.get(0).length()];
        for (int r = 0; r < input.size(); r++){
            for (int c = 0; c < input.get(0).length(); c++){
                grid[r][c] = input.get(r).charAt(c);
            }
        }
    }
}