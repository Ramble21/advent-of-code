package com.github.ramble21.y2023.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class AbstractGraph {
    private final HashSet<AbstractGraphNode> nodes = new HashSet<>();
    private final HashSet<AbstractGraphEdge> edges = new HashSet<>();
    private final HashSet<AbstractGraphEdge> originalEdges = new HashSet<>();
    public AbstractGraph(HashMap<String, HashSet<String>> graphMap) {
        for (String key : graphMap.keySet()) {
            nodes.add(new AbstractGraphNode(key));
        }
        for (String key : graphMap.keySet()) {
            AbstractGraphNode node = getNodeByID(key);
            for (String value : graphMap.get(key)) {
                AbstractGraphNode neighbor = getNodeByID(value);
                edges.add(new AbstractGraphEdge(node, neighbor));
            }
        }
        originalEdges.addAll(edges);
    }
    public int getRandomCut() {
        while (nodes.size() > 2) {
            AbstractGraphEdge randomEdge = getRandomEdge();
            combineNodes(randomEdge.A, randomEdge.B);
        }
        ArrayList<AbstractGraphNode> nodesAL = new ArrayList<>(nodes);
        AbstractGraphNode A = nodesAL.get(0);
        AbstractGraphNode B = nodesAL.get(1);
        int cutSize = 0;
        for (AbstractGraphEdge edge : originalEdges) {
            if (
                (A.nodeGroup.contains(edge.A.identifier) && B.nodeGroup.contains(edge.B.identifier)) ||
                (A.nodeGroup.contains(edge.B.identifier) && B.nodeGroup.contains(edge.A.identifier))
                ) {
                cutSize++;
            }
        }
        return cutSize;
    }
    public int getGroupProduct() {
        int x = 1;
        for (AbstractGraphNode node : nodes) {
            node.flattenGroup();
            x *= node.nodeGroup.size();
        }
        return x;
    }
    private AbstractGraphEdge getRandomEdge() {
        int randomIndex = (int)(Math.random() * edges.size());
        return new ArrayList<>(edges).get(randomIndex);
    }
    private void combineNodes(AbstractGraphNode A, AbstractGraphNode B) {
        AbstractGraphEdge oldEdge = new AbstractGraphEdge(A, B);
        boolean nodesAreNeighbors = false;
        for (AbstractGraphEdge edge : edges) {
            if (edge.equals(oldEdge)) {
                nodesAreNeighbors = true;
                break;
            }
        }
        if (!nodesAreNeighbors) {
            throw new IllegalArgumentException("Only neighboring nodes can be combined! " + A + " and " + B + " are not neighboring!");
        }
        String newID = A.identifier;
        AbstractGraphNode C = new AbstractGraphNode(newID);
        C.addToGroup(A.nodeGroup, B.nodeGroup);
        for (AbstractGraphEdge edge : edges) {
            if (edge.A.equals(A)) {
                edge.A = C;
            }
            else if (edge.B.equals(A)) {
                edge.B = C;
            }
            else if (edge.A.equals(B)) {
                edge.A = C;
            }
            else if (edge.B.equals(B)) {
                edge.B = C;
            }
        }
        nodes.remove(A);
        nodes.remove(B);
        nodes.add(C);
    }
    private AbstractGraphNode getNodeByID(String identifier) {
        for (AbstractGraphNode node : nodes) {
            if (node.identifier.equals(identifier)) {
                return node;
            }
        }
        throw new IllegalArgumentException("Not a valid node: " + identifier);
    }
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AbstractGraph other = (AbstractGraph) obj;
        return nodes.equals(other.nodes) && edges.equals(other.edges);
    }
    @Override
    public int hashCode() {
        return Objects.hash(nodes, edges);
    }
    @Override
    public String toString() {
        return "Graph(\n    nodes=" + nodes + "\n    edges=" + edges + "\n)";
    }
}
