package com.github.Ramble21.y2023.classes;

import java.util.Arrays;

public class Brick {
    private final Location3D endA;
    private final Location3D endB;
    public Brick(String inputLine) {
        int[] coords = Arrays.stream(inputLine.split("[,~]")).mapToInt(Integer::parseInt).toArray();
        endA = new Location3D(coords[0], coords[1], coords[2]);
        endB = new Location3D(coords[3], coords[4], coords[5]);
    }
    public void fall(Brick[] bricks) {
        while (!hasCollision(bricks)) {
            endA.z--;
            endB.z--;
        }
    }
    public int getMinZ() {
        return Math.min(endA.z, endB.z);
    }
    public int getMaxZ() {
        return Math.max(endA.z, endB.z);
    }
    public boolean canBeDisintegrated(Brick[] bricks) {
        Brick[] result = new Brick[bricks.length - 1];
        int i = 0;
        for (Brick b : bricks) {
            if (!(b.endA.equals(endA) && b.endB.equals(endB))) {
                result[i++] = b;
            }
        }
        for (Brick b : result) {
            if (!b.hasCollision(result)) {
                return false;
            }
        }
        return true;
    }
    public boolean hasCollision(Brick[] bricks) {
        if (getMinZ() == 1) {
            return true;
        }
        int[] ranges = getXYRanges();
        int targetZ = getMinZ() - 1;
        for (Brick b : bricks) {
            if (b.getMinZ() <= targetZ && b.getMaxZ() >= targetZ) {
                int[] bRanges = b.getXYRanges();
                boolean xOverlap = !(ranges[1] < bRanges[0] || ranges[0] > bRanges[1]);
                boolean yOverlap = !(ranges[3] < bRanges[2] || ranges[2] > bRanges[3]);
                if (xOverlap && yOverlap) {
                    return true;
                }
            }
        }
        return false;
    }
    private int[] getXYRanges() {
        return new int[]{Math.min(endA.x, endB.x), Math.max(endA.x, endB.x), Math.min(endA.y, endB.y), Math.max(endA.y, endB.y)};
    }
    public String toString() {
        return "[" + endA + "->" + endB + "]";
    }

}
