package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.y2023.classes.BoatRace;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day6 extends DaySolver {
    private final int[] times;
    private final int[] recordDistances;
    public Day6() throws IOException {
        List<String> input = getInputLines(2023, 6);
        times = Arrays.stream(input.get(0).substring(input.get(0).indexOf(":") + 1).trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        recordDistances = Arrays.stream(input.get(1).substring(input.get(1).indexOf(":") + 1).trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
    }
    public long solvePart1() throws IOException {
        BoatRace[] races = new BoatRace[times.length];
        for (int i = 0; i < races.length; i++){
            races[i] = new BoatRace(times[i], recordDistances[i]);
        }
        long product = 1;
        for (BoatRace race : races){
            product *= race.getNumRecordBeats();
        }
        return product;
    }

    public long solvePart2() throws IOException {
        BoatRace longRace = new BoatRace(removeKerning(times), removeKerning(recordDistances));
        return longRace.getNumRecordBeats();
    }
    private static long removeKerning(int[] arr){
        StringBuilder result = new StringBuilder();
        for (int x : arr){
            result.append(x);
        }
        return Long.parseLong(result.toString());
    }
}