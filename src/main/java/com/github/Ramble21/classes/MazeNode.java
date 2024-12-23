package com.github.Ramble21.classes;

import java.util.Arrays;
import java.util.HashMap;

public class MazeNode implements Comparable<MazeNode> {
    private final Location loc;
    private MazeNode previousNode;
    private final Direction[] directions;
    private int distance;
    private int pathsCount;
    private final boolean isOrigin;

    private static final HashMap<Location, Integer> knownDistances = new HashMap<>();
    private static final HashMap<Location, Integer> knownPathCounts = new HashMap<>();
    public static HashMap<Location, Integer> getKnownDistances(){
        return knownDistances;
    }
    public static HashMap<Location, Integer> getKnownPathCounts(){
        return knownPathCounts;
    }
    public MazeNode(Location loc, MazeNode previousNode, Direction[] directions){
        this.loc = loc;
        this.previousNode = previousNode;
        this.directions = directions;
        isOrigin = previousNode == null;

        if (knownDistances.containsKey(this.loc)){
            this.distance = knownDistances.get(this.loc);
            this.pathsCount = knownPathCounts.get(this.loc);
        }
        else{
            this.distance = Integer.MAX_VALUE;
            this.pathsCount = 0;
            knownDistances.put(this.loc, Integer.MAX_VALUE);
            knownPathCounts.put(this.loc, 0);
        }
        if (isOrigin){
            this.distance = 0;
            this.pathsCount = 1;
            knownDistances.put(this.loc, 0);
            knownPathCounts.put(this.loc, 1);
        }
    }
    public Direction[] getDirections() {
        return directions;
    }
    public Location getLoc() {
        return loc;
    }
    public boolean isOrigin() {
        return isOrigin;
    }
    public MazeNode getPreviousNode() {
        return previousNode;
    }
    public String toString() {
        return loc + "@" + Arrays.toString(directions) + "&" + distance;
    }
    public void setDistance(int distance) {
        this.distance = distance;
        knownDistances.put(this.loc, distance);
    }
    public int getPathsCount() {
        return pathsCount;
    }
    public void setPathsCount(int x) {
        this.pathsCount += x;
        knownPathCounts.put(this.loc, pathsCount);
    }
    public int getDistance() {
        return distance;
    }
    public void setPreviousNode(MazeNode previousNode){
        this.previousNode = previousNode;
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MazeNode other = (MazeNode) obj;
        return loc == other.loc && previousNode.getLoc() == other.previousNode.getLoc();
    }
    @Override
    public int compareTo(MazeNode other) {
        return Integer.compare(this.distance, other.distance);
    }
}
