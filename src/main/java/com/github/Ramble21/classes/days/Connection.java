package com.github.Ramble21.classes.days;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Connection {

    private final String computer1;
    private final String computer2;
    private static final HashMap<String, HashSet<String>> existingConnections = new HashMap<>();
    private static final HashSet<Connection> connections = new HashSet<>();
    public Connection(String textRaw){
        computer1 = textRaw.substring(0, 2);
        computer2 = textRaw.substring(3);
        updateConnections();
        connections.add(this);
    }
    private void updateConnections(){
        HashSet<String> connections1 = existingConnections.getOrDefault(computer1, new HashSet<>());
        connections1.add(computer2);
        existingConnections.put(computer1, connections1);
        HashSet<String> connections2 = existingConnections.getOrDefault(computer2, new HashSet<>());
        connections2.add(computer1);
        existingConnections.put(computer2, connections2);
    }
    public static HashSet<String> findAllSets(){
        HashSet<String> result = new HashSet<>();
        for (Connection c : connections){
            for (String s : existingConnections.keySet()){
                if (hasConnectionWith(c.computer1, s) && hasConnectionWith(c.computer2, s)){
                    result.add(arrayify(c.computer1, c.computer2, s));
                }
            }
        }
        return result;
    }
    private static boolean hasConnectionWith(String first, String other){
        return existingConnections.get(first).contains(other);
    }
    private static String arrayify(String computer1, String computer2, String computer3){
        String[] strings = {computer1, computer2, computer3};
        Arrays.sort(strings);
        boolean hasT = strings[0].startsWith("t") || strings[1].startsWith("t") ||strings[2].startsWith("t");
        return strings[0] + "," + strings[1] + "," + strings[2] + "," + (hasT ? "t" : "f");
    }
}
