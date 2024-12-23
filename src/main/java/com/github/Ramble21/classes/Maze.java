package com.github.Ramble21.classes;
import java.util.*;

public class Maze {

    private char[][] grid;
    private Location currentLoc;
    private Direction currentDir;
    private boolean hasReachedEnd = false;

    private HashSet<Location> visited = new HashSet<>();
    private PriorityQueue<MazeNode> queue = new PriorityQueue<>();

    private int totalPoints;
    public int getTotalPoints() {
        return totalPoints;
    }

    public Maze(char[][] grid){
        this.grid = grid;
        currentDir = Direction.UP;
        for (int r = 1; r < grid.length-1; r++){
            for (int c = 1; c < grid[0].length-1; c++){
                if (grid[r][c] == 'S') {
                    grid[r][c] = '@';
                    currentLoc = new Location(c, r);
                }
            }
        }

        origin = new MazeNode(currentLoc, null, getNodeDirections(currentLoc));
        queue.add(origin);
    }
    public boolean hasReachedEnd() {
        return hasReachedEnd;
    }
    public Location getCurrentLoc() {
        return currentLoc;
    }
    public char[][] getGrid() {
        return grid;
    }
    public Direction[] getNodeDirections(Location l){
        ArrayList<Direction> dirs = new ArrayList<>();
        if (canTurnClockwise(l, currentDir)) dirs.add(currentDir.getClockwise());
        if (canTurnCounterClockwise(l, currentDir)) dirs.add(currentDir.getCounterClockwise());
        if (canMoveForward(l, currentDir)) dirs.add(currentDir);
        if (canMoveBackward(l, currentDir)) {
            assert currentDir.getClockwise() != null;
            dirs.add(currentDir.getClockwise().getClockwise());
        }
        return dirs.toArray(new Direction[0]);
    }

    private final MazeNode origin;
    private final HashSet<Location> forwards = new HashSet<>();

    public void executeAlgorithm(){
        MazeNode node = queue.poll();
        assert node != null;

        visited.add(currentLoc);
        grid[currentLoc.getY()][currentLoc.getX()] = 'x';
        Location oldLoc = currentLoc;

        currentLoc = node.getLoc();
        grid[currentLoc.getY()][currentLoc.getX()] = '@';

        for (MazeNode neighbor : getNeighbors(node)){

            if (neighbor == null) continue;

            int tentativeDistance = node.getDistance() + getEdgeWeight(node, neighbor);

            if (turnedToArrive(node, neighbor)) tentativeDistance += 1000;

            if (tentativeDistance < neighbor.getDistance()){
                neighbor.setDistance(tentativeDistance);
                neighbor.setPreviousNode(node);
                //System.out.println(neighbor.getLoc() + " ~ " + tentativeDistance);
                neighbor.setPathsCount(node.getPathsCount());
                queue.add(neighbor);
            }
            else if (tentativeDistance == neighbor.getDistance()){
                neighbor.setPathsCount(neighbor.getPathsCount() + node.getPathsCount());
            }

            if (grid[neighbor.getLoc().getY()][neighbor.getLoc().getX()] == 'E') {
                hasReachedEnd = true;
                System.out.println("\nAlgorithm finished");
                MazeNode current = neighbor;
                while (!current.isOrigin()){
                    System.out.println(current + " " + current.getDistance());
                    current = current.getPreviousNode();
                }
                totalPoints = neighbor.getDistance();
                return;
            }
        }
    }
    public boolean turnedToArrive(MazeNode original, MazeNode neighbor){
        Direction currentDir = getDirection(original);
        Direction newDir = getDirection(neighbor);
        //System.out.println("Old: " + currentDir + " New: " + newDir + " at " + original.getLoc() + "->" + neighbor.getLoc() + " = " + Boolean.toString(newDir != currentDir));
        return newDir != currentDir;
    }
    public Direction getDirection(MazeNode original){
        if (original.getPreviousNode() == null) return Direction.RIGHT;
        Location loc1 = original.getLoc();
        Location loc2 = original.getPreviousNode().getLoc();
        if (loc1.getX() == loc2.getX()){
            if (loc1.getY() > loc2.getY()) return Direction.DOWN;
            return Direction.UP;
        }
        else if (loc1.getY() == loc2.getY()){
            if (loc1.getX() > loc2.getX()) return Direction.RIGHT;
            return Direction.LEFT;
        }
        else throw new RuntimeException("Nodes " + original + " and " + original.getPreviousNode() + " cannot be compared!");
    }
    public int getEdgeWeight(MazeNode node1, MazeNode node2){
        Location loc1 = node1.getLoc();
        Location loc2 = node2.getLoc();
        if (loc1.getX() == loc2.getX()){
            return Math.abs(loc1.getY() - loc2.getY());
        }
        else if (loc1.getY() == loc2.getY()){
            return Math.abs(loc2.getX() - loc1.getX());
        }
        else throw new RuntimeException("Nodes " + node1 + " and " + node2 + " cannot be compared!");
    }
    public MazeNode[] getNeighbors(MazeNode current){
        MazeNode[] output = new MazeNode[current.getDirections().length];
        for (int i = 0; i < output.length; i++){
            Location loc = current.getLoc();
            while (true){
                Location newLoc = loc.getDirectionalLoc(current.getDirections()[i]);
                if (canTurn(newLoc)){
                    output[i] = new MazeNode(newLoc, current, getNodeDirections(newLoc));
                    break;
                }
                else if (grid[newLoc.getY()][newLoc.getX()] == 'E'){
                    output[i] = new MazeNode(newLoc, current, getNodeDirections(newLoc));
                    break;
                }
                else if (isDeadEnd(newLoc, current.getDirections()[i])){
                    output[i] = null;
                    break;
                }
                else {
                    loc = newLoc;
                }
            }
        }
        return output;
    }
    public boolean canMoveForward(Location currentLoc, Direction currentDir){
        Location targetLoc = currentLoc.getDirectionalLoc(currentDir);
        return grid[targetLoc.getY()][targetLoc.getX()] == '.' || grid[targetLoc.getY()][targetLoc.getX()] == 'E';
    }
    public boolean canTurnClockwise(Location currentLoc, Direction currentDir){
        Location targetLoc = currentLoc.getDirectionalLoc(currentDir.getClockwise());
        return grid[targetLoc.getY()][targetLoc.getX()] == '.' || grid[targetLoc.getY()][targetLoc.getX()] == 'E';
    }
    public boolean canTurnCounterClockwise(Location currentLoc, Direction currentDir){
        Location targetLoc = currentLoc.getDirectionalLoc(currentDir.getCounterClockwise());
        return grid[targetLoc.getY()][targetLoc.getX()] == '.' || grid[targetLoc.getY()][targetLoc.getX()] == 'E';
    }
    public boolean canMoveBackward(Location currentLoc, Direction currentDir){
        assert currentDir.getClockwise() != null;
        Location targetLoc = currentLoc.getDirectionalLoc(currentDir.getClockwise().getClockwise());
        return grid[targetLoc.getY()][targetLoc.getX()] == '.' || grid[targetLoc.getY()][targetLoc.getX()] == 'E';
    }
    public boolean isDeadEnd(Location currentLoc, Direction currentDir){
        return !canMoveForward(currentLoc, currentDir) && !canTurn(currentLoc);
    }
    public boolean canTurn(Location loc){
        for (Direction d : Direction.getDirectionSet()){
            Location left = loc.getDirectionalLoc(d);
            Location up = loc.getDirectionalLoc(d.getClockwise());
            if (grid[up.getY()][up.getX()] == '.' && grid[left.getY()][left.getX()] == '.') return true;
        }
        return false;
    }
}
