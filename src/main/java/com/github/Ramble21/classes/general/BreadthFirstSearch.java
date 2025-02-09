package com.github.Ramble21.classes.general;
import java.util.*;

public class BreadthFirstSearch {

    private final Location start;
    private final Location end;
    private final char[][] grid;
    private int result;
    private Route route;
    private HashMap<Location, Integer> distances;

    public BreadthFirstSearch(Location start, Location end, char[][] grid){
        this.start = start;
        this.end = end;
        char[][] ogGrid = new char[grid.length][grid[0].length];
        for (int r = 0; r < grid.length; r++){
            System.arraycopy(grid[r], 0, ogGrid[r], 0, grid[0].length);
        }
        this.grid = ogGrid;
        run();
    }
    public int getResult(){
        return result;
    }
    public Route getRoute(){
        return route;
    }
    public HashMap<Location, Integer> getDistances(){
        return distances;
    }

    public void run() throws RuntimeException{
        HashSet<Location> visited = new HashSet<>();
        HashMap<Location, Location> previousLocs = new HashMap<>();
        HashMap<Location, Integer> distances = new HashMap<>();
        Queue<Location> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        distances.put(start, 0);
        previousLocs.put(start, start);

        while (!queue.isEmpty()){
            Location current = queue.poll();
            if (grid[current.getY()][current.getX()] == '.') grid[current.getY()][current.getX()] = 'x';
            if (current.equals(end)){
                ArrayList<Location> r = new ArrayList<>();
                r.add(end);
                Location backtrack = end;
                while (!previousLocs.get(backtrack).equals(backtrack)){
                    backtrack = previousLocs.get(backtrack);
                    r.add(0, backtrack);
                }
                result = distances.get(current);
                route = new Route(r);
                this.distances = distances;
                return;
            }
            for (Location neighbor : Location.getNeighbors(current, grid)){
                if (!visited.contains(neighbor)){
                    visited.add(neighbor);
                    previousLocs.put(neighbor, current);
                    distances.put(neighbor, distances.get(current) + 1);
                    queue.add(neighbor);
                }
            }
        }
        throw new RuntimeException("No solution");
    }
}
