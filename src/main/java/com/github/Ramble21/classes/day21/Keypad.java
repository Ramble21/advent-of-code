package com.github.Ramble21.classes.day21;

import com.github.Ramble21.classes.general.*;
import java.util.HashSet;

public class Keypad {
    private static final char[][] numericKeypad = {
            {'7', '8', '9'},
            {'4', '5', '6'},
            {'1', '2', '3'},
            {'#', '0', 'A'}
    };
    private static final char[][] directionalKeypad = {
            {'#', '^', 'A'},
            {'<', 'v', '>'}
    };
    private static Location charToLoc(char ch, boolean isDirectional){
        char[][] keypad = isDirectional ? directionalKeypad : numericKeypad;
        for (int r = 0; r < keypad.length; r++) {
            for (int c = 0; c < keypad[r].length; c++) {
                if (keypad[r][c] == ch) {
                    return new Location(c, r);
                }
            }
        }
        throw new IllegalArgumentException("Character not found in keypad: " + ch);
    }

    public static HashSet<String> robot(String s, boolean isDirectional){
        char last = 'A';
        boolean firstIteration = true;
        HashSet<String> result = new HashSet<>();
        for (char c : s.toCharArray()){
            HashSet<String> miniRoutes = findRoutes(charToLoc(last, isDirectional), charToLoc(c, isDirectional), isDirectional);
            last = c;
            if (firstIteration){
                firstIteration = false;
                result.addAll(miniRoutes);
            }
            else if (!miniRoutes.isEmpty()){
                appendHashSets(result, miniRoutes);
            }
        }
        return result;
    }
    private static void appendHashSets(HashSet<String> A, HashSet<String> B) {
        HashSet<String> result = new HashSet<>();
        for (String a : A) {
            for (String b : B) {
                result.add(a + b);
            }
        }
        A.clear();
        A.addAll(result);
    }
    private static HashSet<String> findRoutes(Location start, Location end, boolean isDirectional) {
        HashSet<String> routes = new HashSet<>();
        if (start.equals(end)) {
            routes.add("A");
            return routes;
        }
        int dx = end.getX() - start.getX();
        int dy = end.getY() - start.getY();
        StringBuilder moves = new StringBuilder();
        moves.append((dx > 0 ? ">" : "<").repeat(Math.max(0, Math.abs(dx))));
        moves.append((dy > 0 ? "v" : "^").repeat(Math.max(0, Math.abs(dy))));
        permute(moves.toString().toCharArray(), 0, moves.length() - 1, routes, start, isDirectional);
        return new HashSet<>(routes.stream().map(route -> route + "A").toList());
    }
    private static void permute(char[] arr, int left, int right, HashSet<String> routes, Location start, boolean isDirectional) {
        if (left == right) {
            String route = new String(arr);
            if (isValidRoute(route, start, isDirectional)) {
                routes.add(route);
            }
        }
        else {
            HashSet<Character> used = new HashSet<>();
            for (int i = left; i <= right; i++) {
                if (used.contains(arr[i])) {
                    continue;
                }
                used.add(arr[i]);
                swap(arr, left, i);
                permute(arr, left + 1, right, routes, start, isDirectional);
                swap(arr, left, i);
            }
        }
    }
    private static void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    private static boolean isValidRoute(String route, Location start, boolean isDirectional) {
        char[][] keypad = isDirectional ? directionalKeypad : numericKeypad;
        HashSet<Location> locs = getLocationsAlongRoute(start, route);
        for (Location loc : locs){
            if (!loc.isOnGrid(keypad) || keypad[loc.getY()][loc.getX()] == '#'){
                return false;
            }
        }
        return true;
    }
    private static HashSet<Location> getLocationsAlongRoute(Location start, String route) {
        HashSet<Location> locations = new HashSet<>();
        locations.add(new Location(start.getX(), start.getY()));
        Location current = new Location(start.getX(), start.getY());
        for (char move : route.toCharArray()) {
            if (move == 'A') break;
            current = current.getDirectionalLoc(Direction.charToDir(move));
            locations.add(new Location(current.getX(), current.getY()));
        }
        return locations;
    }
}
