package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Node {
    private String line;
    private MapPoint coordinates;

//    public Node(String line, Pair<Double, Double> coordinates) {
//        this(line, new MapPoint(coordinates.getElem1(), coordinates.getElem2()));
//    }

    public Node(String line, MapPoint coordinates) {
        this.line = line;
        this.coordinates = coordinates;
    }

    public String getLine() {
        return line;
    }

    public MapPoint getCoordinates() {
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
                '}';
    }
    

    public double euclideanDistance(MapPoint p) {
        if (p == null) throw new NullPointerException("Invalid Point");
        return Math.sqrt(Math.pow(coordinates.getLat()-p.getLat(),2)+Math.pow(coordinates.getLong() - p.getLong(),2));
    }

    public double eculideanDistance(Node n) {
        return euclideanDistance(n.getCoordinates());
    }

}
