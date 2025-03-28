package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.helper_classes.Direction;
import com.github.Ramble21.helper_classes.Location;

import java.io.IOException;
import java.util.*;

public class Day10 extends DaySolver {
    private final char[][] grid;
    private final HashMap<Location, Integer> distances = new HashMap<>();
    private Location start;

    public Day10() throws IOException {
        grid = getInputAsGrid(2023, 10);
        for (int r = 0; r < grid.length; r++){
            for (int c = 0; c < grid[0].length; c++){
                if (grid[r][c] == 'S') start = new Location(c, r);
            }
        }
        determineStartTile();
    }
    public long solvePart1() throws IOException {
        customBFS();
        int max = 0;
        for (int distance : distances.values()){
            max = Math.max(distance, max);
        }
        return max;
    }
    public long solvePart2() throws IOException {
        return getAllLocsInsidePolygon(distances.keySet()).size();
    }
    private HashSet<Location> getAllLocsInsidePolygon(Set<Location> polygon){
        HashSet<Location> result = new HashSet<>();
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (Location loc : polygon) {
            minX = Math.min(minX, loc.getX());
            minY = Math.min(minY, loc.getY());
            maxX = Math.max(maxX, loc.getX());
            maxY = Math.max(maxY, loc.getY());
        }
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Location point = new Location(x, y);
                if (isPointInside(point, polygon)) {
                    result.add(point);
                }
            }
        }
        return result;
    }
    private boolean isPointInside(Location p, Set<Location> polygon) {
        for (Direction d : Direction.getCardinalDirections()){
            if (!p.getDirectionalLoc(d).isOnGrid(grid)) return false;
        }
        if (polygon.contains(p)) return false;
        Location current = new Location(-1, p.y);
        boolean returnValue = false;
        while (current.x <= p.x) {
            if (polygon.contains(current) && facesNorth(current)){
                returnValue = !returnValue;
            }
            current = new Location(current.x + 1, current.y);
        }
        return returnValue;
    }
    private boolean facesNorth(Location l){
        if (!l.isOnGrid(grid)) return false;
        return switch (grid[l.y][l.x]){
            case 'L', '|', 'J' -> true;
            case '-', 'F', '7', '.' -> false;
            default -> throw new RuntimeException();
        };
    }
    private void customBFS(){
        HashSet<Location> visited = new HashSet<>();
        Queue<Location> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);
        distances.put(start, 0);
        while (!queue.isEmpty()) {
            Location current = queue.poll();
            for (Location neighbor : getNeighbors(current)) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    distances.put(neighbor, distances.get(current) + 1);
                    queue.add(neighbor);
                }
            }
        }
    }
    private void determineStartTile() {
        int x = start.getX();
        int y = start.getY();
        boolean north = y > 0 && "|7F".indexOf(grid[y - 1][x]) != -1;
        boolean south = y < grid.length - 1 && "|LJ".indexOf(grid[y + 1][x]) != -1;
        boolean west = x > 0 && "-LF".indexOf(grid[y][x - 1]) != -1;
        boolean east = x < grid[0].length - 1 && "-J7".indexOf(grid[y][x + 1]) != -1;
        if (north && south) grid[y][x] = '|';
        else if (east && west) grid[y][x] = '-';
        else if (north && east) grid[y][x] = 'L';
        else if (north && west) grid[y][x] = 'J';
        else if (south && west) grid[y][x] = '7';
        else if (south && east) grid[y][x] = 'F';
    }
    private List<Location> getNeighbors(Location current) {
        int x = current.getX();
        int y = current.getY();
        return switch (grid[y][x]) {
            case '|' -> List.of(new Location(x, y - 1), new Location(x, y + 1));
            case '-' -> List.of(new Location(x - 1, y), new Location(x + 1, y));
            case 'L' -> List.of(new Location(x, y - 1), new Location(x + 1, y));
            case 'J' -> List.of(new Location(x, y - 1), new Location(x - 1, y));
            case '7' -> List.of(new Location(x, y + 1), new Location(x - 1, y));
            case 'F' -> List.of(new Location(x, y + 1), new Location(x + 1, y));
            default -> List.of();
        };
    }
}