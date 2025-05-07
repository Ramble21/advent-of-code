package com.github.Ramble21.y2023.classes;

import java.util.*;

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
    public static final HashMap<Brick, Integer> memo = new HashMap<>();
    public int disintegrationChainReactionLength(Brick[] bricks) {
        if (memo.containsKey(this)) {
            return memo.get(this);
        }
        Queue<Brick> queue = new LinkedList<>();
        HashSet<Brick> toppled = new HashSet<>();
        queue.add(this);
        toppled.add(this);
        while (!queue.isEmpty()) {
            Brick current = queue.poll();
            outerLoop:
            for (Brick next : current.getBricksAbove(bricks)) {
                for (Brick dependency : next.getBricksBelow(bricks)) {
                    if (!toppled.contains(dependency)) {
                        continue outerLoop;
                    }
                }
                toppled.add(next);
                queue.add(next);
            }
        }
        return toppled.size() - 1;
    }
    public boolean canBeDisintegrated(Brick[] bricks) {
        Brick[] result = new Brick[bricks.length - 1];
        int i = 0;
        for (Brick b : bricks) {
            if (!b.equals(this)) {
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
    public HashSet<Brick> getBricksAbove(Brick[] bricks) {
        HashSet<Brick> res = new HashSet<>();
        for (Brick b : bricks) {
            if (b.getMinZ() == getMaxZ() + 1) {
                int[] ranges = getXYRanges();
                int[] bRanges = b.getXYRanges();
                boolean xOverlap = !(ranges[1] < bRanges[0] || ranges[0] > bRanges[1]);
                boolean yOverlap = !(ranges[3] < bRanges[2] || ranges[2] > bRanges[3]);
                if (xOverlap && yOverlap) {
                    res.add(b);
                }
            }
        }
        return res;
    }
    public HashSet<Brick> getBricksBelow(Brick[] bricks) {
        HashSet<Brick> res = new HashSet<>();
        for (Brick b : bricks) {
            if (b.getMaxZ() == getMinZ() - 1) {
                int[] ranges = getXYRanges();
                int[] bRanges = b.getXYRanges();
                boolean xOverlap = !(ranges[1] < bRanges[0] || ranges[0] > bRanges[1]);
                boolean yOverlap = !(ranges[3] < bRanges[2] || ranges[2] > bRanges[3]);
                if (xOverlap && yOverlap) {
                    res.add(b);
                }
            }
        }
        return res;
    }
    public boolean hasCollision(Brick[] bricks) {
        if (getMinZ() == 1) {
            return true;
        }
        int[] ranges = getXYRanges();
        for (Brick b : bricks) {
            if (b.getMinZ() <= getMinZ() - 1 && b.getMaxZ() >= getMinZ() - 1) {
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
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Brick other = (Brick) obj;
        return endA == other.endA && endB == other.endB;
    }
    @Override
    public int hashCode() {
        return Objects.hash(endA, endB);
    }
}
