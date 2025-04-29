package com.github.Ramble21.y2023.days;

import com.github.Ramble21.DaySolver;
import com.github.Ramble21.y2023.classes.Pulse;
import com.github.Ramble21.y2023.classes.PulseModule;

import java.io.IOException;
import java.util.*;

public class Day20 extends DaySolver {
    private final PulseModule[] modules;
    private final PulseModule broadcaster;
    public Day20() throws IOException {
        modules = getInputLines(2023, 20).stream().map(PulseModule::new).toArray(PulseModule[]::new);
        PulseModule broadcaster = null;
        for (PulseModule module : modules) {
            if (module.getIdentifier().equals("broadcaster")) {
                broadcaster = module;
            }
            module.initializeRequirements(modules);
        }
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
        return numButtonPressesNeeded("rx");
    }
    public long numButtonPressesNeeded(String moduleName) {
        ArrayList<PulseModule> requirements = getRequirementsOf(moduleName);
        boolean onlyContainsConjunctions = requirements.stream()
                .noneMatch(r -> r.getPrefix() == '%');
        if (onlyContainsConjunctions) {
            if (requirements.size() == 1) {
                return numButtonPressesNeeded(requirements.get(0).getIdentifier());
            }
            else {
                long[] count = new long[requirements.size()];
                for (int i = 0; i < requirements.size(); i++) {
                    count[i] = numButtonPressesNeeded(requirements.get(i).getIdentifier());
                }
                return Day8.lcmOfArray(count);
            }
        }
        return binaryCounterLength(moduleName);
    }
    public long binaryCounterLength(String conjunctionModName) {
        ArrayList<PulseModule> counterChain = getCounterChain(conjunctionModName);
        StringBuilder binaryCount = new StringBuilder();
        for (PulseModule p : counterChain) {
            char binaryResult = '0';
            for (String s : p.getDestinations()) {
                if (s.equals(conjunctionModName)) {
                    binaryResult = '1';
                    break;
                }
            }
            binaryCount.append(binaryResult);
        }
        binaryCount.reverse();
        System.out.println(binaryCount);
        return Integer.parseInt(binaryCount.toString(), 2);
    }
    public ArrayList<PulseModule> getCounterChain(String conjunctionModName) {
        ArrayList<PulseModule> result = getRequirementsOf(conjunctionModName);
        PulseModule conjunction = PulseModule.getModuleByName(conjunctionModName, modules);
        assert conjunction != null;
        for (String s : conjunction.getDestinations()) {
            PulseModule mod = PulseModule.getModuleByName(s, modules);
            assert mod != null;
            if (mod.getPrefix() == '%') {
                result.add(PulseModule.getModuleByName(s, modules));
            }
        }
        result.sort(Comparator.comparingInt(conj -> numberStepsFromBroadcaster(conj.getIdentifier())));
        result.remove(0);
        System.out.println(result);
        return result;
    }
    public String generateSequence(ArrayList<PulseModule> modules) {
        StringBuilder result = new StringBuilder();
        for (PulseModule m : modules) {
            result.append(m.toString().charAt(0));
        }
        return result.reverse().toString();
    }
    public ArrayList<PulseModule> getRequirementsOf(String moduleName) {
        ArrayList<PulseModule> result = new ArrayList<>();
        for (PulseModule p : modules) {
            for (String destination : p.getDestinations()) {
                if (destination.equals(moduleName)) {
                    result.add(p);
                    break;
                }
            }
        }
        return result;
    }
    public int numberStepsFromBroadcaster(String moduleIdentifier) {
        for (String s : broadcaster.getDestinations()) {
            if (s.equals(moduleIdentifier)) {
                return 1;
            }
        }
        for (PulseModule p : getRequirementsOf(moduleIdentifier)) {
            if (p.getPrefix() == '%') {
                return 1 + numberStepsFromBroadcaster(p.getIdentifier());
            }
        }
        throw new RuntimeException(moduleIdentifier);
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
    public void resetModules() {
        for (PulseModule m : modules) {
            m.reset();
        }
    }
}