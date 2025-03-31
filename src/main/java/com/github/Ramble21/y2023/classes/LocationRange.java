package com.github.Ramble21.y2023.classes;

import com.github.Ramble21.helper_classes.Direction;
import com.github.Ramble21.helper_classes.Location;

public class LocationRange {
    private final Location first;
    private final Location second;
    private final Direction fromFirstToSecond;
    public LocationRange(Location first, Location second) {
        this.first = first;
        this.second = second;
        this.fromFirstToSecond = first.getDirectionToGo(second);
    }
    public boolean contains(Location l) {
        if (fromFirstToSecond == Direction.RIGHT || fromFirstToSecond == Direction.LEFT) {
            return l.y == first.y && Math.min(first.x, second.x) <= l.x && l.x <= Math.max(first.x, second.x);
        }
        if (fromFirstToSecond == Direction.UP || fromFirstToSecond == Direction.DOWN) {
            return l.x == first.x && Math.min(first.y, second.y) <= l.y && l.y <= Math.max(first.y, second.y);
        }
        throw new IllegalArgumentException();
    }
    public int getSize() {
        if (first.x == second.x) return Math.abs(second.y - first.y) + 1;
        return Math.abs(first.x - second.x) + 1;
    }
    public int getMaxX() {
        return Math.max(first.x, second.x);
    }
    public int getMinX() {
        return Math.min(first.x, second.x);
    }
    public int getMaxY() {
        return Math.max(first.y, second.y);
    }
    public int getMinY() {
        return Math.min(first.y, second.y);
    }
    public Location getFirst() {
        return first;
    }
    public Location getLast() {
        return second;
    }
}
