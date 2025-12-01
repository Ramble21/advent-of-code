package com.github.ramble21.y2023.classes;

import com.github.ramble21.helper_classes.Location;

import java.util.HashSet;

public class Polygon {
    private final HashSet<LocationRange> sides;
    public Polygon() {
        sides = new HashSet<>();
    }
    public void addSide(Location first, Location second) {
        sides.add(new LocationRange(first, second));
    }
    public boolean contains(Location loc) {
        for (LocationRange l : sides) {
            if (l.contains(loc)) return true;
        }
        return false;
    }
    public int getSize() {
        int result = 0;
        for (LocationRange l : sides) {
            result += l.getSize() - 1;
        }
        return result;
    }
    public int getMaxX() {
        int result = Integer.MIN_VALUE;
        for (LocationRange l : sides) {
            result = Math.max(result, l.getMaxX());
        }
        return result;
    }
    public int getMinX() {
        int result = Integer.MAX_VALUE;
        for (LocationRange l : sides) {
            result = Math.min(result, l.getMinX());
        }
        return result;
    }
    public int getMaxY() {
        int result = Integer.MIN_VALUE;
        for (LocationRange l : sides) {
            result = Math.max(result, l.getMaxY());
        }
        return result;
    }
    public int getMinY() {
        int result = Integer.MAX_VALUE;
        for (LocationRange l : sides) {
            result = Math.min(result, l.getMinY());
        }
        return result;
    }
    public int getNextX(int currentX, int y) {
        if (contains(new Location(currentX + 1, y)) && contains(new Location(currentX + 1, y - 1))) {
            return currentX + 1;
        }
        int min = Integer.MAX_VALUE;
        for (LocationRange range : sides) {
            if (range.getMaxX() < currentX) continue;
            if (range.getLast().x == range.getFirst().x && y - 1 >= range.getMinY() && y <= range.getMaxY() ) {
                min = Math.min(min, range.getLast().x);
            }
            if (range.getLast().x == range.getFirst().x) continue;
            if (range.getLast().y != y) continue;
            if (range.getMinX() > currentX && contains(new Location(range.getMinX(), y - 1))){
                min = Math.min(min, range.getMinX());
            }
            else if (contains(new Location(range.getMaxX(), y - 1))){
                min = Math.min(min, range.getMaxX());
            }
        }
        return min;
    }
    public int getNumNonNorths(int firstX, int lastX, int y) {
        // this one may or may not work IDK
        int count = 0;
        for (LocationRange range : sides) {
            if (range.getLast().y != y || range.getFirst().y != y) continue;
            if (range.getLast().x == range.getFirst().x) {
                count += (range.getMaxY() > y) ? 1 : 0;
                continue;
            }
            int min = range.getMinX();
            int max = range.getMaxX();
            if (contains(new Location(min, y - 1))) min++;
            if (contains(new Location(max, y - 1))) max--;
            if (min > lastX || max < firstX) continue;
            min = Math.max(min, firstX);
            max = Math.min(max, lastX);
            count += (max - min) + 1;
        }
        return count;
    }
}
