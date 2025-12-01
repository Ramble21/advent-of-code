package com.github.ramble21.y2024.classes;
import com.github.ramble21.helper_classes.Location;

import java.util.*;

public class Route {
    private ArrayList<Location> route = new ArrayList<>();
    public Route(ArrayList<Location> route){
        this.route = route;
    }
    public Route(Location start, Location end){
        ArrayList<Location> temp = new ArrayList<>();
        temp.add(start);
        temp.add(end);
        this.route = temp;
    }
    public ArrayList<Location> toList(){
        return route;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Route other = (Route) obj;
        return this.route == other.route;
    }
    @Override
    public int hashCode(){
        return Objects.hash(toString());
    }
    @Override
    public String toString(){
        return route.isEmpty() ? "empty" : route.get(0) + " -> " + route.get(route.size()-1);
    }
}
