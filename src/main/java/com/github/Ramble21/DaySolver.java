package com.github.Ramble21;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class DaySolver {

    public static String getInputFilePath(int dayNumber) {
        return "inputs/day" + String.valueOf(dayNumber) + ".txt";
    }

    public static List<String> getInputLines(int dayNumber) throws IOException {
        return new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Main.class.getResourceAsStream(getInputFilePath(dayNumber)))
        )).lines().toList();
    }

    public static String getInput(int dayNumber) throws IOException {
        return new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(Main.class.getResourceAsStream(getInputFilePath(dayNumber)))
        )).lines().collect(Collectors.joining("\n"));
    }

    public abstract int solvePart1() throws IOException;
    public abstract int solvePart2() throws IOException;
}

/* copy and paste for a new day class because im lazy - replace X with day number
public class DayX extends DaySolver{
    private List<String> input;
    public Day1() throws IOException {
        input = getInputLines(X);
    }
    public int solvePart1() throws IOException {
        return 0;
    }

    public int solvePart2() throws IOException {
        return 0;
    }
}
 */