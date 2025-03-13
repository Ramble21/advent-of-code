package com.github.Ramble21.y2023.classes;

public class BoatRace {
    private final int time;
    private final int recordDistance;
    public BoatRace(int time, int recordDistance){
        this.time = time;
        this.recordDistance = recordDistance;
    }
    public int getNumRecordBeats(){
        int count = 0;
        for (int i = 1; i < time; i++){
            if (simulateRace(i) > recordDistance) count++;
        }
        return count;
    }
    private int simulateRace(int buttonHoldTime){
        return (time - buttonHoldTime) * buttonHoldTime;
    }
}
