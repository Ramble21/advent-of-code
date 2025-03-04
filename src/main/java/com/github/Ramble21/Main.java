package com.github.Ramble21;

import java.io.IOException;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws Exception {
        testSpecificDay(22);
    }
    public static void testEveryDay() throws IOException {
        DaySolver[] solvers = IntStream.rangeClosed(1, 25)
                .mapToObj(i -> {
                    try {
                        return (DaySolver) Class.forName("com.github.Ramble21.Day" + i)
                                .getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(DaySolver[]::new);
        long totalMs = 0;
        for (DaySolver solver : solvers){
            System.out.println(solver.solvePart1String());
            System.out.println(solver.solvePart2String());
            totalMs += solver.getPartOneMS() + solver.getPartTwoMS();
        }
        System.out.println("Advent of Code 2024 Total: " + totalMs + " ms");
    }
    public static void testSpecificDay(int dayNo) throws Exception {
        String className = "com.github.Ramble21.Day" + dayNo;
        DaySolver solver = (DaySolver) Class.forName(className).getDeclaredConstructor().newInstance();
        System.out.println(solver.solvePart1String());
        System.out.println(solver.solvePart2String());
    }
}