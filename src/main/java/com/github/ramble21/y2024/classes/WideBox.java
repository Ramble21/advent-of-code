package com.github.ramble21.y2024.classes;

import com.github.ramble21.helper_classes.Direction;
import com.github.ramble21.helper_classes.Location;

import java.util.ArrayList;

public class WideBox {
    private final Location index1;
    private final Location index2;
    private static final ArrayList<WideBox> boxesPushed = new ArrayList<>();

    public Location getIndex1() {
        return index1;
    }
    public int getY(){
        return index1.getY();
    }
    public static void resetBoxesPushed(){
        boxesPushed.clear();
    }

    public WideBox(char[][] grid, Location index1, Location index2){
        char char1 = grid[index1.getY()][index1.getX()];
        char char2 = grid[index2.getY()][index2.getX()];
        if (char1 == '[' && char2 == ']'){
            this.index1 = index1;
            this.index2 = index2;
        }
        else if (char1 == ']' && char2 == '['){
            this.index1 = index2;
            this.index2 = index1;
        }
        else{
            throw new RuntimeException("Invalid position!");
        }
    }
    public void forceMove (Direction dir, char[][] grid){
        grid[index1.getY()][index1.getX()] = '.';
        grid[index2.getY()][index2.getX()] = '.';
        grid[index1.getY()+dir.getDeltaY()][index1.getX()+dir.getDeltaX()] = '[';
        grid[index2.getY()+dir.getDeltaY()][index2.getX()+dir.getDeltaX()] = ']';
        boxesPushed.add(this);
    }
    public void undoForceMove (Direction dir, char[][] grid){
        grid[index1.getY()+dir.getDeltaY()][index1.getX()+dir.getDeltaX()] = '.';
        grid[index2.getY()+dir.getDeltaY()][index2.getX()+dir.getDeltaX()] = '.';
        grid[index1.getY()][index1.getX()] = '[';
        grid[index2.getY()][index2.getX()] = ']';
    }
    public void undoAllMistakes(Direction dir, char[][] grid){
        for (int i = boxesPushed.size()-1; i >= 0; i--){
            boxesPushed.get(i).undoForceMove(dir, grid);
        }
        resetBoxesPushed();
    }
    public boolean tryToMove(Direction dir, char[][] grid){
        Location target1 = index1.getDirectionalLoc(dir);
        Location target2 = index2.getDirectionalLoc(dir);
        if (dir == Direction.RIGHT || dir == Direction.LEFT){
            Location target = target1;
            if (dir == Direction.RIGHT) target = target2;
            if (grid[target.getY()][target.getX()] == '.'){
                forceMove(dir, grid);
                return true;
            }
            else if (grid[target.getY()][target.getX()] == '#'){
                undoAllMistakes(dir, grid);
                return false;
            }
            else{
                WideBox newBox = grabBox(grid, target);
                assert newBox != null;
                if (newBox.tryToMove(dir, grid)){

                    forceMove(dir, grid);
                    return true;
                }
                else{

                    return false;
                }
            }
        }
        else{
            if (grid[target1.getY()][target1.getX()] == '.' && grid[target2.getY()][target2.getX()] == '.'){
                forceMove(dir, grid);
                return true;
            }
            else if (grid[target1.getY()][target1.getX()] == '#' || grid[target2.getY()][target2.getX()] == '#'){
                undoAllMistakes(dir, grid);
                return false;
            }
            else{
                Location[] targets = {target1, target2};
                for (Location target : targets){
                    WideBox newBox = grabBox(grid, target);
                    if (newBox != null){
                        if (!newBox.tryToMove(dir, grid)){
                            return false;
                        }
                    }
                }
                forceMove(dir, grid);
                return true;
            }
        }
    }

    public static WideBox grabBox(char[][] grid, Location loc){
        char middle = grid[loc.getY()][loc.getX()];
        char left = grid[loc.getY()][loc.getX()-1];
        char right = grid[loc.getY()][loc.getX()+1];
        if (middle == '[' && right == ']'){
            return new WideBox(grid, loc, loc.getDirectionalLoc(Direction.RIGHT));
        }
        else if (middle == ']' && left == '['){
            return new WideBox(grid, loc, loc.getDirectionalLoc(Direction.LEFT));
        }
        else return null;
    }
    public String toString(){
        return "box: " + index1 + "," + index2;
    }
}
