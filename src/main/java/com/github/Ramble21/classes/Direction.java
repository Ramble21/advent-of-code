package com.github.Ramble21.classes;

public enum Direction {
    LEFT(-1, 0, '^'),
    RIGHT(1, 0, 'v'),
    UP(0, -1, '<'),
    DOWN(0, 1, '>');

    private final int deltaX;
    private final int deltaY;
    private final char charRep;

    Direction(int deltaX, int deltaY, char charRep){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.charRep = charRep;
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

}
