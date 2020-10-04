package model;

import java.util.Objects;

public class Edge {
    private Node targetNode;
    public Double distance;

    public Edge(Node target, Double dist) {
        targetNode = target;
        distance = dist;
    }

    public Node getTargetNode() {
        return targetNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return targetNode.equals(edge.targetNode) &&
                distance.equals(edge.distance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetNode, distance);
    }
}
