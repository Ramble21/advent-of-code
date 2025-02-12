package com.github.Ramble21;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DaySolver solution = new Day23();
        System.out.println(solution.solvePart1Timed());
        System.out.println(solution.solvePart2Timed());
    }
}