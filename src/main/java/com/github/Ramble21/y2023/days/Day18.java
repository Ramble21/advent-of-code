package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.helper_classes.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day18 extends DaySolver {
    private final List<String> input;
    public Day18() throws IOException {
        input = getInputLines(2023, 18);
    }
    public long solvePart1() throws IOException {
        HashSet<Location> perimeter = new HashSet<>();
        Location current = new Location(0, 0);
        perimeter.add(current);
        for (String inputLine : input) {
            String[] parsed = inputLine.split("\\s+");
            for (int i = 0; i < Integer.parseInt(parsed[1]); i++) {
                current = current.getDirectionalLoc(parseDirection(parsed[0]));
                perimeter.add(current);
            }
        }
        int maxX = 0, maxY = 0;
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        for (Location l : perimeter) {
            maxX = Math.max(l.x, maxX);
            maxY = Math.max(l.y, maxY);
            minX = Math.min(l.x, minX);
            minY = Math.min(l.y, minY);
        }

        return perimeter.size() + getAllLocsInsidePolygon(maxX, maxY, minX, minY, perimeter).size();
    }

    public long solvePart2() throws IOException {
        return 0;
    }
    private Direction parseDirection(String ch) {
        return switch (ch) {
            case "U" -> Direction.UP;
            case "L" -> Direction.LEFT;
            case "R" -> Direction.RIGHT;
            case "D" -> Direction.DOWN;
            default -> throw new IllegalArgumentException(ch);
        };
    }
    private HashSet<Location> getAllLocsInsidePolygon(int maxX, int maxY, int minX, int minY, Set<Location> polygon){
        HashSet<Location> result = new HashSet<>();
        for (int y = minY; y <= maxY; y++) {
            boolean isInside = false;
            for (int x = minX; x <= maxX; x++) {
                Location current = new Location(x, y);
                if (polygon.contains(current) && polygon.contains(current.getDirectionalLoc(Direction.UP))) {
                    isInside = !isInside;
                }
                else if (!polygon.contains(current) && isInside) {
                    result.add(current);
                }
            }
        }
        return result;
    }

}