package com.github.Ramble21.classes.days;

public class Gate {

    private final String inputOne;
    private final String inputTwo;
    private String output;
    private final String operation;
    private final String toString;

    public Gate(String inputLine) {
        this.toString = inputLine;
        String[] arr = inputLine.split(" ");
        this.inputOne = arr[0];
        this.operation = arr[1];
        this.inputTwo = arr[2];
        this.output = arr[4];
    }

    public int getResultOfRunning(int inputOne, int inputTwo){
        return switch (operation){
            case "AND" -> inputOne & inputTwo;
            case "OR" -> inputOne | inputTwo;
            case "XOR" -> inputOne ^ inputTwo;
            default -> throw new IllegalArgumentException();
        };
    }

    public String getInputOne() {
        return inputOne;
    }
    public String getInputTwo() {
        return inputTwo;
    }
    public String getOperation() {
        return operation;
    }
    public String getOutput() {
        return output;
    }
    public void setOutput(String output) {
        this.output = output;
    }
    public String toString() {
        return toString;
    }
}
