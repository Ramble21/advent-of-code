package com.github.ramble21.y2023.classes;

public class Inequality {
    private char inequalitySymbol;
    private char partLabel;
    private int comparisonNumber;
    private final String destination;
    private final boolean isDefault;
    public Inequality(String inputLine) {
        inequalitySymbol = inputLine.contains("<") ? '<' : '>';
        partLabel = inputLine.charAt(0);
        comparisonNumber = Integer.parseInt(inputLine.substring(inputLine.indexOf(inequalitySymbol) + 1, inputLine.indexOf(":")));
        destination = inputLine.substring(inputLine.indexOf(":") + 1);
        isDefault = false;
    }
    public Inequality(String inputLine, boolean isDefault) {
        this.destination = inputLine;
        this.isDefault = isDefault;
    }
    private Inequality(Inequality reversed) {
        destination = reversed.destination;
        isDefault = reversed.isDefault;
        partLabel = reversed.partLabel;
        if (reversed.inequalitySymbol == '<') {
            inequalitySymbol = '>';
            comparisonNumber = reversed.comparisonNumber - 1;
        }
        else {
            inequalitySymbol = '<';
            comparisonNumber = reversed.comparisonNumber + 1;
        }
    }
    public Inequality reversed() {
        return new Inequality(this);
    }
    public boolean inputPasses(int input) {
        if (isDefault) return true;
        if (inequalitySymbol == '<') return input < comparisonNumber;
        return input > comparisonNumber;
    }
    public String getDestination() {
        return destination;
    }
    public char getPartLabel() {
        return partLabel;
    }
    public int getComparisonNumber() {
        return comparisonNumber;
    }
    public char getInequalitySymbol() {
        return inequalitySymbol;
    }
    public String toString() {
        if (isDefault) return destination;
        return partLabel + "" + inequalitySymbol + comparisonNumber + ":" + destination;
    }
    public boolean isDefault() {
        return isDefault;
    }
}