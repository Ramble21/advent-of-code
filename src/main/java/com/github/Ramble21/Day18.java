package com.github.Ramble21;

import com.github.Ramble21.classes.general.*;
import java.io.IOException;
import java.util.*;

public class Day18 extends DaySolver{
    private final List<String> input;
    private final ArrayList<Location> coords;
    private final char[][] grid;
    private final int gridSize = 71;

    public Day18() throws IOException {
        input = getInputLines(18);
        coords = inputToCoords();
        grid = createGrid(gridSize);
    }
    public long solvePart1() throws IOException {
        addLocations(12);
        return bfs(new Location(0, 0), new Location(gridSize-1, gridSize-1));
    }

    public long solvePart2() throws IOException {
        return 0; // need string
    }

    public String solvePart2S() {
        String goal = null;
        for (int i = 0; i < coords.size(); i++){
            try{
                addLocations(i);
                bfs(new Location(0, 0), new Location(gridSize-1, gridSize-1));
            } catch (RuntimeException e) {
                goal = coords.get(i-1).getX() + "," + coords.get(i-1).getY();
                break;
            }
        }
        return goal;
    }

    public ArrayList<Location> inputToCoords(){
        ArrayList<Location> gyatt = new ArrayList<>();
        for (String s : input){
            int[] nums = Regex.parseFirstTwoIntegers(s);
            gyatt.add(new Location(nums[0], nums[1]));
        }
        return gyatt;
    }
    public char[][] createGrid(int dimension){
        char[][] x = new char[dimension][dimension];
        for (int r = 0; r < x.length; r++){
            for (int c = 0; c < x[0].length; c++){
                x[r][c] = '.';
            }
        }
        return x;
    }
    public void addLocations(int count){
        for (int r = 0; r < grid.length; r++){
            for (int c = 0; c < grid[0].length; c++){
                grid[r][c] = '.';
            }
        }
        for (int i = 0; i < count; i++){
            grid[coords.get(i).getY()][coords.get(i).getX()] = '#';
        }
    }

    private HashSet<Location> visited = new HashSet<>();
    private HashMap<Location, Integer> distances = new HashMap<>();

    public int bfs(Location start, Location end) throws RuntimeException{
        visited = new HashSet<>();
        distances = new HashMap<>();
        Queue<Location> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        distances.put(start, 0);
        while (!queue.isEmpty()){
            Location current = queue.poll();
            grid[current.getY()][current.getX()] = 'x';
            if (current.equals(end)){
                return distances.get(current);
            }
            for (Location neighbor : getNeighbors(current)){
                if (!visited.contains(neighbor)){
                    visited.add(neighbor);
                    distances.put(neighbor, distances.get(current) + 1);
                    queue.add(neighbor);
                }
            }
        }
        throw new RuntimeException("No solution");
    }

    public ArrayList<Location> getNeighbors(Location l){
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
            if (grid[output.get(i).getY()][output.get(i).getX()] == '#' || grid[output.get(i).getY()][output.get(i).getX()] == 'x'){
                output.remove(i);
                i--;
            }
        }
        return output;
    }
}
