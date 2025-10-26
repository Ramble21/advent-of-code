package com.github.ramble21.y2023.days;

import com.github.ramble21.DaySolver;
import com.github.ramble21.helper_classes.Direction;
import com.github.ramble21.helper_classes.Location;

import java.io.IOException;

public class Day14 extends DaySolver {
    private final char[][] grid;
    public Day14() throws IOException {
        grid = getInputAsGrid(2023, 14);
    }
    public long solvePart1() throws IOException {
        char[][] copy = new char[grid.length][grid[0].length];
        for (int r = 0; r < grid.length; r++) {
            System.arraycopy(grid[r], 0, copy[r], 0, grid[0].length);
        }
        int sum = 0;
        for (int r = 0; r < copy.length; r++) {
            for (int c = 0; c < copy[0].length; c++) {
                if (copy[r][c] == 'O') {
                    Location result = shift(new Location(c , r), Direction.UP);
                    sum += copy.length - result.y;
                }
            }
        }
        return sum;
    }
    public long solvePart2() throws IOException {
        int[] sequence = new int[512];
        for (int i = 0; i < 512; i++) {
            sequence[i] = oneCycle();
        }
        int cycleStart = getCycleStart(sequence);
        int cycleLength = getCycleLength(sequence, cycleStart);
        return sequence[(1000000000 - 1 - cycleStart) % cycleLength + cycleStart];
    }
    private int getCycleLength(int[] sequence, int cycleStart) {
        int i = cycleStart + 1;
        while (!(sequence[i] == sequence[cycleStart] &&
                sequence[i + 1] == sequence[cycleStart + 1] &&
                sequence[i + 2] == sequence[cycleStart + 2]
                )) {
            i++;
        }
        return i - cycleStart;
    }
    private int getCycleStart(int[] sequence) {
        // https://en.wikipedia.org/wiki/Cycle_detection#Tortoise_and_hare
        int hare = 2, tortoise = 1;
        while (!(sequence[hare] == sequence[tortoise] &&
                sequence[hare + 1] == sequence[tortoise + 1] &&
                sequence[hare + 2] == sequence[tortoise + 2]
        )) {
            hare += 2;
            tortoise++;
        }
        tortoise = 0;
        while (sequence[hare] != sequence[tortoise]) {
            hare++;
            tortoise++;
        }
        return tortoise;
    }

    private int oneCycle() {
        for (Direction d : new Direction[]{Direction.UP, Direction.LEFT}) {
            for (int r = 0; r < grid.length; r++) {
                for (int c = 0; c < grid[0].length; c++) {
                    if (grid[r][c] == 'O') {
                        shift(new Location(c , r), d);
                    }
                }
            }
        }
        int sum = 0;
        for (Direction d : new Direction[]{Direction.DOWN, Direction.RIGHT}) {
            sum = 0;
            for (int r = grid.length - 1; r >= 0; r--) {
                for (int c = grid[0].length - 1; c >= 0; c--) {
                    if (grid[r][c] == 'O') {
                        Location result = shift(new Location(c , r), d);
                        sum += grid.length - result.y;
                    }
                }
            }
        }
        return sum;
    }
    private Location shift(Location l, Direction d) {
        Location shifted = new Location(l.x, l.y);
        while (true) {
            Location next = shifted.getDirectionalLoc(d);
            if (!next.isOnGrid(grid) || grid[next.y][next.x] != '.') {
                break;
            }
            shifted = next;
        }
        grid[l.y][l.x] = '.';
        grid[shifted.y][shifted.x] = 'O';
        return shifted;
    }
}