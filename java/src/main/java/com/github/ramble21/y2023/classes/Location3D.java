package com.github.ramble21.y2023.classes;

import com.github.ramble21.helper_classes.Location;

import java.util.Objects;

public class Location3D extends Location {
    public int z;
    public Location3D(int x, int y, int z) {
        super(x,y);
        this.z = z;
    }
    public String toString() {
        return "(" + x + "," + y + "," + z + ")";
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Location3D other = (Location3D) obj;
        return x == other.x && y == other.y && z == other.z;
    }
    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}