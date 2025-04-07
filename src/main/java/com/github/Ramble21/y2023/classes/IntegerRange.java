package com.github.Ramble21.y2023.classes;

public class IntegerRange {
    private int min;
    private int max;
    private final char c;
    private boolean isEmpty = false;
    public IntegerRange(char c, int min, int max) {
        this.min = min;
        this.max = max;
        this.c = c;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }
    public boolean isInRange(int n) {
        return n >= min && n <= max;
    }
    public char getPartLabel() {
        return c;
    }
    public void setMax(int max) {
        this.max = max;
    }
    public int getNumValues() {
        return (isEmpty) ? 0 : max - min + 1;
    }
    public IntegerRange trim(IntegerRange other) {
        if (other.min > max || min > other.max) {
            isEmpty = true;
            return this;
        }
        if (other.min == min && other.max == max) {
            return other;
        }
        if (other.min > min && other.max < max) {
            return other;
        }
        if (min > other.min && max < other.max) {
            return this;
        }
        if (min < other.min) {
            min = other.min;
            return this;
        }
        else {
            other.min = min;
            return other;
        }
    }
    public String toString() {
        return (isEmpty) ? "EMPTY" : c + ": " +  "[" + min + "," + max + "]";
    }
    public IntegerRange copy() {
        return new IntegerRange(c, min, max);
    }
}
