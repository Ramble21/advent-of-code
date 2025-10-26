package com.github.ramble21.y2023.classes;

public record Pulse(boolean high, PulseModule targetModule, String from) {
    public String toString() {
        return from + " -" + (high ? "high" : "low") + "-> " + ((targetModule == null) ? "TESTING_MODULE" : targetModule.getIdentifier());
    }
}
