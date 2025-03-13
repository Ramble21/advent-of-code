package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.y2023.classes.BoatRace;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day6 extends DaySolver {
    private final BoatRace[] races;
    public Day6() throws IOException {
        List<String> input = getInputLines(2023, 6);
        int[] times = Arrays.stream(input.get(0).substring(input.get(0).indexOf(":") + 1).trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        int[] recordDistances = Arrays.stream(input.get(1).substring(input.get(1).indexOf(":") + 1).trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        races = new BoatRace[times.length];
        for (int i = 0; i < races.length; i++){
            races[i] = new BoatRace(times[i], recordDistances[i]);
        }
    }
    public long solvePart1() throws IOException {
        int product = 1;
        for (BoatRace race : races){
            product *= race.getNumRecordBeats();
        }
        return product;
    }

    public long solvePart2() throws IOException {
        return 0;
    }
}