package com.github.Ramble21;

import java.io.IOException;
import java.util.List;

public class Day4 extends DaySolver{
    private final List<String> input;
    private char[][] wordSearch;
    public Day4() throws IOException {
        input = getInputLines(4);
        initializeWordSearchArr();
    }
    public int solvePart1() throws IOException {
        int count = 0;
        for (int r = 0; r < wordSearch.length; r++){
            for (int c = 0; c < wordSearch[0].length; c++){
                int totalRows = wordSearch.length;
                int totalColumns = wordSearch[0].length;
                // this cant be the most efficient way to do this shit but i can't be bothered to think of another way so roast me all you want
                if (wordSearch[r][c] != 'X' && wordSearch[r][c] != 'M' && wordSearch[r][c] != 'A' && wordSearch[r][c] != 'S') count++;
                if (c <= totalColumns - 4){
                    if (wordSearch[r][c] == 'X' && wordSearch[r][c + 1] == 'M' && wordSearch[r][c + 2] == 'A' && wordSearch[r][c + 3] == 'S') count++;
                    if (wordSearch[r][c] == 'S' && wordSearch[r][c + 1] == 'A' && wordSearch[r][c + 2] == 'M' && wordSearch[r][c + 3] == 'X') count++;
                }
                if (r <= totalRows - 4){
                    if (wordSearch[r][c] == 'X' && wordSearch[r + 1][c] == 'M' && wordSearch[r + 2][c] == 'A' && wordSearch[r + 3][c] == 'S') count++;
                    if (wordSearch[r][c] == 'S' && wordSearch[r + 1][c] == 'A' && wordSearch[r + 2][c] == 'M' && wordSearch[r + 3][c] == 'X') count++;
                }
                if (r <= totalRows - 4 && c <= totalColumns - 4){
                    if (wordSearch[r][c] == 'X' && wordSearch[r + 1][c + 1] == 'M' && wordSearch[r + 2][c + 2] == 'A' && wordSearch[r + 3][c + 3] == 'S') count++;
                    if (wordSearch[r][c] == 'S' && wordSearch[r + 1][c + 1] == 'A' && wordSearch[r + 2][c + 2] == 'M' && wordSearch[r + 3][c + 3] == 'X') count++;
                }
                if (r >= 3 && c <= totalColumns - 4){
                    if (wordSearch[r][c] == 'X' && wordSearch[r - 1][c + 1] == 'M' && wordSearch[r - 2][c + 2] == 'A' && wordSearch[r - 3][c + 3] == 'S') count++;
                    if (wordSearch[r][c] == 'S' && wordSearch[r - 1][c + 1] == 'A' && wordSearch[r - 2][c + 2] == 'M' && wordSearch[r - 3][c + 3] == 'X') count++;
                }
            }
        }
        return count;
    }

    public int solvePart2() throws IOException {
        int count = 0;
        for (int r = 0; r < wordSearch.length-2; r++){
            for (int c = 0; c < wordSearch[0].length-2; c++){
                int totalRows = wordSearch.length;
                int totalColumns = wordSearch[0].length;
                if (wordSearch[r][c] == 'M' && wordSearch[r + 1][c + 1] == 'A' && wordSearch[r + 2][c + 2] == 'S' && wordSearch[r][c + 2] == 'S' && wordSearch[r + 2][c] == 'M') count++;
                if (wordSearch[r][c] == 'S' && wordSearch[r + 1][c + 1] == 'A' && wordSearch[r + 2][c + 2] == 'M' && wordSearch[r][c + 2] == 'M' && wordSearch[r + 2][c] == 'S') count++;
                if (wordSearch[r][c] == 'M' && wordSearch[r + 1][c + 1] == 'A' && wordSearch[r + 2][c + 2] == 'S' && wordSearch[r][c + 2] == 'M' && wordSearch[r + 2][c] == 'S') count++;
                if (wordSearch[r][c] == 'S' && wordSearch[r + 1][c + 1] == 'A' && wordSearch[r + 2][c + 2] == 'M' && wordSearch[r][c + 2] == 'S' && wordSearch[r + 2][c] == 'M') count++;
            }
        }
        return count;
    }

    public void initializeWordSearchArr(){
        wordSearch = new char[input.size()][input.get(0).length()];
        for (int r = 0; r < wordSearch.length; r++){
            for (int c = 0; c < wordSearch[0].length; c++){
                wordSearch[r][c] = input.get(r).charAt(c);
            }
        }
    }
}