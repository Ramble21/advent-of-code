package com.github.ramble21.y2023.days;

import com.github.ramble21.DaySolver;
import com.github.ramble21.helper_classes.Location;

import java.io.IOException;
import java.util.*;

public class Day21 extends DaySolver {
    private final char[][] grid;
    private final Location startingPoint;

    private final HashMap<Location, Integer> distances = new HashMap<>();
    private final HashSet<Location> evenLocs = new HashSet<>();
    private final HashSet<Location> oddLocs = new HashSet<>();

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
        HashSet<Location> bfs = bfs(startingPoint);
        for (Location l : bfs) {
            if (distances.get(l) % 2 == 0) {
                evenLocs.add(l);
            } else {
                oddLocs.add(l);
            }
        }
        return evenLocs.stream().filter(loc -> distances.get(loc) <= 64).count();
    }
    private HashSet<Location> bfs(Location start) {
        HashSet<Location> visited = new HashSet<>();
        Queue<Location> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        distances.put(start, 0);
        while (!queue.isEmpty()){
            Location current = queue.poll();
            for (Location neighbor : Location.getNeighbors(current, grid)){
                if (!visited.contains(neighbor)){
                    visited.add(neighbor);
                    distances.put(neighbor, distances.get(current) + 1);
                    queue.add(neighbor);
                }
            }
        }
        return visited;
    }
    public long solvePart2() throws IOException {
        int numTiles = 26501365 / grid.length;
        long numOdds = (long) Math.pow(numTiles + 1, 2);
        long numEvens = (long) Math.pow(numTiles, 2);
        long evenFulls = evenLocs.size();
        long oddFulls = oddLocs.size();
        long evenCorners = evenLocs.stream().filter(loc -> distances.get(loc) > 65).count();
        long oddCorners = oddLocs.stream().filter(loc -> distances.get(loc) > 65).count();
        return (numOdds * oddFulls) + (numEvens * evenFulls) - ((numTiles + 1) * oddCorners) + (numTiles * evenCorners);
    }
}