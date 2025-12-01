package com.github.ramble21.y2023.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class PulseModule {
    private final String identifier;
    private final String[] destinations;
    private final char prefix;

    private boolean flipFlopState;
    private HashMap<String, Boolean> mostRecentPulses;

    public PulseModule(String inputLine) {
        String[] parsed = inputLine.split("[\\s,->]+");
        this.prefix = parsed[0].charAt(0);
        this.identifier = (prefix == 'b') ? "broadcaster" : parsed[0].substring(1);
        this.destinations = Arrays.copyOfRange(parsed, 1, parsed.length);
        if (prefix == '%') flipFlopState = false;
    }

    public void reset() {
        mostRecentPulses.clear();
        flipFlopState = false;
    }
    public String getIdentifier() {
        return identifier;
    }
    public String[] getDestinations() {
        return destinations;
    }
    public char getPrefix() {
        return prefix;
    }
    public HashMap<String, Boolean> getMostRecentPulses() {
        return mostRecentPulses;
    }
    public boolean onlyRemembersHighPulses() {
        if (prefix == '%') return false;
        return mostRecentPulses.containsValue(false);
    }
    public void initializeRequirements(PulseModule[] modules) {
        ArrayList<PulseModule> result = new ArrayList<>();
        for (PulseModule p : modules) {
            for (String destination : p.getDestinations()) {
                if (destination.equals(identifier)) {
                    result.add(p);
                    break;
                }
            }
        }
        mostRecentPulses = new HashMap<>();
        for (PulseModule requirement : result) {
            mostRecentPulses.put(requirement.identifier, false);
        }
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
    public String toString() {
        return "" + (prefix == '&' ? (onlyRemembersHighPulses() ? 1 : 0) : (flipFlopState ? 1 : 0)) + prefix + identifier + "->" + Arrays.toString(getDestinations());
    }
}
