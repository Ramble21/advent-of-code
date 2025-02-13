package com.github.Ramble21;

import com.github.Ramble21.classes.general.Regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public abstract class DaySolver {

    public static String getInputFilePath(int dayNumber) {
        return "inputs/day" + dayNumber + ".txt";
    }

    public static List<String> getInputLines(int dayNumber) throws IOException {
        return new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Main.class.getResourceAsStream(getInputFilePath(dayNumber)))
        )).lines().toList();
    }

    public static void print2DArr(char[][] array) {
        for (char[] row : array) {
            System.out.println(row);
        }
    }


    public abstract long solvePart1() throws IOException;
    public abstract long solvePart2() throws IOException;

    public String solvePart1String() throws IOException {
        return timePart1();
    }
    public String solvePart2String() throws IOException {
        return timePart2();
    }
    public String timePart1() throws IOException {
        long start = System.currentTimeMillis();
        long result = solvePart1();
        long end = System.currentTimeMillis();
        System.out.println("Day " + parseDayNumber() + " part 1: " + (end - start) + " ms");
        return String.valueOf(result);
    }
    public String timePart2() throws IOException {
        long start = System.currentTimeMillis();
        long result = solvePart2();
        long end = System.currentTimeMillis();
        System.out.println("Day " + parseDayNumber() + " part 2: " + (end - start) + " ms");
        return String.valueOf(result);
    }
    private int parseDayNumber() {
        String className = this.getClass().getSimpleName();
        return Integer.parseInt(className.replaceAll("[^0-9]", ""));
    }
}

/* copy and paste for a new day class because im lazy - replace X with day number

package com.github.Ramble21;

import java.io.IOException;
import java.util.List;
import com.github.Ramble21.classes.general.*;

public class DayX extends DaySolver{
    private final List<String> input;
    public Day1() throws IOException {
        input = getInputLines(X);
    }
    public long solvePart1() throws IOException {
        return 0;
    }

    public long solvePart2() throws IOException {
        return 0;
    }
}

 */