package com.github.ramble21.y2023.classes;

public class BoatRace {
    private final long time;
    private final long recordDistance;
    public BoatRace(long time, long recordDistance){
        this.time = time;
        this.recordDistance = recordDistance;
    }
    public long getNumRecordBeats(){
        long count = 0;
        for (long i = 1; i < time; i++){
            if (simulateRace(i) > recordDistance) count++;
        }
        return count;
    }
    private long simulateRace(long buttonHoldTime){
        return (time - buttonHoldTime) * buttonHoldTime;
    }
}
