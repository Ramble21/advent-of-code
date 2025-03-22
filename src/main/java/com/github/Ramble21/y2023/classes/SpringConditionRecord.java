package com.github.Ramble21.y2023.classes;

import java.util.Arrays;
import java.util.HashMap;

public class SpringConditionRecord {
    private int[] groupsOfDamagedSprings;
    private String springs;
    public SpringConditionRecord(String inputLine) {
        String[] split = inputLine.split("\\s+");
        this.springs = split[0];
        this.groupsOfDamagedSprings = Arrays.stream(split[1].split(",")).mapToInt(Integer::parseInt).toArray();
    }
    public long getNumPossibilities(){
        return numWays(springs, groupsOfDamagedSprings.clone());
    }
    public static HashMap<String, Long> memo = new HashMap<>();
    private long numWays(String springs, int[] groups) {
        String key = springs + Arrays.toString(groups);
        if (memo.containsKey(key)) return memo.get(key);
        if (springs.isEmpty() && groups.length == 0) {
            return 1;
        }
        if (springs.isEmpty()) {
            long value = (groups.length == 1 && groups[0] == 0) ? 1 : 0;
            memo.put(key, value);
            return value;
        }
        if (groups.length == 0) {
            for (char c : springs.toCharArray()) {
                if (c == '#') {
                    memo.put(key, 0L);
                    return 0;
                }
            }
            memo.put(key, 1L);
            return 1;
        }
        if (!springs.contains("?")) {
            long result = works(springs, groups) ? 1 : 0;
            memo.put(key, result);
            return result;
        }
        long result = 0;
        char c = springs.charAt(0);
        if (c != '#') { // . or ?
            result += numWays(springs.substring(1), groups);
        }
        if (c != '.') { // # or ?
            String updated = update(springs, groups);
            if (updated != null) {
                result += numWays(updated, Arrays.copyOfRange(groups, 1, groups.length));
            }
        }
        memo.put(key, result);
        return result;
    }
    private static String update(String springs, int[] groups) {
        if (springs.length() < groups[0]) return null;
        for (char c : springs.substring(0, groups[0]).toCharArray()) {
            if (c == '.') return null;
        }
        String result = springs.substring(groups[0]);
        if (result.isEmpty()) return result;
        else if (result.charAt(0) == '#') return null;
        else if (result.charAt(0) == '?'){
            return "." + result.substring(1);
        }
        return result;
    }
    private static boolean works(String springs, int[] groups) {
        String[] split = springs.replaceAll("^\\.+", "").split("\\.+");
        if (split.length != groups.length) return false;
        for (int i = 0; i < split.length; i++) {
            if (split[i].length() != groups[i]) return false;
        }
        return true;
    }
    public void unfold() {
        StringBuilder temp = new StringBuilder().append(springs);
        int[] temp2 = new int[groupsOfDamagedSprings.length * 5];
        for (int i = 0; i < 4; i++) {
            temp.append('?').append(springs);
        }
        for (int i = 0; i < temp2.length; i++){
            temp2[i] = groupsOfDamagedSprings[i % groupsOfDamagedSprings.length];
        }
        springs = temp.toString();
        groupsOfDamagedSprings = temp2;
    }
}
