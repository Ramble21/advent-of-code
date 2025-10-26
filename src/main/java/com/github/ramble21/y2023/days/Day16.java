package com.github.ramble21.y2023.days;

import com.github.ramble21.DaySolver;
import com.github.ramble21.helper_classes.Direction;
import com.github.ramble21.helper_classes.Location;
import com.github.ramble21.y2023.classes.Beam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day16 extends DaySolver {
    private final char[][] grid;

    public Day16() throws IOException {
        grid = getInputAsGrid(2023, 16);
    }

    public long solvePart1() throws IOException {
        return getTilesEnergized(new Location(0, 0), Direction.RIGHT);
    }

    public long solvePart2() throws IOException {
        int max = 0;
        for (int r = 0; r < grid.length; r++) {
            max = Math.max(max, getTilesEnergized(new Location(0, r), Direction.RIGHT));
            max = Math.max(max, getTilesEnergized(new Location(grid[0].length - 1, r), Direction.LEFT));
        }
        for (int c = 0; c < grid[0].length; c++) {
            max = Math.max(max, getTilesEnergized(new Location(c, 0), Direction.DOWN));
            max = Math.max(max, getTilesEnergized(new Location(c, grid.length - 1), Direction.UP));
        }
        return max;
    }

    private int getTilesEnergized(Location l, Direction d) {
        HashSet<Location> energized = new HashSet<>();
        ArrayList<Beam> beams = new ArrayList<>();
        HashSet<String> previousStates = new HashSet<>();

        Direction initialDir = getInitialDir(l, d);
        beams.add(new Beam(new Location(l.x, l.y), initialDir));

        while (!beams.isEmpty()) {
            ArrayList<Beam> newBeams = new ArrayList<>();
            for (int i = 0; i < beams.size(); i++) {
                Beam current = beams.get(i);
                String currentState = current.getLocation().toString() + current.getDirection().toString();
                if (previousStates.contains(currentState) || current.getStatusCode() == -1) {
                    beams.remove(i);
                    i--;
                    continue;
                }
                previousStates.add(currentState);
                energized.add(current.getLocation());
                current.move(grid);
                Location currentLoc = current.getLocation();
                if (current.getStatusCode() == 1) {
                    beams.remove(i);
                    i--;
                    newBeams.add(new Beam(currentLoc, Direction.UP));
                    newBeams.add(new Beam(currentLoc, Direction.DOWN));
                } else if (current.getStatusCode() == 2) {
                    beams.remove(i);
                    i--;
                    newBeams.add(new Beam(currentLoc, Direction.LEFT));
                    newBeams.add(new Beam(currentLoc, Direction.RIGHT));
                }
            }
            beams.addAll(newBeams);
        }
        return energized.size();
    }

    private Direction getInitialDir(Location l, Direction d) {
        Direction initialDir = d;
        if (initialDir == Direction.RIGHT && (grid[l.y][l.x] == '|' || grid[l.y][l.x] == '\\'))
            initialDir = Direction.DOWN;
        else if (initialDir == Direction.DOWN && (grid[l.y][l.x] == '|' || grid[l.y][l.x] == '\\'))
            initialDir = Direction.RIGHT;
        else if (initialDir == Direction.LEFT && (grid[l.y][l.x] == '-' || grid[l.y][l.x] == '/'))
            initialDir = Direction.UP;
        else if (initialDir == Direction.UP && (grid[l.y][l.x] == '-' || grid[l.y][l.x] == '/'))
            initialDir = Direction.LEFT;
        return initialDir;
    }
}