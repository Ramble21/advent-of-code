package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.y2024.classes.Hailstone;
import java.util.*;
import org.apache.commons.math3.linear.*;

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
    public long solvePart2() {
        // i basically just copied this logic and switched it to java i have no idea how to do the linear algebra
        // https://pastebin.com/NmR6ZDXL
        Hailstone h0 = hailstones[0];
        Hailstone h1 = hailstones[1];
        Hailstone h2 = hailstones[2];

        double[] p0 = Arrays.stream(h0.getPositionVector()).mapToDouble(x -> x).toArray();
        double[] p1 = Arrays.stream(h1.getPositionVector()).mapToDouble(x -> x).toArray();
        double[] p2 = Arrays.stream(h2.getPositionVector()).mapToDouble(x -> x).toArray();

        double[] v0 = Arrays.stream(h0.getVelocityVector()).asDoubleStream().toArray();
        double[] v1 = Arrays.stream(h1.getVelocityVector()).asDoubleStream().toArray();
        double[] v2 = Arrays.stream(h2.getVelocityVector()).asDoubleStream().toArray();

        Array2DRowRealMatrix M = new Array2DRowRealMatrix(6, 6);
        ArrayRealVector rhs = new ArrayRealVector(6);

        RealVector p0cv0 = cross(p0, v0);
        RealVector p1cv1 = cross(p1, v1);
        RealVector p2cv2 = cross(p2, v2);

        rhs.setSubVector(0, p1cv1.subtract(p0cv0));
        rhs.setSubVector(3, p2cv2.subtract(p0cv0));

        M.setSubMatrix(crossMatrix(v0).subtract(crossMatrix(v1)).getData(), 0, 0);
        M.setSubMatrix(crossMatrix(v0).subtract(crossMatrix(v2)).getData(), 3, 0);
        M.setSubMatrix(crossMatrix(p1).subtract(crossMatrix(p0)).getData(), 0, 3);
        M.setSubMatrix(crossMatrix(p2).subtract(crossMatrix(p0)).getData(), 3, 3);

        RealVector solution = new LUDecomposition(M).getSolver().solve(rhs);
        RealVector rockPos = solution.getSubVector(0, 3);

        return Math.round(rockPos.getEntry(0)) + Math.round(rockPos.getEntry(1)) + Math.round(rockPos.getEntry(2));
    }
    public static Array2DRowRealMatrix crossMatrix(double[] v) {
        return new Array2DRowRealMatrix(new double[][] {
                {0, -v[2], v[1]},
                {v[2], 0, -v[0]},
                {-v[1], v[0], 0}
        });
    }
    public static RealVector cross(double[] a, double[] b) {
        return new ArrayRealVector(new double[] {
                a[1] * b[2] - a[2] * b[1],
                a[2] * b[0] - a[0] * b[2],
                a[0] * b[1] - a[1] * b[0]
        });
    }
}