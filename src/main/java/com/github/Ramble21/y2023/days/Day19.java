package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.y2023.classes.Inequality;
import com.github.Ramble21.y2023.classes.RatingCombo;
import com.github.Ramble21.y2023.classes.Workflow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day19 extends DaySolver {
    private final HashSet<Workflow> workflows = new HashSet<>();
    private final String[] parts;
    public Day19() throws IOException {
        List<String> input = getInputLines(2023, 19);
        int emptyIndex = -1;
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i).isBlank()) {
                emptyIndex = i;
                break;
            }
        }

        parts = new String[input.size() - emptyIndex - 1];
        for (int i = 0; i < emptyIndex; i++) {
            workflows.add(new Workflow(input.get(i)));
        }
        for (int i = emptyIndex + 1; i < input.size(); i++) {
            parts[i - (emptyIndex + 1)] = input.get(i);
        }
    }
    public long solvePart1() throws IOException {
        int totalRatingNumbers = 0;
        for (String part : parts) {
            HashMap<String, Integer> ratings = convertToHashMap(part);
            String name = "in";
            while (!(name.equals("A") || name.equals("R"))) {
                Workflow current = getWorkflowByName(name);
                name = current.testSetOfNumbers(ratings);
            }
            if (name.equals("A")) {
                for (int rating : ratings.values()) {
                    totalRatingNumbers += rating;
                }
            }
        }
        return totalRatingNumbers;
    }
    public long solvePart2() throws IOException {
        return getAllValuesFromWorkflow(getWorkflowByName("in"), new RatingCombo());
    }
    private long getAllValuesFromWorkflow(Workflow w, RatingCombo restraints) {
        long total = 0;
        for (Inequality i : w.getInequalities()) {
            String destination = i.getDestination();
            RatingCombo newRestraints = restraints.getAppended(i);
            if (destination.equals("A")) {
                long x = newRestraints.getTotalCombos();
                total += x;
            }
            else if (!destination.equals("R")){
                total += getAllValuesFromWorkflow(getWorkflowByName(destination), newRestraints);
            }
            if (!i.isDefault()) {
                restraints.appendInequality(i.reversed());
            }
        }
        return total;
    }
    private HashSet<ArrayList<Inequality>> getInequalityTrees() {
        ArrayList<Inequality> temp = new ArrayList<>();
        HashSet<ArrayList<Inequality>> trees = new HashSet<>();
        Workflow current = getWorkflowByName("in");
        getAllTreesFromWorkflow(trees, temp, current);
        return trees;
    }
    private void getAllTreesFromWorkflow(HashSet<ArrayList<Inequality>> trees, ArrayList<Inequality> current, Workflow goal) {
        for (Inequality i : goal.getInequalities()) {
            if (i.getDestination().equals("A")) {
                current.add(i);
                trees.add(new ArrayList<>(current));
                current.remove(current.size() - 1);
            }
            else if (!i.getDestination().equals("R")) {
                current.add(i);
                Workflow destination = getWorkflowByName(i.getDestination());
                getAllTreesFromWorkflow(trees, current, destination);
                current.remove(current.size() - 1);
            }
        }
    }
    private Workflow getWorkflowByName(String name) {
        for (Workflow w : workflows) {
            if (w.getName().equals(name)) return w;
        }
        throw new IllegalArgumentException();
    }
    private HashMap<String, Integer> convertToHashMap(String inputLine) {
        HashMap<String, Integer> map = new HashMap<>();
        inputLine = inputLine.substring(1, inputLine.length() - 1);
        for (String pair : inputLine.split(",")) {
            String[] keyValue = pair.split("=");
            map.put(keyValue[0].trim(), Integer.parseInt(keyValue[1].trim()));
        }
        return map;
    }
}