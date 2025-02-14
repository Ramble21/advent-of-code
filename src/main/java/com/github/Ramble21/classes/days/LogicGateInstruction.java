package com.github.Ramble21.classes.days;

import java.util.Arrays;

public class LogicGateInstruction {
    private final LogicGate gateOne;
    private final LogicGate gateTwo;
    private final String operation;
    private final String[] actualInput;
    private LogicGate result = null;
    public LogicGateInstruction(String inputLine){
        String[] actualInput = inputLine.split(" ");
        gateOne = new LogicGate(actualInput[0]);
        operation = actualInput[1];
        gateTwo = new LogicGate(actualInput[2]);
        this.actualInput = actualInput;
        run();
    }
    public LogicGate getResult(){
        return result;
    }
    public void run(){
        if (gateOne.getValue() == -1 || gateTwo.getValue() == -1){
            return;
        }
       int value = switch (operation){
           case "OR" -> gateOne.getValue() | gateTwo.getValue();
           case "AND" -> gateOne.getValue() & gateTwo.getValue();
           case "XOR" -> gateOne.getValue() ^ gateTwo.getValue();
           default -> throw new IllegalArgumentException();
       };
       result = new LogicGate(actualInput[4], value);
    }
}
