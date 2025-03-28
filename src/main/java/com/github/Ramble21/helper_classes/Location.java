package com.github.Ramble21.helper_classes;

import java.util.ArrayList;
import java.util.Objects;

public class Location {
    public final int x;
    public final int y;
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
    public static int getTaxicabDistance(Location first, Location second){
        return Math.abs(first.x -second.x) + Math.abs(first.y -second.y);
    }

    public boolean isOnGrid(char[][] grid) {
        return x < grid[0].length && y < grid.length && x >= 0 && y >= 0;
    }

    public Location getDirectionalLoc(Direction dir) {
        return new Location(x + dir.getDeltaX(), y + dir.getDeltaY());
    }
    public static ArrayList<Location> getNeighbors(Location l, char[][] grid){
        ArrayList<Location> output = new ArrayList<>();
        output.add(new Location(l.getX(), l.getY() + 1));
        output.add(new Location(l.getX(), l.getY() - 1));
        output.add(new Location(l.getX() + 1, l.getY()));
        output.add(new Location(l.getX() - 1, l.getY()));
        for (int i = 0; i < output.size(); i++){
            int x = output.get(i).getX();
            int y = output.get(i).getY();
            if (y >= grid.length || x < 0 || x >= grid[0].length || y < 0){
                output.remove(i);
                i--;
                continue;
            }
            if ((grid[y][x] == '#' || grid[y][x] == 'x')){
                output.remove(i);
                i--;
            }
        }
        return output;
    }
    public Direction getDirectionToGo(Location other) {
        int dx = other.x - x;
        int dy = other.y - y;
        if ((dx != 0) == (dy != 0)) return null;
        if (dx > 0) return Direction.RIGHT;
        if (dx < 0) return Direction.LEFT;
        if (dy > 0) return Direction.DOWN;
        return Direction.UP;
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
