package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.y2023.classes.CategoryMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 extends DaySolver {
    private final ArrayList<CategoryMap> maps = new ArrayList<>();
    private final long[] seeds;
    public Day5() throws IOException {
        List<String> input = getInputLines(2023, 5);
        seeds = Arrays.stream(input.get(0).substring(input.get(0).indexOf(":") + 1).trim().split("\\s+")).mapToLong(Long::parseLong).toArray();

        int mapBeginning = 2;
        for (int i = 2; i <= input.size(); i++){
            if (i == input.size() || input.get(i).isBlank()){
                maps.add(new CategoryMap(input.subList(mapBeginning, i)));
                mapBeginning = i + 1;
            }
        }
    }
    public long solvePart1() throws IOException {
        long minResult = Long.MAX_VALUE;
        for (long seed : seeds){
            long temp = seed;
            for (CategoryMap map : maps){
                temp = map.map(temp);
            }
            minResult = Math.min(minResult, temp);
        }
        return minResult;
    }

    public long solvePart2() throws IOException {
        long minLocNumber = 0;
        while (true){
            long temp = minLocNumber;
            for (int i = maps.size() - 1; i >= 0; i--) {
                temp = maps.get(i).backtrackMap(temp);
            }
            for (int i = 1; i < seeds.length; i += 2){
                if (temp >= seeds[i - 1] && temp < (seeds[i - 1] + seeds[i])){
                    return minLocNumber;
                }
            }
            minLocNumber++;
        }
    }
}