package com.github.Ramble21.y2023.classes;

import java.util.HashMap;

public class Workflow {
    private final String name;
    private final Inequality[] inequalities;

    public Workflow(String inputLine) {
        name = inputLine.substring(0, inputLine.indexOf("{"));
        String[] parts = inputLine.substring(inputLine.indexOf("{") + 1, inputLine.length() - 1).split(",");
        inequalities = new Inequality[parts.length];
        for (int i = 0; i < parts.length - 1; i++) {
            inequalities[i] = new Inequality(parts[i]);
        }
        inequalities[parts.length - 1] = new Inequality(parts[parts.length - 1], true);
    }
    public String getName() {
        return name;
    }
    public Inequality[] getInequalities() {
        return inequalities;
    }
    public String testSetOfNumbers(HashMap<String, Integer> map) {
        for (Inequality inequality : inequalities) {
            if (inequality.isDefault() || inequality.inputPasses(map.get(inequality.getPartLabel() + ""))) {
                return inequality.getDestination();
            }
        }
        throw new RuntimeException();
    }
}
