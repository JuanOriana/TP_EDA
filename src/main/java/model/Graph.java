package model;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {
    private HashSet<Node> nodes = new HashSet<>(); //A ser remplazado con la data-struct correspondiente.
    private HashMap<Node, List<Pair<Node,Integer>>> edges; //Potencialmente se puede implementar que cada nodo contenga sus vecinos tambien
    private HashMap<Node,Integer> distances;
    private HashSet<Node> settled;
    private PriorityQueue<Node> unsettled;

    void insert(Node node){
        throw new UnsupportedOperationException();
    }

    Node findNearestPoint(Pair<Double,Double> coordinates){
        throw new UnsupportedOperationException();
    }

    void printDijkstra(Node start, Node end){
    throw new UnsupportedOperationException();
    }
}
