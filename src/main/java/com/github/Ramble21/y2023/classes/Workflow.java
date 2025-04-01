package com.github.Ramble21.y2023.classes;

import java.util.HashMap;
import java.util.Objects;

public class Workflow {
    private final String name;
    private final Inequality[] inequalities;
    private final String defaultDestination;

    public Workflow(String inputLine) {
        name = inputLine.substring(0, inputLine.indexOf("{"));
        String[] parts = inputLine.substring(inputLine.indexOf("{") + 1, inputLine.length() - 1).split(",");
        inequalities = new Inequality[parts.length - 1];
        defaultDestination = parts[parts.length - 1];
        for (int i = 0; i < parts.length - 1; i++) {
            inequalities[i] = new Inequality(parts[i]);
        }
    }
    public String getName() {
        return name;
    }
    public String testSetOfNumbers(HashMap<String, Integer> map) {
        for (Inequality inequality : inequalities) {
            if (inequality.inputPasses(map.get(inequality.getPartLabel() + ""))) {
                return inequality.getDestination();
            }
        }
        return defaultDestination;
    }

}
class Inequality {
    private final String destination;
    private final char inequalitySymbol;
    private final char partLabel;
    private final int comparisonNumber;
    public Inequality(String inputLine) {
        inequalitySymbol = inputLine.contains("<") ? '<' : '>';
        partLabel = inputLine.charAt(0);
        comparisonNumber = Integer.parseInt(inputLine.substring(inputLine.indexOf(inequalitySymbol) + 1, inputLine.indexOf(":")));
        destination = inputLine.substring(inputLine.indexOf(":") + 1);
    }
    public boolean inputPasses(int input) {
        if (inequalitySymbol == '<') return input < comparisonNumber;
        return input > comparisonNumber;
    }
    public String getDestination() {
        return destination;
    }
    public char getPartLabel() {
        return partLabel;
    }
}