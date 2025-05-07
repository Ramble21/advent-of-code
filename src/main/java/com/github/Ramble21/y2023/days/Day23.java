package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.helper_classes.Direction;
import com.github.Ramble21.helper_classes.Location;

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
        HashMap<Location, Integer> distances = new HashMap<>();
        HashMap<Location, HashSet<Location>> previousLocs = new HashMap<>();
        Queue<Location> queue = new LinkedList<>();
        queue.add(start);
        distances.put(start, 0);
        previousLocs.put(start, new HashSet<>());

        while (!queue.isEmpty()){
            Location current = queue.poll();
            for (Location neighbor : getNeighbors(current)) {
                if (!previousLocs.get(current).contains(neighbor)) {
                    HashSet<Location> neighborPrev = new HashSet<>(previousLocs.get(current))   ;
                    neighborPrev.add(current);
                    distances.put(neighbor, distances.get(current) + 1);
                    previousLocs.put(neighbor, neighborPrev);
                    queue.add(neighbor);
                }
            }
        }
        return distances.get(end);
    }
    public ArrayList<Location> getNeighbors(Location current) {
        ArrayList<Location> result;
        if (grid[current.y][current.x] != '.') {
            result = new ArrayList<>(List.of(current.getDirectionalLoc(Direction.charToDir(grid[current.y][current.x]))));
        }
        else {
            result = Location.getNeighbors(current, grid);
        }
        result.removeIf(l -> grid[l.y][l.x] == '#');
        return result;
    }

    public long solvePart2() throws IOException {
        return 0;
    }
}
