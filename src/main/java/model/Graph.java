package model;
import java.util.*;

public class Graph {
    public HashSet<Node> nodes = new HashSet<>(); //A ser remplazado con la data-struct correspondiente.
    public HashMap<Node, Set<Edge>> edges = new HashMap<>();
    private HashMap<Node,Integer> distances;
    private HashSet<Node> settled;
    private PriorityQueue<Node> unsettled;

    public void insertNode(Node node){
        nodes.add(node);
    }
    public void insertEdge(Node root ,Edge edge){
        edges.putIfAbsent(root,new HashSet<>());
        edges.get(root).add(edge);
    }

    Node findNearestPoint(MapPoint coordinates){
        throw new UnsupportedOperationException();
    }



    void printDijkstra(Node start, Node end){
        throw new UnsupportedOperationException();
    }

    //TODO chequear si esta funcion tiene que estar en Graph y no en Node
    //Dado un set de nodos y una coordenada espacial, encuentro el mas cercano
    public static Node closestToPoint (MapPoint coord, Set<Node> set){
        if (set.isEmpty()) return null;
        double minDist = Double.MAX_VALUE;
        Node closest = null;
        for (Node node:set){
            double dist = node.manhattanDist(coord);
            if (dist<minDist){
                closest=node;
                minDist=dist;
            }
        }
        return closest;
    }

    private class Cell extends HashSet<Node> {};

}
