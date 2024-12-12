package com.github.Ramble21.classes;

import java.util.Objects;

public class Location {
    private final int x;
    private final int y;
    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public String toString(){
        return "(" + y + "," + x + ")";
    }

    public boolean isOnGrid(char[][] grid) {
        return x < grid[0].length && y < grid.length && x >= 0 && y >= 0;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Location other = (Location)obj;
        return x == other.x && y == other.y;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
