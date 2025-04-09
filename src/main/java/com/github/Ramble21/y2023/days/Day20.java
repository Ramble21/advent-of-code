package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.y2023.classes.Pulse;
import com.github.Ramble21.y2023.classes.PulseModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Day20 extends DaySolver {
    private final PulseModule[] modules;
    private final PulseModule broadcaster;
    public Day20() throws IOException {
        modules = getInputLines(2023, 20).stream().map(PulseModule::new).toArray(PulseModule[]::new);
        PulseModule broadcaster = null;
        for (PulseModule module : modules) {
            if (module.getIdentifier().equals("broadcaster")) {
                broadcaster = module;
                break;
            }
        }
        assert broadcaster != null;
        this.broadcaster = broadcaster;
    }
    public long solvePart1() throws IOException {
        long totalLow = 0, totalHigh = 0;
        for (int i = 0; i < 1000; i++) {
            long[] button = pushButton();
            totalLow += button[0];
            totalHigh += button[1];
        }
        return totalLow * totalHigh;
    }
    public long solvePart2() throws IOException {
        return 0;
    }
    public long[] pushButton() {
        long low = broadcaster.getDestinations().length + 1, high = 0;
        Queue<Pulse> pulses = new LinkedList<>(broadcaster.getPulses(false, "button", modules));
        while (!pulses.isEmpty()) {
            Pulse current = pulses.poll();
            if (current.targetModule() == null) continue;
            ArrayList<Pulse> result = current.targetModule().getPulses(current.high(), current.from(), modules);
            for (Pulse p : result) {
                if (p.high()) high++;
                else low++;
            }
            pulses.addAll(result);
        }
        return new long[]{low, high};
    }
}