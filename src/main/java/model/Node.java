package model;

import java.util.Objects;

public class Node {
    private String line;
    private Pair<Double,Double> coordinates;

    public Node(String line, Pair<Double, Double> coordinates) {
        this.line = line;
        this.coordinates = coordinates;
    }

    public String getLine() {
        return line;
    }

    public Pair<Double, Double> getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(line, node.line) &&
                Objects.equals(coordinates, node.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, coordinates);
    }
}
