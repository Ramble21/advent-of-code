package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.helper_classes.Direction;
import com.github.Ramble21.helper_classes.Location;
import com.github.Ramble21.y2023.classes.Beam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Day16 extends DaySolver {
    private final char[][] grid;
    public Day16() throws IOException {
        grid = getInputAsGrid(2023, 16);
    }
    public long solvePart1() throws IOException {

        HashSet<Location> energized = new HashSet<>();
        ArrayList<Beam> beams = new ArrayList<>();
        HashSet<String> previousStates = new HashSet<>();
        Direction initialDir = Direction.RIGHT;
        if (grid[0][0] == '|' || grid[0][0] == '\\') initialDir = Direction.DOWN;
        beams.add(new Beam(new Location(0, 0), initialDir));

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

    public long solvePart2() throws IOException {
        return 0;
    }
}