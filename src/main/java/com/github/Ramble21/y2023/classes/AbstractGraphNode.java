package com.github.Ramble21.y2023.classes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class AbstractGraphNode {
    public String identifier;
    public HashSet<String> nodeGroup = new HashSet<>();
    public AbstractGraphNode(String identifier) {
        this.identifier = identifier;
        nodeGroup.add(identifier);
    }
    public void addToGroup(HashSet<String> A, HashSet<String> B) {
        this.nodeGroup.addAll(A);
        this.nodeGroup.addAll(B);
    }
    public void flattenGroup() {
        HashSet<String> flattened = new HashSet<>();
        for (String s : nodeGroup) {
            String[] a = s.split("-");
            flattened.addAll(Arrays.asList(a));
        }
        nodeGroup = flattened;
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbstractGraphNode other = (AbstractGraphNode) obj;
        return identifier.equals(other.identifier);
    }
    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
    @Override
    public String toString() {
        return '{' + identifier + '}';
    }
}
