package com.github.ramble21.y2024.classes;

import java.util.Arrays;

public class FileBlock {

    private static int currentIndex = 0;
    private static int[] indexIDs;
    private static int firstFileSize = 0;

    public FileBlock(int size, int ID){
        if (firstFileSize == 0) firstFileSize = size;
        for (int i = 0; i < size; i++){
            indexIDs[currentIndex++] = ID;
        }
    }
    public static void initializeIndexIds(int size){
        indexIDs = new int[size];
    }
    public static void addEmptyFile(int size){
        for (int i = 0; i < size; i++){
            indexIDs[currentIndex++] = 0;
        }
    }
    public static long getChecksum(int[] indexIDs){
        long total = 0;
        for (int i = 0; i < indexIDs.length; i++){
            total += (long) i * indexIDs[i];
        }
        return total;
    }
    public static int[] getCompactedFileBlocks() {
        int[] compacted = indexIDs.clone();
        for (int i = compacted.length - 1; i >= firstFileSize; i--){
            if (compacted[i] != 0){
                compacted[getFirstBlankSpace(compacted)] = compacted[i];
                compacted[i] = 0;
            }
        }
        return Arrays.copyOfRange(compacted, 1, compacted.length);
    }
    public static int[] getGreedyCompactedFileBlocks() {
        int[] greedyCompacted = indexIDs.clone();
        for (int i = greedyCompacted.length - 1; i >= firstFileSize; i--){
            if (greedyCompacted[i] != 0){
                int num = greedyCompacted[i];
                int fileLen = getFileLen(greedyCompacted, i);
                int blankSpace = getFirstBlankSpaceOfSize(greedyCompacted, fileLen);
                if (blankSpace == -1 || blankSpace > i) {
                    i -= fileLen - 1;
                }
                else {
                    for (int j = 0; j < fileLen; j++){
                        greedyCompacted[blankSpace - j] = num;
                        greedyCompacted[i - j] = 0;
                    }
                }
            }
        }
        return greedyCompacted;
    }
    private static int getFileLen(int[] indexIDs, int lastIndex){
        int total = 0;
        int i = lastIndex;
        while (indexIDs[i] == indexIDs[lastIndex]){
            i--;
            total++;
        }
        return total;
    }
    private static int getFirstBlankSpace(int[] indexIDs){
        for (int i = firstFileSize; i < indexIDs.length; i++){
            if (indexIDs[i] == 0) return i;
        }
        throw new RuntimeException("No empty space");
    }
    private static int getFirstBlankSpaceOfSize(int[] indexIDs, int size){
        outerLoop:
        for (int i = (firstFileSize + size) - 1; i < indexIDs.length; i++){
            for (int j = 0; j < size; j++){
                if (indexIDs[i - j] != 0) continue outerLoop;
            }
            return i;
        }
        return -1;
    }
}
