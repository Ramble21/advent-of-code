package com.github.Ramble21;

import com.github.Ramble21.*;
import com.github.Ramble21.classes.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Day9 solution = new Day9();
        System.out.println(solution.solvePart1Long());
        System.out.println(solution.solvePart2Long());
    }
}