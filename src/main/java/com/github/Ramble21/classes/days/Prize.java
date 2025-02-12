package com.github.Ramble21.classes.days;

public class Prize {
    private final int x;
    private final int y;
    private final long massiveX;
    private final long massiveY;
    public Prize(int x, int y){
        this.x = x;
        this.y = y;
        massiveX = x + 10000000000000L;
        massiveY = y + 10000000000000L;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public long getMassiveX() {
        return massiveX;
    }
    public long getMassiveY() {
        return massiveY;
    }

    public String toString(){
        return "(" + x + "," + y + ")";
    }
}
