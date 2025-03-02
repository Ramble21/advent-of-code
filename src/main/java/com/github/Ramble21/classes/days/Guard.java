package com.github.Ramble21.classes.days;

import com.github.Ramble21.classes.general.Direction;
import com.github.Ramble21.classes.general.Location;

public class Guard {
    private Location currentLoc;
    private Direction currentDir;
    private boolean isOnMappedArea = true;
    private final char[][] grid;
    public Guard(Location startingLoc, char[][] grid){
        currentLoc = startingLoc;
        currentDir = Direction.UP;
        this.grid = grid;
    }
    public Location getCurrentLoc(){
        return currentLoc;
    }
    public Direction getCurrentDir(){
        return currentDir;
    }
    public boolean isOnMappedArea(){
        return isOnMappedArea;
    }
    public void move(){
        Location inFront = currentLoc.getDirectionalLoc(currentDir);
        if (!inFront.isOnGrid(grid)) isOnMappedArea = false;
        else if (grid[inFront.getY()][inFront.getX()] != '#'){
            currentLoc = inFront;
        }
        else currentDir = currentDir.getClockwise();
    }
}
