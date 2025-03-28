package com.github.Ramble21.y2023.classes;

import com.github.Ramble21.helper_classes.*;

import java.util.HashSet;
import java.util.Objects;

public class HeatBlock {
    private final Location loc;
    private final Direction dir;
    private final int numForwards;
    private final HeatBlock previousBlock;

    public HeatBlock() {
        this.loc = new Location(0, 0);
        this.previousBlock = null;
        this.dir = null;
        this.numForwards = 0;
    }
    public HeatBlock(Location l, Direction d, int numForwards, HeatBlock previousBlock) {
        this.loc = l;
        this.dir = d;
        this.numForwards = numForwards;
        this.previousBlock = previousBlock;
    }
    public HashSet<Location> nextLocations() {
        HashSet<Location> locs = new HashSet<>();
        for (Direction d : Direction.getCardinalDirections()) {
            if (dir != null && d == dir.getFlipped()) continue;
            if (d == dir && numForwards >= 3) continue;
            locs.add(loc.getDirectionalLoc(d));
        }
        return locs;
    }
    public HashSet<Location> nextUltraLocations() {
        HashSet<Location> locs = new HashSet<>();
        if (numForwards < 4 && numForwards != 0) {
            assert dir != null;
            locs.add(loc.getDirectionalLoc(dir));
            return locs;
        }
        for (Direction d : Direction.getCardinalDirections()) {
            if (dir != null && d == dir.getFlipped()) continue;
            if (d == dir && numForwards >= 10) continue;
            locs.add(loc.getDirectionalLoc(d));
        }
        return locs;
    }
    public Location getLocation() {
        return loc;
    }
    public Direction getDirection() {
        return dir;
    }
    public HeatBlock getPreviousBlock() {
        return previousBlock;
    }
    public HeatBlock move(Location l) {
        Direction dir = loc.getDirectionToGo(l);
        int numForwards = (this.numForwards == 0 || dir == this.dir) ? (this.numForwards + 1) : 1;
        return new HeatBlock(l, dir, numForwards, this);
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HeatBlock other = (HeatBlock) obj;
        if (other.previousBlock == null) return this.previousBlock == null;
        if (this.previousBlock == null) return false;
        return loc.equals(other.loc) && nextLocations().equals(other.nextLocations()) && numForwards == other.numForwards && other.previousBlock.getLocation().equals(previousBlock.getLocation());
    }
    @Override
    public int hashCode() {
        if (previousBlock == null) return Objects.hash(loc, nextLocations(), numForwards);
        return Objects.hash(loc, nextLocations(), numForwards, previousBlock.getLocation());
    }
    @Override
    public String toString() {
        return loc + "\\" + numForwards + dir;
    }
}
