package com.github.Ramble21.y2024.classes;

import com.github.Ramble21.helper_classes.Location;

import java.util.Arrays;
import java.util.Objects;

public class Hailstone {
    private final long[] position;
    private final int[] velocity;
    private final int hailstoneNumber;
    private static int currentHailstoneNumber = 0;
    public Hailstone(String inputLine) {
        position = Arrays.stream(inputLine.substring(0, inputLine.indexOf("@")).trim().split("[\\s,]+")).mapToLong(Long::parseLong).toArray();
        velocity = Arrays.stream(inputLine.substring(inputLine.indexOf("@")+1).trim().split("[\\s,]+")).mapToInt(Integer::parseInt).toArray();
        hailstoneNumber = currentHailstoneNumber++;
    }
    public int[] getVelocityVector() {
        return velocity;
    }
    public long[] getPositionVector() {
        return position;
    }
    public String toString() {
        return "#" + hailstoneNumber;
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Hailstone other = (Hailstone) obj;
        return Arrays.equals(position, other.position) && Arrays.equals(velocity, other.velocity);
    }
    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(position), Arrays.hashCode(velocity));
    }
}
