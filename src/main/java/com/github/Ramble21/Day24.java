package com.github.Ramble21;

import com.github.Ramble21.classes.days.Gate;

import java.io.IOException;
import java.util.*;

public class Day24 extends DaySolver {
    private final List<String> input;
    private final ArrayList<Gate> gates = new ArrayList<>();
    private final HashMap<String, Integer> initialValues = new HashMap<>();

    public Day24() throws IOException {
        input = getInputLines(24);
        for (String s : input){
            if (s.contains(":")){
                initialValues.put(s.substring(0, 3), Integer.parseInt(s.charAt(s.length()-1) + ""));
            }
            else if (!s.isEmpty()){
                gates.add(new Gate(s));
            }
        }
    }

    public long solvePart1() throws IOException {
        HashMap<String, Integer> results = new HashMap<>();
        LinkedList<Gate> gateQueue = new LinkedList<>(gates);
        while (!gateQueue.isEmpty()){
            Gate current = gateQueue.pollFirst();
            if (results.containsKey(current.getInputOne()) && results.containsKey(current.getInputTwo())){
                results.put(current.getOutput(), current.getResultOfRunning(results.get(current.getInputOne()), results.get(current.getInputTwo())));
            }
            else if (initialValues.containsKey(current.getInputOne()) && initialValues.containsKey(current.getInputTwo())){
                results.put(current.getOutput(), current.getResultOfRunning(initialValues.get(current.getInputOne()), initialValues.get(current.getInputTwo())));
            }
            else{
                gateQueue.addLast(current);
            }
        }
        String currentGateNo = "z00";
        StringBuilder result = new StringBuilder();
        while (results.containsKey(currentGateNo)){
            result.append(results.get(currentGateNo));
            currentGateNo = "z" + (Integer.parseInt(currentGateNo.substring(1)) + 1);
            if (currentGateNo.length() == 2) currentGateNo = "z0" + currentGateNo.substring(currentGateNo.length()-1);
        }
        return Long.parseLong(result.reverse().toString(), 2);
    }

    public long solvePart2() throws IOException {
        return 0;
    }
}
