package com.github.Ramble21.classes;

public class Prize {
    private final int x;
    private final int y;
    public Prize(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public String toString(){
        return "(" + x + "," + y + ")";
    }
}
