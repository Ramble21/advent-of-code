package com.github.Ramble21.classes.days;
import com.github.Ramble21.DaySolver;
import com.github.Ramble21.classes.general.Direction;
import com.github.Ramble21.classes.general.Location;

import java.util.*;

public class Maze {

    private final char[][] grid;
    private Location currentLoc;
    private final Direction currentDir;
    private MazeNode finalNode;
    private int totalLocationsInBestPaths;

    private final HashSet<MazeNode> visited = new HashSet<>();
    private final PriorityQueue<MazeNode> queue = new PriorityQueue<>();

    private final HashSet<MazeNode> checkedOnce = new HashSet<>();


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

        MazeNode origin = new MazeNode(currentLoc, null, getNodeDirections(currentLoc), Direction.RIGHT);
        queue.add(origin);
    }
    public boolean hasFinishedAllNodes() {
        return queue.isEmpty();
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
    public int getTotalLocationsInBestPaths() {
        return totalLocationsInBestPaths;
    }
    public void executeAlgorithm(){
        MazeNode node = queue.poll();
        assert node != null;
        HashSet<Location> locs = new HashSet<>();
        for (MazeNode m : visited){
            locs.add(m.getLoc());
        }
        if (locs.contains(node.getLoc())) return;

        grid[currentLoc.getY()][currentLoc.getX()] = 'x';
        currentLoc = node.getLoc();
        grid[currentLoc.getY()][currentLoc.getX()] = '@';
        checkNeighbors(node);
        visited.add(node);
    }

    public void checkNeighbors(MazeNode node){
        for (MazeNode neighbor : getNeighbors(node)){
            int tentativeDistance = node.getDistance() + getEdgeWeight(node, neighbor);

            if (node.getDirectionUponArriving() != neighbor.getDirectionUponArriving()){
                tentativeDistance += 1000;
            }

            if (tentativeDistance < neighbor.getDistance()){
                neighbor.setDistance(tentativeDistance);
                if (!queue.contains(neighbor)) queue.add(neighbor);
                if (grid[neighbor.getLoc().getY()][neighbor.getLoc().getX()] == 'E') {
                    finalNode = neighbor;
                    totalPoints = neighbor.getDistance();
                }
            }

            checkedOnce.add(neighbor);
        }
    }

    public ArrayList<Location> getAllLocationsBetween(Location start, Location end) {
        ArrayList<Location> locations = new ArrayList<>();
        int x1 = start.getX();
        int y1 = start.getY();
        int x2 = end.getX();
        int y2 = end.getY();
        int dx = Integer.compare(x2, x1);
        int dy = Integer.compare(y2, y1);
        int currentX = x1;
        int currentY = y1;
        while (currentX != x2 || currentY != y2) {
            currentX += dx;
            currentY += dy;
            locations.add(new Location(currentX, currentY));
        }
        locations.add(start);
        return locations;
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
    public Direction getDirectionToGo(Location original, Location neighbor) throws RuntimeException{
        int dx = neighbor.getX() - original.getX();
        int dy = neighbor.getY() - original.getY();
        if (dx > 0) return Direction.RIGHT;
        if (dx < 0) return Direction.LEFT;
        if (dy > 0) return Direction.DOWN;
        if (dy < 0) return Direction.UP;
        throw new RuntimeException("Nodes " + original + " and " + neighbor + " cannot be compared!");
    }
    public ArrayList<MazeNode> getNeighbors(MazeNode current) {
        ArrayList<MazeNode> output = new ArrayList<>();
        for (Direction direction : current.getDirections()) {
            Location loc = current.getLoc();
            while (true) {
                Location newLoc = loc.getDirectionalLoc(direction);
                Direction dir = getDirectionToGo(loc, newLoc);
                if (grid[newLoc.getY()][newLoc.getX()] == 'E'){
                    output.add(new MazeNode(newLoc, current, getNodeDirections(newLoc), dir));
                    break;
                }
                else if (grid[newLoc.getY()][newLoc.getX()] == '#' || isDeadEnd(newLoc, direction)){
                    break;
                }
                else if (canTurn(newLoc)) {
                    output.add(new MazeNode(newLoc, current, getNodeDirections(newLoc), dir));
                    break;
                }
                loc = newLoc;
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
            if ((grid[up.getY()][up.getX()] == '.' || grid[up.getY()][up.getX()] == '@')
                    && (grid[left.getY()][left.getX()] == '.' || grid[left.getY()][left.getX()] == '@')) return true;
        }
        return false;
    }
    public void findAllPaths(){
        HashSet<Location> allLocs = new HashSet<>();
        backtrack(finalNode, null, allLocs);
        totalLocationsInBestPaths = allLocs.size();
        removeX();
    }
    public void removeX(){
        for (int r = 0; r < grid.length; r++){
            for (int c = 0; c < grid[0].length; c++){
                if (grid[r][c] == 'x') grid[r][c] = '.';
            }
        }
    }
    public void backtrack(MazeNode current, MazeNode child, HashSet<Location> set){

        ArrayList<MazeNode> parents = current.getSafeParentNodes(visited);

        if (!current.isOrigin()){

            ArrayList<MazeNode> neighbors = getNewNeighbors(current);
            neighbors.removeIf(neighbor -> {
                ArrayList<Location> parentLocs = new ArrayList<>();
                for (MazeNode m : parents){
                    parentLocs.add(m.getLoc());
                }
                if (parentLocs.contains(neighbor.getLoc())) {
                    return true;
                }
                else {

                    ArrayList<MazeNode> neighborParents = neighbor.getSafeParentNodes(visited);
                    ArrayList<Location> neighborParentLocs = new ArrayList<>();
                    for (MazeNode m : neighborParents) {
                        neighborParentLocs.add(m.getLoc());
                    }
                    return (neighborParentLocs.contains(current.getLoc()) || neighbor.getLoc().equals(finalNode.getLoc()));
                }
            });

            for (MazeNode neighbor : neighbors){
                if (checkIfExtraParent(child, current, neighbor)){
                    parents.add(neighbor);
                }
            }

            for (MazeNode parent : parents){

                ArrayList<Location> list = getAllLocationsBetween(current.getLoc(), parent.getLoc());
                set.addAll(list);
                for (Location l : list){
                    grid[l.getY()][l.getX()] = 'O';
                }
                backtrack(parent, current, set);
            }
        }
    }
    public boolean checkIfExtraParent(MazeNode C, MazeNode B, MazeNode A){
        int num = Math.abs(getEdgeWeight(A, B));


        Direction RED = getDirectionToGo(A.getLoc(), B.getLoc());
        Direction BLUE = A.getDirectionUponArriving();
        Direction ORANGE = B.getDirectionUponArriving();
        Direction GREEN = (C != null) ? getDirectionToGo(B.getLoc(), C.getLoc()) : null;

        if (BLUE != RED) num += 1000;
        if (GREEN != null && ORANGE != GREEN && RED == GREEN) num -= 1000;

        return A.getDistance() + num == B.getDistance();
    }
    public ArrayList<MazeNode> getNewNeighbors(MazeNode current){
        ArrayList<MazeNode> output = new ArrayList<>();
        for (MazeNode node : visited){
            if (canAccess(node, current) && noIntermediateNode(node, current)) output.add(node);
        }
        return output;
    }
    public boolean canAccess(MazeNode one, MazeNode two){
        if ((!(one.getLoc().getX() == two.getLoc().getX() || one.getLoc().getY() == two.getLoc().getY())) || (one.getLoc().equals(two.getLoc()))) return false;
        Location loc = one.getLoc();
        Direction dir = getDirectionToGo(two.getLoc(), one.getLoc());
        dir = dir.getFlipped();
        while (!loc.equals(two.getLoc())){
            loc = loc.getDirectionalLoc(dir);
            if (grid[loc.getY()][loc.getX()] == '#'){
                return false;
            }
        }
        return true;
    }
    public boolean noIntermediateNode(MazeNode one, MazeNode two){
        Location loc = one.getLoc();
        Direction dir = getDirectionToGo(two.getLoc(), one.getLoc());
        dir = dir.getFlipped();
        while (!loc.equals(two.getLoc())){
            loc = loc.getDirectionalLoc(dir);
            if (canTurnNew(loc) && !loc.equals(two.getLoc()) && !loc.equals(one.getLoc())){
                return false;
            }
        }
        return true;
    }
    public boolean canTurnNew(Location loc){
        for (Direction d : Direction.getDirectionSet()){
            Location left = loc.getDirectionalLoc(d);
            Location up = loc.getDirectionalLoc(d.getClockwise());
            if (((grid[up.getY()][up.getX()] == '.' || grid[up.getY()][up.getX()] == 'x' || grid[up.getY()][up.getX()] == 'O'))
                    && (grid[left.getY()][left.getX()] == '.' || grid[left.getY()][left.getX()] == 'x' || grid[left.getY()][left.getX()] == 'O')) return true;
        }
        return false;
    }
}
