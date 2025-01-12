package com.github.Ramble21;

import java.io.IOException;
import java.util.*;

public class Day19 extends DaySolver{
    private final List<String> input;
    private final String[] patterns;
    private final String[] designs;
    private int dividerLine = -1;
    public Day19() throws IOException {
        input = getInputLines(19);
        for (int i = 0; i < input.size(); i++){
            if (input.get(i).isEmpty() || input.get(i).isBlank()) {
                dividerLine = i;
                break;
            }
        }
        patterns = input.get(0).split(", ");
        designs = designs();
    }

    private final HashMap<String, String> originalDesigns = new HashMap<>();
    private final HashMap<String, ArrayList<String>> map = new HashMap<>();
    private final HashSet<String> goodDesigns = new HashSet<>();
    private final HashMap<String, Boolean> memoization = new HashMap<>();

    public long solvePart1() throws IOException {
        for (String design : designs) {
            map.put(design, new ArrayList<>());
            originalDesigns.put(design, design);
            keepTrying(design, design);
        }
        return goodDesigns.size();
    }

    public boolean keepTrying(String originalDesign, String remainingDesign){
        if (remainingDesign.isBlank() || remainingDesign.isEmpty()){
            goodDesigns.add(originalDesign);
            return true;
        }
        if (memoization.containsKey(remainingDesign)) return memoization.get(remainingDesign);
        if (goodDesigns.contains(originalDesign)) return true;
        boolean result = false;
        for (String pattern : patterns){
            if (remainingDesign.startsWith(pattern)){
                ArrayList<String> temp = map.get(originalDesign);
                temp.add(pattern);
                map.put(originalDesign, temp);
                String newRemaining = getRemainingDesign(remainingDesign, pattern);

                boolean x = (keepTrying(originalDesign, newRemaining));
                memoization.put(newRemaining, x);
                if (x){
                    result = true;
                    goodDesigns.add(originalDesign);
                }
            }
        }
        return result;
    }

    public long solvePart2() throws IOException {
        return 0;
    }

    public String[] designs(){
        String[] output = new String[input.size()-(dividerLine+1)];
        for (int i = (dividerLine+1); i < input.size(); i++){
            output[i-(dividerLine+1)] = input.get(i);
        }
        return output;
    }

    public String getRemainingDesign(String whole, String piece){
        return whole.substring(piece.length());
    }

}