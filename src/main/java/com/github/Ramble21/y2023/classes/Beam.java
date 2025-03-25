package com.github.Ramble21.y2023.classes;

import com.github.Ramble21.helper_classes.*;

import java.util.Objects;

import static com.github.Ramble21.helper_classes.Direction.*;

public class Beam {
    private Location l;
    private Direction d;
    private int statusCode = -2;

    public Beam(Location l, Direction d) {
        this.l = l;
        this.d = d;
    }
    public String toString() {
        return l + "[" + d + "]" + "&" + statusCode;
    }
    public Location getLocation() {
        return l;
    }
    public Direction getDirection() {
        return d;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public void move(char[][] grid) {
        Location next = l.getDirectionalLoc(d);
        /* Return values:
            -1 : Location is not on grid
            0 : Location was successfully moved to, keep beam as is
            1 : There was a split, kill and make 2 new vertical rays
            2 : There was a split, kill and make 2 new horizontal rays
         */
        if (!next.isOnGrid(grid)) {
            statusCode = -1;
            return;
        }
        l = next;
        if (grid[next.y][next.x] == '.') {
            statusCode = 0;
        }
        else if (grid[next.y][next.x] == '-') {
            statusCode = (d == Direction.LEFT || d == RIGHT) ? 0 : 2;
        }
        else if (grid[next.y][next.x] == '|') {
            statusCode = (d == Direction.UP || d == Direction.DOWN) ? 0 : 1;
        }
        else if (grid[next.y][next.x] == '/') {
            d = switch (d) {
                case UP -> RIGHT;
                case RIGHT -> UP;
                case LEFT -> DOWN;
                case DOWN -> LEFT;
                default -> throw new RuntimeException();
            };
            statusCode = 0;
        }
        else {
            d = switch (d) {
                case UP -> LEFT;
                case LEFT -> UP;
                case DOWN -> RIGHT;
                case RIGHT -> DOWN;
                default -> throw new RuntimeException();
            };
            statusCode = 0;
        }
    }
}
