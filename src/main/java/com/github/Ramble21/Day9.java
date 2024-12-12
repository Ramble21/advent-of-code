package com.github.Ramble21;

import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day9 extends DaySolver{
    private final List<String> input;
    private char[] diskMap;
    private String[] fileBlocks;

    public Day9() throws IOException {
        input = getInputLines(9);
    }
    public int solvePart1() throws IOException {
        return 0; // it needs to be returned as a long
    }
    public int solvePart2() throws IOException {
        return 0; // same
    }
    public long solvePart1Long(){
        initDiskMap();
        initFileBlocks();
        compactFileBlocks();
        long checksum = 0;
        for (int i = 0; i < fileBlocks.length; i++){
            if (fileBlocks[i].equals(".")) break;
            checksum += Long.parseLong(fileBlocks[i])*i;
        }
        return checksum;
    }
    public long solvePart2Long(){
        initDiskMap();
        initFileBlocks();
        greedyCompactFileBlocks();
        long checksum = 0;
        for (int i = 0; i < fileBlocks.length; i++){
            if (fileBlocks[i].equals(".")) continue;
            checksum += Long.parseLong(fileBlocks[i])*i;
        }
        return checksum;
    }


    public void initDiskMap(){
        ArrayList<Character> diskMapAL = new ArrayList<>();
        for (String s : input){
            for (int i = 0; i < s.length(); i++){
                diskMapAL.add(s.charAt(i));
            }
        }
        diskMap = new char[diskMapAL.size()];
        for (int i = 0; i < diskMapAL.size(); i++) {
            diskMap[i] = diskMapAL.get(i);
        }
    }
    public void initFileBlocks(){
        ArrayList<String> fbAsAL = new ArrayList<>();
        for (int i = 0; i < diskMap.length; i++){
            if (i % 2 == 0){
                for (int j = 0; j < Integer.parseInt(Character.toString(diskMap[i])); j++) {
                    fbAsAL.add(Integer.toString(i/2));
                }
            }
            else{
                for (int j = 0; j < Integer.parseInt(Character.toString(diskMap[i])); j++) {
                    fbAsAL.add(".");
                }
            }
        }
        fileBlocks = new String[fbAsAL.size()];
        for (int i = 0; i < fbAsAL.size(); i++){
            fileBlocks[i] = fbAsAL.get(i);
        }
    }
    public void compactFileBlocks(){
        for (int i = fileBlocks.length-1; i >= 0; i--){
            int newIndex = 0;
            while (!fileBlocks[newIndex].equals(".")) {
                newIndex++;
            }
            if (newIndex >= i) break;
            fileBlocks[newIndex] = fileBlocks[i];
            fileBlocks[i] = ".";
        }
    }
    public void greedyCompactFileBlocks(){
        int blockLength = 1;
        for (int i = fileBlocks.length-1; i >= 0; i -= blockLength){
            blockLength = 1;
            if (fileBlocks[i].equals(".")) continue;

            ArrayList<Integer> block = new ArrayList<>();
            int j = 0;
            while (i-j >= 0 && fileBlocks[i - j].equals(fileBlocks[i])) {
                block.add(i-j);
                j++;
            }

            blockLength = block.size();
            ArrayList<Integer> targetBlock = new ArrayList<>();
            int k = 0;
            while (true){
                if (targetBlock.size() == block.size()){
                    break;
                }
                if (k > fileBlocks.length-1 || k > block.get(0)){
                    targetBlock.clear();
                    break;
                }
                if (!fileBlocks[k].equals(".")){
                    k++;
                    targetBlock.clear();
                    continue;
                }
                targetBlock.add(k);
                k++;
            }

            if (targetBlock.isEmpty()) continue;
            for (int l = 0; l < block.size(); l++){
                fileBlocks[targetBlock.get(l)] = fileBlocks[block.get(l)];
                fileBlocks[block.get(l)] = ".";
            }
        }
    }
}