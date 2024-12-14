package com.github.Ramble21;

import com.github.Ramble21.*;
import com.github.Ramble21.classes.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        DaySolver solution = new Day14();
        System.out.println(solution.solvePart1());
        System.out.println(solution.solvePart2());
    }
}