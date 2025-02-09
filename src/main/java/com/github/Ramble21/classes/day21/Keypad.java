package com.github.Ramble21.classes.day21;

import com.github.Ramble21.classes.general.*;
import java.util.HashSet;
import java.util.Set;

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
        if (start.equals(end)) {
            HashSet<String> routes = new HashSet<>();
            routes.add("A");
            return routes;
        }
        String moves = (
                (end.getX() - start.getX() > 0 ? ">" : "<").repeat(Math.max(0, Math.abs(end.getX() - start.getX()))) +
                (end.getY() - start.getY() > 0 ? "v" : "^").repeat(Math.max(0, Math.abs(end.getY() - start.getY())))
        );
        HashSet<String> routes = permute(moves, start, isDirectional);
        return new HashSet<>(routes.stream().map(route -> route + "A").toList());
    }
    private static HashSet<String> permute(String moves, Location start, boolean isDirectional){
        String ONE = getLR(moves) + getUD(moves);
        String TWO = getUD(moves) + getLR(moves);
        HashSet<String> result = ONE.equals(TWO) ? new HashSet<>(Set.of(ONE)) : new HashSet<>(Set.of(ONE, TWO));
        result.removeIf(s -> !isValidRoute(s, start, isDirectional));
        return result;
    }
    private static String getLR(String full){
        StringBuilder LR = new StringBuilder();
        for (char c : full.toCharArray()){
            if (c == '<' || c == '>') LR.append(c);
        }
        return LR.toString();
    }
    private static String getUD(String full){
        StringBuilder UD = new StringBuilder();
        for (char c : full.toCharArray()){
            if (c == 'v' || c == '^') UD.append(c);
        }
        return UD.toString();
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
