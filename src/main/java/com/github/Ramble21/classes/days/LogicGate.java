package com.github.Ramble21.classes.days;

import java.util.HashMap;

public class LogicGate {
    private final String identifier;
    private int value;
    private final static HashMap<String, Integer> logicGates = new HashMap<>();
    public LogicGate(String identifier, int value){
        this.identifier = identifier;
        this.value = value;
        logicGates.put(identifier, value);
    }
    public LogicGate(String identifier){
        this.identifier = identifier;
    }
    public int getValue(){
        return logicGates.getOrDefault(identifier, -1);
    }
    public static HashMap<String, Integer> getLogicGates(){
        return logicGates;
    }
}
