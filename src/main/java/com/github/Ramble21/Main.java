package com.github.Ramble21;

public class Main {
    public static void main(String[] args) throws Exception {
        testSpecificDay(2023, 14);

    }
    public static void testEveryDay(int year) throws Exception {
        long totalMs = 0;
        for (int i = 1; i <= 25; i++){
            String className = "com.github.Ramble21.y" + year + ".days.Day" + i;
            try {
                long start = System.currentTimeMillis();
                DaySolver solver = (DaySolver) Class.forName(className).getDeclaredConstructor().newInstance();
                long end = System.currentTimeMillis();
                System.out.println("Day " + i + " class creation: " + (end - start) + " ms");
                solver.solve();
                totalMs += (solver.getPartOneMS() + solver.getPartTwoMS() + (start - end));
                System.out.println();
            }
            catch (ClassNotFoundException e){
                System.out.println("Advent of Code " + year + " Partial Total: " + totalMs + " ms");
                return;
            }
        }
        System.out.println("Advent of Code " + year + " Total: " + totalMs + " ms");
    }
    public static void testSpecificDay(int year, int dayNo) throws Exception {
        String className = "com.github.Ramble21.y" + year + ".days.Day" + dayNo;
        long start = System.currentTimeMillis();
        DaySolver solver = (DaySolver) Class.forName(className).getDeclaredConstructor().newInstance();
        long end = System.currentTimeMillis();
        System.out.println("Day " + dayNo + " class creation: " + (end - start) + " ms");
        solver.solve();
    }
}