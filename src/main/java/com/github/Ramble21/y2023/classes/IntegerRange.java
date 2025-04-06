package com.github.Ramble21.y2023.classes;

public class IntegerRange {
    private int min;
    private int max;
    private final char c;
    private boolean isDead;
    public IntegerRange(char c, int min, int max) {
        this.min = min;
        this.max = max;
        this.c = c;
        isDead = false;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }
    public boolean isInRange(int n) {
        if (isDead) return false;
        return n >= min && n <= max;
    }
    public char getPartLabel() {
        return c;
    }
    public void setMax(int max) {
        this.max = max;
    }
    public int getNumValues() {
        return (isDead) ? 0 : (max - min) + 1;
    }
    public void trim(IntegerRange other) {
        if (other.c != c || other.min > max || min > other.max) {
            return;
        }
        if (other.min >= min && other.max <= max) {
            other.isDead = true;
            return;
        }
        if (min >= other.min && max <= other.max) {
            isDead = true;
            return;
        }
        if (other.min < min) {
            other.max = min - 1;
        }
        else {
            max = other.min - 1;
        }
    }
    public String toString() {
        return c + ": " + (isDead ? "DEAD" : "[" + min + "," + max + "]");
    }
}
