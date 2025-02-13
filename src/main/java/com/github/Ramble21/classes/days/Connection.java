package com.github.Ramble21.classes.days;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Connection {

    private final String computer1;
    private final String computer2;

    private static final HashMap<String, HashSet<String>> existingConnections = new HashMap<>();
    private static final HashSet<Connection> connections = new HashSet<>();
    private static HashSet<String> result;

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
        result = new HashSet<>();
        for (Connection c : connections){
            for (String s : existingConnections.keySet()){
                if (hasConnectionWith(c.computer1, s) && hasConnectionWith(c.computer2, s)){
                    result.add(arrayify(new String[]{c.computer1, c.computer2, s}, true));
                }
            }
        }
        return result;
    }
    public static String findLongestSet(HashSet<String> result){
        while (true){
            HashSet<String> temp = findSetsOfLength(result);
            if (temp.isEmpty()){
                for (String s : result){
                    return s;
                }
            }
            else result = temp;
        }
    }
    private static HashSet<String> findSetsOfLength(HashSet<String> oneLess){
        HashSet<String> result = new HashSet<>();
        for (String set : oneLess){
            String[] args = unarrayify(set);
            outerLoop:
            for (String newString : existingConnections.keySet()){
                for (String oldString : args){
                    if (!hasConnectionWith(oldString, newString)){
                        continue outerLoop;
                    }
                }
                String[] newSet = new String[args.length+1];
                newSet[0] = newString;
                System.arraycopy(args, 0, newSet, 1, newSet.length - 1);
                result.add(arrayify(newSet, false));
            }
        }
        return result;
    }
    private static boolean hasConnectionWith(String first, String other){
        return existingConnections.get(first).contains(other);
    }
    private static String[] unarrayify(String strings){
        if (strings.endsWith("|")) {
            strings = strings.substring(0, strings.length() - 1);
        }
        return strings.split(",");
    }
    private static String arrayify(String[] strings, boolean part1){
        Arrays.sort(strings);
        StringBuilder set = new StringBuilder();
        set.append(strings[0]);
        for (int i = 1; i < strings.length; i++){
            set.append(",").append(strings[i]);
        }
        if (part1){
            boolean hasT = false;
            for (String s : strings){
                if (s.startsWith("t")) {
                    hasT = true;
                    break;
                }
            }
            set.append(hasT ? "" : "|");
        }
        return set.toString();
    }
}
