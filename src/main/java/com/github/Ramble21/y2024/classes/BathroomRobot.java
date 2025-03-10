package com.github.Ramble21.y2024.classes;

public class BathroomRobot {

    private int currentX;
    private int currentY;
    private final int xVelocity;
    private final int yVelocity;
    private static char[][] grid = null;

    public BathroomRobot(int x, int y, int xv, int yv){

        this.currentX = x;
        this.currentY = y;
        this.xVelocity = xv;
        this.yVelocity = yv;
        if (grid == null){
            grid = new char[103][101];
            for (int r = 0; r < grid.length; r++){
                for (int c = 0; c < grid[0].length; c++){
                    grid[r][c] = '.';
                }
            }
        }
        updateGrid(x, y);
    }
    public void updateGrid(int x, int y){
        if (grid[y][x] == '.'){
            grid[y][x] = '1';
            return;
        }
        int num = (Integer.parseInt(String.valueOf(grid[y][x])));
        grid[y][x] = (char)(num + '1');
    }
    public void unUpdateGrid(int x, int y){
        int num = (Integer.parseInt(String.valueOf(grid[y][x])));
        grid[y][x] = (char)(num + '0' - 1);
        if (grid[y][x] == '0') grid[y][x] = '.';
    }
    public void predictPosition(int seconds){
        int finalX = currentX;
        int finalY = currentY;
        unUpdateGrid(currentX, currentY);
        for (int i = 0; i < seconds; i++){
            finalX += xVelocity;
            finalY += yVelocity;
            if (finalX >= grid[0].length) finalX %= grid[0].length;
            else if (finalX < 0) finalX += grid[0].length;
            if (finalY >= grid.length) finalY %= grid.length;
            else if (finalY < 0) finalY += grid.length;
        }
        currentX = finalX;
        currentY = finalY;
        updateGrid(finalX, finalY);
    }
    public static char[][] getGrid(){
        return grid;
    }
    public static long[] countRobotsInQuadrants(){
        int midRow = grid.length/2;
        int midCol = grid[0].length/2;

        long q1 = 0;
        for (int r = 0; r < midRow; r++){
            for (int c = 0; c < midCol; c++){
                if (grid[r][c] == '.') continue;
                q1 += grid[r][c] - '0';
            }
        }
        long q2 = 0;
        for (int r = midRow+1; r < grid.length; r++){
            for (int c = midCol+1; c < grid[0].length; c++){
                if (grid[r][c] == '.') continue;
                q2 += grid[r][c] - '0';
            }
        }
        long q3 = 0;
        for (int r = midRow+1; r < grid.length; r++){
            for (int c = 0; c < midCol; c++){
                if (grid[r][c] == '.') continue;
                q3 += grid[r][c] - '0';
            }
        }
        long q4 = 0;
        for (int r = 0; r < midRow; r++){
            for (int c = midCol+1; c < grid[0].length; c++){
                if (grid[r][c] == '.') continue;
                q4 += grid[r][c] - '0';
            }
        }
        return new long[]{q1, q4, q3, q2};
    }
}
