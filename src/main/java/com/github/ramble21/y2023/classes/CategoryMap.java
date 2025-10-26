package com.github.ramble21.y2023.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryMap {
    private final ArrayList<CategoryMapSection> sections = new ArrayList<>();
    private final String inputType;
    private final String outputType;
    public CategoryMap(List<String> inputLineSection){
        String[] firstLine = inputLineSection.get(0).split("[ \\-]");
        inputType = firstLine[0];
        outputType = firstLine[2];
        for (int i = 1; i < inputLineSection.size(); i++){
            sections.add(new CategoryMapSection(inputLineSection.get(i)));
        }
    }
    public String getInputType() {
        return inputType;
    }
    public String getOutputType() {
        return outputType;
    }
    public long map(long x){
        for (CategoryMapSection section : sections){
            if (section.canMap(x)){
                return section.map(x);
            }
        }
        return x;
    }
    public long backtrackMap(long x){
        for (CategoryMapSection section : sections){
            if (section.canBacktrackMap(x)){
                return section.backtrackMap(x);
            }
        }
        return x;
    }
}
class CategoryMapSection {
    private final long range;
    private final long destinationStart;
    private final long sourceStart;
    public CategoryMapSection(String inputLine){
        long[] arr = Arrays.stream(inputLine.split(" ")).mapToLong(Long::parseLong).toArray();
        this.range = arr[2];
        this.destinationStart = arr[0];
        this.sourceStart = arr[1];
    }
    public boolean canMap(long x){
        return (x >= sourceStart) && (x < sourceStart + range);
    }
    public boolean canBacktrackMap(long x){
        return (x >= destinationStart) && (x < destinationStart + range);
    }
    public long map(long x){
        return x + (destinationStart - sourceStart);
    }
    public long backtrackMap(long x){
        return x - (destinationStart - sourceStart);
    }
    public String toString(){
        return "{" + destinationStart + " " + sourceStart + " " + range + "}";
    }
}