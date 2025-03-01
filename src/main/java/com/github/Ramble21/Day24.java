package com.github.Ramble21;

import com.github.Ramble21.classes.days.Gate;

import java.io.IOException;
import java.util.*;

public class Day24 extends DaySolver {
    private final List<String> input;

    private final ArrayList<Gate> gates = new ArrayList<>();
    private final HashMap<String, Integer> initialValues = new HashMap<>();
    private final HashMap<Integer, String> carryOuts = new HashMap<>();
    private final ArrayList<String> swappedWires = new ArrayList<>();
    private String part2;

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
        carryOuts.put(0, Objects.requireNonNull(getGateByInputs("x00", "y00", "AND")).getOutput());
        for (int adderNo = 1; adderNo <= 44; adderNo++){
            Gate[] fullAdder = getFullAdder(adderNo);
            if (adderWorks(fullAdder, adderNo)){
                carryOuts.put(adderNo, fullAdder[4].getOutput());
            }
            else if (fullAdder[4] != null && coutAndSumAreSwapped(fullAdder, adderNo)){
                Gate[] unswapped = swapCoutWithSum(fullAdder, adderNo);
                carryOuts.put(adderNo, unswapped[4].getOutput());
            }
            else{
                Gate[] unswapped = unswapAdder(fullAdder, adderNo);
                carryOuts.put(adderNo, unswapped[4].getOutput());
            }
        }
        swappedWires.sort(String::compareTo);
        part2 = String.join(",", swappedWires);
        return 0; // String needed
    }
    public String solvePart2String() throws IOException {
        timePart2();
        return part2;
    }
    private boolean coutAndSumAreSwapped(Gate[] adder, int adderNo){
        return adder[4].getOutput().equals("z" + (adderNo < 10 ? "0" + adderNo : adderNo));
    }
    private Gate[] unswapAdder(Gate[] adder, int adderNo) {
        if (adderWorks(adder, adderNo)){
            return adder;
        }
        // try to swap CIN with A and B
        Gate carryIn = getGateByOutput(carryOuts.get(adderNo - 1));
        swapGates(adder[1], carryIn);
        carryOuts.put(adderNo - 1, carryIn.getOutput());
        if (adderWorks(adder, adderNo)){
            swappedWires.add(adder[1].getOutput());
            swappedWires.add(carryIn.getOutput());
            return getFullAdder(adderNo);
        }
        swapGates(adder[1], carryIn);
        carryOuts.put(adderNo - 1, carryIn.getOutput());
        // try to swap A and B with A xor B
        swapGates(adder[0], adder[1]);
        if (adderWorks(adder, adderNo)){
            swappedWires.add(adder[1].getOutput());
            swappedWires.add(adder[0].getOutput());
            return getFullAdder(adderNo);
        }
        swapGates(adder[0], adder[1]);
        // try to swap A and B with SUM
        swapGates(adder[1], adder[2]);
        if (adderWorks(adder, adderNo)){
            swappedWires.add(adder[1].getOutput());
            swappedWires.add(adder[2].getOutput());
            return getFullAdder(adderNo);
        }
        swapGates(adder[1], adder[2]);
        // try to swap SUM with (A xor B) and CIN
        swapGates(adder[3], adder[2]);
        if (adderWorks(adder, adderNo)){
            swappedWires.add(adder[2].getOutput());
            swappedWires.add(adder[3].getOutput());
            return getFullAdder(adderNo);
        }
        swapGates(adder[3], adder[2]);
        throw new IllegalArgumentException();
    }
    private Gate[] swapCoutWithSum(Gate[] adder, int adderNo){
        swapGates(adder[4], adder[2]);
        swappedWires.add(adder[2].getOutput());
        swappedWires.add(adder[4].getOutput());
        return getFullAdder(adderNo);
    }
    private void swapGates(Gate one, Gate two){
        int oneIndex = getGateIndexByInputs(one.getInputOne(), one.getInputTwo(), one.getOperation());
        int twoIndex = getGateIndexByInputs(two.getInputOne(), two.getInputTwo(), two.getOperation());
        String temp = one.getOutput();
        one.setOutput(two.getOutput());
        gates.set(oneIndex, one);
        two.setOutput(temp);
        gates.set(twoIndex, two);
    }
    private boolean adderWorks(Gate[] adder, int adderNo){
        if (!Arrays.asList(adder).contains(null)){
            return !coutAndSumAreSwapped(adder, adderNo);
        }
        else{
            Gate[] retry = getFullAdder(adderNo);
            return !Arrays.asList(retry).contains(null) && !coutAndSumAreSwapped(retry, adderNo);
        }
    }
    private Gate getGateByOutput(String output) {
        for (Gate g : gates){
            if (g.getOutput().equals(output)) return g;
        }
        throw new IllegalArgumentException();
    }

    private Gate getGateByInputs(String input1, String input2, String operation) {
        for (Gate g : gates){
            if (!g.getOperation().equals(operation)) continue;
            if (g.getInputOne().equals(input1) && g.getInputTwo().equals(input2)) return g;
            else if (g.getInputTwo().equals(input1) && g.getInputOne().equals(input2)) return g;
        }
        return null;
    }
    private int getGateIndexByInputs(String input1, String input2, String operation) {
        for (int i = 0; i < gates.size(); i++){
            Gate g = gates.get(i);
            if (!g.getOperation().equals(operation)) continue;
            if (g.getInputOne().equals(input1) && g.getInputTwo().equals(input2)) return i;
            else if (g.getInputTwo().equals(input1) && g.getInputOne().equals(input2)) return i;
        }
        return 100;
    }
    private Gate[] getFullAdder(int adderNo){
        Gate[] result = new Gate[5];
        String x = "x" + (adderNo < 10 ? "0" + adderNo : adderNo);
        String y = "y" + (adderNo < 10 ? "0" + adderNo : adderNo);
        String cin = carryOuts.get(adderNo - 1);

        result[0] = getGateByInputs(x, y, "XOR");
        result[1] = getGateByInputs(x, y, "AND");
        result[2] = (result[0] == null) ? null : getGateByInputs(cin, result[0].getOutput(), "XOR");
        result[3] = (result[0] == null) ? null : getGateByInputs(cin, result[0].getOutput(), "AND");
        result[4] = (result[1] == null || result[3] == null) ? null : getGateByInputs(result[3].getOutput(), result[1].getOutput(), "OR");
        return result;
    }
}
