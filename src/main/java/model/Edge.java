package model;

public class Edge {
    public Double dist;
    private Node target;

    public Edge(Node target, Double dist) {
        this.target = target;
        this.dist = dist;
    }

    public Node getTarget() {
        return target;
    }
}
