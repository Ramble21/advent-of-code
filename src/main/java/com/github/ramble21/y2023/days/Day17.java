package com.github.ramble21.y2023.days;

import com.github.ramble21.DaySolver;
import com.github.ramble21.helper_classes.Location;
import com.github.ramble21.y2023.classes.HeatBlock;

import java.io.IOException;
import java.util.*;

public class Day17 extends DaySolver {
    private final char[][] grid;
    public Day17() throws IOException {
        grid = getInputAsGrid(2023, 17);
    }
    public long solvePart1() throws IOException {
        HashMap<HeatBlock, Integer> distances = new HashMap<>();
        HeatBlock origin = new HeatBlock();
        Location target = new Location(grid[0].length - 1, grid.length - 1);
        distances.put(origin, 0);
        Comparator<HeatBlock> comparator = Comparator.comparingInt(distances::get);

        PriorityQueue<HeatBlock> queue = new PriorityQueue<>(comparator);
        queue.add(origin);
        HashSet<HeatBlock> visited = new HashSet<>();

        int maxAcceptableDistance = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            HeatBlock current = queue.poll();
            if (distances.get(current) >= maxAcceptableDistance) continue;

            for (Location neighbor : current.nextLocations()) {
                HeatBlock block = current.move(neighbor);
                if (!neighbor.isOnGrid(grid) || visited.contains(block)) continue;

                int d = grid[neighbor.y][neighbor.x] - '0' + distances.get(current);
                if (d < maxAcceptableDistance && (!distances.containsKey(block) || d < distances.get(block))) {
                    distances.put(block, d);
                    queue.add(block);
                    if (neighbor.equals(target)) {
                        maxAcceptableDistance = d;
                    }
                }
            }
            visited.add(current);
        }
        return maxAcceptableDistance;
    }

    public long solvePart2() throws IOException {
        HashMap<HeatBlock, Integer> distances = new HashMap<>();
        HeatBlock origin = new HeatBlock();
        Location target = new Location(grid[0].length - 1, grid.length - 1);
        distances.put(origin, 0);
        Comparator<HeatBlock> comparator = Comparator.comparingInt(distances::get);
        PriorityQueue<HeatBlock> queue = new PriorityQueue<>(comparator);
        queue.add(origin);
        HashSet<HeatBlock> visited = new HashSet<>();

        int maxAcceptableDistance = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            HeatBlock current = queue.poll();
            if (distances.get(current) >= maxAcceptableDistance) continue;

            for (Location neighbor : current.nextUltraLocations()) {
                HeatBlock block = current.move(neighbor);
                if (!neighbor.isOnGrid(grid) || visited.contains(block)) continue;

                int d = grid[neighbor.y][neighbor.x] - '0' + distances.get(current);
                if (d < maxAcceptableDistance && (!distances.containsKey(block) || d < distances.get(block))) {
                    distances.put(block, d);
                    queue.add(block);
                    if (neighbor.equals(target)) {
                        maxAcceptableDistance = d;
                    }
                }
            }
            visited.add(current);
        }
        return maxAcceptableDistance;
    }


}