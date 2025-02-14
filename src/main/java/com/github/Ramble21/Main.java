package com.github.Ramble21;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        DaySolver solution = new Day24();
        System.out.println(solution.solvePart1String());
        System.out.println(solution.solvePart2String());
    }
}