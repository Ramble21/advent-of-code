package com.github.Ramble21;

import java.io.IOException;
import java.util.*;

import com.github.Ramble21.classes.general.*;

public class Day20 extends DaySolver{
    private final List<String> input;
    private char[][] grid;
    public Day20() throws IOException {
        input = getInputLines(20);
        inputToGrid();
    }
    public long solvePart1() throws IOException {
        Location start = null;
        Location end = null;
        for (int r = 0; r < grid.length; r++){
            for (int c = 0; c < grid[0].length; c++){
                if (grid[r][c] == 'S') start = new Location(c, r);
                else if (grid[r][c] == 'E') end = new Location(c, r);
            }
        }
        int normal = bfs(start, end, grid);
        inputToGrid();
        print2DArr(grid);

        int count = 0;
        for (int r = 0; r < grid.length; r++){
            for (int c = 0; c < grid[0].length; c++){
                Location One = new Location(c, r);
                if (!qualifies(One)) continue;
                for (Location Two : getNeighbors(One, true)){
                    if (grid[Two.getY()][Two.getX()] == '#') continue;
                    inputToGrid();
                    grid[One.getY()][One.getX()] = '1';
                    grid[Two.getY()][Two.getX()] = '2';
                    int x = bfs(start, end, grid);
                    if (normal - x >= 100){
                        count++;
                    }
                }
                System.out.println(r + " " + c);
            }
        }
        return count;
    }
    public long solvePart2() throws IOException {
        return 0;
    }
    public boolean qualifies(Location loc){
        if (grid[loc.getY()][loc.getX()] != '#') return false;
        for (Location neighbor : getNeighbors(loc, true)){
            if (grid[neighbor.getY()][neighbor.getX()] == 'x' || grid[neighbor.getY()][neighbor.getX()] == '.') return true;
        }
        return false;
    }
    public int bfs(Location start, Location end, char[][] grid) throws RuntimeException{
        HashSet<Location> visited = new HashSet<>();
        HashMap<Location, Integer> distances = new HashMap<>();
        Queue<Location> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        distances.put(start, 0);
        while (!queue.isEmpty()){
            Location current = queue.poll();
            if (grid[current.getY()][current.getX()] == '.') grid[current.getY()][current.getX()] = 'x';
            if (current.equals(end)){
                return distances.get(current);
            }
            for (Location neighbor : getNeighbors(current, false)){
                if (!visited.contains(neighbor)){
                    visited.add(neighbor);
                    distances.put(neighbor, distances.get(current) + 1);
                    queue.add(neighbor);
                }
            }
        }
        throw new RuntimeException("No solution");
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
        if (!all && grid[l.getY()][l.getX()] == '1'){
            output.removeIf(a -> grid[a.getY()][a.getX()] != '2');
        }
        return output;
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