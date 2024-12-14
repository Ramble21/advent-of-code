package com.github.Ramble21.classes;

public class ClawButton {
    private final int deltaX;
    private final int deltaY;
    private final char name;
    public ClawButton(int deltaX, int deltaY, char name){
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.name = name;
    }
    public int getDeltaX() {
        return deltaX;
    }
    public int getDeltaY() {
        return deltaY;
    }
    public char getName() {
        return name;
    }
    public String toString(){
        return "claw" + name + ": x=" + deltaX + ", y=" + deltaY;
    }
}
