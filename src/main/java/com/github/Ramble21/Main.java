package com.github.Ramble21;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DaySolver solution = new Day20();
        System.out.println(solution.solvePart1());
        System.out.println(solution.solvePart2());
    }
}