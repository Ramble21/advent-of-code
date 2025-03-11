package com.github.Ramble21.helper_classes;

public enum Direction {
    LEFT(-1, 0, '<'),
    RIGHT(1, 0, '>'),
    UP(0, -1, '^'),
    DOWN(0, 1, 'v'),

    DOWNLEFT(-1, 1, '↙'),
    UPLEFT(-1, -1, '↖'),
    UPRIGHT(1, -1, '↗'),
    DOWNRIGHT(1, 1, '↘');

    private final int deltaX;
    private final int deltaY;
    private final char charRep;
    private static final Direction[] cardinalDirections = {Direction.LEFT, Direction.UP, Direction.RIGHT, Direction.DOWN};
    private static final Direction[] allDirections = {Direction.LEFT, Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.UPLEFT, Direction.UPRIGHT, Direction.DOWNLEFT, Direction.DOWNRIGHT};


    Direction(int deltaX, int deltaY, char charRep){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.charRep = charRep;
    }
    public static Direction[] getCardinalDirections() {
        return cardinalDirections;
    }
    public static Direction[] getAllDirections() {
        return allDirections;
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
            default -> throw new IllegalArgumentException("Method doesn't support non cardinal directions");
        };
    }
    public Direction getClockwise(){
        return switch (this) {
            case UP -> Direction.RIGHT;
            case RIGHT -> Direction.DOWN;
            case DOWN -> Direction.LEFT;
            case LEFT -> Direction.UP;
            default -> throw new IllegalArgumentException("Method doesn't support non cardinal directions");
        };
    }
    public Direction getFlipped(){
        return switch (this) {
            case UP -> Direction.DOWN;
            case RIGHT -> Direction.LEFT;
            case DOWN -> Direction.UP;
            case LEFT -> Direction.RIGHT;
            default -> throw new IllegalArgumentException("Method doesn't support non cardinal directions");
        };
    }
}
