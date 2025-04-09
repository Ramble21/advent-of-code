package com.github.Ramble21.y2023.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PulseModule {
    private final String identifier;
    private final String[] destinations;
    private final char prefix;

    private boolean flipFlopState;
    private HashMap<String, Boolean> mostRecentPulses = new HashMap<>();

    public PulseModule(String inputLine) {
        String[] parsed = inputLine.split("[\\s,->]+");
        this.prefix = parsed[0].charAt(0);
        this.identifier = (prefix == 'b') ? "broadcaster" : parsed[0].substring(1);
        this.destinations = Arrays.copyOfRange(parsed, 1, parsed.length);
        if (prefix == '%') flipFlopState = false;
    }
    public String getIdentifier() {
        return identifier;
    }
    public String[] getDestinations() {
        return destinations;
    }
    private Boolean getOutputPulse(boolean input, String inputFrom, PulseModule[] modules) {
        if (prefix == 'b') {
            return false;
        }
        else if (prefix == '%') {
            if (input) return null;
            flipFlopState = !flipFlopState;
            return flipFlopState;
        }
        else {
            mostRecentPulses.put(inputFrom, input);
            for (PulseModule m : modules) {
                for (String d : m.getDestinations()) {
                    if (d.equals(identifier) && !mostRecentPulses.getOrDefault(m.identifier, false)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
    public ArrayList<Pulse> getPulses(boolean input, String inputFrom, PulseModule[] modules) {
        ArrayList<Pulse> result = new ArrayList<>();
        Boolean skibidi = getOutputPulse(input, inputFrom, modules);
        for (String destination : destinations) {
            if (skibidi != null) {
                result.add(new Pulse(skibidi, getModuleByName(destination, modules), identifier));
            }
        }
        return result;
    }
    public static PulseModule getModuleByName(String goal, PulseModule[] modules) {
        for (PulseModule p : modules) {
            if (p.identifier.equals(goal)) return p;
        }
        return null;
    }
}
