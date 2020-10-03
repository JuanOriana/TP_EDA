package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private String line;
    private Pair<Double,Double> coordinates;
    private int stopNumber;

    public Node(String line, Pair<Double, Double> coordinates) {
        this.line = line;
        this.coordinates = coordinates;
    }

    public int getStopNumber() {
        return stopNumber;
    }

    public void setStopNumber(int stopNumber) {
        this.stopNumber = stopNumber;
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

    @Override
    public String toString() {
        return "Node{" +
                "line='" + line + '\'' +
                ", coordinates=" + coordinates +
                ", stopNumber=" + stopNumber +
                '}';
    }
    

    public double manhattanDist(Pair<Double, Double> p) {
        if (p == null) throw new NullPointerException("Invalid Node");
        double x = p.getElem1();
        double y = p.getElem2();

        return Math.abs(x - coordinates.getElem1()) + Math.abs(y - getCoordinates().getElem2());
    }

}
