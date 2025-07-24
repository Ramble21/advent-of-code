package com.github.Ramble21.y2023.classes;

import java.util.Objects;
import java.util.Set;

public class AbstractGraphEdge {
    public AbstractGraphNode A;
    public AbstractGraphNode B;
    public AbstractGraphEdge(AbstractGraphNode A, AbstractGraphNode B) {
        this.A = A;
        this.B = B;
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbstractGraphEdge other = (AbstractGraphEdge) obj;
        return (A.equals(other.A) && B.equals(other.B)) || (A.equals(other.B) && B.equals(other.A));
    }
    @Override
    public int hashCode() {
        return Set.of(A, B).hashCode();
    }
    @Override
    public String toString() {
        return A + "-" + B;
    }
}
