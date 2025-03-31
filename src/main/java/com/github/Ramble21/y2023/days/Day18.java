package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.helper_classes.*;

import java.io.IOException;
import java.util.*;

public class Day18 extends DaySolver {
    private final List<String> input;
    public Day18() throws IOException {
        input = getInputLines(2023, 18);
    }
    public long solvePart1() throws IOException {
        HashSet<Location> perimeter = new HashSet<>();
        Location current = new Location(0, 0);
        perimeter.add(current);
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
            for (int i = 0; i < len; i++) {
                current = current.getDirectionalLoc(dir);
                perimeter.add(current);
            }
        }

        Collections.sort(importantYValues);
        for (int i = importantYValues.size() - 2; i >= 0; i--) {
            importantYValues.add(i + 1, importantYValues.get(i) + 1);
        }

        return solve(perimeter, importantYValues);
    }

    public long solvePart2() throws IOException {
        HashSet<Location> perimeter = new HashSet<>();
        Location current = new Location(0, 0);
        perimeter.add(current);
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
            for (int i = 0; i < len; i++) {
                current = current.getDirectionalLoc(dir);
                perimeter.add(current);
            }
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
    private long solve(HashSet<Location> perimeter, ArrayList<Integer> importantYValues) {
        int maxX = 0, maxY = 0;
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        for (Location l : perimeter) {
            maxX = Math.max(l.x, maxX);
            maxY = Math.max(l.y, maxY);
            minX = Math.min(l.x, minX);
            minY = Math.min(l.y, minY);
        }
        return perimeter.size() + getNumLocsInsidePolygon(maxX, maxY, minX, minY, perimeter, importantYValues);
    }
    private long getNumLocsInsidePolygon(int maxX, int maxY, int minX, int minY, Set<Location> polygon, ArrayList<Integer> importantYValues){
        long result = 0;
        int y = minY;
        while (y <= maxY) {
            long rowWideResult = solvePolygonRow(minX, maxX, y, polygon);
            int nextImportantY = getNextImportantYValue(y, maxY, importantYValues);
            result += rowWideResult * (nextImportantY - y);
            System.out.println("+" + rowWideResult + "*" + (nextImportantY - y));
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
    private long solvePolygonRow(int minX, int maxX, int y, Set<Location> polygon) {
        long result = 0;
        boolean isInside = false;
        int x = minX;
        while (x <= maxX) {
            Location current = new Location(x, y);
            System.out.println(current);
            if (polygon.contains(current) && polygon.contains(current.getDirectionalLoc(Direction.UP))) {
                isInside = !isInside;
                x++;
            } else if (polygon.contains(current) && !polygon.contains(current.getDirectionalLoc(Direction.UP))) {
                x++;
            } else if (!polygon.contains(current) && !isInside) {
                int nextX = Integer.MAX_VALUE;
                for (Location l : polygon) {
                    if (l.y == y) {
                        if (polygon.contains(l.getDirectionalLoc(Direction.UP)) && l.x > x) {
                            nextX = Math.min(nextX, l.x);
                        }
                    }
                }
                if (nextX == Integer.MAX_VALUE) {
                    break;
                }
                x = nextX;
            } else if (!polygon.contains(current) && isInside) {
                int nextX = Integer.MAX_VALUE;
                int numNotNorths = 0;
                for (Location l : polygon) {
                    if (l.y == y) {
                        if (l.x > x) {
                            nextX = Math.min(nextX, l.x);
                        }
                    }
                }
                for (Location l : polygon) {
                    if (l.y == y) {
                        if (!polygon.contains(l.getDirectionalLoc(Direction.UP)) && l.x < nextX && l.x > x) {
                            numNotNorths++;
                        }
                    }
                }
                result += (nextX - x - numNotNorths);
                x = nextX;
            }
        }
        return result;
    }

}