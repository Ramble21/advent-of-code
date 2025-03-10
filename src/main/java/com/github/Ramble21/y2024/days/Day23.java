package com.github.Ramble21.y2024.days;

import com.github.Ramble21.DaySolver;

import java.io.IOException;
import java.util.*;

public class Day23 extends DaySolver {

    private final ArrayList<HashSet<String>> cliques = new ArrayList<>();
    private final HashMap<String, HashSet<String>> connections = new HashMap<>();
    private String part2;

    public Day23() throws IOException {
        List<String> input = getInputLines(2024, 23);
        for (String s : input){
            cliques.add(new HashSet<>(Arrays.asList(s.split("-"))));
            String computer1 = s.substring(0, 2);
            String computer2 = s.substring(3);
            HashSet<String> current1 = connections.getOrDefault(computer1, new HashSet<>());
            HashSet<String> current2 = connections.getOrDefault(computer2, new HashSet<>());
            current1.add(computer2);
            current2.add(computer1);
            connections.put(computer1, current1);
            connections.put(computer2, current2);
        }

    }
    public long solvePart1() throws IOException {
        int count = 0;
        ArrayList<HashSet<String>> temp = updateConnections(cliques);
        cliques.clear();
        cliques.addAll(temp);
        for (HashSet<String> clique : cliques){
            for (String cpu : clique){
                if (cpu.startsWith("t")){
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    public long solvePart2() throws IOException {
        while (true){
            ArrayList<HashSet<String>> temp = updateConnections(cliques);
            if (temp.isEmpty()) break;
            cliques.clear();
            cliques.addAll(temp);
        }
        String[] sorted = cliques.get(0).toArray(new String[0]);
        Arrays.sort(sorted);
        part2 = String.join(",", sorted);
        return 0; // String needed
    }
    public String solvePart2String() throws IOException {
        timePart2();
        return part2;
    }

    private ArrayList<HashSet<String>> updateConnections(ArrayList<HashSet<String>> previousCliques){
        ArrayList<HashSet<String>> newCliques = new ArrayList<>();
        for (HashSet<String> previousClique : previousCliques) {
            newCliques.addAll(addAdditions(previousClique));
        }
        removeDuplicates(newCliques);
        return newCliques;
    }
    private HashSet<HashSet<String>> addAdditions(HashSet<String> previousClique){
        HashSet<HashSet<String>> result = new HashSet<>();
        String firstNeeded = previousClique.iterator().next();
        outerLoop:
        for (String s : connections.get(firstNeeded)){
            for (String needed : previousClique){
                if (needed.equals(firstNeeded)) continue;
                if (!connections.get(needed).contains(s)) continue outerLoop;
            }
            HashSet<String> n = new HashSet<>(previousClique);
            n.add(s);
            result.add(n);
        }
        return result;
    }
    private void removeDuplicates(ArrayList<HashSet<String>> cliques){
        HashSet<HashSet<String>> seen = new HashSet<>();
        cliques.removeIf(clique -> {
            if (seen.contains(clique)) return true;
            seen.add(clique);
            return false;
        });
    }
}