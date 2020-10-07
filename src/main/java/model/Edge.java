package model;

import java.util.Objects;

public class Edge {
    public Double dist;
    private Node target;

    public Edge(Node target, Double dist) {
        this.target = target;
        this.dist = dist;
    }

    public Double getDist() {
        return dist;
    }

    public Node getTarget() {
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return Objects.equals(target, edge.target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dist, target);
    }
}
