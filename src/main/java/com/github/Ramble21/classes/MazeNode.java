package com.github.Ramble21.classes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MazeNode implements Comparable<MazeNode> {
    private final Location loc;
    private ArrayList<MazeNode> parentNodes = new ArrayList<>();
    private final Direction[] directions;
    private int distance;
    private final boolean isOrigin;


    private boolean sussyCase;
    public boolean isSussyCase() {
        return sussyCase;
    }
    public void setSussyCase(boolean sussyCase) {
        this.sussyCase = sussyCase;
    }

    private final Direction directionUponArriving;
    private static final HashMap<Location, Integer> knownDistances = new HashMap<>();

    public MazeNode(Location loc, MazeNode parentNode, Direction[] directions, Direction directionUponArriving){
        this.loc = loc;
        if (!(parentNode == null)) this.parentNodes.add(parentNode);
        this.directions = directions;
        this.directionUponArriving = directionUponArriving;
        isOrigin = parentNode == null;

        if (knownDistances.containsKey(this.loc)){
            this.distance = knownDistances.get(this.loc);
        }
        else{
            this.distance = Integer.MAX_VALUE;
            knownDistances.put(this.loc, Integer.MAX_VALUE);
        }
        if (isOrigin){
            this.distance = 0;
            knownDistances.put(this.loc, 0);
        }
    }
    public Direction[] getDirections() {
        return directions;
    }
    public Location getLoc() {
        return loc;
    }
    public ArrayList<MazeNode> getSafeParentNodes(HashSet<MazeNode> visited){
        ArrayList<MazeNode> safes = new ArrayList<>();
        for (MazeNode m : parentNodes){
            for (MazeNode v : visited){
                if (m.getLoc().equals(v.getLoc())){
                    safes.add(v);
                }
            }
        }
        return safes;
    }
    public String toString() {
        return loc + "-" + directionUponArriving + "&" + distance;
    }
    public void setDistance(int distance) {
        this.distance = distance;
        knownDistances.put(this.loc, distance);
    }
    public int getDistance() {
        return distance;
    }
    public Direction getDirectionUponArriving() {
        return directionUponArriving;
    }

    public void addParentNode(MazeNode parentNode){
        parentNodes.add(parentNode);
    }
    public void clearParentNodes(){
        parentNodes.clear();
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MazeNode other = (MazeNode) obj;
        return loc == other.loc && directionUponArriving == other.directionUponArriving;
    }
    @Override
    public int compareTo(MazeNode other) {
        return Integer.compare(this.distance, other.distance);
    }

    public boolean isOrigin() { // this DOES have usages ignore intellij
        return isOrigin;
    }
}
