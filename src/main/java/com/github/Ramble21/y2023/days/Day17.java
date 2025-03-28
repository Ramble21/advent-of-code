package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.helper_classes.Location;
import com.github.Ramble21.y2023.classes.HeatBlock;

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
        distances.put(origin, 0);
        Comparator<HeatBlock> comparator = Comparator.comparingInt(block -> distances.getOrDefault(block, Integer.MAX_VALUE));
        PriorityQueue<HeatBlock> queue = new PriorityQueue<>(comparator);
        queue.add(origin);
        HashSet<HeatBlock> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            HeatBlock current = queue.poll();
            for (Location neighbor : current.nextLocations()) {
                HeatBlock block = current.move(neighbor);
                if (!neighbor.isOnGrid(grid) || visited.contains(block)) continue;

                int d = grid[neighbor.y][neighbor.x] - '0' + distances.get(current);
                if (!distances.containsKey(block) || d < distances.get(block)) {
                    distances.put(block, d);
                    queue.add(block);
                }
            }
            visited.add(current);
        }
        int min = Integer.MAX_VALUE;
        Location target = new Location(grid[0].length - 1, grid.length - 1);
        for (HeatBlock l : visited) {
            if (l.getLocation().equals(target)) {
                min = Math.min(distances.get(l), min);
            }
        }
        return min;
    }

    public long solvePart2() throws IOException {
        return 0;
    }


}