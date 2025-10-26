package com.github.ramble21.y2023.classes;

import com.github.ramble21.helper_classes.Direction;
import com.github.ramble21.helper_classes.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class GraphNode {
    private HashMap<Location, Integer> vertices = new HashMap<>();
    private final Location loc;
    public GraphNode(Location l, char[][] grid) {
        this.loc = l;
        for (Location vertex : getNeighboringIntersections(grid)) {
            vertices.put(vertex, Location.getTaxicabDistance(l, vertex));
        }
    }
    public void condenseGraph(HashMap<Location, GraphNode> graph) {
        HashMap<Location, Integer> verticesUpdated = new HashMap<>();
        for (Location l : vertices.keySet()) {
            Location base = loc;
            Location current = l;
            int replacementDistance = Location.getTaxicabDistance(base, current);
            while (graph.get(current).vertices.size() == 2) {
                for (Location neighbor : graph.get(current).vertices.keySet()) {
                    if (!neighbor.equals(base)) {
                        replacementDistance += Location.getTaxicabDistance(current, neighbor);
                        base = current;
                        current = neighbor;
                        break;
                    }
                }
            }
            verticesUpdated.put(current, replacementDistance);
        }
        vertices = verticesUpdated;
    }
    public Location getLoc() {
        return loc;
    }
    public HashMap<Location, Integer> getVertices() {
        return vertices;
    }
    private ArrayList<Location> getNeighboringIntersections(char[][] grid) {
        ArrayList<Location> goodLocs = new ArrayList<>();
        for (Direction dir : Direction.getCardinalDirections()) {
            Location next = loc.getDirectionalLoc(dir);
            if (!next.isOnGrid(grid) || grid[next.y][next.x] == '#') {
                continue;
            }
            while (true) {
                Location LEFT = next.getDirectionalLoc(dir.getClockwise());
                Location RIGHT = next.getDirectionalLoc(dir.getCounterClockwise());
                Location FRONT = next.getDirectionalLoc(dir);
                if (!FRONT.isOnGrid(grid) || grid[FRONT.y][FRONT.x] == '#') {
                    break;
                }
                if (LEFT.isOnGrid(grid) && grid[LEFT.y][LEFT.x] != '#') {
                    break;
                }
                if (RIGHT.isOnGrid(grid) && grid[RIGHT.y][RIGHT.x] != '#') {
                    break;
                }
                next = FRONT;
            }
            goodLocs.add(next);
        }
        return goodLocs;
    }
    public String toString() {
        return loc + "->" + vertices;
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        GraphNode other = (GraphNode) obj;
        return loc == other.loc;
    }
    @Override
    public int hashCode() {
        return Objects.hash(loc);
    }
}
