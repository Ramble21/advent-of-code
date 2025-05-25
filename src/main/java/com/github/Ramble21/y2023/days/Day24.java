package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.y2024.classes.Hailstone;

import java.io.IOException;

public class Day24 extends DaySolver {
    private final Hailstone[] hailstones;
    public Day24() throws IOException {
        hailstones = getInputLines(2023, 24).stream().map(Hailstone::new).toArray(Hailstone[]::new);
    }
    public long solvePart1() throws IOException {
        int total = 0;
        for (int i = 0; i < hailstones.length; i++) {
            for (int j = i+1; j < hailstones.length; j++) {
                if (intersectsXY(hailstones[i], hailstones[j], 200000000000000.0, 400000000000000.0)) {
                    total++;
                }
            }
        }
        return total;
    }
    public boolean intersectsXY(Hailstone A, Hailstone B, double MIN, double MAX) {
        double slope_A = (double) A.getVelocityVector()[1] / A.getVelocityVector()[0];
        double intercept_A = A.getPositionVector()[1] - ((double) A.getVelocityVector()[1] * A.getPositionVector()[0] / A.getVelocityVector()[0]);
        double slope_B = (double) B.getVelocityVector()[1] / B.getVelocityVector()[0];
        double intercept_B = B.getPositionVector()[1] - ((double) B.getVelocityVector()[1] * B.getPositionVector()[0] / B.getVelocityVector()[0]);
        if (slope_A == slope_B && intercept_A == intercept_B) {
            return true;
        }
        if (slope_A == slope_B) {
            return false;
        }
        double x = (intercept_B - intercept_A) / (slope_A - slope_B);
        double y = slope_A * x + intercept_A;
        boolean withinTestArea = (x > MIN && x < MAX && y > MIN && y < MAX);
        boolean inTheFuture = meetsInFuture(A, x, y) && meetsInFuture(B, x, y);
        return withinTestArea && inTheFuture;
    }
    private boolean meetsInFuture(Hailstone hailstone, double x, double y) {
        boolean xInFuture = hailstone.getVelocityVector()[0] > 0 ? x > hailstone.getPositionVector()[0] : x < hailstone.getPositionVector()[0];
        boolean yInFuture = hailstone.getVelocityVector()[1] > 0 ? y > hailstone.getPositionVector()[1] : y < hailstone.getPositionVector()[1];
        return xInFuture && yInFuture;
    }
    public long solvePart2() throws IOException {
        return 0;
    }
}