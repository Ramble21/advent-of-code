package com.github.ramble21.y2024.days;

import com.github.ramble21.DaySolver;
import com.github.ramble21.y2024.classes.FileBlock;

import java.io.IOException;
import java.util.List;

public class Day9 extends DaySolver {
    private final List<String> input;

    public Day9() throws IOException {
        input = getInputLines(2024, 9);
        FileBlock.initializeIndexIds(getTotalIDs());
        int currentID = 0;
        boolean isEmptySpace = false;
        for (char c : input.get(0).toCharArray()){
            if (isEmptySpace) FileBlock.addEmptyFile(c - '0');
            else new FileBlock(c - '0', currentID++);
            isEmptySpace = !isEmptySpace;
        }
    }

    public long solvePart1(){
        return FileBlock.getChecksum(FileBlock.getCompactedFileBlocks());
    }
    public long solvePart2(){
        return FileBlock.getChecksum(FileBlock.getGreedyCompactedFileBlocks());
    }

    public int getTotalIDs(){
        int total = 0;
        for (char c : input.get(0).toCharArray()){
            total += (c - '0');
        }
        return total;
    }

}