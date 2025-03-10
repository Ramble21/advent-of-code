package com.github.Ramble21.helper_classes;

public enum Direction {
    LEFT(-1, 0, '<'),
    RIGHT(1, 0, '>'),
    UP(0, -1, '^'),
    DOWN(0, 1, 'v');

    private final int deltaX;
    private final int deltaY;
    private final char charRep;
    private static final Direction[] directionSet = {Direction.LEFT, Direction.UP, Direction.RIGHT, Direction.DOWN};

    Direction(int deltaX, int deltaY, char charRep){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.charRep = charRep;
    }
    public static Direction[] getDirectionSet() {
        return directionSet;
    }
    public int getDeltaX() {
        return deltaX;
    }
    public int getDeltaY() {
        return deltaY;
    }
    public char getCharRep() {
        return charRep;
    }
    public static Direction charToDir(char direction){
        return switch (direction) {
            case '^' -> Direction.UP;
            case 'v' -> Direction.DOWN;
            case '<' -> Direction.LEFT;
            case '>' -> Direction.RIGHT;
            default -> null;
        };
    }
    public Direction getCounterClockwise(){
        return switch (this) {
            case UP -> Direction.LEFT;
            case LEFT -> Direction.DOWN;
            case DOWN -> Direction.RIGHT;
            case RIGHT -> Direction.UP;
        };
    }
    public Direction getClockwise(){
        return switch (this) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
        };
    }
    public Direction getFlipped(){
        return switch (this) {
            case UP -> Direction.DOWN;
            case RIGHT -> Direction.LEFT;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
        };
    }
    public static Direction getDirectionToGo(Location from, Location to){
        int dx = to.getX() - from.getX();
        int dy = to.getY() - from.getY();
        if (dx > 0) return Direction.RIGHT;
        if (dx < 0) return Direction.LEFT;
        if (dy > 0) return Direction.DOWN;
        if (dy < 0) return Direction.UP;
        throw new RuntimeException("Locs " + from + " and " + to + " cannot be compared!");
    }
}
