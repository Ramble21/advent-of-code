package com.github.ramble21.y2024.classes;

import java.util.Objects;

public class Pair {
    public long l;
    public String s;
    public Pair(String s, long l){
        this.s = s;
        this.l = l;
    }
    @Override
    public String toString(){
        return "|" + s + " : " + l + "|";
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pair other = (Pair)obj;
        return l == other.l && Objects.equals(s, other.s);
    }
    @Override
    public int hashCode() {
        return Objects.hash(l, s);
    }
}
