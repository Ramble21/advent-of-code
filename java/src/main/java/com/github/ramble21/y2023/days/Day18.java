package com.github.ramble21.y2023.days;

import com.github.ramble21.DaySolver;
import com.github.ramble21.helper_classes.*;
import com.github.ramble21.y2023.classes.Polygon;

import java.io.IOException;
import java.util.*;

public class Day18 extends DaySolver {
    private final List<String> input;
    public Day18() throws IOException {
        input = getInputLines(2023, 18);
    }
    public long solvePart1() throws IOException {
        Polygon perimeter = new Polygon();
        Location current = new Location(0, 0);

        ArrayList<Integer> importantYValues = new ArrayList<>();
        int currentImportantYValue = 0;

        for (String inputLine : input) {
            String[] parsed = inputLine.split("\\s+");
            Direction dir = parseDirection(parsed[0]);
            int len = Integer.parseInt(parsed[1]);
            if (dir == Direction.DOWN || dir == Direction.UP) {
                int next = currentImportantYValue + len;
                if (dir == Direction.UP) next -= (len * 2);
                importantYValues.add(next);
                currentImportantYValue = next;
            }
            Location furthestOut = new Location(current.x + dir.getDeltaX() * len, current.y + dir.getDeltaY() * len);
            perimeter.addSide(current, furthestOut);
            current = furthestOut;
        }

        Collections.sort(importantYValues);
        for (int i = importantYValues.size() - 2; i >= 0; i--) {
            importantYValues.add(i + 1, importantYValues.get(i) + 1);
        }

        return solve(perimeter, importantYValues);
    }

    public long solvePart2() throws IOException {
        Polygon perimeter = new Polygon();
        Location current = new Location(0, 0);
        ArrayList<Integer> importantYValues = new ArrayList<>();
        int currentImportantYValue = 0;

        for (String inputLine : input) {
            String[] parsed = inputLine.split("\\s+");
            Direction dir = parseDirection(parsed[2].substring(7, 8));
            int len = Integer.parseInt(parsed[2].substring(2, 7), 16);
            if (dir == Direction.DOWN || dir == Direction.UP) {
                int next = currentImportantYValue + len;
                if (dir == Direction.UP) next -= (len * 2);
                importantYValues.add(next);
                currentImportantYValue = next;
            }
            Location furthestOut = new Location(current.x + dir.getDeltaX() * len, current.y + dir.getDeltaY() * len);
            perimeter.addSide(current, furthestOut);
            current = furthestOut;
        }

        Collections.sort(importantYValues);
        for (int i = importantYValues.size() - 2; i >= 0; i--) {
            importantYValues.add(i + 1, importantYValues.get(i) + 1);
        }

        return solve(perimeter, importantYValues);
    }
    private Direction parseDirection(String ch) {
        return switch (ch) {
            case "U", "3" -> Direction.UP;
            case "L", "2" -> Direction.LEFT;
            case "R", "0" -> Direction.RIGHT;
            case "D", "1" -> Direction.DOWN;
            default -> throw new IllegalArgumentException(ch);
        };
    }
    private long solve(Polygon perimeter, ArrayList<Integer> importantYValues) {
        return perimeter.getSize() + getNumLocsInsidePolygon(perimeter.getMaxX(), perimeter.getMaxY(), perimeter.getMinX(), perimeter.getMinY(), perimeter, importantYValues);
    }
    private long getNumLocsInsidePolygon(int maxX, int maxY, int minX, int minY, Polygon polygon, ArrayList<Integer> importantYValues){
        long result = 0;
        int y = minY;
        while (y <= maxY) {
            long rowWideResult = solvePolygonRow(minX, maxX, y, polygon);
            int nextImportantY = getNextImportantYValue(y, maxY, importantYValues);
            result += rowWideResult * (nextImportantY - y);
            y = nextImportantY;
        }
        return result;
    }
    private int getNextImportantYValue(int y, int maxY, ArrayList<Integer> importantYValues) {
        for (int num : importantYValues) {
            if (num > y) return num;
        }
        return maxY + 1;
    }
    private long solvePolygonRow(int minX, int maxX, int y, Polygon polygon) {
        long result = 0;
        boolean isInside = false;
        int x = minX;
        while (x <= maxX) {
            Location current = new Location(x, y);
            if (polygon.contains(current) && polygon.contains(current.getDirectionalLoc(Direction.UP))) {
                isInside = !isInside;
                x++;
            }
            else if (!polygon.contains(current) && !isInside) {
                int nextX = polygon.getNextX(x, y);
                if (nextX == Integer.MAX_VALUE) break;
                x = nextX;
            }
            else if ( (!polygon.contains(current) && isInside) || (polygon.contains(current) && !polygon.contains(current.getDirectionalLoc(Direction.UP)))) {
                int nextX = polygon.getNextX(x, y);
                int numNonNorths = polygon.getNumNonNorths(x, nextX, y);
                if (isInside) {
                    result += (nextX - x - numNonNorths);
                }
                x = nextX;
            }
        }
        return result;
    }
}