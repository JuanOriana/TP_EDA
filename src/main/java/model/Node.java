package model;

import java.util.ArrayList;
import java.util.List;
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

    public int closest(List<Pair<Double, Double>> points) {

        if (points.size() < 1) throw new RuntimeException("Empty node list");

        int minIndex = 0, currentIndex = 0;
        double minDist = manhattanDist(points.get(0));

        for (Pair p : points) {
            double currentDist = manhattanDist(p);
            if (minDist > currentDist) {
                minDist = currentDist;
                minIndex = currentIndex;
            }
            currentIndex++;
        }

        return minIndex;
    }

    private double manhattanDist(Pair<Double, Double> p) {
        if (p == null) throw new NullPointerException("Invalid Node");
        double x = p.getElem1();
        double y = p.getElem2();

        return Math.abs(x - coordinates.getElem1()) + Math.abs(y - getCoordinates().getElem2());
    }
}
