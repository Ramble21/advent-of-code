package com.github.ramble21.y2023.days;

import com.github.ramble21.DaySolver;
import com.github.ramble21.helper_classes.Direction;
import com.github.ramble21.helper_classes.Location;
import com.github.ramble21.y2023.classes.GraphNode;

import java.io.IOException;
import java.util.*;

public class Day23 extends DaySolver {
    private final char[][] grid;
    private Location start;
    private Location end;
    public Day23() throws IOException {
        grid = getInputAsGrid(2023, 23);
        for (int c = 0; c < grid[0].length; c++) {
            if (grid[0][c] != '#') {
                start = new Location(c, 0);
            }
            if (grid[grid.length - 1][c] != '#') {
                end = new Location(c, grid.length - 1);
            }
        }
    }
    public long solvePart1() throws IOException {
        return dfsLongestPath(start, new HashSet<>());
    }
    public long solvePart2() throws IOException {
        HashMap<Location, GraphNode> graph = new HashMap<>();
        GraphNode startingNode = null;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                Location loc = new Location(c, r);
                if (isVertex(loc)) {
                    GraphNode node = new GraphNode(loc, grid);
                    graph.put(loc, node);
                    if (loc.equals(start)) {
                        startingNode = node;
                    }
                }
            }
        }
        HashMap<Location, GraphNode> condensedGraph = new HashMap<>();
        for (GraphNode l : graph.values()) {
            if (l.getVertices().size() != 2) {
                l.condenseGraph(graph);
                condensedGraph.put(l.getLoc(), l);
            }
        }
        assert startingNode != null;
        return dfsGraph(startingNode, new HashSet<>(), condensedGraph);
    }
    private int dfsGraph(GraphNode current, HashSet<GraphNode> visited, HashMap<Location, GraphNode> graph) {
        if (current.getLoc().equals(end)) {
            return 0;
        }
        visited.add(current);
        int max = Integer.MIN_VALUE;
        HashMap<Location, Integer> neighbors = current.getVertices();
        for (Map.Entry<Location, Integer> entry : neighbors.entrySet()) {
            GraphNode neighbor = graph.get(entry.getKey());
            int distance = entry.getValue();
            if (!visited.contains(neighbor)) {
                max = Math.max(dfsGraph(neighbor, visited, graph) + distance, max);
            }
        }
        visited.remove(current);
        return max;
    }
    private int dfsLongestPath(Location current, HashSet<Location> visited) {
        if (current.equals(end)) {
            return 0;
        }
        visited.add(current);
        int max = Integer.MIN_VALUE;
        for (Location neighbor : getNeighbors(current)) {
            if (!visited.contains(neighbor)) {
                max = Math.max(dfsLongestPath(neighbor, visited) + Location.getTaxicabDistance(current, neighbor), max);
            }
        }
        visited.remove(current);
        return max;
    }
    private ArrayList<Location> getNeighbors(Location current) {
        ArrayList<Location> result = Location.getNeighbors(current, grid);
        if (grid[current.y][current.x] != '.') {
            result = new ArrayList<>(List.of(current.getDirectionalLoc(Direction.charToDir(grid[current.y][current.x]))));
        }
        result.removeIf(l -> grid[l.y][l.x] == '#');
        return result;
    }
    private boolean isVertex(Location l) {
        if (l.equals(start) || l.equals(end)) {
            return true;
        }
        if (grid[l.y][l.x] == '#') {
            return false;
        }
        ArrayList<Location> neighbors = Location.getNeighbors(l, grid);
        neighbors.removeIf(a -> grid[a.y][a.x] == '#');
        if (neighbors.size() != 2) {
            return neighbors.size() > 2;
        }
        return neighbors.get(0).x != neighbors.get(1).x && neighbors.get(0).y != neighbors.get(1).y;
    }
}

